<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>書籍予約画面</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css" rel="stylesheet"
        integrity="sha384-KK94CHFLLe+nY2dmCWGMq91rCGa5gtU4mk92HdvYe+M/SXH301p5ILy+dN9+nJOZ" crossorigin="anonymous">
    <link rel="stylesheet" type="text/css" href="css/style.css">
</head>
<body>
    <script>
        function ajaxRequest(url, buttonValue) {
            fetch(url, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({ bookId: buttonValue })
            })
                .then(response => response.text())
                .then(data => {
                    // サーバーからのレスポンスを受け取ったときの処理
                    // 検索ボタンを探してクリックする
                    // この処理を実行するのは書籍予約実行や、予約取消実行を行うと画面のURLが変わってしまいInputの値が保持できなくなってしまうため
                    const button = document.getElementById('search');
                    button.click();
                })
                .catch(error => {
                    // エラーが発生したときの処理
                    console.error(error);
                });
        }
    </script>
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
    <div class="container">
        <div class="d-flex justify-content-end mt-3">
            <div class="d-flex justify-content-end mt-3">
                User ID : ${sessionScope.userId}
                User Name : ${sessionScope.userName}
            </div>
            <form action="${pageContext.request.contextPath}/logout" method="get">
                <button class="btn btn-light btn-sm" type="submit">ログアウト</button>
            </form>
        </div>
        <h2 class="mt-5">予約書籍一覧</h2>
        <c:choose>
            <c:when test="${not empty books}">
                <table class="table table-striped">
                    <thead>
                        <tr>
                            <th>Id</th>
                            <th>Book Name</th>
                            <th>#</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="book" items="${books}">
                            <c:set var="classSuccess" value="" />
                            <tr class="${classSuccess}">
                                <td>${book.getBookId()}</td>
                                <td>${book.getBookName()}</td>
                                <td>
                                    <button type="submit" value="${book.getBookId()}"
                                        onclick="ajaxRequest('${pageContext.request.contextPath}/cancelBookExecution', this.value)"
                                        class="btn btn-info">予約キャンセル</button>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </c:when>
            <c:otherwise>
                <div class="alert alert-info mt-5">
                    Data not found.
                </div>
            </c:otherwise>
        </c:choose>
        <h2 class="mt-5">書籍検索</h2>
        <!--Search Form -->
        <form action="${pageContext.request.contextPath}/searchExecution" method="get" id="searchBookForm" role="form">
            <div class="row mt-5">
                <div class="col-md-5">
                    <input type="text" name="bookName" id="bookName" value="${param.bookName}" class="form-control mb-2"
                        placeholder="Type book name" />
                    <input type="text" name="author" id="author" value="${param.author}" class="form-control mb-2"
                        placeholder="Type author" />
                </div>
                <div class="col-md-5 d-flex align-items-end justify-content-end">
                    <button type="submit" class="btn btn-info" id="search">
                        <span class="glyphicon glyphicon-search"></span>
                        Search
                    </button>
                </div>
            </div>
        </form>
        <hr class="mt-5 mb-5">
        <c:choose>
            <c:when test="${not empty bookStatusDatas}">
                <p>検索結果：${bookStatusDatas.size()}件</p>
                <table class="table table-striped">
                    <thead>
                        <tr>
                            <th>Id</th>
                            <th>Book Name</th>
                            <th>Author</th>
                            <th>Status</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="bookStatus" items="${bookStatusDatas}">
                            <c:set var="classSuccess" value="" />
                            <tr class="${classSuccess}">
                                <td>${bookStatus.getBook().getBookId()}</td>
                                <td>${bookStatus.getBook().getBookName()}</td>
                                <td>${bookStatus.getBook().getAuthor()}</td>
                                <c:choose>
                                    <c:when test="${bookStatus.getStatuts() == 'available'}">
                                        <td>
                                            <button type="submit" value="${bookStatus.getBook().getBookId()}"
                                                onclick="ajaxRequest('${pageContext.request.contextPath}/bookingExecutions', this.value)"
                                                class="btn btn-info">貸出予約</button>
                                        </td>
                                    </c:when>
                                    <c:when test="${bookStatus.getStatuts() == 'borrowed'}">
                                        <td>貸出済み</td>
                                    </c:when>
                                    <c:when test="${bookStatus.getStatuts() == 'reserved'}">
                                        <td>予約済み</td>
                                    </c:when>
                                    <c:otherwise>
                                        <td>不明</td>
                                    </c:otherwise>
                                </c:choose>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </c:when>
            <c:otherwise>
                <div class="alert alert-info mt-5">
                    Data not found.
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