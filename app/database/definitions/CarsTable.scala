package database.definitions

import models.Car
import slick.driver.PostgresDriver.api._

class CarsTable(tag: Tag) extends Table[Car](tag, "cars"){
  def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
  def brand = column[String]("brand")
  def model = column[String]("model")
  def color = column[String]("color")
  def price = column[Int]("price")

  def * = (id.?, brand, model, color, price) <> ((Car.apply _).tupled, Car.unapply)
}