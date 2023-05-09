require("dotenv").config();

const path = require("path");
const AWS = require("aws-sdk");
const multer = require("multer");
const multerS3 = require("multer-s3");

const s3 = new AWS.S3({
  accessKeyId: process.env.ACCESS_KEY_ID,
  secretAccessKey: process.env.SECREAT_ACCESS_KEY,
});

const upload = multer({
  storage: multerS3({
    s3: s3,
    bucket: "khufcloud",
    acl: "public-read",
    contentType: multerS3.AUTO_CONTENT_TYPE,
    key(req, file, cb) {
      console.log(req.body.dir);
      cb(null, `${Date.now()}_${path.basename(file.originalname)}`);
    },
  }),
});

module.exports = upload;
