package api

import models.UsersDto
import org.scalatest.FunSpec
import org.scalatest.mock.MockitoSugar

import org.scalatestplus.play._
import play.api.libs.json.{JsResult, JsSuccess}

import play.api.mvc._
import play.api.test._
import play.api.test.Helpers._

class PrepareDb extends FunSpec with Results with MockitoSugar with OneAppPerSuite with TestUtils{
  describe("Request Users#seeds"){
    describe("creates seeds properly"){
      it("creates seeds"){
        val response = controller.seeds().apply(FakeRequest("GET", "/api/seeds"))
        val result = contentAsString(response)
        result shouldBe """"Success""""
      }
    }
  }
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
}
