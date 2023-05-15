const db = require("../../config/database");

const fileSearch = (fileName, callback) => {
  db.query(
    "select download from files where file_name = ?",
    fileName,
    (err, result) => {
      if (err) callback(err, null);
      else callback(null, result[0]);
    }
  );
};

module.exports = fileSearch;
