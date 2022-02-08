package disruption.api

import com.akkaserverless.scalasdk.testkit.AkkaServerlessTestKit
import disruption.Main
import org.scalatest.BeforeAndAfterAll
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.matchers.should.Matchers
import org.scalatest.time.{Millis, Seconds, Span}
import org.scalatest.wordspec.AsyncWordSpec


// This class was initially generated based on the .proto definition by Akka Serverless tooling.
//
// As long as this file exists it will not be overwritten: you can maintain it yourself,
// or delete it so it is regenerated as needed.

class DisruptionServiceIntegrationSpec
    extends AsyncWordSpec
    with Matchers
    with BeforeAndAfterAll
    with ScalaFutures {

  implicit private val patience: PatienceConfig = PatienceConfig(Span(5, Seconds), Span(500, Millis))
  private val testKit = AkkaServerlessTestKit(Main.createAkkaServerless()).start()
  private val client = testKit.getGrpcClient(classOf[DisruptionService])

  "DisruptionService" must {
    "getDisruption should return what is created" in {
      val in = Disruption(disruptionId = "123", flight = "KL123", eventType = "test")
      for {
        created <- client.create(in)
        got <- client.getDisruption(GetDisruptionRequest(disruptionId = "123"))
      } yield {
        created shouldBe in
        got shouldBe in
        got.flight shouldBe "this should fail"
      }
    }

  }

  override def afterAll(): Unit = {
    testKit.stop()
    super.afterAll()
  }
}
