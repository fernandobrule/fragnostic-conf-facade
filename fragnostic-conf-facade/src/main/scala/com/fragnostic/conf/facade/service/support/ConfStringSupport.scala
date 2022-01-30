package com.fragnostic.conf.facade.service.support

import com.fragnostic.conf.base.service.support.TypesSupport
import com.fragnostic.conf.cache.service.CakeConfCacheService
import com.fragnostic.conf.db.service.CakeConfDbService
import com.fragnostic.conf.env.service.CakeConfEnvService
import com.fragnostic.conf.props.service.CakeConfPropsService
import org.slf4j.{ Logger, LoggerFactory }

import java.util.Locale

trait ConfStringSupport extends TypesSupport {

  private[this] val logger: Logger = LoggerFactory.getLogger("ConfStringSupport")

  def cacheGetString(key: String, valueDefault: String): String =
    envGetString(CakeConfCacheService.confCacheService.getString(key), key, valueDefault)

  def cacheGetString(locale: Locale, key: String, valueDefault: String): String =
    envGetString(locale, key, valueDefault, CakeConfCacheService.confCacheService.getString(locale, key))

  private def envGetString(ans: Either[String, String], key: String, valueDefault: String): String =
    ans fold (
      error => envGetString(key, valueDefault),
      value => value //
    )

  private def envGetString(locale: Locale, key: String, valueDefault: String, ans: Either[String, String]): String =
    ans fold (
      error => envGetString(locale, key, valueDefault),
      value => value //
    )

  private def envGetString(key: String, valueDefault: String): String =
    propsGetString(CakeConfEnvService.confEnvService.getString(key), key) fold (
      error => {
        logger.error(s"envGetString() -\n\t$error\n")
        valueDefault
      },
      opt => opt //
    )

  private def envGetString(locale: Locale, key: String, valueDefault: String): String =
    propsGetString(locale, key, CakeConfEnvService.confEnvService.getString(locale, key)) fold (
      error => {
        logger.error(s"envGetString() -\n\t$error\n")
        valueDefault
      },
      opt => opt //
    )

  private def propsGetString(ans: Either[String, String], key: String): Either[String, String] =
    ans fold ( //
      error => dbGetString(key, CakeConfPropsService.confServiceApi.getString(key)),
      value => Right(value) //
    )

  private def propsGetString(locale: Locale, key: String, ans: Either[String, String]): Either[String, String] =
    ans fold ( //
      error => dbGetString(locale, key, CakeConfPropsService.confServiceApi.getString(key)), //
      value => Right(value) //
    )

  private def dbGetString(key: String, ans: Either[String, String]): Either[String, String] =
    ans fold (
      error => dbGetString(key), //
      value => Right(value) //
    )

  private def dbGetString(locale: Locale, key: String, ans: Either[String, String]): Either[String, String] =
    ans fold (
      error => dbGetString(key), //
      value => Right(value) //
    )

  private def dbGetString(key: String): Either[String, String] =
    CakeConfDbService.confDbService.getString(key)

  private def dbGetString(locale: Locale, key: String): Either[String, String] =
    CakeConfDbService.confDbService.getString(locale, key)

}

