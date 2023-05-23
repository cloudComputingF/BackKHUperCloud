const db = require("../../config/database");

const fileSearch = (path, callback) => {
  db.query(
    "select download from files where file_name = ?",
    path,
    (err, result) => {
      if (err) callback(err, null);
      else callback(null, result);
    }
  );
};

module.exports = fileSearch;
