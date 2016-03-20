package database

import com.google.inject.{Inject, Singleton}
import play.api.db.slick.DatabaseConfigProvider
import slick.driver.JdbcProfile
import play.api.inject.ApplicationLifecycle
import slick.driver.H2Driver.api._
import play.api.Play.current
import scala.concurrent.Future

trait DBConnection {
  val db: Database
}

@Singleton
class DefaultDBConnection @Inject()(dbConfigProvider: DatabaseConfigProvider, lifecycle: ApplicationLifecycle) extends DBConnection {
  val dbConfig = dbConfigProvider.get[JdbcProfile]
  val db: Database = dbConfig.db

  lifecycle.addStopHook{ () =>
    Future.successful(db.close())
  }
}

@Singleton
class TestDBConnection extends DBConnection {
  val dbConfig = DatabaseConfigProvider.get[JdbcProfile]("test")
  val db: Database = dbConfig.db
}