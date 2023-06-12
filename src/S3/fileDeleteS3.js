const s3 = require("../../config/s3Setting");

const fileDeleteS3 = (key) => {
  s3.deleteObject(
    {
      Bucket: "khufcloud",
      Key: key,
    },
    (err, data) => {
      if (err) {
        console.log(err);
      } else {
        console.log(data);
      }
    }
  );
};

module.exports = fileDeleteS3;
