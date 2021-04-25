package com.fragnostic.conf.service.support

import org.scalatest.BeforeAndAfterEach
import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.should.Matchers
import com.fragnostic.conf.cache.service.CakeConfCacheService
import com.fragnostic.conf.facade.service.support.ConfLongSupport

class ConfLongSupportTest extends AnyFunSpec with Matchers with BeforeAndAfterEach with ConfLongSupport {

  override def beforeEach(): Unit =
    CakeConfCacheService.confCacheService.delAllKeys fold (
      error => throw new IllegalStateException(error),
      message => message)

  describe("***Conf Long Support Test***") {

    it("Can Retrieve Long") {
      cacheGetLong("FOCUSED_CALENDAR_SERVICE_TODAY_MINUS_DAYS", 5) should be(56)
    }

  }

}
