package com.fragnostic.conf.facade.service.support

import com.fragnostic.conf.base.service.support.TypesSupport
import com.fragnostic.conf.cache.service.CakeConfCacheService
import com.fragnostic.conf.env.service.CakeConfEnvService
import com.fragnostic.conf.props.service.CakeConfPropsService

trait ConfLongSupport extends TypesSupport {

  def cacheGetLong(key: String, valueDefault: Long): Long =
    envGetLong(CakeConfCacheService.confCacheService.getLong(key), key, valueDefault)

  private def envGetLong(ans: Either[String, Long], key: String, valueDefault: Long): Long =
    ans fold (
      error => envGetLong(key, valueDefault),
      opt => opt //
    )

  private def envGetLong(key: String, valueDefault: Long): Long = {
    propsGetLong(CakeConfEnvService.confEnvService.getLong(key), key) fold (
      error => valueDefault,
      opt => opt //
    )
  }

  private def propsGetLong(ans: Either[String, Long], key: String): Either[String, Long] =
    ans fold (
      error => CakeConfPropsService.confServiceApi.getLong(key),
      opt => Right(opt) //
    )

}

