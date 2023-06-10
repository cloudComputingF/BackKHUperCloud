const db = require("../../config/database");

const fileUpdateKey = (key, callback) => {
  db.query(
    "UPDATE s3file SET file_key = ? WHERE file_key = ?",
    [[`trash/${key}`], key],
    (err, data) => {
      if (err) callback(err, null);
      else callback(null, data);
    }
  );
};

module.exports = fileUpdateKey;
