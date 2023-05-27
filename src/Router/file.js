const express = require("express");
const router = express.Router();

const fileUploadS3 = require("../S3/fileUploadS3");
const fileSearchS3 = require("../S3/fileSearchS3");
const fileInsert = require("../DB/fileInsert");

// POST /files/upload
router.post("/upload", async (req, res) => {
  await fileUploadS3.single("file")(req, res, (err) => {
    if (err) {
      console.log(err);
      res.json({ error: "File Upload Failed", Message: err });
    }

    file = {
      name: req.file.originalname,
      key: req.file.key,
      download: req.file.location,
    };
    fileInsert(file, (err, result) => {
      if (err) {
        console.log(err);
        return res.json({ error: "File Add on DB Failed", Message: err });
      }
      console.log(result);
      res.send("File Upload Success !");
    });
  });
});

// GET /files/search?folder=test1
router.get("/search", (req, res) => {
  const result = [];
  fileSearchS3(req.query.folder, (err, data) => {
    var files = data.Contents;
    var folders = data.CommonPrefixes;
    if (err) {
      console.log(err);
      return res.send({ error: "File Search Failed", Message: err });
    }
    // 파일들
    for (let i = 0; i < files.length; i++) {
      console.log(files[i].Key);
      result.push(files[i].Key);
    }
    // 폴더들
    for (let i = 0; i < folders.length; i++) {
      console.log(folders[i].Prefix);
      result.push(folders[i].Prefix);
    }
    res.send({
      Message: result,
    });
  });
});

// console.log(data);
// res.send(data);

module.exports = router;
