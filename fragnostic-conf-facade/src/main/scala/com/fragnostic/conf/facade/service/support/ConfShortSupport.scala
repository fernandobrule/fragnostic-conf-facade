package com.fragnostic.conf.facade.service.support

import com.fragnostic.conf.base.service.support.TypesSupport
import com.fragnostic.conf.cache.service.CakeConfCacheService
import com.fragnostic.conf.env.service.CakeConfEnvService
import com.fragnostic.conf.props.service.CakeConfPropsService

trait ConfShortSupport extends TypesSupport with CacheSupport {

  def cacheGetShort(key: String, valueDefault: Short): Short =
    CakeConfCacheService.confCacheService.getShort(key) fold (
      error => envGetShort(key, valueDefault),
      value => value //
    )

  private def envGetShort(key: String, valueDefault: Short): Short =
    CakeConfEnvService.confEnvService.getShort(key) fold (
      error => propsGetShort(key, valueDefault),
      value => addToCache(key, value) //
    )

  private def propsGetShort(key: String, valueDefault: Short): Short =
    CakeConfPropsService.confServiceApi.getShort(key) fold (
      error => valueDefault,
      value => addToCache(key, value) //
    )

}

