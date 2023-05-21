require("dotenv").config();

const express = require("express");

console.log(process.env);

const app = express();
const PORT = 8000;

app.use(express.json());

app.listen(PORT, () => {
  console.log(`Listen on ${PORT}`);
});

const fileUpload = require("./File/fileUpload");
const fileInsert = require("./DB/fileInsert");
app.post("/upload/file", fileUpload.single("file"), (req, res) => {
  // Form-Data -> dir , file

  let file = {
    name: req.file.originalname,
    donwload: req.file.location,
    folder_path: req.body.dir,
    major_for: req.body.major,
  };

  console.log(file);
  fileInsert(file, (err, result) => {
    console.log("err: ", err);
    console.log("result : ", result);
  });

  res.json({ url: req.file.location });
});

const fileDownload = require("./DB/fileSearch");

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

const db = require("../config/database");

// DB Connect
db.connect((err) => {
  if (err) {
    throw err;
  } else {
    console.log("Connectiong");
  }
});
