package com.fragnostic.conf.facade.service.support

import com.fragnostic.conf.base.service.support.TypesSupport
import com.fragnostic.conf.cache.service.CakeConfCacheService
import com.fragnostic.conf.db.service.CakeConfDbService
import com.fragnostic.conf.env.service.CakeConfEnvService
import com.fragnostic.conf.props.service.CakeConfPropsService
import org.slf4j.{ Logger, LoggerFactory }

trait ConfBooleanSupport extends TypesSupport {

  private[this] val logger: Logger = LoggerFactory.getLogger("ConfBooleanSupport")

  def cacheGetBoolean(key: String, valueDefault: Boolean): Boolean =
    envGetBoolean(CakeConfCacheService.confCacheService.getBoolean(key), key, valueDefault)

  private def envGetBoolean(ans: Either[String, Boolean], key: String, valueDefault: Boolean): Boolean =
    ans fold (
      error => envGetBoolean(key, valueDefault),
      opt => opt //
    )

  private def envGetBoolean(key: String, valueDefault: Boolean): Boolean = {
    propsGetBoolean(CakeConfEnvService.confEnvService.getBoolean(key), key) fold (
      error => {
        logger.error(s"envGetBoolean() -\n\t$error\n")
        valueDefault
      },
      opt => opt //
    )
  }

  private def propsGetBoolean(ans: Either[String, Boolean], key: String): Either[String, Boolean] =
    ans fold (
      error => dbGetBoolean(CakeConfPropsService.confServiceApi.getBoolean(key), key),
      value => Right(value) //
    )

  private def dbGetBoolean(ans: Either[String, Boolean], key: String): Either[String, Boolean] =
    ans fold (
      error => dbGetBoolean(key), //
      value => Right(value) //
    )

  private def dbGetBoolean(key: String): Either[String, Boolean] =
    CakeConfDbService.confDbService.getBoolean(key)

}

