const path = require("path");
const multer = require("multer");
const multerS3 = require("multer-s3");
const s3 = require("../../config/s3Setting");

const upload = multer({
  storage: multerS3({
    s3: s3,
    bucket: "khufcloud",
    acl: "public-read",
    contentType: multerS3.AUTO_CONTENT_TYPE,
    key(req, file, cb) {
      cb(
        null,
        `${req.body.dir}/${Date.now()}_${path.basename(file.originalname)}`
      );
    },
  }),
});

module.exports = upload;
