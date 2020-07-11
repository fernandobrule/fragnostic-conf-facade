package com.fragnostic.conf.service.support

import com.fragnostic.conf.base.service.support.TypesSupport
import com.fragnostic.conf.cache.service.{ CakeCacheService, CakeConfCacheService }
import com.fragnostic.conf.db.service.CakeConfDbService
import com.fragnostic.conf.env.service.CakeConfEnvService
import com.fragnostic.conf.props.service.CakeConfPropsService
import org.slf4j.{ Logger, LoggerFactory }

trait ConfStringSupport extends TypesSupport {

  private[this] val logger: Logger = LoggerFactory.getLogger(getClass.getName)

  def cacheGetString(key: String, valueDefault: String): String =
    envGetString(CakeConfCacheService.confServiceApi.getString(key), key, valueDefault)

  private def envGetString(ans: Either[String, Option[String]], key: String, valueDefault: String): String =
    ans fold (
      error => envGetString(key, valueDefault),
      opt => opt map (value => value) getOrElse {
        envGetString(key, valueDefault)
      })

  private def envGetString(key: String, valueDefault: String): String = {
    propsGetString(CakeConfEnvService.confServiceApi.getString(key), key) fold (
      error => {
        logger.error(s"envGetString() -\n\t$error\n")
        valueDefault
      },
      opt => opt map (value => {
        CakeCacheService.cacheServiceApi.set(key, value.toString)
        value
      }) getOrElse {
        if (logger.isInfoEnabled) {
          logger.info(s"envGetString() -\n\tooooops, we are about to return value default[$valueDefault] for key[$key]\n")
        }
        valueDefault
      })
  }

  private def propsGetString(ans: Either[String, Option[String]], key: String): Either[String, Option[String]] =
    ans fold (
      error => dbGetString(CakeConfPropsService.confServiceApi.getString(key), key),
      opt => opt map (value => Right(Option(value))) getOrElse dbGetString(CakeConfPropsService.confServiceApi.getString(key), key))

  private def dbGetString(ans: Either[String, Option[String]], key: String): Either[String, Option[String]] =
    ans fold (
      error => dbGetString(key), //
      opt => opt map (value => Right(Option(value))) getOrElse dbGetString(key) //
    )

  private def dbGetString(key: String): Either[String, Option[String]] =
    CakeConfDbService.confServiceApi.getString(key)

}

