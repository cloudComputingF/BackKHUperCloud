const express = require("express");
const app = express();

const PORT = 8000;

app.use(express.json());

app.get("/", (req, res) => {
  console.log("KHUCloud");
});

app.listen(PORT, () => {
  console.log(`Listen on ${PORT}`);
});

require("dotenv").config();

const fileUpload = require("./fileUpload");

app.post("/upload/image", fileUpload.single("image"), (req, res) => {
  //console.log(req.file);
  res.json({ url: req.file.location, name: req.file.filename });
});

const fileDelete = require("./fileDelete");
fileDelete("1683808025851_동아리_지원서_(UMC).pdf", "folder1/");
