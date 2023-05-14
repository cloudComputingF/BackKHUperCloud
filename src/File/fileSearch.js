const db = require("../../config/database");

const fileSearch = (file_name) => {
  db.query(
    "select download from files where file_name = ?",
    file_name,
    (err, result) => {
      console.log(result);
      return result;
    }
  );
};

module.exports = fileSearch;
