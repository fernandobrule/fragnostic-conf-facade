package com.fragnostic.conf.facade.service.support

import com.fragnostic.conf.base.service.support.TypesSupport
import com.fragnostic.conf.cache.service.CakeConfCacheService
import com.fragnostic.conf.env.service.CakeConfEnvService
import com.fragnostic.conf.props.service.CakeConfPropsService

trait ConfShortSupport extends TypesSupport {

  def cacheGetShort(key: String, valueDefault: Short): Short =
    envGetShort(CakeConfCacheService.confCacheService.getShort(key), key, valueDefault)

  private def envGetShort(ans: Either[String, Short], key: String, valueDefault: Short): Short =
    ans fold (
      error => envGetShort(key, valueDefault),
      value => value //
    )

  private def envGetShort(key: String, valueDefault: Short): Short = {
    propsGetShort(CakeConfEnvService.confEnvService.getShort(key), key) fold (
      error => valueDefault,
      value => value //
    )
  }

  private def propsGetShort(ans: Either[String, Short], key: String): Either[String, Short] =
    ans fold (
      error => CakeConfPropsService.confServiceApi.getShort(key),
      value => Right(value) //
    )

}

