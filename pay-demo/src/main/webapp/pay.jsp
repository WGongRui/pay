<%@ page import="pay.weixn.bean.WxPayUnifiedOrderBean" %>
<%@ page import="pay.weixn.bean.WxPayInfo" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<html>
<head>
    <title>支付</title>
    <meta charset="utf-8" content="text/html"/>
    <style>
        body{
            text-align: center;
            background-color: aliceblue;
        }
    </style>
</head>
<body>
<br>
    <h1>微信扫码支付</h1>
    <%
        WxPayUnifiedOrderBean wxPayUnifiedOrder = (WxPayUnifiedOrderBean) session.getAttribute("orderInfo");
        WxPayInfo info = (WxPayInfo) session.getAttribute("payInfo");
    %>
    <h3>订单号： <%=wxPayUnifiedOrder.getOut_trade_no()%></h3>
    <h3>金  额： <%=wxPayUnifiedOrder.getTotal_fee()%> </h3>
    <h3>详  情： <%=wxPayUnifiedOrder.getBody()%> </h3>

    <%
        try{
            if(info.isSuccess()) {
                out.println("<img src='");
                out.println(session.getAttribute("qrurl"));
                out.println("'/>");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    %>

</body>
</html>
