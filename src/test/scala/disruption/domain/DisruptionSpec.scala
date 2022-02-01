package disruption.domain

import com.akkaserverless.scalasdk.testkit.ValueEntityResult
import com.akkaserverless.scalasdk.valueentity.ValueEntity
import com.google.protobuf.empty.Empty
import disruption.api
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class DisruptionSpec
    extends AnyWordSpec
    with Matchers {

  "Disruption" must {

    "have example test that can be removed" in {
      val testKit = DisruptionTestKit(new Disruption(_))
      // use the testkit to execute a command
      // and verify final updated state:
      // val result = testKit.someOperation(SomeRequest)
      // verify the response
      // val actualResponse = result.getReply()
      // actualResponse shouldBe expectedResponse
      // verify the final state after the command
      // testKit.currentState() shouldBe expectedState
    }

    "handle command Create" in {
      val testKit = DisruptionTestKit(new Disruption(_))
      // val result = testKit.create(api.Disruption(...))
    }

    "handle command GetDisruption" in {
      val testKit = DisruptionTestKit(new Disruption(_))
      // val result = testKit.getDisruption(api.GetDisruptionRequest(...))
    }

  }
}
