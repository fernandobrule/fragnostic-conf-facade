package com.fragnostic.conf.facade.service.support

import com.fragnostic.conf.base.service.support.TypesSupport
import com.fragnostic.conf.cache.service.CakeConfCacheService
import com.fragnostic.conf.env.service.CakeConfEnvService
import com.fragnostic.conf.props.service.CakeConfPropsService

trait ConfBigDecimalSupport extends TypesSupport with CacheSupport {

  def cacheGetBigDecimal(key: String, valueDefault: BigDecimal): BigDecimal =
    CakeConfCacheService.confCacheService.getBigDecimal(key) fold (
      error => envGetBigDecimal(key, valueDefault),
      value => value //
    )

  private def envGetBigDecimal(key: String, valueDefault: BigDecimal): BigDecimal =
    CakeConfEnvService.confEnvService.getBigDecimal(key) fold (
      error => propsGetBigDecimal(key, valueDefault),
      value => addToCache(key, value) //
    )

  private def propsGetBigDecimal(key: String, valueDefault: BigDecimal): BigDecimal =
    CakeConfPropsService.confServiceApi.getBigDecimal(key) fold (
      error => valueDefault,
      value => addToCache(key, value) //
    )

}

