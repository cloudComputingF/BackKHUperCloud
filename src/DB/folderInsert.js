const db = require("../../config/database");

const folderInsert = (body, callback) => {
  db.query(
    "insert into `paths` (`folder_path`) VALUES (?)",
    [[body.folder_path]],
    (err, result) => {
      if (err) callback(err, null);
      else callback(null, result); // id 값 전송.
    }
  );
};

module.exports = folderInsert;
