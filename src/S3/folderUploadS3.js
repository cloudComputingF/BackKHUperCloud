const s3 = require("../../config/s3Setting");

const Obj = (req, callback) =>
  s3.putObject(
    {
      Key: req.body.path,
      Bucket: "khufcloud",
    },
    (err, data) => {
      if (err) callback(err, null);
      else callback(null, data);
    }
  );

module.exports = Obj;
