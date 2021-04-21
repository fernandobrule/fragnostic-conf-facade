package com.fragnostic.conf.service.support

import com.fragnostic.conf.base.service.support.TypesSupport
import com.fragnostic.conf.cache.service.CakeConfCacheService
import com.fragnostic.conf.db.service.CakeConfDbService
import com.fragnostic.conf.env.service.CakeConfEnvService
import com.fragnostic.conf.props.service.CakeConfPropsService
import org.slf4j.{ Logger, LoggerFactory }

trait ConfBooleanSupport extends TypesSupport {

  private[this] val logger: Logger = LoggerFactory.getLogger(getClass.getName)

  def cacheGetBoolean(key: String, valueDefault: Boolean): Boolean =
    envGetBoolean(CakeConfCacheService.confCacheServiceApi.getBoolean(key), key, valueDefault)

  private def envGetBoolean(ans: Either[String, Option[Boolean]], key: String, valueDefault: Boolean): Boolean =
    ans fold (
      error => envGetBoolean(key, valueDefault),
      opt => opt map (value => value) getOrElse {
        envGetBoolean(key, valueDefault)
      })

  private def envGetBoolean(key: String, valueDefault: Boolean): Boolean = {
    propsGetBoolean(CakeConfEnvService.confServiceApi.getBoolean(key), key) fold (
      error => {
        logger.error(s"envGetBoolean() -\n\t$error\n")
        valueDefault
      },
      opt => opt map (value => {
        CakeConfCacheService.confCacheServiceApi.set(key, value.toString)
        value
      }) getOrElse {
        if (logger.isInfoEnabled) {
          logger.info(s"envGetBoolean() -\n\tooooops, we are about to return value default[$valueDefault] for key[$key]\n")
        }
        valueDefault
      })
  }

  private def propsGetBoolean(ans: Either[String, Option[Boolean]], key: String): Either[String, Option[Boolean]] =
    ans fold (
      error => dbGetBoolean(CakeConfPropsService.confServiceApi.getBoolean(key), key),
      opt => opt map (value => Right(Option(value))) getOrElse dbGetBoolean(CakeConfPropsService.confServiceApi.getBoolean(key), key))

  private def dbGetBoolean(ans: Either[String, Option[Boolean]], key: String): Either[String, Option[Boolean]] =
    ans fold (
      error => dbGetBoolean(key), //
      opt => opt map (value => Right(Option(value))) getOrElse dbGetBoolean(key) //
    )

  private def dbGetBoolean(key: String): Either[String, Option[Boolean]] =
    CakeConfDbService.confServiceApi.getBoolean(key)

}

