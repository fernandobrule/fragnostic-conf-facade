package com.fragnostic.conf.facade.service.support

import com.fragnostic.conf.base.service.support.TypesSupport
import com.fragnostic.conf.cache.service.CakeConfCacheService
import com.fragnostic.conf.env.service.CakeConfEnvService
import com.fragnostic.conf.props.service.CakeConfPropsService

import java.util.Locale

trait ConfStringSupport extends TypesSupport {

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
      error => valueDefault,
      opt => opt //
    )

  private def envGetString(locale: Locale, key: String, valueDefault: String): String =
    propsGetString(locale, key, CakeConfEnvService.confEnvService.getString(locale, key)) fold (
      error => valueDefault,
      opt => opt //
    )

  private def propsGetString(ans: Either[String, String], key: String): Either[String, String] =
    ans fold ( //
      error => CakeConfPropsService.confServiceApi.getString(key),
      value => Right(value) //
    )

  private def propsGetString(locale: Locale, key: String, ans: Either[String, String]): Either[String, String] =
    ans fold ( //
      error => CakeConfPropsService.confServiceApi.getString(key), //
      value => Right(value) //
    )

}

