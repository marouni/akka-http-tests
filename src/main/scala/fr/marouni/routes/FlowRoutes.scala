package fr.marouni.routes

import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Directives._
import fr.marouni.db.{MongoAccess, dbUtils}
import fr.marouni.models.Flow
import fr.marouni.models.formats.FlowFormats._
import reactivemongo.api.commands.WriteResult
import reactivemongo.bson.BSONDocument

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.util.{Failure, Success}

object FlowRoutes extends MongoAccess {

  val flowCollection = dbUtils.dbFromConnection(mongoConnection, "mydb", "flows")

  val routes =
    path("flows") {
      get {
        complete(StatusCodes.OK, flowCollection.flatMap(_.find(BSONDocument.empty).cursor[Flow]().collect[List]()))
      }
    } ~
      path("flows") {
        post {
          entity(as[Flow]) { flow =>
            val savedFlow: Future[WriteResult] = flowCollection.flatMap(_.insert[Flow](flow))
            onComplete(savedFlow){ tryWr =>
              tryWr match {
                case Success(wr) => complete(StatusCodes.OK, "")
                case Failure(e) => complete(StatusCodes.BadRequest, e.getCause)
              }

            }
          }
        }
      }

}
