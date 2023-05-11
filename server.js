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

app.post("/upload/file", fileUpload.single("file"), (req, res) => {
  //console.log(req.file);
  console.log(req.file.location);
  res.json({ url: req.file.location });
});
