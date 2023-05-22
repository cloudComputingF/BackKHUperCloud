const db = require("../../config/database");

const fileAdd = (file, callback) => {
  db.query(
    "insert into `files` (`file_name`, `download`, `folder_path`, `major_for`) VALUES (?)",
    [[file.name, file.download, file.folder_path, file.major_for]],
    (err, result) => {
      if (err) callback(err, null);
      else callback(null, result[0]);
    }
  );
};

module.exports = fileAdd;
