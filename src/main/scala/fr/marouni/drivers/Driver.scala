package fr.marouni.drivers

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer
import fr.marouni.db.MongoAccess
import fr.marouni.models.{Flow, Stage}
import fr.marouni.routes.FlowRoutes
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
import spray.json.DefaultJsonProtocol._
import spray.json._

object Driver extends MongoAccess {

  def main(args: Array[String]): Unit = {

    // needed to run the route
    implicit val system = ActorSystem ()
    implicit val materializer = ActorMaterializer ()
    // needed for the future map/flatmap in the end
    implicit val executionContext = system .dispatcher

    import fr.marouni.models.formats.FlowFormats._

    val stage: Stage = Stage("1", "Stage_1", "cat /tmp/file")
    val flow = Flow("1", "Flow_1", Seq(stage))
    println(flow.toJson)

    val bindingFuture = Http().bindAndHandle (FlowRoutes.routes, "localhost" , 8080)
    println (s"Server online at http://localhost:8080/\nPress RETURN to stop...")
      io.StdIn.readLine() // let it run until user presses return
    bindingFuture
      .flatMap (_.unbind ()) // trigger unbinding from the port
      .onComplete (_ ⇒ system .terminate ()) // and shutdown when done

  }

}
