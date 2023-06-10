const db = require("../../config/database");

const fileInsertPassword = (file, callback) => {
  db.query(
    "insert into `s3file` (`file_name`, `file_key`, `download`, `file_password`) VALUES (?)",
    [[file.name, file.key, file.download, file.password]],
    (err, result) => {
      if (err) callback(err, null);
      else callback(null, result);
    }
  );
};

module.exports = fileInsertPassword;

// file.folder_path.replace(/\"/gi, "")
