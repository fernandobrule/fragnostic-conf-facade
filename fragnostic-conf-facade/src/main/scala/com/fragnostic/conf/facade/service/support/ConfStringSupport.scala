package com.fragnostic.conf.facade.service.support

import com.fragnostic.conf.base.service.support.{ KeyComposeSupport, TypesSupport }
import com.fragnostic.conf.cache.service.CakeConfCacheService
import com.fragnostic.conf.env.service.CakeConfEnvService
import com.fragnostic.conf.props.service.CakeConfPropsService

import java.util.Locale

trait ConfStringSupport extends TypesSupport with CacheSupport with KeyComposeSupport {

  def cacheGetString(key: String, valueDefault: String): String =
    CakeConfCacheService.confCacheService.getString(key) fold (
      error => envGetString(key, valueDefault),
      value => value //
    )

  private def envGetString(key: String, valueDefault: String): String =
    CakeConfEnvService.confEnvService.getString(key) fold (
      error => propsGetString(key, valueDefault),
      value => addToCache(key, value) //
    )

  private def propsGetString(key: String, valueDefault: String): String =
    CakeConfPropsService.confServiceApi.getString(key) fold (
      error => valueDefault,
      value => addToCache(key, value) //
    )

  def cacheGetString(locale: Locale, key: String, valueDefault: String): String =
    CakeConfCacheService.confCacheService.getString(locale, key) fold (
      error => envGetString(locale, key, valueDefault),
      value => value //
    )

  private def envGetString(locale: Locale, key: String, valueDefault: String): String =
    CakeConfEnvService.confEnvService.getString(locale, key) fold (
      error => propsGetString(locale, key, valueDefault),
      value => addToCache(compose(locale, key), value) //
    )

  private def propsGetString(locale: Locale, key: String, valueDefault: String): String =
    CakeConfPropsService.confServiceApi.getString(key) fold (
      error => valueDefault,
      value => addToCache(compose(locale, key), value) //
    )

}

