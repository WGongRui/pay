package wx.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pay.utils.StrUtils;
import pay.utils.WeixinPayUtils;
import pay.weixn.WeixinPay;
import pay.weixn.bean.WxPayConfigBean;
import pay.weixn.bean.WxPayInfo;
import pay.weixn.bean.WxPayUnifiedOrderBean;
import pay.weixn.exception.WxPayException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.net.URLEncoder;
import java.util.Date;
import java.util.UUID;


@Controller
@RequestMapping(path = "/pay")
public class WxPayController {

    @Autowired
    @Qualifier("wxPayConfigBean")
    WxPayConfigBean wxPayConfigBean;

    /**
     * 在这里可以设置好模板
     * @param total_fee
     * @throws IOException
     */
    @RequestMapping(path = "/wxpay",method = RequestMethod.POST)
    public String wxPay(String out_trade_no,String total_fee,String body, HttpSession session) throws IOException, WxPayException {
        WxPayUnifiedOrderBean wxPayUnifiedOrder = new WxPayUnifiedOrderBean();
        wxPayUnifiedOrder.
                //////////设置基本信息/////////////////////
                setNonce_str(UUID.randomUUID().toString().replace("-","")).
                setSpbill_create_ip("222.73.202.154").
                ///////////支付方式 : 扫码///////////////
                setTrade_type("NATIVE").
                //////////订单信息////////////////////////
                setOut_trade_no(out_trade_no).
                setBody(body).
                setTime_start(StrUtils.getformatDate(new Date())).
                setTime_expire(StrUtils.getformatDate(new Date(System.currentTimeMillis()+1000*60*2))).
                setTotal_fee(total_fee).
                setProduct_id("123456789");

        WxPayInfo info = WeixinPay.getQrUrl(wxPayConfigBean,wxPayUnifiedOrder);
        if(info != null && info.isSuccess()) {
            System.out.println(info.getCode_url());
            String qrurl = "http://qr.liantu.com/api.php?text=" + URLEncoder.encode(info.getCode_url(),"UTF-8").replace("&","%26");
//            String qrurl = "http://paysdk.weixin.qq.com/example/qrcode.php?data=" + URLEncoder.encode(info.getCode_url(),"UTF-8");
            session.setAttribute("qrurl",qrurl);
        } else {
            System.out.println(info.getErrorInfo());
        }
        session.setAttribute("orderInfo",wxPayUnifiedOrder);
        session.setAttribute("payInfo",info);
        return "/pay.jsp";

    }

    @RequestMapping(path = "/nofity")
    public void wxNotify(HttpServletRequest request, HttpServletResponse response) throws IOException {

        StringBuffer buffer = new StringBuffer();
        BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream()));
        String s = null;
        while((s = reader.readLine()) != null) {
            buffer.append(s);
        }

        WxPayInfo payResult = WeixinPayUtils.xmlToBean(buffer.toString());
        WxPayInfo payReturn = new WxPayInfo();
        if(payResult.isSuccess()) {
            System.out.println("交易成功");
            payReturn.setReturn_code("SUCCESS");
            payResult.setReturn_msg("OK");

            //////////////////////////// 执行自己的业务逻辑 STA///////////////////////////////////////////////////
            System.out.println("订单： " + payResult.getOut_trade_no() + "交易成功");

            /////////////////////////////执行自己的业务逻辑 END//////////////////////////////////////////////

        } else {
            System.out.println("交易失败");
            payReturn.setReturn_code("FAIL");
            payResult.setReturn_msg("");
        }

        String returnXml = WeixinPayUtils.beanToXml(payReturn);

        BufferedOutputStream os = new BufferedOutputStream(response.getOutputStream());
        os.write(returnXml.getBytes());
        os.flush();
        os.close();
    }
}
