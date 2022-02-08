package disruption

import com.akkaserverless.scalasdk.SideEffect
import com.akkaserverless.scalasdk.action.Action
import com.akkaserverless.scalasdk.action.ActionCreationContext
import com.google.protobuf.Empty
import disruption.api.Disruption

import java.util.UUID
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

// This class was initially generated based on the .proto definition by Akka Serverless tooling.
//
// As long as this file exists it will not be overwritten: you can maintain it yourself,
// or delete it so it is regenerated as needed.

/** An action. */
class ActionsImpl(creationContext: ActionCreationContext) extends AbstractActionsAction {
  /** Handler for "CreateNew". */
  override def createNew(disruption: Disruption): Action.Effect[Disruption] = {
    val id = UUID.randomUUID().toString
    val created: Future[Disruption] = components.disruption.create(disruption.withDisruptionId(id)).execute()
    val effect: Future[Action.Effect[Disruption]] = created.map(c => effects.reply(c))
    effects.asyncEffect(effect)
  }

  /** Handler for "FlightUpdates". */
  override def flightUpdates(flightUpdatesRequest: FlightUpdatesRequest): Action.Effect[Disruptions] = {
    val ds = flightUpdatesRequest.flights.flatMap(flightToDisruption)
    val response = Disruptions(disruptions = ds)
    val updates = ds.map { d =>
      SideEffect(components.actionsImpl.createNew(d))
    }
    effects
      .reply(response)
      .addSideEffects(updates)
  }

  private def flightToDisruption(f: Flight): Option[api.Disruption] = {
    if (f.isCancelled) {
      Some(new api.Disruption(eventType = "cancellation", flight = f.flightKey))
    } else {
      None
    }
  }
}

