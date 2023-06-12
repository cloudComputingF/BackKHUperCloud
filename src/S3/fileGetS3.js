const s3 = require("../../config/s3Setting");

const fileGetS3 = (key, callback) => {
  s3.getObject(
    {
      Bucket: "khufcloud",
      Key: key,
    },
    (err, data) => {
      console.log(data);
      if (err) callback(err, null);
      else callback(null, data);
    }
  );
};

module.exports = fileGetS3;
