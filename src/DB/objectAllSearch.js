const db = require("../../config/database");

const fileAllSearch = (path, callback) => {
  db.query(
    "select file_name from files where folder_path = ?",
    path,
    (err, result) => {
      if (err) callback(err, null);
      else callback(null, result);
    }
  );
};

module.exports = fileAllSearch;
