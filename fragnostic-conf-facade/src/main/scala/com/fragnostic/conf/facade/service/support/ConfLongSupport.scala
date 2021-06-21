package com.fragnostic.conf.facade.service.support

import com.fragnostic.conf.base.service.support.TypesSupport
import com.fragnostic.conf.cache.service.CakeConfCacheService
import com.fragnostic.conf.db.service.CakeConfDbService
import com.fragnostic.conf.env.service.CakeConfEnvService
import com.fragnostic.conf.props.service.CakeConfPropsService
import org.slf4j.{ Logger, LoggerFactory }

trait ConfLongSupport extends TypesSupport {

  private[this] val logger: Logger = LoggerFactory.getLogger("ConfLongSupport")

  def cacheGetLong(key: String, valueDefault: Long): Long =
    envGetLong(CakeConfCacheService.confCacheService.getLong(key), key, valueDefault)

  private def envGetLong(ans: Either[String, Option[Long]], key: String, valueDefault: Long): Long =
    ans fold (
      error => envGetLong(key, valueDefault),
      opt => opt map (value => value) getOrElse {
        envGetLong(key, valueDefault)
      })

  private def envGetLong(key: String, valueDefault: Long): Long = {
    propsGetLong(CakeConfEnvService.confEnvService.getLong(key), key) fold (
      error => {
        logger.error(s"envGetLong() -\n\t$error\n")
        valueDefault
      },
      opt => opt map (value => {
        CakeConfCacheService.confCacheService.set(key, value.toString)
        value
      }) getOrElse {
        if (logger.isInfoEnabled) {
          logger.info(s"envGetLong() -\n\tooooops, we are about to return value default[$valueDefault] for key[$key]\n")
        }
        valueDefault
      })
  }

  private def propsGetLong(ans: Either[String, Option[Long]], key: String): Either[String, Option[Long]] =
    ans fold (
      error => dbGetLong(CakeConfPropsService.confServiceApi.getLong(key), key),
      opt => opt map (value => Right(Option(value))) getOrElse dbGetLong(CakeConfPropsService.confServiceApi.getLong(key), key))

  private def dbGetLong(ans: Either[String, Option[Long]], key: String): Either[String, Option[Long]] =
    ans fold (
      error => dbGetLong(key), //
      opt => opt map (value => Right(Option(value))) getOrElse dbGetLong(key) //
    )

  private def dbGetLong(key: String): Either[String, Option[Long]] =
    CakeConfDbService.confDbService.getLong(key)

}

