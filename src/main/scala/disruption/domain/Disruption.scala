package disruption.domain

import com.akkaserverless.scalasdk.valueentity.ValueEntity
import com.akkaserverless.scalasdk.valueentity.ValueEntityContext
import disruption.api

// This class was initially generated based on the .proto definition by Akka Serverless tooling.
//
// As long as this file exists it will not be overwritten: you can maintain it yourself,
// or delete it so it is regenerated as needed.

/** A value entity. */
class Disruption(context: ValueEntityContext) extends AbstractDisruption {
  override def emptyState: DisruptionState = new DisruptionState()

  override def create(currentState: DisruptionState, disruption: api.Disruption): ValueEntity.Effect[api.Disruption] = {
    effects
      .updateState(convertToDomain(disruption))
      .thenReply(disruption)
  }

  def convertToDomain(d: api.Disruption): DisruptionState =
    DisruptionState(
      disruptionId = d.disruptionId,
      eventType = d.eventType,
      flight = d.flight
    )


  override def getDisruption(currentState: DisruptionState, getDisruptionRequest: api.GetDisruptionRequest): ValueEntity.Effect[api.Disruption] =
    if (currentState.disruptionId == "") {
      effects.error(s"Disruption ${getDisruptionRequest.disruptionId} does not exist!")
    } else {
      effects.reply(convertToApi(currentState))
    }

  def convertToApi(d: DisruptionState): api.Disruption =
    api.Disruption(
      disruptionId = d.disruptionId,
      eventType = d.eventType,
      flight = d.flight
    )

}

