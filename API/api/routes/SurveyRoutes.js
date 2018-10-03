'use strict';
module.exports = function(app) {
  var DiscountHandlers = require('../controllers/DiscountController.js');


  app.route('/list')
      .post(DiscountHandlers.list);

  app.route('/get_all')
      .post(DiscountHandlers.get_all);


    };
