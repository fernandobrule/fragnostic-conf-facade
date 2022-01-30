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

  private def envGetLong(ans: Either[String, Long], key: String, valueDefault: Long): Long =
    ans fold (
      error => envGetLong(key, valueDefault),
      opt => opt //
    )

  private def envGetLong(key: String, valueDefault: Long): Long = {
    propsGetLong(CakeConfEnvService.confEnvService.getLong(key), key) fold (
      error => {
        logger.error(s"envGetLong() -\n\t$error\n")
        valueDefault
      },
      opt => opt //
    )
  }

  private def propsGetLong(ans: Either[String, Long], key: String): Either[String, Long] =
    ans fold (
      error => dbGetLong(CakeConfPropsService.confServiceApi.getLong(key), key),
      opt => Right(opt) //
    )

  private def dbGetLong(ans: Either[String, Long], key: String): Either[String, Long] =
    ans fold (
      error => dbGetLong(key), //
      value => Right(value) //
    )

  private def dbGetLong(key: String): Either[String, Long] =
    CakeConfDbService.confDbService.getLong(key)

}

