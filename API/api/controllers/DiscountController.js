'user strict';
var mongoose = require('mongoose');

exports.list = function(req, res) {
  mongoose.connection.collection("results").find({'region':req.body.region}).toArray(function(err, data) {

      res.send(data);
      })
  };

  exports.get_all = function(req, res) {
    mongoose.connection.collection("results").find().toArray(function(err, data) {

        res.send(data);
})
};
