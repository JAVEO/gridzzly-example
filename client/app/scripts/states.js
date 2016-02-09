'use strict';

app.config(function($stateProvider, $urlRouterProvider) {
  $urlRouterProvider.otherwise("/users");

  $stateProvider
    .state('users', {
      url: "/users",
      controller: 'UsersCtrl',
      templateUrl: "/views/users.html"
    });
});
