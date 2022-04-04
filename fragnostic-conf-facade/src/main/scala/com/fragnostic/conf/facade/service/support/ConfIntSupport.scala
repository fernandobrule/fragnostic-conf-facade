package com.fragnostic.conf.facade.service.support

import com.fragnostic.conf.base.service.support.TypesSupport
import com.fragnostic.conf.cache.service.CakeConfCacheService
import com.fragnostic.conf.env.service.CakeConfEnvService
import com.fragnostic.conf.props.service.CakeConfPropsService

trait ConfIntSupport extends TypesSupport with CacheSupport {

  def cacheGetInt(key: String, valueDefault: Int): Int =
    CakeConfCacheService.confCacheService.getInt(key) fold (
      error => envGetInt(key, valueDefault),
      value => value //
    )

  private def envGetInt(key: String, valueDefault: Int): Int =
    CakeConfEnvService.confEnvService.getInt(key) fold (
      error => propsGetInt(key, valueDefault),
      value => addToCache(key, value) //
    )

  private def propsGetInt(key: String, valueDefault: Int): Int =
    CakeConfPropsService.confServiceApi.getInt(key) fold (
      error => valueDefault,
      value => addToCache(key, value) //
    )

}

