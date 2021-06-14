<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>寫食譜</title>
    <!-- Bootstrap CSS -->
    <link href="${pageContext.request.contextPath}/bootstrap-5.0.1-dist/css/bootstrap.min.css"
          rel="stylesheet" integrity="sha384-+0n0xVW2eSR5OomGNYDnhzAbDsOXxcvSN1TPprVMTNDbiYZCxYbOOl7+AMvyTG2x"
          crossorigin="anonymous">
    <!--    JavaScript; choose one of the two!-->
    <script src="${pageContext.request.contextPath}/bootstrap-5.0.1-dist/js/jquery-3.6.0.js"/>
    <!-- Option 1: Bootstrap Bundle with Popper -->
    <script src="${pageContext.request.contextPath}/bootstrap-5.0.1-dist/js/bootstrap.bundle.min.js"
            integrity="sha384-gtEjrD/SeCtmISkJkNUaaKMoLD0//ElJ19smozuHV6z3Iehds+3Ulb9Bn9Plx0x4"
            crossorigin="anonymous"></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/demo.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/homePage.css">
    <style>
        .inner {
            border: 1px solid #bcbebf;
            padding: 15px;
        }

        .showAD {
            border: 1px solid #bcbebf;
            text-align: center;
        }

        #RecTitle {
            border-color: #dc3545;
        }

        .onTop {
            margin-top: 40px;
        }

        #CreateNow {
            width: 200px;
            float: right;
        }

        .smallInner {
            margin: 10px 0px;
        }
    </style>
</head>
<body>
<!--頁首-->
<header class="container-fluid mainColor">
    <div class="title">✿海貓食屋✿</div>
    <div class="littleNav">
        <ul>
            <li><a href="#" class="top">RECIPES</a></li>
            <li><a href="#">SHOP</a></li>
        </ul>
    </div>
    <div class="simpleSearch">
        <input type="text" id="searchLine" placeholder="Search...">
        <label for="searchLine">
            <span class="fontIcon searchIcon"></span></label>
    </div>
    <div class="Login">
        <span class="text">SIGN UP</span><em>/</em>
        <span class="text">LOG IN</span>
    </div>
    <button type="button" class="userIcon"></button>
</header>

<!--主體-->
<div class="container">
    <form action="${pageContext.request.contextPath}/createRecipe/add" method="get">
        <div class="row justify-content-around onTop">
            <div class="col-md-8">
                <div class="row">
                    <label for="CategoryName">選擇食譜的分類</label>
                    <select class="form-control" id="CategoryName" name="CategoryId">
                        <c:forEach items="${categoryList}" var="cateogry">
                            <option value="${category.categoryId}">${cateogry.categoryName}</option>
                        </c:forEach>
                    </select>
                </div>
                <div class="row">
                    <label for="RecTitle">請輸入食譜名稱</label>
                    <div class="input-group">
                        <input type="text" class="form-control" placeholder="例如：番茄炒蛋" id="RecTitle" name="RecTitle">
                    </div>
                    <p style="color: #bcbebf">建議不要加上個人化名稱，像是「安德森的廚房」，避免食譜名稱過長。</p>
                    <div class="form-check smallInner">
                        <input class="form-check-input" type="checkbox" value="" id="agree"><!--checked disabled-->
                        <label class="form-check-label" for="agree">
                            發布食譜及代表同意海貓食屋「服務條款」。
                        </label>
                    </div>
                    <div id="CreateNow" style="position: static">
                        <input type="submit" class="btn btn-outline-danger" value="開始寫食譜">
                    </div>
                </div>
            </div>
            <div class="col-md-3 showAD">
                <h3>食譜名稱</h3>
                <img src="${pageContext.request.contextPath}/images/wall06.jpg" width="200px" height="200px">
                <p>食譜簡介</p>
            </div>
        </div>
    </form>
</div>
<!--頁尾-->
<footer class="container-fluid mainColor">
    <dl>
        <dt>關於我們</dt>
        <dd><a href="#">公司資訊</a></dd>
        <dd><a href="#">徵才訊息</a></dd>
        <dd><a href="#">廣告合作</a></dd>
        <dd><a href="#">市集上架</a></dd>
        <dd><a href="#">品牌資產</a></dd>
        <dd><a href="#">隱私權與條款</a></dd>
    </dl>
    <dl>
        <dt>會員服務</dt>
        <dd><a href="#">個人頁面</a></dd>
        <dd><a href="#">食譜收藏</a></dd>
        <dd><a href="#">商品訂單</a></dd>
        <dd><a href="#">帳號設定</a></dd>
        <dd><a href="#">忘記密碼</a></dd>
        <dd><a href="#">VIP會員</a></dd>
    </dl>
    <dl>
        <dt>逛食譜</dt>
        <dd><a href="#">人氣食譜</a></dd>
        <dd><a href="#">新廚上菜</a></dd>
        <dd><a href="#">今日話題</a></dd>
        <dd><a href="#">醉心食譜</a></dd>
        <dd><a href="#">全部分類</a></dd>
    </dl>
</footer>

</body>
</html>
