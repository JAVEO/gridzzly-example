'use strict';

app.factory("UsersFactory", function($resource, myConfig) {
  return $resource(myConfig.SERVER_URL + "/users?page=:page&perPage=:perPage&sortBy=:sortBy&sortDir=:sortDir", {}, {
    query: { method: "GET", isArray: false, params: { page: '@page', perPage: '@perPage', sortBy: '@sortBy', sortDir: '@sortDir'}}
  });
});

app.factory("SeedsFactory", function($resource, myConfig) {
  return $resource(myConfig.SERVER_URL + "/seeds", {}, {
    query: { method: "GET", isArray: true}
  });
});
