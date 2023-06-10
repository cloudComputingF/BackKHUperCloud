require("dotenv").config();

const express = require("express");
const app = express();
const cors = require("cors");
const PORT = 8000;
const uploadRouter = require("./src/Router/file");

app.use(express.json());
app.use(cors());
// File Upload
app.use("/files", uploadRouter);

app.listen(PORT, () => {
  console.log(`Listen on ${PORT}`);
});

// Search Dup Files => 중복파일 알려주기.
const fileAllURL = require("./src/DB/fileAllURL");
app.get("/dup", (req, res) => {
  fileAllURL((err, result) => {
    if (err) {
      res.send({ Error: err });
    } else {
      const urlList = JSON.stringify(result);

      const { spawn } = require("child_process");
      const pyProg = spawn("python", ["./src/Python/image.py", urlList]);

      pyProg.stdout.on("data", function (data) {
        // All  Dup Image, data(python script return value)
        console.log(data.toString());
        res.write(data);
        res.end("중복 검사 완료.");
      });
    }
  });
});
