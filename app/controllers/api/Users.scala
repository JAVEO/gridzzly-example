package controllers.api

import com.google.inject.{Inject, Singleton}
import database.{DBConnection, DefaultDBConnection}
import database.definitions.{UserCarsTable, CarsTable, UsersTable}
import faker._
import grid.{GridConditions, FilterColumns}
import models.User.GendersCollection.Gender
import models.User.UserStatusesCollection.UserStatus
import models._
import org.joda.time.DateTime
import play.api.libs.json.Json
import play.api.mvc._
import play.api.mvc.Results._
import slick.lifted.TableQuery
import scala.concurrent.ExecutionContext.Implicits.global
import slick.driver.H2Driver.api._

import scala.concurrent.Future
import scala.util.Random

@Singleton
class Users @Inject()(implicit val dbConfig: DBConnection) extends Controller{
  val usersTable = TableQuery[UsersTable]
  val carsTable = TableQuery[CarsTable]
  val userCarsTable = TableQuery[UserCarsTable]
  implicit val userFormatCombinator = Json.format[User]
  implicit val userStatusCombinator = Json.format[UserStatus]
  implicit val genderCombinator = Json.format[Gender]
  implicit val userDtoFormatCombinator = Json.format[UserDto]
  implicit val carDtoFormatCombinator = Json.format[Car]
  implicit val usersWithCarDtoFormatCombinator = Json.format[UserWithCarDto]
  implicit val usersDtoFormatCombinator = Json.format[UsersDto]

  def list(page: Int, perPage: Int, sortBy: String, sortDir: String, filterBy: FilterColumns) = Action.async { implicit request =>
    val gridConditions = GridConditions(page, perPage, sortBy, sortDir, filterBy)
    val searchResults: (Future[Seq[(User, Option[Car])]], Future[Int]) = UsersGrid().run(gridConditions)

    val result = for {
      users <- searchResults._1
      count <- searchResults._2
    } yield UsersDto(users.map{case (user, car) => UserWithCarDto(user.toUserDto, car)}, count)

    result.map(response => Ok(Json.toJson(response)))
  }

  def seeds () = Action.async {implicit request =>
    val usersIds = Future.sequence{
      0.to(120).map(_ => {
        dbConfig.db.run(
          (usersTable returning usersTable.map(_.id)) += User(
            None,
            Internet.email,
            Name.first_name,
            Name.last_name,
            Random.nextInt(3) + 1,
            Random.nextInt(2) + 1,
            Random.nextInt(1000) + 1,
            faker.Lorem.sentence(7)
          )
        )})
    }

    val carsIds = Future.sequence{0.to(50).map(_ => {
      dbConfig.db.run(
        (carsTable returning carsTable.map(_.id)) += Car(
          None,
          Company.name,
          Lorem.sentence(5),
          Lorem.sentence(10),
          Random.nextInt(100) + 1
        )
      )
    })}

    (for {
      uId <- usersIds
      cId <- carsIds
      first50UserIds = uId.take(uId.length -10)
      _ <- Future.traverse(first50UserIds)(userId => {
        dbConfig.db.run(
          userCarsTable  += UserCar(
            userId,
            cId.toList(Random.nextInt(cId.length))
          )
        )})
    } yield ()).map(response => Ok(Json.toJson("Success")))
  }
}
