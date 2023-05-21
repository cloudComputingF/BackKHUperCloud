const db = require("../../config/database");

const folderSearch = (folder, callback) => {
  db.query(
    "select id from paths where path = ?",
    folder.path,
    (err, result) => {
      if (err) callback(err, null);
      else callback(null, result[0]); // id 값 전송.
    }
  );
};

module.exports = folderSearch;
