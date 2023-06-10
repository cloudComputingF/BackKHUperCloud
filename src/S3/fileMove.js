const s3 = require("../../config/s3Setting");

fileMove = async (key, callback) => {
  params = {
    Bucket: "khufcloud",
    CopySource: "khufcloud" + "/" + key,
    Key: "trash/" + key,
  };
  s3.copyObject(params, (err, data) => {
    if (err) {
      console.log(err);
      callback(err, null);
    } else {
      console.log(data);
      callback(null, data);
    }
  });
};

module.exports = fileMove;
