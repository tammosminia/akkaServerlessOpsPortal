package disruption

import com.akkaserverless.scalasdk.SideEffect
import com.akkaserverless.scalasdk.action.Action
import com.akkaserverless.scalasdk.action.ActionCreationContext

// This class was initially generated based on the .proto definition by Akka Serverless tooling.
//
// As long as this file exists it will not be overwritten: you can maintain it yourself,
// or delete it so it is regenerated as needed.

/** An action. */
class ActionsImpl(creationContext: ActionCreationContext) extends AbstractActionsAction {

  override def flightUpdates(flightUpdatesRequest: FlightUpdatesRequest): Action.Effect[Disruptions] = {
    val ds = flightUpdatesRequest.flights.flatMap(flightToDisruption)
    val updates = ds.map { d =>
      //SideEffect moet een DeferredCall zijn (een functie van een Service)
      SideEffect(components.disruption.create(d))
    }
    val response = Disruptions(disruptions = ds)
    effects.reply(response).addSideEffects(updates)
  }

  private def flightToDisruption(f: Flight): Option[api.Disruption] = {
    if (f.isCancelled) {
      Some(new api.Disruption(eventType = "cancellation", flight = f.flightKey))
    } else {
      None
    }
  }

}

