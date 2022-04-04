package com.fragnostic.conf.facade.service.support

import com.fragnostic.conf.base.service.support.TypesSupport
import com.fragnostic.conf.cache.service.CakeConfCacheService
import com.fragnostic.conf.env.service.CakeConfEnvService
import com.fragnostic.conf.props.service.CakeConfPropsService

trait ConfLongSupport extends TypesSupport with CacheSupport {

  def cacheGetLong(key: String, valueDefault: Long): Long =
    CakeConfCacheService.confCacheService.getLong(key) fold (
      error => envGetLong(key, valueDefault),
      value => value //
    )

  private def envGetLong(key: String, valueDefault: Long): Long = {
    CakeConfEnvService.confEnvService.getLong(key) fold (
      error => propsGetLong(key, valueDefault),
      value => addToCache(key, value) //
    )
  }

  private def propsGetLong(key: String, valueDefault: Long): Long =
    CakeConfPropsService.confServiceApi.getLong(key) fold (
      error => valueDefault,
      value => addToCache(key, value) //
    )

}

