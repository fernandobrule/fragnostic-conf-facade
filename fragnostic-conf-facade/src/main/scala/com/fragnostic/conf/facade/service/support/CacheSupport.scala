package com.fragnostic.conf.facade.service.support

import com.fragnostic.conf.cache.service.CakeConfCacheService

trait CacheSupport {

  def addToCache[T](key: String, value: T): T = {
    CakeConfCacheService.confCacheService.set(key, value.toString)
    value
  }

}
