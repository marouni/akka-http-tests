package fr.marouni.models.formats

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import fr.marouni.models.{Flow, Stage}
import reactivemongo.bson.{BSONDocumentReader, BSONDocumentWriter, Macros}
import spray.json.DefaultJsonProtocol

object FlowFormats extends SprayJsonSupport with DefaultJsonProtocol {

  implicit val stageFormat = jsonFormat3(Stage)
  implicit val flowFormat = jsonFormat3(Flow)

  implicit def stageWriter: BSONDocumentWriter[Stage] = Macros.writer[Stage]
  implicit def flowWriter: BSONDocumentWriter[Flow] = Macros.writer[Flow]
  implicit def stageReader: BSONDocumentReader[Stage] = Macros.reader[Stage]
  implicit def flowReader: BSONDocumentReader[Flow] = Macros.reader[Flow]
}
