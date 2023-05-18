const db = require("../../config/database");

const fileAdd = (file, callback) => {
  db.query(
    "insert into files (file_name, download, foler_path, upload_by, major_for) VALUES ?",
    file.name,
    file.download,
    file.folder_path,
    file.upload_by,
    file.major_for,
    (err, result) => {
      if (err) callback(err, null);
      else callback(null, result[0]);
    }
  );
};

module.exports = fileAdd;
