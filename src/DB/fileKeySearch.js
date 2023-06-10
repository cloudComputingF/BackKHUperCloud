const db = require("../../config/database");

const fileKeySearch = (name, callback) => {
  db.query("select * from s3file where file_key = ?", name, (err, result) => {
    if (err) callback(err, null);
    else {
      callback(null, result);
    }
  });
};

module.exports = fileKeySearch;
