<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="base" value="${pageContext.request.contextPath}" />

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
   <script type="text/javascript" src="${base}/static/js/jquery-1.8.3.min.js"></script>
   <script type="text/javascript" src="${base}/static/js/highcharts.js"></script>
   <title>Webspider</title>
   <style type="text/css">
   .worker{
            margin:0px auto;
            text-align:center;
    }
   </style>
</head>
	
<body>
		<c:forEach items="${workerURLs}" var="worker">
		<div class="worker">
				<a href="http://${worker}" >${worker}</a>
		</div>
		</c:forEach>
</body>
</html>