<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <title>Modify Information</title>
</head>
<body>
<h2>회원 정보 수정</h2>
<form action="/update" method="post">
  <p>
    Name<br>
    <input type="text" name="name" value="${user.name}"/>
  </p>
  <p>
    Email<br>
    <input type="text" name="email" value="${user.email}"/>
  </p>
  <p>
    Password<br>
    <input type="text" name="password" value="${user.password}"/>
  </p>

  <button type="submit">저장하기</button>
</form>
</body>
</html>