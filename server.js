require("dotenv").config();

const express = require("express");
const app = express();
const cors = require("cors");
const PORT = 8000;
const uploadRouter = require("./src/Router/file");

app.use(express.json());
app.use(
  cors({
    origin: "*",
  })
);
// File Upload
app.use("/files", uploadRouter);

app.listen(PORT, () => {
  console.log(`Listen on ${PORT}`);
});

// Test
