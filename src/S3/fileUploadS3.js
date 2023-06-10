const path = require("path");
const multer = require("multer");
const multerS3 = require("multer-s3");
const s3 = require("../../config/s3Setting");

const fileDupName = require("../DB/fileDupName");

const fileUpload = multer({
  storage: multerS3({
    s3: s3,
    bucket: "khufcloud",
    acl: "",
    contentType: multerS3.AUTO_CONTENT_TYPE,
    key(req, file, cb) {
      fileDupName(
        `${req.body.dir}${path.basename(file.originalname)}`,
        (err, data) => {
          console.log(err);
          console.log(data);
          console.log(data.length);
          if (err) cb(err, null);
          else if (data.length == 0) {
            cb(null, `${req.body.dir}${path.basename(file.originalname)}`);
          } else {
            cb(
              null,
              `${req.body.dir}${path.basename(file.originalname)}_${
                data.length
              }`
            );
          }
        }
      );
    },
  }),
});

module.exports = fileUpload;
