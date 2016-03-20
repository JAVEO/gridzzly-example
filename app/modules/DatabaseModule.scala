package modules

import com.google.inject.AbstractModule
import database.{TestDBConnection, DefaultDBConnection, DBConnection}

class DatabaseModule extends AbstractModule {
  def configure() = {
    bind(classOf[DBConnection]).to(classOf[DefaultDBConnection])
  }
}