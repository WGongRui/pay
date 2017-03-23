package pay.utils;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import pay.weixn.bean.WxPayConfigBean;
import pay.weixn.bean.WxPayInfo;
import pay.weixn.bean.WxPayUnifiedOrderBean;
import pay.weixn.constant.Constants;
import pay.weixn.exception.WxPayException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class WeixinPayUtils {

    /**
     * 读取Bean中的字段值，封装成 map 最后生成要发送的xml数据
     * @param objs
     * @return
     */
	public static String beanToXml(Object...objs) {
		StringBuilder xml = new StringBuilder();

        Map<String,Object> map = BeanUtils.getParaValues(objs);

		List<String> list = map.keySet().stream().sorted().collect(Collectors.toList());

		xml.append("<xml>");

		for (String field : list) {
            if(field.equals("key")) continue;
			if(field.equalsIgnoreCase("detail")) {
				xml.append("<detail><![CDATA[").append(map.get(field)).append("]]</detail>");
			} else {
				xml.append("<").append(field).append(">").append(map.get(field)).append("</").append(field).append(">");
			}
		}
		xml.append("</xml>");

		return xml.toString();
	}

    public static WxPayInfo xmlToBean(String str) {
        WxPayInfo payResult = new WxPayInfo();

        List<String> fields = Arrays.asList(WxPayInfo.class.getSuperclass().getDeclaredFields()).
                stream().
                map(field -> field.getName()).
                collect(Collectors.toList());
        fields.addAll(Arrays.asList(WxPayInfo.class.getDeclaredFields()).
                stream().
                map(field -> field.getName()).
                collect(Collectors.toList()));
        try{
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document doc = builder.parse(new ByteArrayInputStream(str.getBytes()));
            Element root = doc.getDocumentElement();
            NodeList ns = root.getChildNodes();
            int len = ns.getLength();
            for(int i = 0;i < len;i++) {
                Node node = ns.item(i);
                String name = node.getNodeName();
                String value = node.getTextContent();
                if(fields.contains(name)) {
                   String methodName = "set" + name.substring(0,1).toUpperCase() + name.substring(1);
                    try {
                        payResult.getClass().getMethod(methodName,String.class).invoke(payResult, value);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            payResult = null;
        }
        return payResult;
    }
}
