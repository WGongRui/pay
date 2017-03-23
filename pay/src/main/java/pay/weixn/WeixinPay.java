package pay.weixn;

import pay.utils.WeixinPayUtils;
import pay.weixn.bean.WxPayConfigBean;
import pay.weixn.bean.WxPayInfo;
import pay.weixn.constant.Constants;
import pay.weixn.exception.WxPayException;
import pay.utils.BeanUtils;
import pay.utils.MD5;
import pay.utils.StrUtils;
import pay.weixn.bean.WxPayUnifiedOrderBean;

import java.io.*;
import java.net.*;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class WeixinPay {

	public static WxPayInfo getQrUrl(WxPayConfigBean wxPayConfigBean,WxPayUnifiedOrderBean wxPayUnifiedOrder) throws WxPayException {
        if(wxPayUnifiedOrder.getTrade_type().equalsIgnoreCase("NATIVE")) {
			return WeixinPayUtils.xmlToBean(unifiedOrder(wxPayConfigBean,wxPayUnifiedOrder));
		} else if(wxPayUnifiedOrder.getTrade_type().equalsIgnoreCase("NATIVE")) {

        }
		return null;
	}

    public static WxPayInfo wapPay(WxPayConfigBean wxPayConfigBean,WxPayUnifiedOrderBean wxPayUnifiedOrder) {

        return null;
    }

	/**
	 * 
	 * 统一下单，WxPayUnifiedOrder中out_trade_no、body、total_fee、trade_type必填
	 * appid、mchid、spbill_create_ip、nonce_str不需要填入
	 * @param wxPayConfigBean $inputObj
	 * @return 成功时返回，其他抛异常
	 * @throws WxPayException 
	 */
	private static String unifiedOrder(WxPayConfigBean wxPayConfigBean,WxPayUnifiedOrderBean wxPayUnifiedOrder) throws WxPayException{
		
		//	检测必填参数
		if(StrUtils.strIsNull(wxPayUnifiedOrder.getOut_trade_no())) {
			throw new WxPayException("缺少统一支付接口必填参数out_trade_no！");
		}else if(StrUtils.strIsNull(wxPayUnifiedOrder.getBody())) {
			throw new WxPayException("缺少统一支付接口必填参数body！");
		}else if(StrUtils.strIsNull(wxPayUnifiedOrder.getTotal_fee())) {
			throw new WxPayException("缺少统一支付接口必填参数total_fee！");
		}else if(StrUtils.strIsNull(wxPayUnifiedOrder.getTrade_type())) {
			throw new WxPayException("缺少统一支付接口必填参数trade_type！");
		}
		
		//	关联参数
		if(wxPayUnifiedOrder.getTrade_type().equals("JSAPI") && StrUtils.strIsNull(wxPayUnifiedOrder.getOpenid())) {
			throw new WxPayException("统一支付接口中，缺少必填参数openid！trade_type为JSAPI时，openid为必填参数！");
		}
		if(wxPayUnifiedOrder.getTrade_type().equals("NATIVE") && StrUtils.strIsNull(wxPayUnifiedOrder.getProduct_id())) {
			throw new WxPayException("统一支付接口中，缺少必填参数product_id！trade_type为JSAPI时，product_id为必填参数！");
		}
		
		// 商家信息
		if(StrUtils.strIsNull(wxPayConfigBean.getAppid())) {
			throw new WxPayException("统一支付接口中，缺少必填参数公众账号 appid!");
		}
		if(StrUtils.strIsNull(wxPayConfigBean.getMch_id())) {
			throw new WxPayException("统一支付接口中，缺少必填参数商户号 mch_id!");
		}
		if(StrUtils.strIsNull(wxPayUnifiedOrder.getSpbill_create_ip())) {
			throw new WxPayException("统一支付接口中，缺少必填参数终端 ip	spbill_create_ip!");
		}

        return postData(wxPayConfigBean,wxPayUnifiedOrder);
	}

    // 设置签名
	private static void setSign(WxPayConfigBean wxPayConfigBean,WxPayUnifiedOrderBean wxPayUnifiedOrder) {
        // 订单信息
		Map<String,Object> objectMap = BeanUtils.getParaValues(wxPayConfigBean,wxPayUnifiedOrder);
        // 排序
		List<String> list = objectMap.keySet().stream().sorted().collect(Collectors.toList());
        if(list.contains("sign")) list.remove("sign");
        if(list.contains("key")) list.remove("key");

		StringBuilder builder = new StringBuilder();

		for (String field : list) {
            builder.append(field).append("=").append(objectMap.get(field)).append("&");
		}
		builder.append("key=").append(wxPayConfigBean.getKey());
		String origin = builder.toString();
		wxPayUnifiedOrder.setSign(MD5.sign(origin,Constants.CHARSET).toUpperCase());
	}

	// 向微信服务器发送数据
    public static String postData(WxPayConfigBean wxPayConfigBean,WxPayUnifiedOrderBean wxPayUnifiedOrder) {
        try {

			setSign(wxPayConfigBean,wxPayUnifiedOrder);

			String xml = WeixinPayUtils.beanToXml(wxPayConfigBean,wxPayUnifiedOrder);

            System.out.println(xml);
            URL url = new URL(new String(Constants.WXPAYURL));
			HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

			httpURLConnection.setDoOutput(true);
			httpURLConnection.setConnectTimeout(Constants.TIMEOUT);
			httpURLConnection.setReadTimeout(Constants.TIMEOUT);
			httpURLConnection.setRequestMethod("POST");
			httpURLConnection.setDoInput(true);

			BufferedOutputStream os = new BufferedOutputStream(httpURLConnection.getOutputStream());
			os.write(xml.getBytes(Constants.CHARSET));
			os.flush();
			os.close();

			StringBuffer buffer = new StringBuffer();
			BufferedReader reader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream(), Constants.CHARSET));
			String s = null;
			while((s = reader.readLine()) != null) {
				buffer.append(s);
			}
			reader.close();

			httpURLConnection.disconnect();

			return buffer.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
