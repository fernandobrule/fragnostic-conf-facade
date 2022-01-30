package com.fragnostic.conf.facade.service.support

import com.fragnostic.conf.base.service.support.TypesSupport
import com.fragnostic.conf.cache.service.CakeConfCacheService
import com.fragnostic.conf.db.service.CakeConfDbService
import com.fragnostic.conf.env.service.CakeConfEnvService
import com.fragnostic.conf.props.service.CakeConfPropsService
import org.slf4j.{ Logger, LoggerFactory }

trait ConfShortSupport extends TypesSupport {

  private[this] val logger: Logger = LoggerFactory.getLogger("ConfShortSupport")

  def cacheGetShort(key: String, valueDefault: Short): Short =
    envGetShort(CakeConfCacheService.confCacheService.getShort(key), key, valueDefault)

  private def envGetShort(ans: Either[String, Short], key: String, valueDefault: Short): Short =
    ans fold (
      error => envGetShort(key, valueDefault),
      value => value //
    )

  private def envGetShort(key: String, valueDefault: Short): Short = {
    propsGetShort(CakeConfEnvService.confEnvService.getShort(key), key) fold (
      error => {
        logger.error(s"envGetShort() -\n\t$error\n")
        valueDefault
      },
      value => value //
    )
  }

  private def propsGetShort(ans: Either[String, Short], key: String): Either[String, Short] =
    ans fold (
      error => dbGetShort(CakeConfPropsService.confServiceApi.getShort(key), key),
      value => Right(value) //
    )

  private def dbGetShort(ans: Either[String, Short], key: String): Either[String, Short] =
    ans fold (
      error => dbGetShort(key), //
      value => Right(value) //
    )

  private def dbGetShort(key: String): Either[String, Short] =
    CakeConfDbService.confDbService.getShort(key)

}

