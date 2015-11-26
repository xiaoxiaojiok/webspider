<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="base" value="${pageContext.request.contextPath}" />

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
   <script type="text/javascript" src="${base}/static/js/jquery-1.8.3.min.js"></script>
   <script type="text/javascript" src="${base}/static/js/highcharts.js"></script>
   <script>
   $(function () {
	    $(document).ready(function() {
	        Highcharts.setOptions({
	            global: {
	                useUTC: false
	            }
	        });
	        var chart;
	        chart = new Highcharts.Chart({
	            chart: {
	                renderTo: 'container',
	                type: 'spline',
	                animation: Highcharts.svg,
	                // don't animate in old IE               
	                marginRight: 10,
	                events: {
	                    load: function() {}
	                }
	            },
	            title: {
	                text: 'Live random data'
	            },
	            xAxis: {
	                type: 'datetime',
	                tickPixelInterval: 150
	            },
	            yAxis: [{
	                title: {
	                    text: 'Value'
	                },
	                plotLines: [{
	                    value: 0,
	                    width: 1,
	                    color: '#808080'
	                }]
	            },
	            {
	                title: {
	                    text: 'Name'
	                },
	                plotLines: [{
	                    value: 0,
	                    width: 1,
	                    color: '#808080'
	                }]
	            }],
	            tooltip: {
	                formatter: function() {
	                    return '<b>' + this.series.name + '</b><br/>' + Highcharts.dateFormat('%Y-%m-%d %H:%M:%S', this.x) + '<br/>' + Highcharts.numberFormat(this.y, 2);
	                }
	            },
	            legend: {
	                enabled: false
	            },
	            exporting: {
	                enabled: false
	            },
	            series: [{
	                name: 'Random data',
	                data: (function() { // generate an array of random data                             
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
	            },
	            {
	                name: 'Random data',
	                data: (function() { // generate an array of random data                             
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
	        }); // set up the updating of the chart each second                     
	        var series = chart.series[0];
	        var series1 = chart.series[1];
	        setInterval(function() {
	            var x = (new Date()).getTime(),
	            // current time         
	            y = Math.random();
	            series.addPoint([x, y + 1], true, true);
	            series1.addPoint([x, y - 1], true, true);
	        },
	        1000);
	    });
	});
   </script>
   <title>Webspider</title>
</head>
	
<body>
   <div id="container" style="min-width:800px;height:400px;"></div>
</body>
</html>