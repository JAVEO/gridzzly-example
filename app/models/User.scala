package models

import models.User.{GendersCollection, UserStatusesCollection}

case class User( id: Option[Long],
                 email: String,
                 firstName: String,
                 lastName: String,
                 status: Int,
                 gender: Int,
                 salary: Long,
                 notes: String){

  def toUserDto: UserDto = {
    UserDto(id.get, email, firstName, lastName,
      UserStatusesCollection.findStatusById(status),
      GendersCollection.findGenderById(gender),
      notes, salary)
  }
}

object User{
  object UserStatusesCollection {
    sealed case class UserStatus(name: String, id: Int)

    object Active extends UserStatus("Active", 1)
    object Blocked extends UserStatus("Blocked", 2)
    object Premium extends UserStatus("Premium", 3)

    val userStatuses = Seq(Active, Blocked, Premium)

    def findStatusById(id: Int): UserStatus = {
      userStatuses.find(_.id == id).head
    }
  }

  object GendersCollection {
    sealed case class Gender(name: String, id: Int)

    object Male extends Gender("male", 1)
    object Female extends Gender("female", 2)
    object Test1 extends Gender("test1", 111)
    object Test2 extends Gender("test2", 112)

    val genders = Seq(Male, Female, Test1, Test2)

    def findGenderById(id: Int): Gender = {
      genders.find(_.id == id).head
    }
  }
}
