package api

import grid.FilterColumns
import org.scalatest.{BeforeAndAfter, FunSpec}
import org.scalatest.mock.MockitoSugar

import org.scalatestplus.play._

import play.api.mvc._
import scala.concurrent.duration._
import scala.concurrent.Await
import scala.concurrent.ExecutionContext.Implicits.global

class UsersListFilteringSpec extends FunSpec with Results with MockitoSugar with OneAppPerSuite with BeforeAndAfter with TestUtils{
  before{
    val users = for {
      _ <- clearDb
      _ <- createUser("Frank@javeo.eu", "Frank", "Kowalski", 1, 1, 2, "notes1")
      _ <- createUser("Frank2@javeo.eu", "Frank2", "Kowalski2", 2, 111, 2, "notes2")
      _ <- createUser("Frank3@javeo.eu", "Frank3", "Kowalski3", 3, 112, 2, "notes3")
    } yield ()

    Await.result(users, 10 seconds)
  }

  describe("Filtering") {
    it("filters properly with email param"){
      for{
        allUsers   <- usersListRequest(1, numberOfAllUsers, "", "", FilterColumns(Map("user.email" -> "Frank")))
        secondUser <- usersListRequest(1, numberOfAllUsers, "", "", FilterColumns(Map("user.email" -> "Frank2")))
        thirdUser  <- usersListRequest(1, numberOfAllUsers, "", "", FilterColumns(Map("user.email" -> "Frank3")))
        none       <- usersListRequest(1, numberOfAllUsers, "", "", FilterColumns(Map("user.email" -> "Aladeen")))
      } yield {
        allUsers.usersWithCars.length shouldEqual 3
        secondUser.usersWithCars.length shouldEqual 1
        thirdUser.usersWithCars.length shouldEqual 1
        none.usersWithCars.length shouldEqual 0
      }
    }

    it("filters properly with email param and sort by lastName"){
      for{
        allUsers   <- usersListRequest(1, numberOfAllUsers, "user.lastName", "asc", FilterColumns(Map("user.email" -> "Frank")))
        secondUser <- usersListRequest(1, numberOfAllUsers, "", "", FilterColumns(Map("user.email" -> "Frank2")))
        thirdUser  <- usersListRequest(1, numberOfAllUsers, "", "", FilterColumns(Map("user.email" -> "Frank3")))
        none       <- usersListRequest(1, numberOfAllUsers, "", "", FilterColumns(Map("user.email" -> "Aladeen")))
      } yield {
        allUsers.usersWithCars.length shouldEqual 3
        allUsers.usersWithCars.map(_.user.lastName) shouldBe sorted
        secondUser.usersWithCars.length shouldEqual 1
        thirdUser.usersWithCars.length shouldEqual 1
        none.usersWithCars.length shouldEqual 0
      }
    }

    it("filters properly with firstName param"){
      for{
        allUsers   <- usersListRequest(1, numberOfAllUsers, "", "", FilterColumns(Map("user.firstName" -> "Frank")))
        secondUser <- usersListRequest(1, numberOfAllUsers, "", "", FilterColumns(Map("user.firstName" -> "Frank2")))
        thirdUser  <- usersListRequest(1, numberOfAllUsers, "", "", FilterColumns(Map("user.firstName" -> "Frank3")))
        none       <- usersListRequest(1, numberOfAllUsers, "", "", FilterColumns(Map("user.firstName" -> "Aladeen")))
      } yield {
        allUsers.usersWithCars.length shouldEqual 3
        secondUser.usersWithCars.length shouldEqual 1
        thirdUser.usersWithCars.length shouldEqual 1
        none.usersWithCars.length shouldEqual 0
      }
    }

    it("filters properly with lastName param"){
      for{
        allUsers   <- usersListRequest(1, numberOfAllUsers, "", "", FilterColumns(Map("user.lastName" -> "Kowalski")))
        secondUser <- usersListRequest(1, numberOfAllUsers, "", "", FilterColumns(Map("user.lastName" -> "Kowalski2")))
        thirdUser  <- usersListRequest(1, numberOfAllUsers, "", "", FilterColumns(Map("user.lastName" -> "Kowalski3")))
        none       <- usersListRequest(1, numberOfAllUsers, "", "", FilterColumns(Map("user.lastName" -> "Aladeen")))
      } yield {
        allUsers.usersWithCars.length shouldEqual 3
        secondUser.usersWithCars.length shouldEqual 1
        secondUser.usersWithCars.head.user.firstName shouldEqual "Frank2"
        thirdUser.usersWithCars.length shouldEqual 1
        thirdUser.usersWithCars.head.user.firstName shouldEqual "Frank3"
        none.usersWithCars.length shouldEqual 0
      }
    }

    it("filters properly with gender param"){
      for{
        allUsers <- usersListRequest(1, numberOfAllUsers, "", "", FilterColumns(Map("user.gender" -> "1")))
        secondUser <-usersListRequest(1, numberOfAllUsers, "", "", FilterColumns(Map("user.gender" -> "111")))
        thirdUser <-usersListRequest(1, numberOfAllUsers, "", "", FilterColumns(Map("user.gender" -> "112")))
        none <-usersListRequest(1, numberOfAllUsers, "", "", FilterColumns(Map("user.gender" -> "11")))
      } yield {
        allUsers.usersWithCars.length shouldEqual 1
        allUsers.usersWithCars.head.user.firstName shouldEqual "Frank"
        secondUser.usersWithCars.length shouldEqual 1
        secondUser.usersWithCars.head.user.firstName shouldEqual "Frank2"
        thirdUser.usersWithCars.length shouldEqual 1
        thirdUser.usersWithCars.head.user.firstName shouldEqual "Frank3"
        none.usersWithCars.length shouldEqual 0
      }
    }

    it("filters properly with status param"){
      for{
        allUsers <- usersListRequest(1, numberOfAllUsers, "", "", FilterColumns(Map("user.status" -> "1")))
        secondUser <-usersListRequest(1, numberOfAllUsers, "", "", FilterColumns(Map("user.status" -> "2")))
        thirdUser <-usersListRequest(1, numberOfAllUsers, "", "", FilterColumns(Map("user.status" -> "3")))
        none <-usersListRequest(1, numberOfAllUsers, "", "", FilterColumns(Map("user.status" -> "11")))
      } yield {
        allUsers.usersWithCars.length shouldEqual 1
        allUsers.usersWithCars.head.user.firstName shouldEqual "Frank"
        secondUser.usersWithCars.length shouldEqual 1
        secondUser.usersWithCars.head.user.firstName shouldEqual "Frank2"
        thirdUser.usersWithCars.length shouldEqual 1
        thirdUser.usersWithCars.head.user.firstName shouldEqual "Frank3"
        none.usersWithCars.length shouldEqual 0
      }
    }
  }
}