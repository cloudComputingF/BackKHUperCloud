require("dotenv").config();

const express = require("express");
const app = express();
const PORT = 8000;
const uploadRouter = require("./src/Router/file");

app.use(express.json());

// File Upload
app.use("/files", uploadRouter);

app.listen(PORT, () => {
  console.log(`Listen on ${PORT}`);
});
