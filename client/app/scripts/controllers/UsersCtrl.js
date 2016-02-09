'use strict';

app.controller('UsersCtrl', function ($scope, UsersFactory, SeedsFactory) {
  $scope.rowCollection = {};
  $scope.amountPerPage = 10;
  $scope.currentPageIndex = 0;

  $scope.generateUsers = function(){
    SeedsFactory.query();
  };

  $scope.callServer = function(tableState){
    var pagination = tableState.pagination;
    var start = pagination.start || 0;
    var page = start + 1;
    var sortDir = tableState.sort.reverse ? "desc": "asc";
    var sortBy = tableState.sort.predicate;
    var predicate = tableState.search.predicateObject;
    var filterByParamNames = predicate ? Object.keys(predicate): [];

    var params = {
      page: page,
      perPage: $scope.amountPerPage,
      sortBy: sortBy,
      sortDir: sortDir
    };

    _.forEach(filterByParamNames, function(name){
      return params["filterBy["+name+"]"] = predicate[name];
    });


    UsersFactory.query(params, function(res){
      console.log(res.usersWithCars)
      $scope.displayedCollection = res.usersWithCars;
      tableState.pagination.numberOfPages = Math.ceil(res.count/$scope.amountPerPage);
      $scope.currentPageIndex = page - 1;
    });
  };
});
