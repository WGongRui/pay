package pay.weixn.bean;

public class WxPayUnifiedOrderBean {

	/**
	 * 设备号  否	
	 */
    protected String device_info;

	/**
	 * 随机字符串  是
	 */
    protected String nonce_str;
	
	/**
	 *签名 是 
	 *签名算法
	 * https://pay.weixin.qq.com/wiki/doc/api/native.php?chapter=4_3
	 */
    protected String sign;

	/**
	 * 商品描述  必须
	 */
    protected String body;
	
	/**
	 * 商品详情	否
	 */
    protected String detail;
	
	/**
	 * 附加数据	否
	 */
    protected String attach;
	
	/**
	 * 商户订单号	 是
	 */
    protected String out_trade_no;

	
	/**
	 * 标价金额  是	
	 */
    protected String total_fee;
	
	/**
	 * 终端IP 是
	 * APP和网页支付提交用户端ip，Native支付填调用微信支付API的机器IP。
	 */
    protected String spbill_create_ip;
	
	/**
	 * 交易起始时间  否
	 * 订单生成时间，格式为yyyyMMddHHmmss，如2009年12月25日9点10分10秒表示为20091225091010。其他详见时间规则
	 */
    protected String time_start;
	
	/**
	 * 交易结束时间 否
	 * 订单失效时间，格式为yyyyMMddHHmmss，如2009年12月27日9点10分10秒表示为20091227091010。其他详见时间规则
	 * 注意：最短失效时间间隔必须大于5分钟
	 */
    protected  String time_expire;
	
	/**
	 * 商品标记	否
	 * 商品标记，使用代金券或立减优惠功能时需要的参数，说明详见代金券或立减优惠
	 */
    protected String goods_tag;

	/**
	 * 交易类型	是
	 * 取值如下：JSAPI，NATIVE，APP等，说明详见参数规定
	 * JSAPI--公众号支付、NATIVE--原生扫码支付、APP--app支付，统一下单接口trade_type的传参可参考这里
     * MICROPAY--刷卡支付，刷卡支付有单独的支付接口，不调用统一下单接口
	 */
    protected  String trade_type;
	
	/**
	 * 商品ID 否
	 */
    protected String product_id;
	
	/**
	 * 用户标识	否
	 * trade_type=JSAPI时（即公众号支付），此参数必传，此参数为微信用户在商户对应appid下的唯一标识。
	 */
    protected String openid;

	public String getDevice_info() {
		return device_info;
	}

	public WxPayUnifiedOrderBean setDevice_info(String deviceInfo) {
		device_info = deviceInfo;
		return this;
	}

	public String getNonce_str() {
		return nonce_str;
	}

	public WxPayUnifiedOrderBean setNonce_str(String nonceStr) {
		nonce_str = nonceStr;
		return this;
	}

	public String getSign() {
		return sign;
	}


	public String getBody() {
		return body;
	}

	public WxPayUnifiedOrderBean setBody(String body) {
		this.body = body;
		return this;
	}

	public String getDetail() {
		return detail;
	}

	public WxPayUnifiedOrderBean setDetail(String detail) {
		this.detail = detail;
		return this;
	}

	public String getAttach() {
		return attach;
	}

	public WxPayUnifiedOrderBean setAttach(String attach) {
		this.attach = attach;
		return this;
	}

	public String getOut_trade_no() {
		return out_trade_no;
	}

	public WxPayUnifiedOrderBean setOut_trade_no(String outTradeNo) {
		out_trade_no = outTradeNo;
		return this;
	}

	public String getTotal_fee() {
		return total_fee;
	}

	public WxPayUnifiedOrderBean setTotal_fee(String totalFee) {
		total_fee = totalFee;
		return this;
	}

	public String getSpbill_create_ip() {
		return spbill_create_ip;
	}

	public WxPayUnifiedOrderBean setSpbill_create_ip(String spbillCreateIp) {
		spbill_create_ip = spbillCreateIp;
		return this;
	}

	public String getTime_start() {
		return time_start;
	}

	public WxPayUnifiedOrderBean setTime_start(String timeStart) {
		time_start = timeStart;
		return this;
	}

	public String getTime_expire() {
		return time_expire;
	}

	public WxPayUnifiedOrderBean setTime_expire(String timeExpire) {
		time_expire = timeExpire;
		return this;
	}

	public String getGoods_tag() {
		return goods_tag;
	}

	public WxPayUnifiedOrderBean setGoods_tag(String goodsTag) {
		goods_tag = goodsTag;
		return this;
	}

	public String getTrade_type() {
		return trade_type;
	}

	public WxPayUnifiedOrderBean setTrade_type(String tradeType) {
		trade_type = tradeType;
		return this;
	}

	public String getProduct_id() {
		return product_id;
	}

	public WxPayUnifiedOrderBean setProduct_id(String productId) {
		product_id = productId;
		return this;
	}

	public String getOpenid() {
		return openid;
	}

	public WxPayUnifiedOrderBean setOpenid(String openid) {
		this.openid = openid;
		return this;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

}