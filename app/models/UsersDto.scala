package models

import models.User.GendersCollection.Gender
import models.User.UserStatusesCollection.UserStatus
import org.joda.time.DateTime

case class UsersDto(usersWithCars: Seq[UserWithCarDto], count: Int)

case class UserDto(id: Long,
                   email: String,
                   firstName: String,
                   lastName: String,
                   status: UserStatus,
                   gender: Gender,
                   notes: String,
                   salary: Long)

case class UserWithCarDto(user: UserDto, car: Option[Car])
