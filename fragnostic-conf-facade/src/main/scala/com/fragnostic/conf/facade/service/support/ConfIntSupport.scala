package com.fragnostic.conf.facade.service.support

import com.fragnostic.conf.base.service.support.TypesSupport
import com.fragnostic.conf.cache.service.CakeConfCacheService
import com.fragnostic.conf.env.service.CakeConfEnvService
import com.fragnostic.conf.props.service.CakeConfPropsService

trait ConfIntSupport extends TypesSupport {

  def cacheGetInt(key: String, valueDefault: Int): Int =
    envGetInt(CakeConfCacheService.confCacheService.getInt(key), key, valueDefault)

  private def envGetInt(ans: Either[String, Int], key: String, valueDefault: Int): Int =
    ans fold (
      error => envGetInt(key, valueDefault),
      opt => opt //
    )

  private def envGetInt(key: String, valueDefault: Int): Int =
    propsGetInt(CakeConfEnvService.confEnvService.getInt(key), key) fold (
      error => valueDefault,
      opt => opt //
    )

  private def propsGetInt(ans: Either[String, Int], key: String): Either[String, Int] =
    ans fold (
      error => CakeConfPropsService.confServiceApi.getInt(key),
      opt => Right(opt) //
    )

}

