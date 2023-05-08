const express = require("express");
const app = express();

const PORT = 8000;

app.get("/", (req, res) => {
  console.log("KHUCloud");
});

app.listen(PORT, () => {
  console.log(`Listen on ${PORT}`);
});

require("dotenv").config();

/* 
test = require("./fileUpload");

test("./file.txt");

*/
