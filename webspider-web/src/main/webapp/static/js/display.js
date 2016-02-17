$(function () {
    $(document).ready(function() {
        Highcharts.setOptions({
            global: {
                useUTC: false
            }
        });



    $("#ws").change(function(){
            
            var chart1 = null;
            var chart2 = null;
            var chart3 = null;
            var choice = $("#ws").val();

            var url1 = "http://" + choice + "/spider/" + $("li")[0].lastChild.data + "?callback=?";
            var url2 = "http://" + choice + "/spider/" + $("li")[1].lastChild.data + "?callback=?";
            var url3 = "http://" + choice + "/spider/" + $("li")[2].lastChild.data + "?callback=?";

                chart1 = new Highcharts.Chart({
                    chart: {
                        renderTo : "container",
                        type: 'spline',
                        animation: Highcharts.svg, // don't animate in old IE
                        marginRight: 10,
                        events: {
                            load: function() {

                                // set up the updating of the chart each second
                                var series = this.series[0];
                                var series1 = this.series[1];
                                var series2 = this.series[2];
                                var series3 = this.series[3];
                                var series4 = this.series[4];
                                var series5 = this.series[5];

                                setInterval(function() {
                                    var x = (new Date()).getTime(), // current time
                                        y;
                                        //url = new Array();

                                        /*for(var i=0; i<$('a').length; i++){
                                          url.push($('a')[i].href + "?callback=?");
                                        }*/

                                    $.getJSON(url1,function(result){
                                        y = result.successPageCount;
                                        y1 = result.errorPageCount;
                                        y2 = result.pagePerSecond;
                                        y3 = result.leftPageCount;
                                        y4 = result.threadAlive;
                                        y5 = result.totalPageCount;

                                        series.addPoint([x, y], true, true);
                                        series1.addPoint([x, y1], true, true);
                                        series2.addPoint([x, y2], true, true);
                                        series3.addPoint([x, y3], true, true);
                                        series4.addPoint([x, y4], true, true);
                                        series5.addPoint([x, y5], true, true);
                                    });

                                }, 1000);
                            }
                        }
                    },
                    title: {
                        text: 'Spider Status'
                    },
                    subtitle: {
                        text: 'Spider Id: ' + $("li")[0].lastChild.data
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
                            layout: 'vertical',
                            align: 'right',
                            verticalAlign: 'middle',
                            floating: true,
                            borderWidth: 0
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

                            for (i = -10; i <= 0; i++) {
                                data.push({
                                    x: time + i * 1000,
                                    y: Math.random()
                                });
                            }
                            return data;
                        })()
                    },
                    {
                        name: 'errorPageCount',
                            data: (function() {
                            // generate an array of random data
                            var data = [],
                                time = (new Date()).getTime(),
                                i;

                            for (i = -10; i <= 0; i++) {
                                data.push({
                                    x: time + i * 1000,
                                    y: Math.random()
                                });
                            }
                            return data;
                        })()
                    },
                    {
                        name: 'pagePerSecond',
                            data: (function() {
                            // generate an array of random data
                            var data = [],
                                time = (new Date()).getTime(),
                                i;

                            for (i = -10; i <= 0; i++) {
                                data.push({
                                    x: time + i * 1000,
                                    y: Math.random()
                                });
                            }
                            return data;
                        })()
                    },
                    {
                        name: 'leftPageCount',
                            data: (function() {
                            // generate an array of random data
                            var data = [],
                                time = (new Date()).getTime(),
                                i;

                            for (i = -10; i <= 0; i++) {
                                data.push({
                                    x: time + i * 1000,
                                    y: Math.random()
                                });
                            }
                            return data;
                        })()
                    },
                    {
                        name: 'threadAlive',
                            data: (function() {
                            // generate an array of random data
                            var data = [],
                                time = (new Date()).getTime(),
                                i;

                            for (i = -10; i <= 0; i++) {
                                data.push({
                                    x: time + i * 1000,
                                    y: Math.random()
                                });
                            }
                            return data;
                        })()
                    },
                    {
                        name: 'totalPageCount',
                            data: (function() {
                            // generate an array of random data
                            var data = [],
                                time = (new Date()).getTime(),
                                i;

                            for (i = -10; i <= 0; i++) {
                                data.push({
                                    x: time + i * 1000,
                                    y: Math.random()
                                });
                            }
                            return data;
                        })()
                    }]
                });
                chart2 = new Highcharts.Chart({
                    chart: {
                        renderTo : "container1",
                        type: 'spline',
                        animation: Highcharts.svg, // don't animate in old IE
                        marginRight: 10,
                        events: {
                            load: function() {

                                // set up the updating of the chart each second
                                var series = this.series[0];
                                var series1 = this.series[1];
                                var series2 = this.series[2];
                                var series3 = this.series[3];
                                var series4 = this.series[4];
                                var series5 = this.series[5];

                                setInterval(function() {
                                    var x = (new Date()).getTime(), // current time
                                        y;
                                        /*url = new Array();

                                        for(var i=0; i<$('a').length; i++){
                                          url.push($('a')[i].href + "?callback=?");
                                        }*/

                                    $.getJSON(url2,function(result){
                                        y = result.successPageCount;
                                        y1 = result.errorPageCount;
                                        y2 = result.pagePerSecond;
                                        y3 = result.leftPageCount;
                                        y4 = result.threadAlive;
                                        y5 = result.totalPageCount;

                                        series.addPoint([x, y], true, true);
                                        series1.addPoint([x, y1], true, true);
                                        series2.addPoint([x, y2], true, true);
                                        series3.addPoint([x, y3], true, true);
                                        series4.addPoint([x, y4], true, true);
                                        series5.addPoint([x, y5], true, true);
                                    });

                                }, 1000);
                            }
                        }
                    },
                    title: {
                        text: 'Spider Status'
                    },
                    subtitle: {
                        text: 'Spider Id: ' + $("li")[1].lastChild.data
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
                            layout: 'vertical',
                            align: 'right',
                            verticalAlign: 'middle',
                            floating: true,
                            borderWidth: 0
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

                            for (i = -10; i <= 0; i++) {
                                data.push({
                                    x: time + i * 1000,
                                    y: Math.random()
                                });
                            }
                            return data;
                        })()
                    },
                    {
                        name: 'errorPageCount',
                            data: (function() {
                            // generate an array of random data
                            var data = [],
                                time = (new Date()).getTime(),
                                i;

                            for (i = -10; i <= 0; i++) {
                                data.push({
                                    x: time + i * 1000,
                                    y: Math.random()
                                });
                            }
                            return data;
                        })()
                    },
                    {
                        name: 'pagePerSecond',
                            data: (function() {
                            // generate an array of random data
                            var data = [],
                                time = (new Date()).getTime(),
                                i;

                            for (i = -10; i <= 0; i++) {
                                data.push({
                                    x: time + i * 1000,
                                    y: Math.random()
                                });
                            }
                            return data;
                        })()
                    },
                    {
                        name: 'leftPageCount',
                            data: (function() {
                            // generate an array of random data
                            var data = [],
                                time = (new Date()).getTime(),
                                i;

                            for (i = -10; i <= 0; i++) {
                                data.push({
                                    x: time + i * 1000,
                                    y: Math.random()
                                });
                            }
                            return data;
                        })()
                    },
                    {
                        name: 'threadAlive',
                            data: (function() {
                            // generate an array of random data
                            var data = [],
                                time = (new Date()).getTime(),
                                i;

                            for (i = -10; i <= 0; i++) {
                                data.push({
                                    x: time + i * 1000,
                                    y: Math.random()
                                });
                            }
                            return data;
                        })()
                    },
                    {
                        name: 'totalPageCount',
                            data: (function() {
                            // generate an array of random data
                            var data = [],
                                time = (new Date()).getTime(),
                                i;

                            for (i = -10; i <= 0; i++) {
                                data.push({
                                    x: time + i * 1000,
                                    y: Math.random()
                                });
                            }
                            return data;
                        })()
                    }]
                });
                chart3 = new Highcharts.Chart({
                    chart: {
                        renderTo : "container2",
                        type: 'spline',
                        animation: Highcharts.svg, // don't animate in old IE
                        marginRight: 10,
                        events: {
                            load: function() {

                                // set up the updating of the chart each second
                                var series = this.series[0];
                                var series1 = this.series[1];
                                var series2 = this.series[2];
                                var series3 = this.series[3];
                                var series4 = this.series[4];
                                var series5 = this.series[5];

                                setInterval(function() {
                                    var x = (new Date()).getTime(), // current time
                                        y;
                                        /*url = new Array();

                                        for(var i=0; i<$('a').length; i++){
                                          url.push($('a')[i].href + "?callback=?");
                                        }*/

                                    $.getJSON(url3,function(result){
                                        y = result.successPageCount;
                                        y1 = result.errorPageCount;
                                        y2 = result.pagePerSecond;
                                        y3 = result.leftPageCount;
                                        y4 = result.threadAlive;
                                        y5 = result.totalPageCount;

                                        series.addPoint([x, y], true, true);
                                        series1.addPoint([x, y1], true, true);
                                        series2.addPoint([x, y2], true, true);
                                        series3.addPoint([x, y3], true, true);
                                        series4.addPoint([x, y4], true, true);
                                        series5.addPoint([x, y5], true, true);
                                    });

                                }, 1000);
                            }
                        }
                    },
                    title: {
                        text: 'Spider Status'
                    },
                    subtitle: {
                        text: 'Spider Id: ' + $("li")[2].lastChild.data
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
                            layout: 'vertical',
                            align: 'right',
                            verticalAlign: 'middle',
                            floating: true,
                            borderWidth: 0
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

                            for (i = -10; i <= 0; i++) {
                                data.push({
                                    x: time + i * 1000,
                                    y: Math.random()
                                });
                            }
                            return data;
                        })()
                    },
                    {
                        name: 'errorPageCount',
                            data: (function() {
                            // generate an array of random data
                            var data = [],
                                time = (new Date()).getTime(),
                                i;

                            for (i = -10; i <= 0; i++) {
                                data.push({
                                    x: time + i * 1000,
                                    y: Math.random()
                                });
                            }
                            return data;
                        })()
                    },
                    {
                        name: 'pagePerSecond',
                            data: (function() {
                            // generate an array of random data
                            var data = [],
                                time = (new Date()).getTime(),
                                i;

                            for (i = -10; i <= 0; i++) {
                                data.push({
                                    x: time + i * 1000,
                                    y: Math.random()
                                });
                            }
                            return data;
                        })()
                    },
                    {
                        name: 'leftPageCount',
                            data: (function() {
                            // generate an array of random data
                            var data = [],
                                time = (new Date()).getTime(),
                                i;

                            for (i = -10; i <= 0; i++) {
                                data.push({
                                    x: time + i * 1000,
                                    y: Math.random()
                                });
                            }
                            return data;
                        })()
                    },
                    {
                        name: 'threadAlive',
                            data: (function() {
                            // generate an array of random data
                            var data = [],
                                time = (new Date()).getTime(),
                                i;

                            for (i = -10; i <= 0; i++) {
                                data.push({
                                    x: time + i * 1000,
                                    y: Math.random()
                                });
                            }
                            return data;
                        })()
                    },
                    {
                        name: 'totalPageCount',
                            data: (function() {
                            // generate an array of random data
                            var data = [],
                                time = (new Date()).getTime(),
                                i;

                            for (i = -10; i <= 0; i++) {
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

});