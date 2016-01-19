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
            text-align:center;
    }
   </style>
</head>
	
<body>
                <div class="worker">
                <br/>
                    Sites：<br/>
                        <div>
                            <c:forEach items="${allSites}" var="site">
                                    <a href="#" >${site}</a>

                            </c:forEach>
                        </div>
                        <br/>
                    Worker主机：<br/>
                        <div>
                        <c:forEach items="${workerURLs}" var="worker">
                                <a href="http://${worker}" >${worker}</a>
                        </c:forEach>
                        </div>
        		</div>
</body>
</html>