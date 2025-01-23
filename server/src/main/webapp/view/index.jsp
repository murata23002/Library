<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <title>User Login</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css" rel="stylesheet"
        integrity="sha384-KK94CHFLLe+nY2dmCWGMq91rCGa5gtU4mk92HdvYe+M/SXH301p5ILy+dN9+nJOZ" crossorigin="anonymous">
    <link rel="stylesheet" type="text/css" href="css/style.css">
</head>

<body>
    <!--ログインに成功したら書籍画面のサーブレットへ遷移させます。この処理はメッセージの上でなくてはならない。メッセージを表示する前に削除してしまうため。-->
    <c:if test="${not empty sessionScope.userId}">
        <c:redirect url="/searchExecution" />
    </c:if>
    <!--以下のコードはメッセージを表示するために使用しています。セッションスコープにメッセージがあるかないかを判定しています。-->
    <c:if test="${not empty sessionScope.errorMessage}">
        <div class="alert alert-danger alert-dismissible fade show" role="alert">
            ${sessionScope.errorMessage}
            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
        </div>
        <c:remove var="errorMessage" scope="session" />
    </c:if>
    <c:if test="${not empty sessionScope.successMessage}">
        <div class="alert alert-success alert-dismissible fade show" role="alert">
            ${sessionScope.successMessage}
            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
        </div>
        <c:remove var="successMessage" scope="session" />
    </c:if>
    <!--login Form -->
    <form action="${pageContext.request.contextPath}/login" method="post" role="form">
        <div class="row mt-5">
            <div class="col-md-5">
                <input type="text" name="userId" id="userId" class="form-control mb-2" placeholder="Type User Id" />
                <input type="text" name="userName" id="userName" class="form-control mb-2"
                    placeholder="Type User Name" />
            </div>
            <div class="col-md-5 d-flex align-items-end justify-content-end">
                <button type="submit" class="btn btn-info">
                    <span class="glyphicon glyphicon-search"></span>
                    Login
                </button>
            </div>
        </div>
    </form>
    <script src="js/main.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-ENjdO4Dr2bkBIFxQpeoTz1HIcje39Wm4jDKdf19U8gI4ddQ3GYNS7NTKfAdVQSZe"
        crossorigin="anonymous"></script>
</body>
</html>