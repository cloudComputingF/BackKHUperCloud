const path = require("path");
const multer = require("multer");
const multerS3 = require("multer-s3");
const s3 = require("../../config/s3Setting");

const fileUpload = multer({
  storage: multerS3({
    s3: s3,
    bucket: "khufcloud",
    acl: "",
    contentType: multerS3.AUTO_CONTENT_TYPE,
    key(req, file, cb) {
      cb(null, `${req.body.dir}${path.basename(file.originalname)}`);
    },
  }),
});

module.exports = fileUpload;
