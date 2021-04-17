package com.fragnostic.conf.service.support

import org.scalatest.BeforeAndAfterEach
import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.should.Matchers

class ConfLongSupportTest extends AnyFunSpec with Matchers with BeforeAndAfterEach with ConfLongSupport {

  describe("Conf Long Support Test") {

    cacheGetLong("FOCUSED_CALENDAR_SERVICE_TODAY_MINUS_DAYS", 5) should be(56)

  }

}
