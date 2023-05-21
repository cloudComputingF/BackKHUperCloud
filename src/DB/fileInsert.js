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

// mysqlConnection.query(
//   "INSERT INTO `users` (`nid`, `name`, `dob`, `gender`) VALUES (?)",
//   [[b.nid, b.name, b.dob, b.gender]],
//   (error, results) => {
//     if (!error) res.status(201).send(result.insertId);
//     else res.send(error);
//   }
// );
