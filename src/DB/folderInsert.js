const db = require("../../config/database");

const folderInsert = (folder, callback) => {
  db.query(
    "insert into `paths` (`path`) VALUES (?)",
    [[folder.path]],
    (err, result) => {
      if (err) callback(err, null);
      else callback(null, result[0]); // id 값 전송.
    }
  );
};

module.exports = folderInsert;
