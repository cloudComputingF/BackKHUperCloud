const db = require("../../config/database");
const fileAllURL = (callback) => {
  db.query("select download  from s3file", (err, result, fields) => {
    if (err) {
      callback(err, null);
    } else {
      callback(null, result);
    }
  });
};

module.exports = fileAllURL;
