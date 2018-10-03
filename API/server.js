var express = require('express'),
    app = express(),
    port = process.env.PORT || 3000;
    mongoose = require('mongoose'),
    // Survey = require('./api/models/SurveyModel'),
    // Admin = require('./api/models/AdminModel'),
    // Patient = require('./api/models/PatientModel'),
    // jsonwebtoken = require("jsonwebtoken");
    bodyParser = require('body-parser');

mongoose.Promise = global.Promise;
mongoose.connect('mongodb+srv://amad123:amad123@cluster0-mnw6b.mongodb.net/records?retryWrites=true');
var db = mongoose.connection;

app.use(bodyParser.urlencoded({ extended: true }));
app.use(bodyParser.json());

var routes = require('./api/routes/SurveyRoutes'); //importing route
routes(app); //register the route

app.listen(port);

console.log('Advanced MAD Project 2 RESTful API server started on: ' + port);
