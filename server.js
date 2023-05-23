require("dotenv").config();

const express = require("express");

console.log(process.env);

const app = express();
const PORT = 8000;

app.use(express.json());

app.listen(PORT, () => {
  console.log(`Listen on ${PORT}`);
});

const fileUpload = require("./src/File/fileUpload");

const fileInsert = require("./src/DB/fileInsert");

// 폴더를 생성해야 해 -> 생성할 폴더의 경로를 주면, 만든다!
const folderInsert = require("./src/DB/folderInsert");
app.post("/upload/folder", (req, res) => {
  // req.body.folder_path = "경로", ex : "/"
  folderInsert(req.body, (err, data) => {
    if (err) {
      console.log(err);
    } else {
      console.log("Folder Insert Success"); // 폴더의 아이디값.
      res.send({ folderId: data.InsertId, Message: "Success Make Folder" });
    }
  });
});

const objectSearch = require("./src/DB/objectSearch");
// 폴더가 있는지 확인해야해.
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

const fileAllSearch = require("./src/DB/fileAllSearch");
app.get("/list/files", (req, res) => {
  fileAllSearch(req.query.file_path, (err, data) => {
    if (err) {
      console.log(err);
    } else {
      res.send(data);
    }
  });
});

const fileDownload = require("./src/DB/objectSearch");
app.get("/download/file", (req, res) => {
  console.log(req.query.file_name);

  fileDownload(req.query.file_name, (err, data) => {
    if (err) {
      console.log(err);
    } else {
      res.send(data);
    }
  });
});

const db = require("./config/database");
const fileSearch = require("./src/DB/objectSearch");

// DB Connect
db.connect((err) => {
  if (err) {
    throw err;
  } else {
    console.log("Connectiong");
  }
});

// const fileDelete = require("./src/File/fileDelete");
// fileDelete("1683808025851_동아리_지원서_(UMC).pdf", "folder1/");
