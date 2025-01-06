<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 2025-01-06
  Time: 오후 4:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <h1>사용자 등록</h1>
    <%--
    action: URL 데이터 전송, method: POST방식으로 서버에 데이터 전송
    --%>
    <form action="" method="post">
        <table>
            <tr>
                <td>userName</td>
                <td><input type="text" name="userName" required></td>
                <td>password</td>
                <td><input type="password" name="password" required></td>
            </tr>
            <tr>
                <td>name</td>
                <td><input type="text" name="name" required></td>
                <td>email</td>
                <td><input type="password" name="email" required></td>
            </tr>
        </table>
        <button>추가</button>
    </form>
    <h1>사용자 조회</h1>
    <form action="" method="get">
        <input type="text" name="searchValue">
        <button type="submit">조회</button>
    </form>
    <table>
        <%--
            사용자 정보를 표시할 테이블
        --%>
        <tr>
            <th>username</th>
            <th>password</th>
            <th>name</th>
            <th>email</th>
        </tr>

        <%--
            jstl 태그를 사용하여 사용자 목록을 반복
            user: 속성 이름, users: 속성 값(출력할 데이터)
        --%>
        <c:forEach var="user" items="${users}">
            <tr>
                <td>${user.username}</td>
                <td>${user.password}</td>
                <td>${user.name}</td>
                <td>${user.email}</td>
            </tr>
        </c:forEach>
    </table>
</body>
</html>
