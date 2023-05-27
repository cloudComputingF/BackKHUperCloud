const db = require("../../config/database");

const fileInsert = (file, callback) => {
  db.query(
    "insert into `s3file` (`file_name`, `file_key`, `download`) VALUES (?)",
    [[file.name, file.key, file.download]],
    (err, result) => {
      if (err) callback(err, null);
      else callback(null, result);
    }
  );
};

module.exports = fileInsert;

// file.folder_path.replace(/\"/gi, "")
