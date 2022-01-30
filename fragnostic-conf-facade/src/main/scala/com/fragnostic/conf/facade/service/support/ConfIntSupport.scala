package com.fragnostic.conf.facade.service.support

import com.fragnostic.conf.base.service.support.TypesSupport
import com.fragnostic.conf.cache.service.CakeConfCacheService
import com.fragnostic.conf.db.service.CakeConfDbService
import com.fragnostic.conf.env.service.CakeConfEnvService
import com.fragnostic.conf.props.service.CakeConfPropsService
import org.slf4j.{ Logger, LoggerFactory }

@SerialVersionUID(234234234L)
trait ConfIntSupport extends TypesSupport {

  private[this] val logger: Logger = LoggerFactory.getLogger("ConfIntSupport")

  def cacheGetInt(key: String, valueDefault: Int): Int =
    envGetInt(CakeConfCacheService.confCacheService.getInt(key), key, valueDefault)

  private def envGetInt(ans: Either[String, Int], key: String, valueDefault: Int): Int =
    ans fold (
      error => envGetInt(key, valueDefault),
      opt => opt //
    )

  private def envGetInt(key: String, valueDefault: Int): Int =
    propsGetInt(CakeConfEnvService.confEnvService.getInt(key), key) fold (
      error => {
        logger.error(s"envGetInt() -\n\t$error\n")
        valueDefault
      },
      opt => opt //
    )

  private def propsGetInt(ans: Either[String, Int], key: String): Either[String, Int] =
    ans fold (
      error => dbGetInt(CakeConfPropsService.confServiceApi.getInt(key), key),
      opt => Right(opt) //
    )

  private def dbGetInt(ans: Either[String, Int], key: String): Either[String, Int] =
    ans fold (
      error => dbGetInt(key), //
      opt => Right(opt) //
    )

  private def dbGetInt(key: String): Either[String, Int] =
    CakeConfDbService.confDbService.getInt(key)

}

