<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="base" value="${pageContext.request.contextPath}" />

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
   <script type="text/javascript" src="${base}/static/js/jquery-1.8.3.min.js"></script>
   <script type="text/javascript" src="${base}/static/js/highcharts.js"></script>

<script type='text/javascript'>
	$(function () {
        $(document).ready(function() {
            Highcharts.setOptions({
                global: {
                    useUTC: false
                }
            });

            var chart;
            $('#container').highcharts({
                chart: {
                    type: 'spline',
                    animation: Highcharts.svg, // don't animate in old IE
                    marginRight: 10,
                    events: {
                        load: function() {

                            // set up the updating of the chart each second
                            var series = this.series[0];
                            setInterval(function() {
                                var x = (new Date()).getTime(), // current time
                                    y,
                                    url = new Array();                        //

                                    for(var i=0; i<$('a').length; i++){
                                      url.push($('a')[i].href + "?callback=?");
                                    }
                                $.getJSON(url[0],function(result){
                                	y = result.successPageCount;
                                	series.addPoint([x, y], true, true);
                                });

                            }, 1000);
                        }
                    }
                },
                title: {
                    text: 'spider status'
                },
                xAxis: {
                    type: 'datetime',
                    tickPixelInterval: 150
                },
                yAxis: {
                    title: {
                        text: 'Value'
                    },
                    plotLines: [{
                        value: 0,
                        width: 1,
                        color: '#808080'
                    }]
                },
                tooltip: {
                    formatter: function() {
                            return '<b>'+ this.series.name +'</b><br/>'+
                            Highcharts.dateFormat('%Y-%m-%d %H:%M:%S', this.x) +'<br/>'+
                            Highcharts.numberFormat(this.y, 2);
                    }
                },
                legend: {
                    enabled: false
                },
                exporting: {
                    enabled: false
                },
                series: [{
                    name: 'successPageCount',
                    data: (function() {
                        // generate an array of random data
                        var data = [],
                            time = (new Date()).getTime(),
                            i;

                        for (i = -19; i <= 0; i++) {
                            data.push({
                                x: time + i * 1000,
                                y: Math.random()
                            });
                        }
                        return data;
                    })()
                }]
            });
        });

    });
	</script>

   <title>Webspider</title>
   <style type="text/css">
   .spider{
            text-align:center;
    }
   </style>
</head>

<body>
                <div class="spider">
                <br/>
                    URLs：<br/>
                        <div>
                            <c:forEach items="${spiderURLs}" var="spider">
                                    <a href="http://${spider}" >${spider}</a>
                            </c:forEach>
                        </div>
                        <br/>
        		</div>
        		<div id="container" style="min-width:800px;height:400px;"></div>
</body>
</html>