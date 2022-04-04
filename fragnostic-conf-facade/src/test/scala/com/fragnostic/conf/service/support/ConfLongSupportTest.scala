package com.fragnostic.conf.service.support

import com.fragnostic.conf.cache.service.CakeConfCacheService
import com.fragnostic.conf.facade.service.support.ConfLongSupport
import org.scalatest.BeforeAndAfterEach
import org.scalatest.funspec.AnyFunSpec
import org.slf4j.{ Logger, LoggerFactory }

class ConfLongSupportTest extends AnyFunSpec with BeforeAndAfterEach with ConfLongSupport {

  private[this] val logger: Logger = LoggerFactory.getLogger("ConfLongSupportTest")

  override def beforeEach(): Unit =
    CakeConfCacheService.confCacheService.delAllKeys fold (
      error => logger.error(s"beforeEach() - $error"),
      message => message)

  describe("***Conf Long Support Test***") {

    it("Can Retrieve Long") {
      assertResult(cacheGetLong("FOCUSED_CALENDAR_SERVICE_TODAY_MINUS_DAYS", 5))(56)
    }

  }

}
