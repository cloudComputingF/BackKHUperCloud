const db = require("../../config/database");

const fileDupName = (key, callback) => {
  db.query(
    `select * from s3file where file_key like ?"%"`,
    key,
    (err, result) => {
      if (err) callback(err, null);
      else {
        callback(null, result);
      }
    }
  );
};

module.exports = fileDupName;
