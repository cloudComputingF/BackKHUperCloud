<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="ko">
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <title>Login</title>
</head>
<body>
<form action="/login" method="post">
  <h2>로그인</h2>
  <div>
    <input type="text" name="email" placeholder="Email"/>
  </div>
  <div>
    <input type="password" name="password" placeholder="Password"/>
  </div>

  <button type="submit">로그인</button>
  <button type="button" onclick="location.href='signup'">회원가입</button>
</form>
</body>
</html>