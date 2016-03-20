package api

import org.scalatest.FunSpec
import org.scalatest.mock.MockitoSugar
import org.scalatestplus.play._
import play.api.mvc._

class UsersListSortingSpec extends FunSpec with Results with MockitoSugar with OneAppPerSuite with TestUtils{
  describe("Request Users#list") {
    describe("Sorts properly") {
      it("sort by users firstName asc"){
        val resultJson = usersListRequest(1, numberOfAllUsers, "user.firstName", "asc")
        isSortedByField[String](resultJson, _.get.usersWithCars.map(_.user.firstName))
      }
      it("sort by users firstName desc"){
        val result = usersListRequest(1, numberOfAllUsers, "user.firstName", "desc")
        isSortedByField[String](result, _.get.usersWithCars.map(_.user.firstName).reverse)
      }
      it("sort by users lastName asc"){
        val result = usersListRequest(1, numberOfAllUsers, "user.lastName", "asc")
        isSortedByField[String](result, _.get.usersWithCars.map(_.user.lastName))
      }
      it("sort by users lastName desc"){
        val result = usersListRequest(1, numberOfAllUsers, "user.lastName", "desc")
        isSortedByField[String](result, _.get.usersWithCars.map(_.user.lastName).reverse)
      }
      it("sort by users status desc"){
        val result = usersListRequest(1, numberOfAllUsers, "user.status", "desc")
        isSortedByField[Int](result, _.get.usersWithCars.map(_.user.status.id).reverse)
      }
      it("sort by users status asc"){
        val result = usersListRequest(1, numberOfAllUsers, "user.status", "asc")
        isSortedByField[Int](result, _.get.usersWithCars.map(_.user.status.id))
      }
      it("sort by users gender desc"){
        val result = usersListRequest(1, numberOfAllUsers, "user.gender", "desc")
        isSortedByField[Int](result, _.get.usersWithCars.map(_.user.gender.id).reverse)
      }
      it("sort by users gender asc"){
        val result = usersListRequest(1, numberOfAllUsers, "user.gender", "asc")
        isSortedByField[Int](result, _.get.usersWithCars.map(_.user.gender.id))
      }
      it("sort by users notes asc"){
        val result = usersListRequest(1, numberOfAllUsers, "user.notes", "asc")
        isSortedByField[String](result, _.get.usersWithCars.map(_.user.notes))
      }
      it("sort by users notes desc"){
        val result = usersListRequest(1, numberOfAllUsers, "user.notes", "desc")
        isSortedByField[String](result, _.get.usersWithCars.map(_.user.notes).reverse)
      }
      it("sort by users salary desc"){
        val result = usersListRequest(1, numberOfAllUsers, "user.salary", "desc")
        isSortedByField[Long](result, _.get.usersWithCars.map(_.user.salary).reverse)
      }
      it("sort by users salary asc"){
        val result = usersListRequest(1, numberOfAllUsers, "user.salary", "asc")
        isSortedByField[Long](result, _.get.usersWithCars.map(_.user.salary))
      }
      it("sort by car brand asc"){
        val result = usersListRequest(1, numberOfAllUsers, "carBrand", "asc")
        isSortedByField[Option[String]](result, _.get.usersWithCars.map(_.car.map(_.brand)))
      }
      it("sort by car brand desc"){
        val result = usersListRequest(1, numberOfAllUsers, "carBrand", "desc")
        isSortedByField[Option[String]](result, _.get.usersWithCars.map(_.car.map(_.brand)).reverse)
      }
      it("sort by users email desc"){
        val result = usersListRequest(1, numberOfAllUsers, "user.email", "desc")
        isSortedByField[String](result, _.get.usersWithCars.map(_.user.email).reverse)
      }
      it("sort by users email asc"){
        val result = usersListRequest(1, numberOfAllUsers, "user.email", "asc")
        isSortedByField[String](result, _.get.usersWithCars.map(_.user.email))
      }
      it("sort by users id desc by default"){
        val result = usersListRequest(1, numberOfAllUsers, "", "")
        isSortedByField[Long](result, _.get.usersWithCars.map(_.user.id).reverse)
      }
    }
  }
}