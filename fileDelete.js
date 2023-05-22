require("dotenv").config();

const AWS = require("aws-sdk");

const s3 = new AWS.S3({
  accessKeyId: process.env.ACCESS_KEY_ID,
  secretAccessKey: process.env.SECREAT_ACCESS_KEY,
});

const fileDelete = (fileName, directory) => {
  s3.deleteObject(
    {
      Bucket: "khufcloud",
      Key: directory + fileName,
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

const dirDelete = (folderName) => {
  s3.deleteObjects({
    Bucket: "khucloud",

    // DB 에서 파일 서치해야해.
    key: directory,
  });
};

module.exports = fileDelete;
