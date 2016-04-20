package models

import database.DBConnection
import database.definitions.{UserCarsTable, CarsTable, UsersTable}
import grid._
import slick.lifted.TableQuery
import slick.driver.H2Driver.api._
import scala.concurrent.ExecutionContext.Implicits.global

@Gridzzly
case class UsersGrid() extends Grid[(UsersTable, Rep[Option[CarsTable]]), (User, Option[Car]), Seq]{
  val query = for {
    ((user, userCar), car) <- TableQuery[UsersTable].joinLeft(TableQuery[UserCarsTable]).on{
      case (user, userCar) => user.id === userCar.userId
    }.joinLeft(TableQuery[CarsTable]).on{
      case ((user, userCar), car) => userCar.map(_.carId) === car.id}
  } yield (user, car)

  val columns = Seq(
    GridColumn[UsersTable, String]("First name", user => user.firstName),
    GridColumn[UsersTable, String]("Last name", user => user.lastName),
    GridColumn[UsersTable, Int]("Status", user => user.status, Equals()),
    GridColumn[UsersTable, Int]("Gender", user => user.gender, Equals()),
    GridColumn[UsersTable, String]("Notes", user => user.notes),
    GridColumn[UsersTable, Long]("Salary", user => user.salary),
    GridOptionColumn[CarsTable, String]("Car brand", "carBrand", car => car.map(_.brand)),
    GridColumn[UsersTable, String]("Email", user => user.email))

  val defaultSortBy = DefaultGridColumn[UsersTable, Long](user => user.id.desc)
}