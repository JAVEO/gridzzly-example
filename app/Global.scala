import filters.CorsHeadersFilter
import play.api._
import play.api.mvc.WithFilters

object Global extends WithFilters(CorsHeadersFilter) with GlobalSettings {
}