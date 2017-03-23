package pay.weixn.bean;

/**
 * 微信支付 配置文件
 */
public class WxPayConfigBean {

    /**
     * 公众账号ID 必须
     */
    protected String appid;

    /**
     * 商户号  必须
     */
    protected String mch_id;

    /**
     * 签名类型 否
     */
    protected String sign_type;

    /**
     * 标价币种	否  默认 CNY：人民币
     */
    protected String fee_type;

    /**
     * 通知地址	是
     * 异步接收微信支付结果通知的回调地址，通知url必须为外网可访问的url，不能携带参数。
     */
    protected String notify_url;

    /**
     * 指定支付方式	否
     * no_credit
     * 上传此参数 no_credit--可限制用户不能使用信用卡支付
     */
    protected String limit_pay;

    /**
     * 商户支付密钥  是
     */
    protected String key;

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getMch_id() {
        return mch_id;
    }

    public void setMch_id(String mch_id) {
        this.mch_id = mch_id;
    }

    public String getSign_type() {
        return sign_type;
    }

    public void setSign_type(String sign_type) {
        this.sign_type = sign_type;
    }

    public String getFee_type() {
        return fee_type;
    }

    public void setFee_type(String fee_type) {
        this.fee_type = fee_type;
    }

    public String getNotify_url() {
        return notify_url;
    }

    public void setNotify_url(String notify_url) {
        this.notify_url = notify_url;
    }

    public String getLimit_pay() {
        return limit_pay;
    }

    public void setLimit_pay(String limit_pay) {
        this.limit_pay = limit_pay;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
