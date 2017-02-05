package fr.marouni.db

trait MongoAccess {

  private val driver = new reactivemongo.api.MongoDriver
  val mongoConnection = driver.connection(List("localhost"))
}
