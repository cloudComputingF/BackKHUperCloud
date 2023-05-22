const db = require("../../config/database");

const folderSearch = (body, callback) => {
  db.query(
    "select id from paths where folder_path = ?",
    body.folder_path,
    (err, result) => {
      if (err) callback(err, null);
      else callback(null, result); // id 값 전송.
    }
  );
};

module.exports = folderSearch;
