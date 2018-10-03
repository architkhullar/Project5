'user strict';
var mongoose = require('mongoose');

exports.list = function(req, res) {
  mongoose.connection.collection("results").find({'region':req.body.region}).toArray(function(err, data) {

      res.send(data);
      })
  };

  exports.get_image = function(req, res) {
    mongoose.connection.collection("fs.files").findOne({'filename':req.body.photo},function(err, data) {

      mongoose.connection.collection("fs.chunks").findOne({'files_id': data._id},function(err, ph) {
        res.send(ph.data);
});
});
};
