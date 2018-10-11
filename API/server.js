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

app.use(express.static('images')); //Serves resources from public folder

app.use(bodyParser.urlencoded({ extended: true }));
app.use(bodyParser.json());

var routes = require('./api/routes/SurveyRoutes'); //importing route
routes(app); //register the route

var braintree = require("braintree");

var gateway = braintree.connect({
  environment: braintree.Environment.Sandbox,
  merchantId: "m25hx22pr8nv3xd6",
  publicKey: "hnjzyx6rf2v8r4yt",
  privateKey: "12701cdd49b7fdf4a6f21e697e7983bf"
});

app.get("/client_token", function (req, res) {
      gateway.clientToken.generate({}, function (err, response) {
        res.send(response.clientToken);
      });
    });

app.post("/checkout", function (req, res) {
          var nonceFromTheClient = req.body.nonce;
          var amt = req.body.amount;
          gateway.transaction.sale({
        amount: amt,
        paymentMethodNonce: nonceFromTheClient,
        options: {
        submitForSettlement: true
      }
    }, function (err, result) {
      console.log(result);
      res.send({nonce:nonceFromTheClient,  res:result, a: amt});
    });
        });

app.listen(port);

console.log('Advanced MAD Project 2 RESTful API server started on: ' + port);
