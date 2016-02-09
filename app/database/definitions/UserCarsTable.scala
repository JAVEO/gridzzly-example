package database.definitions

import models.UserCar
import slick.driver.PostgresDriver.api._

class UserCarsTable(tag: Tag) extends Table[UserCar](tag, "user_cars"){
  def userId = column[Long]("user_id")
  def carId = column[Long]("car_id")
  def * = (userId, carId) <> ((UserCar.apply _).tupled, UserCar.unapply)

  def pk = primaryKey("pk_users_cars", (userId, carId))
  def userFK = foreignKey("user_fk", userId, TableQuery[UsersTable])(_.id)
  def carFK = foreignKey("car_fk", carId, TableQuery[CarsTable])(_.id)
}