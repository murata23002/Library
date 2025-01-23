<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.libease.model.User" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>ユーザー検索画面</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-KK94CHFLLe+nY2dmCWGMq91rCGa5gtU4mk92HdvYe+M/SXH301p5ILy+dN9+nJOZ" crossorigin="anonymous">
    <link rel="stylesheet" type="text/css" href="css/style.css">
</head>
<body>
<div class="container">
    <h2 class="mt-5">User List</h2>
    <!--Search Form -->
    <form action="${response.encodeRedirectURL(pageContext.request.contextPath + '/userListSearch')}" method="get" id="searchUserNameForm" role="form">
        <div class="row mt-5">
            <div class="col-md-5">
                <input type="text" name="userName" id="userName" value="${param.userName}" class="form-control"
                       placeholder="Type the Name or Id" />
            </div>
            <div class="col-md-2">
                <button type="submit" class="btn btn-info">
                    <span class="glyphicon glyphicon-search"></span>
                    Search
                </button>
            </div>
        </div>
    </form>

    <hr class="mt-5 mb-5">

    <c:choose>
        <c:when test="${not empty userList}">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th>#</th>
                    <th>Name</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="users" items="${userList}">
                    <c:set var="classSuccess" value="" />
                    <tr class="${classSuccess}">
                        <td>${users.getId()}</td>
                        <td>${users.getName()}</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </c:when>
        <c:otherwise>
            <div class="alert alert-info mt-5">
                No people found matching your search criteria
            </div>
        </c:otherwise>
    </c:choose>

</div>

<script src="js/main.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-ENjdO4Dr2bkBIFxQpeoTz1HIcje39Wm4jDKdf19U8gI4ddQ3GYNS7NTKfAdVQSZe"
        crossorigin="anonymous"></script>
</body>
</html>
