const express = require("express");
const router = express.Router();

const fileUploadS3 = require("../S3/fileUploadS3");
const fileSearchS3 = require("../S3/fileSearchS3");
const fileDeleteS3 = require("../S3/fileDeleteS3");

const folderUploadS3 = require("../S3/folderUploadS3");
const fileInsert = require("../DB/fileInsert");
const fileNameSearch = require("../DB/fileNameSearch");
const fileKeySearch = require("../DB/fileKeySearch");
const fileInsertPassword = require("../DB/fileInsertPassword");

// POST /files/upload
router.post("/upload", fileUploadS3.single("file"), (req, res) => {
  try {
    file = {
      name: req.file.originalname,
      key: req.file.key,
      download: req.file.location,
    };
    console.log(file);
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

// POST/files/upload/password
router.post("/upload/password", fileUploadS3.single("file"), (req, res) => {
  try {
    file = {
      name: req.file.originalname,
      key: req.file.key,
      download: req.file.location,
      password: req.body.password,
    };
  } catch (err) {
    console.log(err);
    res.send({ error: err });
  }
  fileInsertPassword(file, (err, result) => {
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
// GET /files/download
router.get("/download", (req, res) => {
  console.log(req.params);
  fileNameSearch(req.query.fileName, (err, data) => {
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

// GET /files/fileKey/download?fileKey=&password=
router.get("/fileKey/download", (req, res) => {
  fileKeySearch(req.query.fileKey, (err, data) => {
    console.log(data.length);
    if (err) {
      console.log(err);
      res.send({ error: "File Search Failed (File Key)", Message: err });
    } else if (data[0].file_password == null) {
      res.send({
        Message: data,
      });
    } else if (data[0].file_password == req.query.password) {
      res.send({
        Message: data,
      });
    } else {
      res.send({ Message: "Check File Password." });
    }
  });
});

/*
  폴더를 휴지통으로 이동 시키기.
*/
const fileMove = require("../S3/fileMove");
const fileUpdateKey = require("../DB/fileUpdataKey");

router.get("/move/trash", (req, res) => {
  key = req.query.fileKey;
  fileMove(key, (err, data) => {
    if (err) {
      console.log(err);
      res.send({ error: "Move File to Trash Folder Fail", Meesage: err });
    } else {
      fileUpdateKey(key, (err2, data2) => {
        fileDeleteS3(key);
        res.send({ Message: "File Move to Trash Folder Success" });
      });
    }
  });
});

module.exports = router;
