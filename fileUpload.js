const AWS = require("aws-sdk");
const fs = require("fs");
require("dotenv").config();

const s3 = new AWS.S3({
  accessKeyId: process.env.ACCESS_KEY_ID,
  secretAccessKey: process.env.SECREAT_ACCESS_KEY,
});

const uploadFile = (fileName) => {
  const fileContent = fs.readFileSync(fileName);
  const params = {
    Bucket: "khufcloud",
    Key: "test.txt",
    Body: fileContent,
  };

  s3.upload(params, (err, data) => {
    if (err) throw err;
    console.log(`File uploaded successfully. ${data.Location}`);
  });
};

module.exports = uploadFile;
