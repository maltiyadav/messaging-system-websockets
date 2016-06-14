package models.dao

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

import play.api.libs.iteratee.Iteratee

import reactivemongo.bson.BSONDocument
import reactivemongo.api.collections.bson.BSONCollection

import models.database.DatabaseConnection

object QueryBuilder extends DatabaseConnection{

  def getDocumentsByQuery(collectionname: String, query: BSONDocument): Future[List[BSONDocument]] = {
    /*val cursor = collection(collectionname).find(query).cursor[BSONDocument]
    val futureresult: Future[List[BSONDocument]] = cursor.collect[List]()
    futureresult*/
    Future(Nil)
  }

  def insertDocuments(collectionname: String, documents: BSONDocument) {
    /*collection(collectionname).insert(documents)*/
  }

}
