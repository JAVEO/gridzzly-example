package api

import models.UsersDto
import org.scalatest.FunSpec
import org.scalatest.mock.MockitoSugar

import org.scalatestplus.play._
import play.api.libs.json.JsSuccess

import play.api.mvc._
class UsersListPaginationSpec extends FunSpec with Results with MockitoSugar with OneAppPerSuite with TestUtils{
  describe("Request Users#list") {
    it("returns proper count amount") {
      val resultJson = usersListRequest(1, 121, "user.firstName", "asc")
      resultJson match {
        case js: JsSuccess[UsersDto] => {
          val usersDto = js.get
          usersDto.count shouldBe numberOfAllUsers
        }
        case _ => fail("Request responded with an error")
      }
    }
  }

  describe("Pagination") {
    it("paginates properly with reasonable page = 2 and perPage = 20"){
      isPaginatedProperly(2, 20)
    }

    it("paginates properly with reasonable values: page = 3 and perPage = 20"){
      isPaginatedProperly(3, 20)
    }

    it("paginates properly with reasonable values: page = 4 and perPage = 20"){
      isPaginatedProperly(4, 20)
    }

    it("paginates properly with reasonable values: page = 5 and perPage = 20"){
      isPaginatedProperly(5, 20)
    }

    it("paginates properly with reasonable values: page = 6 and perPage = 20"){
      isPaginatedProperly(6, 20)
    }

    it("paginates properly with too big page value: page = 10 and perPage = 20"){
      isPaginatedProperly(10, 20)
    }

    it("paginates properly with too big perPage value: page = 1 and perPage = 10000"){
      isPaginatedProperly(1, 10000)
    }
  }
}
