<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>


<!DOCTYPE html>
<html lang="ko">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>User List</title>
</head>
<body>
<h2>User List</h2>
<table>
    <tr>
        <th>id</th>
        <th>name</th>
        <th>username</th>
        <th>email</th>
        <th>password</th>
        <th>address</th>
        <th>phone</th>
        <th>website</th>
        <th>company</th>
    </tr>
    <c:forEach items="${list}" var="u">
        <tr>
            <td>${u.id}</td>
            <td>${u.name}</td>
            <td>${u.username}</td>
            <td>${u.email}</td>
            <td>${u.password}</td>
            <td>${u.address}</td>
            <td>${u.phone}</td>
            <td>${u.website}</td>
            <td>${u.company}</td>
        </tr>
    </c:forEach>
</table>
</body>
</html>