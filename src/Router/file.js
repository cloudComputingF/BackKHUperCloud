const express = require("express");
const router = express.Router();

const fileUploadS3 = require("../S3/fileUploadS3");
const fileSearchS3 = require("../S3/fileSearchS3");
const folderUploadS3 = require("../S3/folderUploadS3");
const fileInsert = require("../DB/fileInsert");
const fileNameSearch = require("../DB/fileNameSearch");
const fileKeySearch = require("../DB/fileKeySearch");
// POST /files/upload
router.post("/upload", fileUploadS3.single("file"), (req, res) => {
  try {
    file = {
      name: req.file.originalname,
      key: req.file.key,
      download: req.file.location,
    };
  } catch (err) {
    console.log(err);
    res.send({ error: err });
  }
  fileInsert(file, (err, result) => {
    if (err) {
      console.log(err);
      return res.json({ error: "File Add on DB Failed", Message: err });
    }
    console.log(result);
    res.send("File Upload Success !");
  });
});

// POST /files/folder
router.post("/folder", async (req, res) => {
  folderUploadS3(req, (err, data) => {
    if (err)
      return res.json({
        error: "Folder Making Failed",
        Message: err,
      });
    return res.json({
      Message: "Folder Making Success",
    });
  });
});

// GET /files/search?folder=test1
router.get("/search", async (req, res) => {
  const result = [];
  await fileSearchS3(req.query.folder, (err, data) => {
    if (err) {
      console.log(err);
      return res.send({ error: "File Search Failed", Message: err });
    }
    var files = data.Contents;
    var folders = data.CommonPrefixes;
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
// GET /files/download/(file_name)
router.get("/download/:fileName", (req, res) => {
  console.log(req.params);
  fileNameSearch(req.params.fileName, (err, data) => {
    if (err) {
      console.log(err);
      res.send({ error: "File Search Failed (One File)", Message: err });
    } else {
      res.send({
        Message: data,
      });
    }
  });
});

// GET /files/download/(file_key)
router.get("/fileKey/download/:fileKey", (req, res) => {
  fileKeySearch(req.params.fileKey, (err, data) => {
    if (err) {
      console.log(err);
      res.send({ error: "File Search Failed (File Key)", Message: err });
    } else {
      res.send({
        Message: data,
      });
    }
  });
});

module.exports = router;
