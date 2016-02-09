'use strict';

var app = angular.module('clientApp', ['ui.router', 'ngResource', 'smart-table']);

app.constant("myConfig", {
  "SERVER_URL": "http://localhost:9001/api"
});
