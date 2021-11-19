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

  private def envGetShort(ans: Either[String, Option[Short]], key: String, valueDefault: Short): Short =
    ans fold (
      error => envGetShort(key, valueDefault),
      opt => opt map (value => value) getOrElse {
        envGetShort(key, valueDefault)
      })

  private def envGetShort(key: String, valueDefault: Short): Short = {
    propsGetShort(CakeConfEnvService.confEnvService.getShort(key), key) fold (
      error => {
        logger.error(s"envGetShort() -\n\t$error\n")
        valueDefault
      },
      opt => opt map (value => {
        CakeConfCacheService.confCacheService.set(key, value.toString)
        value
      }) getOrElse {
        logger.warn(s"envGetShort() -\n\tooooops, we are about to return value default[$valueDefault] for key[$key] that does not exists\n")
        valueDefault
      })
  }

  private def propsGetShort(ans: Either[String, Option[Short]], key: String): Either[String, Option[Short]] =
    ans fold (
      error => dbGetShort(CakeConfPropsService.confServiceApi.getShort(key), key),
      opt => opt map (value => Right(Option(value))) getOrElse dbGetShort(CakeConfPropsService.confServiceApi.getShort(key), key))

  private def dbGetShort(ans: Either[String, Option[Short]], key: String): Either[String, Option[Short]] =
    ans fold (
      error => dbGetShort(key), //
      opt => opt map (value => Right(Option(value))) getOrElse dbGetShort(key) //
    )

  private def dbGetShort(key: String): Either[String, Option[Short]] =
    CakeConfDbService.confDbService.getShort(key)

}

