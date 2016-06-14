package models.database

import reactivemongo.api._
import scala.concurrent.ExecutionContext.Implicits.global

trait DatabaseConnection{
  val driver = new MongoDriver
  val connection = driver.connection(List("localhost"))
  val db = connection.database("test")

  /*
   * Get a JSONCollection (a Collection implementation that is designed to work
   * with JsObject, Reads and Writes.)
   * Note that the `collection` is not a `val`, but a `def`. We do _not_ store
   * the collection reference to avoid potential problems in development with
   * Play hot-reloading.
   */

  def collection(name: String) = db.map(_(name))
}