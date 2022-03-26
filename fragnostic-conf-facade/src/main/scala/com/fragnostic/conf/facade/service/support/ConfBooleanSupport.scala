package com.fragnostic.conf.facade.service.support

import com.fragnostic.conf.base.service.support.TypesSupport
import com.fragnostic.conf.cache.service.CakeConfCacheService
import com.fragnostic.conf.env.service.CakeConfEnvService
import com.fragnostic.conf.props.service.CakeConfPropsService

trait ConfBooleanSupport extends TypesSupport with CacheSupport {

  def cacheGetBoolean(key: String, valueDefault: Boolean): Boolean =
    CakeConfCacheService.confCacheService.getBoolean(key) fold (
      error => envGetBoolean(key, valueDefault),
      value => value //
    )

  private def envGetBoolean(key: String, valueDefault: Boolean): Boolean = {
    CakeConfEnvService.confEnvService.getBoolean(key) fold (
      error => propsGetBoolean(key, valueDefault),
      value => addToCache(key, value) //
    )
  }

  private def propsGetBoolean(key: String, valueDefault: Boolean): Boolean =
    CakeConfPropsService.confServiceApi.getBoolean(key) fold (
      error => valueDefault,
      value => addToCache(key, value) //
    )

}

