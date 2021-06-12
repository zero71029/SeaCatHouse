<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>寫食譜明細</title>
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
    <link rel="stylesheet" href="${pageContext.request.contextPath}/bootstrap-icons-1.5.0/bootstrap-icons.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/demo.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/homePage.css">
    <script type="text/javascript">
        $(document).ready(function () {
            // 設定 點擊照片上傳+換圖片
            upload = function (c, d) {
                var $c = document.querySelector(c);
                var $d = document.querySelector(d);
                var file = $c.files[0];
                var reader = new FileReader();
                reader.readAsDataURL(file);
                reader.onload = function (e) {
                    $d.setAttribute("src", e.target.result);
                };
            };

            // 執行增加一個食譜步驟
            var apple = $('stepGO').innerHTML;
            console.log(apple);
        })
    </script>
    <style>
        body {
            background-color: #EFECE8;
        }

        .fileInput {
            position: absolute;
            top: 0;
            bottom: 0;
            left: 0;
            right: 0;
            opacity: 0;
            display: block;
            width: 100%;
            clear: both;
        }

        .inner {
            border: 1px solid #bcbebf;
            padding: 15px;
        }
        .picmain, .picstep  {
            position: relative;
            background-color: #EFECE8;
            text-align: center;
        }
        #img-main{
            width: 100%;
            height: 100%;
        }
        .img-step{
            width: 100%;
            height: 100%;
        }

        .row {
            margin: 30px 0px;
        }

        .Lbody {
            background-color: #ffffff;
            padding: 20px;
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
    <button class="userIcon"></button>
</header>

<!--主體-->
<div class="container Cbody">
    <form action="${pageContext.request.contextPath}" method="get">
        <div class="row justify-content-around">
            <div class="col-md-7 Lbody">
                <%--                食譜分類 RecCategory--%>
                <%--                食譜名稱 RecTitle--%>
                <div class="row">
                    <div class="inner">
                        <h5>${RecTitle}</h5>
                    </div>
                </div>
                <%--                上傳檔案+顯示圖片 RecPic--%>
                <div class="row">
                    <div class="picmain">
                        <div style="width: 350px;height: 280px; margin:0 auto;">
                            <img src="${pageContext.request.contextPath}/images/uploadpic.png" id="img-main"
                                 class="img-thumbnail"/>
                            <input type="file" accept="image/*" id="file-main" name="file"
                                   onchange="upload('#file-main', '#img-main');" class="fileInput" value=""/>
                        </div>
                    </div>
                </div>
                <%--                簡介 RecText --%>
                <div class="row">
                    <div class="form-group">
                        <label for="RecText">請輸入食譜簡介</label>
                        <textarea class="form-control" id="RecText" rows="3"></textarea>
                    </div>
                </div>
                <%--                    --%>
                <div class="row justify-content-between">
                    <%--                 份量 RecNum--%>
                    <div class="form-group col-md-6">
                        <label for="exampleFormControlSelect1">份量<span>(人份)</span></label>
                        <select class="form-control" id="exampleFormControlSelect1">
                            <option>1</option>
                            <option>2</option>
                            <option>3</option>
                            <option>4</option>
                            <option>5</option>
                            <option>6</option>
                            <option>7</option>
                            <option>8</option>
                            <option>9</option>
                            <option>10+</option>
                        </select>
                    </div>
                    <%--                 分鐘數 RecTime  --%>
                    <div class="form-group col-md-6">
                        <label for="RecTime">烹調時間<span>(分鐘)</span></label>
                        <select class="form-control" id="RecTime">
                            <option>5</option>
                            <option>10</option>
                            <option>15</option>
                            <option>20</option>
                            <option>30</option>
                            <option>45</option>
                            <option>60</option>
                            <option>90</option>
                            <option>120</option>
                            <option>180+</option>
                        </select>
                    </div>
                </div>
                <div class="row" style="margin-bottom: 0px">
                    <label>食材<span>食材15字以內，份量10字以內</span></label><br/>
                </div>
                <div class="row justify-content-between" style="margin-top: 2px">
                    <div class="col-md-7">
                        <div class="input-group">
                            <input type="text" class="form-control" placeholder="食材">
                        </div>
                    </div>
                    <div class="col-md-4">
                        <div class="input-group">
                            <input type="text" class="form-control" placeholder="份量">
                        </div>
                    </div>
                    <div class="col-md-1">
                        <i class="bi bi-trash" style="font-size: 1.5rem; color: #6c6c71"></i>
                    </div>
                </div>
                <div class="row justify-content-between">
                    <button type="button" class="btn btn-outline-info" style=" width: 100%">加入食材</button>
                </div>
                <%--                食材新增--%>
                <div class="row" style="margin-bottom: 0px" id="stepGO">
                    <label>步驟</label><br/>
                </div>

                <c:forEach begin="1" end="3" var="i">
                    <%--                    步驟的照片--%>
                    <div class="row">
                        <div class="col-md-5">
                            <div class="picstep">
                                <div style="width: 280px;height: 210px; margin:0 auto;">
                                    <img src="${pageContext.request.contextPath}/images/uploadstep.png"
                                         class="img-thumbnail img-step"
                                         id="img-step${i}"/>
                                    <input type="file" accept="image/*" id="file-step${i}" name="file"
                                           onchange="upload('#file-step${i}', '#img-step${i}');" class="fileInput"
                                           value=""/>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-7">
                            <div class="row justify-content-between" style="margin: 0px">
                                    <%--                            步驟的說明 --%>
                                <div class="col-md-1"><span style="font-size: 32px" id="step${i}">${i}</span></div>
                                <div class="col-md-8"></div>
                                <div class="col-md-1">
                                    <i class="bi bi-plus-lg" style="font-size: 1.5rem; color: #6c6c71"></i>
                                </div>
                                <div class="col-md-1">
                                    <i class="bi bi-trash" style="font-size: 1.7rem; color: #6c6c71"></i>
                                </div>
                                <div class="col-md-1"></div>
                            </div>
                            <div class="row justify-content-between" style="margin: 0px">
                                <div class="form-group col-md-12">
                                <textarea class="form-control" id="stepText${i}" rows="6"
                                          placeholder="請輸入步驟說明(最多 150 字)"></textarea>
                                </div>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </div>
            <%--            增刪修--%>
            <div class="col-md-1">
                <div class="row justify-content-start">
                    <button id="insert" type="button" class="btn btn-outline-danger">發布</button>
                </div>
                <div class="row justify-content-start">
                    <button type="button" class="btn btn-outline-danger">儲存</button>
                </div>
                <div class="row justify-content-start">
                    <button type="button" class="btn btn-outline-danger">取消</button>
                </div>
                <div class="row justify-content-start">
                    <button type="button" class="btn btn-outline-danger">刪除</button>
                </div>
            </div>
            <div class="col-md-2">
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
