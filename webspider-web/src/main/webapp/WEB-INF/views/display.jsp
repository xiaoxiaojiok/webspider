<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="base" value="${pageContext.request.contextPath}" />

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="${base}/static/js/jquery-1.8.3.min.js"></script>
<script type="text/javascript" src="${base}/static/js/highcharts.js"></script>
<script type="text/javascript" src="${base}/static/js/display.js"></script>

<title>Webspider</title>

<style type="text/css">
    .spider{
            text-align:center;
    }

    .dis{
            visibility:hidden;
    }


</style>

</head>
<body>
    <div class="spider">
    <br/>
        <span style="font-size:18px;">
            <select id="ws" name="workerSelecter">
                <option value="0" selected>请选择主机</option>
                <c:forEach items="${workerURLs}" var="workers">
                    <option value="${workers}">
                        ${workers}
                    </option>
                </c:forEach>
            </select>
        </span>

            <!--<div class="dis">
                <c:forEach items="${spiderURLs}" var="spider">
                        <a href="http://${spider}" >${spider}</a>
                </c:forEach>
            </div>-->

            <div class="dis">
                <c:forEach items="${spiderId}" var="spiderId">
                        <li>${spiderId}</li>
                </c:forEach>
            </div>
    </div>
    <div id="container" class="con" style="min-width:700px;height:400px;margin-bottom:50px"></div>
    <div id="container1" class="con" style="min-width:700px;height:400px;margin-bottom:50px"></div>
    <div id="container2" class="con" style="min-width:700px;height:400px;margin-bottom:50px"></div>

<script>
    //console.log($("li")[0].lastChild.data);
</script>
</body>
</html>