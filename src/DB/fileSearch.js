const db = require("../../config/database");

const fileSearch = (name, callback) => {
  db.query("select * from s3file where fileName = ?", name, (err, result) => {
    if (err) callback(err);
    else {
      callback(result);
    }
  });
};

module.exports = fileSearch;
