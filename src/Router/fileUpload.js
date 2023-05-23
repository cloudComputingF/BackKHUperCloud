const express = require("express");
const router = express.Router();

router.post("/", fileUpload.single("file"), (req, res) => {
  let file = {
    name: req.file.originalname,
    download: req.file.location,
    folder_path: req.body.dir,
    type: req.body.type,
    key: req.file.key,
  };
  objectSearch(file.folder_path, (err, data) => {
    if (err) {
      console.log(err);
      res.send({ Error: err });
    } else {
      if (data[0] == undefined) {
        res.send({ Message: "경로가 잘못되었습니다." });
      } else {
        fileInsert(file, (err, data) => {});
      }
    }
  });
});

app.post("/upload/file", fileUpload.single("file"), (req, res) => {
  // Form-Data -> dir , file
  console.log(req.file);
  let file = {
    name: req.file.originalname,
    download: req.file.location,
    folder_path: req.body.dir,
    type: req.body.type,
    key: req.file.key,
  };
  objectSearch(file.folder_path, (err, data) => {
    if (err) {
      console.log(err);
      res.send({ Error: err });
    } else {
      if (data[0] == undefined) {
        res.send({ Message: "Not Exist Folder" });
      } else {
        // 여기 손보자.
        console.log(file);
        // file.folder_path = data[0].id; // 참조키.
        fileInsert(file, (err, result) => {
          console.log("err: ", err);
          console.log("result : ", result);
          res.json({ url: req.file.location, Message: "Success Upload File" });
        });
      }
    }
  });
});
