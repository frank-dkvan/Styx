package com.styx.frameworks.kafka.serialization

import com.styx.common.BaseSpec
import com.styx.domain.events.{BaseKafkaEvent, BaseNotificationEvent}
import org.joda.time.DateTime

class NotificationEventParserSpec extends BaseSpec {

  val testPayload: Map[String, String] = Map("EVENT_TIME" -> "2017-01-01T00:00:00.012+01:00", "LOGIN " -> "1", "CARD_ID" -> "25", "EVENT" -> "test")
  val referenceDateTime:DateTime = DateTime.parse("2017-01-01T00:00:00.012+01:00")
  val referenceNotificationEvent = BaseNotificationEvent(referenceDateTime, testPayload)

  val testKafkaEvent: BaseKafkaEvent = BaseKafkaEvent("testTopic", testPayload)
  val parser: NotificationEventParser.type = NotificationEventParser

  val generatedNotificationEvent: BaseNotificationEvent = parser.map(testKafkaEvent).get

  "NotificationEventParser" should "return a notification event." in {
    generatedNotificationEvent should be(referenceNotificationEvent)
  }
  
  it should "have an eventTime that is equal to the date/time january 1st 2017 at 00:00:00.012H." in {
    withClue(generatedNotificationEvent) {
      generatedNotificationEvent.eventTime should be(referenceDateTime)
    }
  }

  it should "contain the payload used for event creation." in {
    withClue(generatedNotificationEvent) {
      generatedNotificationEvent.payload should be(testPayload)
    }
  }

}
