const db = require("../../config/database");

const fileNameSearch = (name, callback) => {
  db.query("select * from s3file where file_name = ?", name, (err, result) => {
    if (err) callback(err, null);
    else {
      callback(null, result);
    }
  });
};

module.exports = fileNameSearch;
