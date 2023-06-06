const db = require("../../config/database");

const fileKeySearch = (name, callback) => {
  db.query(
    "select * from s3file where file_key = ?",
    name,
    (err, result, fields) => {
      if (err) callback(err, null);
      else {
        callback(result, null);
      }
    }
  );
};

module.exports = fileKeySearch;
