package api

import controllers.api.Users
import database.TestDBConnection
import database.definitions.UsersTable
import grid.FilterColumns
import models.{User, UsersDto}
import org.scalatest.Matchers
import play.api.libs.json.{JsSuccess, JsResult}
import play.api.test.FakeRequest
import play.api.test.Helpers._
import slick.lifted.TableQuery
import slick.driver.H2Driver.api._

trait TestUtils extends Matchers{
  lazy val dBConnection = new TestDBConnection
  lazy val controller = new Users()(dBConnection)
  implicit lazy val carFormat = controller.carDtoFormatCombinator
  implicit lazy val userWithCarDtoFormat = controller.usersWithCarDtoFormatCombinator
  implicit lazy val userDtoFormat = controller.userDtoFormatCombinator
  implicit lazy val usersDtoFormat = controller.usersDtoFormatCombinator
  val numberOfAllUsers = 121
  val usersTable = TableQuery[UsersTable]

  def clearDb = {
    dBConnection.db.run(
      sqlu"""DELETE FROM "user_cars";
             DELETE FROM "users";
             DELETE FROM "cars";
          """
    )
  }

  def createUser(email: String, firstName: String, lastName: String, status: Int, gender: Int, salary: Long, notes: String) = {
    dBConnection.db.run(
      (usersTable returning usersTable.map(_.id)) += User(
        None,
        email,
        firstName,
        lastName,
        status,
        gender,
        salary,
        notes
      )
    )
  }

  def usersListRequest(page: Int, perPage: Int, sortBy: String, sortDir: String, filterColumns: FilterColumns = FilterColumns(Map())) = {
    val result = controller.list(page, perPage, sortBy, sortDir, filterColumns).apply(FakeRequest("GET", "/api/users"))
    contentAsJson(result).validate(usersDtoFormat)
  }

  def isSortedByField[A](jsResult: JsResult[UsersDto], sortBy: JsResult[UsersDto] => Seq[A])(implicit ordering: Ordering[A]) = {
    jsResult match {
      case js: JsSuccess[UsersDto] => {
        val sortByFieldVals = sortBy(jsResult)

        js.get.count shouldBe numberOfAllUsers
        sortByFieldVals shouldBe sorted
      }
      case _ => fail("Request responded with an error")
    }
  }

  def isPaginatedProperly(page: Int, perPage: Int) = {
    for{
      allUsers <- usersListRequest(1, numberOfAllUsers, "", "")
      paginatedUsers <- usersListRequest(page, perPage, "", "")
      from = (page-1)*perPage
      until = from + perPage
      slicedAllUsers = allUsers.usersWithCars.slice(from, until)
    } yield {
      paginatedUsers.count shouldEqual numberOfAllUsers
      slicedAllUsers shouldEqual paginatedUsers.usersWithCars
    }
  }
}
