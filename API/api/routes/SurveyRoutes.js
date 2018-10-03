'use strict';
module.exports = function(app) {
  var DiscountHandlers = require('../controllers/DiscountController.js');


  app.route('/list')
      .post(DiscountHandlers.list);

  app.route('/get_image')
      .post(DiscountHandlers.get_image);

  
    };
