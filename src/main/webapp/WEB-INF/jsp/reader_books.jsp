<%--
  Created by IntelliJ IDEA.
  User: ryo
  Date: 2018/10/30
  Time: 10:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>全部图书</title>
    <link rel="stylesheet" href="../../static/css/bootstrap.min.css">
    <script src="../../static/js/jquery-3.2.1.js"></script>
    <script src="../../static/js/bootstrap.min.js"></script>
    <style>
        body {
            background-color: rgb(240, 242, 245);
        }
    </style>
</head>
<body>
<nav class="navbar navbar-default" role="navigation" style="background-color:#fff" style="background-color:#fff">
    <div class="container-fluid">
        <div class="navbar-header" style="margin-left: 8%;margin-right: 1%">
            <a class="navbar-brand " href="reader_main.action"><p class="text-primary">我的图书馆</p></a>
        </div>
        <div class="collapse navbar-collapse" id="example-navbar-collapse">
            <ul class="nav navbar-nav navbar-left">
                <li>
                    <a href="reader_querybook.action">
                        图书查询
                    </a>
                </li>
                <li>
                    <a href="reader_info.action">
                        个人信息
                    </a>
                </li>
                <li>
                    <a href="mylend.action">
                        我的借还
                    </a>
                </li>
                <li>
                    <a href="reader_repasswd.action">
                        密码修改
                    </a>
                </li>
            </ul>
            <ul class="nav navbar-nav navbar-right">
                <li><a href="#"><span class="glyphicon glyphicon-user"></span>&nbsp;&nbsp;您好，${readercard.name}</a>
                </li>
                <li><a href="logout.action"><span class="glyphicon glyphicon-log-in"></span>&nbsp;&nbsp;退出</a></li>
            </ul>
        </div>
    </div>
</nav>

<div style="padding: 70px 550px 10px">
    <form method="post" action="reader_querybook_do.action" class="form-inline" id="searchform">
        <div class="input-group">
            <input type="text" placeholder="输入图书名" class="form-control" id="search" name="searchWord"
                   class="form-control">
            <span class="input-group-btn">
                            <input type="submit" value="搜索" class="btn btn-default">
            </span>
        </div>
    </form>
    <script>
        function mySubmit(flag) {
            return flag;
        }

        $("#searchform").submit(function () {
            var val = $("#search").val();
            if (val == '') {
                alert("请输入关键字");
                return mySubmit(false);
            }
        })
    </script>
</div>

<div style="position: relative;">
    <c:if test="${!empty succ}">
        <div class="alert alert-success alert-dismissable">
            <button type="button" class="close" data-dismiss="alert"
                    aria-hidden="true">
                &times;
            </button>
                ${succ}
        </div>
    </c:if>
    <c:if test="${!empty error}">
        <div class="alert alert-danger alert-dismissable">
            <button type="button" class="close" data-dismiss="alert"
                    aria-hidden="true">
                &times;
            </button>
                ${error}
        </div>
    </c:if>
</div>

<div class="panel panel-default" style="width: 90%;margin-left: 5%">
    <div class="panel-heading" style="background-color: #fff">
        <h3 class="panel-title">
            全部图书
        </h3>
    </div>
    <div class="panel-body">
        <table class="table table-hover">
            <thead>
            <tr>
                <th>书名</th>
                <th>作者</th>
                <th>出版社</th>
                <th>ISBN</th>
                <th>价格</th>
                <th>剩余</th>
                <th>状态</th>
                <th>详情</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${books}" var="book">
                <tr>
                    <td><c:out value="${book.name}"></c:out></td>
                    <td><c:out value="${book.author}"></c:out></td>
                    <td><c:out value="${book.publish}"></c:out></td>
                    <td><c:out value="${book.isbn}"></c:out></td>
                    <td><c:out value="${book.price}"></c:out></td>
                    <td><c:out value="${book.num}"></c:out></td>
<%--                    <c:if test="${book.state==1}">--%>
<%--                        <td>在馆</td>--%>
<%--                    </c:if>--%>
<%--                    <c:if test="${book.state==0}">--%>
<%--                        <td>借出</td>--%>
<%--                    </c:if>--%>
                    <c:if test="${book.num!=0}">
                        <td>在馆</td>
                    </c:if>
                    <c:if test="${book.num==0}">
                        <td>全部借出</td>
                    </c:if>
                    <td><a href="readerbookdetail.action?bookId=<c:out value="${book.bookId}"></c:out>">
                        <button type="button" class="btn btn-success btn-xs">详情</button>
                    </a></td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
    <div class="panel-footer">
        <div class="pagination">
            <div class="form-group form-inline">
                总共 ${ page.pages } 页，
                第 ${ page.pageNum } 页，
                共 ${ page.total } 条数据。
                每页 <select class="form-control" onchange="submitPageSize(this)">
                <option value="5" <c:if test="${ page.pageSize == 5 }">selected</c:if> >5</option>
                <option value="10" <c:if test="${ page.pageSize == 10 }">selected</c:if> >10</option>
            </select> 条
            </div>

            <script type="text/javascript">
                // 提交条数
                function submitPageSize(who){
                    // alert(who.value);
                    // 发送请求
                    location.href="${ pageContext.request.contextPath }/reader_querybook.action?pageSize="+who.value + "&pageNum=" + ${ page.pageNum};
                }
            </script>
        </div>

        <div class="box-tools pull-right">
            <ul class="pagination pagination-right">
                <li><a href="javascript:sumbitPageNum(1)" aria-label="Previous">首页</a></li>
                <li><a href="javascript:sumbitPageNum(${ page.pageNum - 1 })">上一页</a></li>
                <c:forEach begin="1" end="${ page.pages }" var="i">
                    <li><a href="javascript:sumbitPageNum(${ i })">${ i }</a></li>
                </c:forEach>

                <li><a href="javascript:sumbitPageNum(${ page.pageNum + 1 })">下一页</a></li>
                <li><a href="javascript:sumbitPageNum(${ page.pages })" aria-label="Next">尾页</a></li>
            </ul>
        </div>

        <script type="text/javascript">
            /* 发送请求，传的参数当前页 */
            function sumbitPageNum(pageNum){
                if (pageNum > ${page.pages} ){
                    pageNum = ${ page.pages };
                }
                if (pageNum < 1){
                    pageNum = 1;
                }

                location.href="${ pageContext.request.contextPath }/reader_querybook.action?pageNum="+pageNum + "&pageSize=" + ${page.pageSize};
            }
        </script>

    </div>

</div>


</body>
</html>
