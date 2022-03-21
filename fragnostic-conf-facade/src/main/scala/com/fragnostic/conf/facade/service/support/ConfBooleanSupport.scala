package com.fragnostic.conf.facade.service.support

import com.fragnostic.conf.base.service.support.TypesSupport
import com.fragnostic.conf.cache.service.CakeConfCacheService
import com.fragnostic.conf.env.service.CakeConfEnvService
import com.fragnostic.conf.props.service.CakeConfPropsService

trait ConfBooleanSupport extends TypesSupport {

  def cacheGetBoolean(key: String, valueDefault: Boolean): Boolean =
    envGetBoolean(CakeConfCacheService.confCacheService.getBoolean(key), key, valueDefault)

  private def envGetBoolean(ans: Either[String, Boolean], key: String, valueDefault: Boolean): Boolean =
    ans fold (
      error => envGetBoolean(key, valueDefault),
      opt => opt //
    )

  private def envGetBoolean(key: String, valueDefault: Boolean): Boolean = {
    propsGetBoolean(CakeConfEnvService.confEnvService.getBoolean(key), key) fold (
      error => valueDefault,
      opt => opt //
    )
  }

  private def propsGetBoolean(ans: Either[String, Boolean], key: String): Either[String, Boolean] =
    ans fold (
      error => CakeConfPropsService.confServiceApi.getBoolean(key),
      value => Right(value) //
    )

}

