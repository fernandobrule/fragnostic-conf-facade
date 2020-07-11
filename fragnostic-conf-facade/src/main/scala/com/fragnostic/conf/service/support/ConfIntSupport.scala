package com.fragnostic.conf.service.support

import com.fragnostic.conf.base.service.support.TypesSupport
import com.fragnostic.conf.cache.service.{ CakeCacheService, CakeConfCacheService }
import com.fragnostic.conf.db.service.CakeConfDbService
import com.fragnostic.conf.env.service.CakeConfEnvService
import com.fragnostic.conf.props.service.CakeConfPropsService
import org.slf4j.{ Logger, LoggerFactory }

trait ConfIntSupport extends TypesSupport {

  private[this] val logger: Logger = LoggerFactory.getLogger(getClass.getName)

  def cacheGetInt(key: String, valueDefault: Int): Int =
    envGetInt(CakeConfCacheService.confServiceApi.getInt(key), key, valueDefault)

  private def envGetInt(ans: Either[String, Option[Int]], key: String, valueDefault: Int): Int =
    ans fold (
      error => envGetInt(key, valueDefault),
      opt => opt map (value => value) getOrElse {
        envGetInt(key, valueDefault)
      })

  private def envGetInt(key: String, valueDefault: Int): Int = {
    propsGetInt(CakeConfEnvService.confServiceApi.getInt(key), key) fold (
      error => {
        logger.error(s"envGetInt() -\n\t$error\n")
        valueDefault
      },
      opt => opt map (value => {
        CakeCacheService.cacheServiceApi.set(key, value.toString)
        value
      }) getOrElse {
        if (logger.isInfoEnabled) {
          logger.info(s"envGetInt() -\n\tooooops, we are about to return value default[$valueDefault] for key[$key]\n")
        }
        valueDefault
      })
  }

  private def propsGetInt(ans: Either[String, Option[Int]], key: String): Either[String, Option[Int]] =
    ans fold (
      error => dbGetInt(CakeConfPropsService.confServiceApi.getInt(key), key),
      opt => opt map (value => Right(Option(value))) getOrElse dbGetInt(CakeConfPropsService.confServiceApi.getInt(key), key))

  private def dbGetInt(ans: Either[String, Option[Int]], key: String): Either[String, Option[Int]] =
    ans fold (
      error => dbGetInt(key), //
      opt => opt map (value => Right(Option(value))) getOrElse dbGetInt(key) //
    )

  private def dbGetInt(key: String): Either[String, Option[Int]] =
    CakeConfDbService.confServiceApi.getInt(key)

}

