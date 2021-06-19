<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">

 <head>
     <meta charset="UTF-8">
<%--     <meta http-equiv="X-UA-Compatible" content="IE=edge">--%>
<%--     <meta name="viewport" content="width=device-width, initial-scale=1.0">--%>
<%--    <link rel="preconnect" href="https://fonts.gstatic.com">--%>
<%--    <link href="https://fonts.googleapis.com/css2?family=Noto+Sans+TC&display=swap" rel="stylesheet">--%>
<%--    <link rel="stylesheet" href="/recipe/css/bootstrap.min.css">--%>
<%--    <link rel="stylesheet" href="/recipe/css/bootstrap.rtl.min.css">--%>
<%--    <link rel="stylesheet" href="/recipe/css/top_nav.css">--%>

<%--     <title>Header</title>--%>
 </head>

 <body>

    <!-- header部分 -->

    <nav class="navbar navbar-expand-lg navbar-light  mainColor ">
        <div class="container-fluid">
            <a class="navbar-brand" href="${pageContext.request.contextPath}/homePage" id="mainTitle">✿海貓食屋✿</a>
            <!-- top-right bar -->
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarScroll" aria-controls="navbarScroll" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
       
          </button>

            <div class="collapse navbar-collapse" id="navbarScroll">


                <ul class="navbar-nav me-auto my-2 my-lg-0" style="--bs-scroll-height: 100px;">
                    <li class="nav-item">
                        <a class="navLink navColor" href="${pageContext.request.contextPath}/recipeHome">食譜</a>
                    </li>

                    <li class="nav-item">
                        <a class="navLink navColor" href="/recipe/views/ShoppingMall" tabindex="-1" aria-disabled="true">商城</a>
                    </li>
                </ul>

                <form class="d-flex" action="/recipe/recipeSearch" method="get">
                    <input class="form-control me-2" type="search" placeholder="Search..." aria-label="Search" name="searchString">
                    <button class="btn btn-search" type="submit">Search</button>
                  </form>
                  <em>|</em>
                  <div id='userConsole'>
                    <img src="${pageContext.request.contextPath}/images/homePic/yarn-brands.svg" id="userIcon">
                    <button id="userName">haha</button>
                    <ul>
                      <li><a href="${pageContext.request.contextPath}/userBack/home">個人主頁</a></li>
                      <li><a href="${pageContext.request.contextPath}/userBack/userSetting">帳號設定</a></li>
                      <li><a href="#">登出</a></li>
                    </ul>
                  </div>

            </div>
        </div>
    </nav>

</body>

</html>