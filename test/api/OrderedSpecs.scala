package api

import org.scalatest.Suites

class OrderedSpecs extends Suites(
  new PrepareDb,
  new UsersListPaginationSpec,
  new UsersListSortingSpec,
  new UsersListFilteringSpec
)
