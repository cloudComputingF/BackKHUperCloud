const db = require("../../config/database");

// const folderInsert = (body, callback) => {
//   db.query(
//     "insert into `paths` (`folder_path`, `major_for`) VALUES (?)",
//     [[body.folder_path, body.major_for]],
//     (err, result) => {
//       if (err) callback(err, null);
//       else callback(null, result); // id 값 전송.
//     }
//   );
// };

// module.exports = folderInsert;

const folderInsert = (file, callback) => {
  db.query(
    "insert into `files` (`file_name`, `download`, `folder_path`, `key`, `type`) VALUES (?)",
    [[file.name, file.download, file.folder_path, file.key, "folder"]],
    (err, result) => {
      if (err) callback(err, null);
      else callback(null, result[0]);
    }
  );
};

module.exports = folderInsert;
