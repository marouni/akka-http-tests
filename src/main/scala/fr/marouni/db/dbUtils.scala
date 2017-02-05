package fr.marouni.db

import reactivemongo.api.MongoConnection
import reactivemongo.api.collections.bson.BSONCollection
import scala.concurrent.ExecutionContext.Implicits.global

import scala.concurrent.Future

/**
  * Created by abbass on 21/01/17.
  */
object dbUtils {

  def dbFromConnection(connection: MongoConnection, database: String, collection: String): Future[BSONCollection] =
    connection.database(database).
      map(_.collection(collection))

}
