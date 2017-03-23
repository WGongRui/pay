<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <!-- 最新版本的 Bootstrap 核心 CSS 文件 -->
    <link rel="stylesheet" href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
    <!-- 可选的 Bootstrap 主题文件（一般不用引入） -->
    <link rel="stylesheet" href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap-theme.min.css" integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp" crossorigin="anonymous">
    <!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
    <script src="https://cdn.bootcss.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>
    <title>create_forex_trade</title>
</head>

<style>
    body {
        background-color:aliceblue;
    }
</style>
<body class="text-center">
<br>
<div class="container">
    <h2>微信支付</h2><br>
    <form class="form-horizontal" action="pay/wxpay.do" method="POST" target="_blank">
        <div class="form-group">
            <label for="out_trade_no" class="col-sm-2 control-label">out_trade_no:</label>
            <div class="col-sm-10">
                <input type="text"  class="form-control"  name="out_trade_no" id="out_trade_no">
            </div>
        </div>
        <div class="form-group">
            <label for="total_fee" class="col-sm-2 control-label">Total_fee:</label>
            <div class="col-sm-10">
                <input type="text"  class="form-control"  name="total_fee" id="total_fee" value="1">
            </div>
        </div>
        <div class="form-group">
            <label for="body" class="col-sm-2 control-label">Body:</label>
            <div class="col-sm-10">
                <input type="text"  class="form-control"  name="body" id="body" value="test">
            </div>
        </div>
        <div class="row">
            <button type="submit" class="btn btn-default btn-lg">Pay</button>
        </div>
    </form>
</div>
</body>
<script>

    var out_trade_no = document.getElementById("out_trade_no");

    //设定时间格式化函数
    Date.prototype.format = function (format) {
        var args = {
            "M+": this.getMonth() + 1,
            "d+": this.getDate(),
            "h+": this.getHours(),
            "m+": this.getMinutes(),
            "s+": this.getSeconds(),
        };
        if (/(y+)/.test(format))
            format = format.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
        for (var i in args) {
            var n = args[i];
            if (new RegExp("(" + i + ")").test(format))
                format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? n : ("00" + n).substr(("" + n).length));
        }
        return format;
    };
    out_trade_no.value = 'test'+ new Date().format("yyyyMMddhhmmss");
</script>
</html>