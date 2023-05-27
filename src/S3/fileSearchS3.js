const s3 = require("../../config/s3Setting");

fileSearchS3 = (prefix, callback) => {
  const params = {
    Bucket: "khufcloud",
    Prefix: prefix,
    Delimiter: "/",
  };
  s3.listObjectsV2(params, (err, data) => {
    console.log(data);
    if (err) callback(err, null);
    callback(null, data);
  });
};

module.exports = fileSearchS3;
