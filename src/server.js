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

app.post("/upload/file", fileUpload.single("file"), (req, res) => {
  //console.log(req.file);
  console.log(req.file.location);
  res.json({ url: req.file.location });
});

const fileDownload = require("./File/fileSearch");
app.get("/download/file", (req, res) => {
  url = fileDownload(req.body.file_name);
  res.send(url);
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
