package pay.utils;

import pay.weixn.bean.WxPayUnifiedOrderBean;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class BeanUtils {

    /**
     * 返回的List中的数据格式是
     * 将bean中的字段值以 key -- value 方式返回
     * 其中数据为null 的不返回
     * @param obj
     * @return
     */
    private static Map getParaValue(Object obj) {

        Map<String,String> map = new HashMap<>();

        Arrays.asList(obj.getClass().getDeclaredFields()).
                stream().
                forEach(field -> {
                    map.put(field.getName().toUpperCase(),field.getName());
                });

        Map<String,Object> keyValues = new HashMap<>();

        Arrays.asList(obj.getClass().getDeclaredMethods()).
                stream().
                filter(method -> !method.getName().startsWith("set")).
                forEach(method -> {
                    try {
                        String methodName = method.getName();
                        Object value = obj.getClass().getDeclaredMethod(methodName).invoke(obj);
                        if(value != null) {
                            keyValues.put(map.get(methodName.replace("get","").toUpperCase()),value);
                        }
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                });
        return keyValues;
    }

    /**
     *
     * @param objs
     * @return
     */
    public static Map getParaValues(Object...objs) {
        // 读取数据
        Map<String,Object> map = new HashMap<>();

        for(Object o : objs) {
            map.putAll(getParaValue(o));
        }
        return map;
    }
}
