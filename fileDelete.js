require("dotenv").config();

const path = require("path");
const AWS = require("aws-sdk");
const multer = require("multer");
const multerS3 = require("multer-s3");

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
      }
      console.log(data);
    }
  );
};

module.exports = fileDelete;
