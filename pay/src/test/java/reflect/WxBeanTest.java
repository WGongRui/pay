package reflect;

import org.junit.Test;
import org.xml.sax.SAXException;
import pay.utils.MD5;
import pay.utils.StrUtils;
import pay.utils.WeixinPayUtils;
import pay.weixn.WeixinPay;
import pay.weixn.bean.WxPayConfigBean;
import pay.weixn.bean.WxPayInfo;
import pay.weixn.bean.WxPayUnifiedOrderBean;
import pay.utils.BeanUtils;
import pay.weixn.constant.Constants;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

public class WxBeanTest {

    WxPayConfigBean getWxPayConfigBean() {
        WxPayConfigBean wxPayConfigBean = new WxPayConfigBean();
        wxPayConfigBean.setAppid("wx426b3015555a46be");
        wxPayConfigBean.setMch_id("1900009851");
        wxPayConfigBean.setKey("8934e7d15453e97507ef794cf7b0519d");
        wxPayConfigBean.setNotify_url("http://paysdk.weixin.qq.com/example/notify.php");
        wxPayConfigBean.setSign_type("NATIVE");
        wxPayConfigBean.setSign_type("MD5");
        return wxPayConfigBean;
    }

    // 测试数据
    @Test
    public void getMap() {

        WxPayConfigBean wxPayConfigBean = getWxPayConfigBean();

        WxPayUnifiedOrderBean wxPayUnifiedOrder = new WxPayUnifiedOrderBean();
        wxPayUnifiedOrder.setAttach("dsdsds").setDetail("ssdsds");

        Map<String,Object> map = BeanUtils.getParaValues(wxPayConfigBean,wxPayUnifiedOrder);
        for (String key : map.keySet()) {
            System.out.println(key + " : " + map.get(key));
        }
    }

    // 排序字段
    @Test
    public void sortMap() {
        WxPayConfigBean wxPayConfigBean = getWxPayConfigBean();
        WxPayUnifiedOrderBean wxPayUnifiedOrder = new WxPayUnifiedOrderBean();
        wxPayUnifiedOrder.setAttach("dsdsds").setDetail("ssdsds").setTime_start("dsdsd");
        BeanUtils.getParaValues(wxPayConfigBean,wxPayUnifiedOrder,wxPayUnifiedOrder).keySet().stream().sorted().forEach(
                e -> System.out.println(e)
        );
    }

    // 测试签名
    @Test
    public void sign() {

        WxPayConfigBean wxPayConfigBean = getWxPayConfigBean();

        WxPayUnifiedOrderBean wxPayUnifiedOrder = new WxPayUnifiedOrderBean();
        wxPayUnifiedOrder.setAttach("dsdsds").setDetail("ssdsds").setTime_start("dsdsd").setTotal_fee("100");

//        BeanUtils.getParaValues(wxPayConfigBean,wxPayUnifiedOrder).keySet().stream().sorted().forEach(
//                e -> System.out.println(e)
//        );

        Map<String,Object> objectMap = BeanUtils.getParaValues(wxPayConfigBean,wxPayUnifiedOrder);
        List<String> list = objectMap.keySet().stream().sorted().collect(Collectors.toList());

        StringBuilder builder = new StringBuilder();
        for (String field : list) {
            if(!field.equalsIgnoreCase("sign") && !field.equalsIgnoreCase("key")) {
                builder.append(field).append("=").append(objectMap.get(field)).append("&");
            }
        }
        builder.append("key=").append(wxPayConfigBean.getKey());
        wxPayUnifiedOrder.setSign(MD5.sign(builder.toString(), Constants.CHARSET));

        System.out.println(wxPayUnifiedOrder.getSign());
        System.out.println("------------------------------------------------------------------");
        BeanUtils.getParaValues(wxPayUnifiedOrder).keySet().stream().sorted().forEach(
                e -> System.out.println(e)
        );
    }

    @Test
    public void generateXml() {
        WxPayConfigBean wxPayConfigBean = getWxPayConfigBean();

        WxPayUnifiedOrderBean wxPayUnifiedOrder = new WxPayUnifiedOrderBean();
        wxPayUnifiedOrder.setAttach("dsdsds").setDetail("ssdsds").setTime_start("dsdsd").setTotal_fee("100");

        BeanUtils.getParaValues(wxPayUnifiedOrder).keySet().stream().sorted().forEach(
                e -> System.out.println(e)
        );

        Map<String,Object> objectMap = BeanUtils.getParaValues(wxPayConfigBean,wxPayUnifiedOrder);
        List<String> list = objectMap.keySet().stream().sorted().collect(Collectors.toList());

        StringBuilder builder = new StringBuilder();
        for (String field : list) {
            if(!field.equalsIgnoreCase("sign") && !field.equalsIgnoreCase("key")) {
                builder.append(field).append("=").append(objectMap.get(field)).append("&");
            }
        }
        builder.append("key=").append(wxPayConfigBean.getKey());
        wxPayUnifiedOrder.setSign(MD5.sign(builder.toString(),Constants.CHARSET));

        System.out.println(wxPayUnifiedOrder.getSign());
        System.out.println("------------------------------------------------------------------");

        StringBuilder xml = new StringBuilder();
        Map<String,Object> objectMap2 = BeanUtils.getParaValues(wxPayConfigBean,wxPayUnifiedOrder);
        List<String> list2 = objectMap2.keySet().stream().collect(Collectors.toList());

        xml.append("<xml>");
        for (String field : list2) {
            if(field.equalsIgnoreCase("detail")) {
                xml.append("<detail><![CDATA[").append(objectMap2.get(field)).append("]]</detail>");
            } else {
                xml.append("<").append(field).append(">").append(objectMap2.get(field)).append("</").append(field).append(">");
            }
        }
        xml.append("</xml>");

        System.out.println(xml.toString());
    }

    @Test
    public void generateXML2() {
        WxPayConfigBean wxPayConfigBean = getWxPayConfigBean();

        WxPayUnifiedOrderBean wxPayUnifiedOrder = new WxPayUnifiedOrderBean();
        wxPayUnifiedOrder.setAttach("dsdsds").setDetail("ssdsds");

        System.out.println(WeixinPayUtils.beanToXml(wxPayConfigBean,wxPayUnifiedOrder));
    }

    @Test
    public void toBean() throws SAXException, InvocationTargetException, IOException, IllegalAccessException, ParserConfigurationException, NoSuchMethodException {
        String str = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
                    "<root>" +
                        "<appid>13456798</appid>" +
                        "<return_code>Success</return_code>" +
                        "<return_msg>Return Msg</return_msg>" +
                    "</root>";
        str = "<xml><return_code><![CDATA[SUCCESS]]></return_code><return_msg><![CDATA[OK]]></return_msg><appid><![CDATA[wx2421b1c4370ec43b]]></appid><mch_id><![CDATA[10000100]]></mch_id><nonce_str><![CDATA[IITRi8Iabbblz1Jc]]></nonce_str><openid><![CDATA[oUpF8uMuAJO_M2pxb1Q9zNjWeS6o]]></openid><sign><![CDATA[7921E432F65EB8ED0CE9755F0E86D72F]]></sign><result_code><![CDATA[SUCCESS]]></result_code><prepay_id><![CDATA[wx201411101639507cbf6ffd8b0779950874]]></prepay_id><trade_type><![CDATA[JSAPI]]></trade_type></xml>";

        WxPayInfo payResult = WeixinPayUtils.xmlToBean(str);
        System.out.println(payResult.toString());
    }

    @Test
    public void testPostData() throws UnknownHostException, UnsupportedEncodingException {
        WxPayConfigBean wxPayConfigBean = getWxPayConfigBean();

        WxPayUnifiedOrderBean wxPayUnifiedOrder = new WxPayUnifiedOrderBean();
        wxPayUnifiedOrder.
                //////////设置基本信息/////////////////////
//                setSpbill_create_ip(InetAddress.getLocalHost().getHostAddress()).
                setSpbill_create_ip("222.73.202.154").
                setNonce_str(UUID.randomUUID().toString().replace("-","")).
                ///////////支付方式，扫码方式///////////////
                setTrade_type("NATIVE").
                //////////订单信息////////////////////////
                setOut_trade_no(System.currentTimeMillis()+"").
                setBody("test").
                setTime_start(StrUtils.getformatDate(new Date())).
                setTime_expire(StrUtils.getformatDate(new Date(System.currentTimeMillis()+1000*60*2))).
                setGoods_tag("Test").
                setTotal_fee("1").
                setProduct_id("123456789");
        String xml = WeixinPay.postData(wxPayConfigBean,wxPayUnifiedOrder);

        WxPayInfo info = WeixinPayUtils.xmlToBean(xml);
        if(info.isSuccess()) {
            System.out.println(info.getCode_url());
        }
//        System.out.println("http://qr.liantu.com/api.php?text="+ URLEncoder.encode(info.getCode_url(),"UTF-8").replace("&","%26"));
        System.out.println("http://paysdk.weixin.qq.com/example/qrcode.php?data=" + URLEncoder.encode(info.getCode_url(),"UTF-8"));
//        System.out.println(WinxinPay.postData(wxPayUnifiedOrder));
//        System.out.println(xml);
    }

}
