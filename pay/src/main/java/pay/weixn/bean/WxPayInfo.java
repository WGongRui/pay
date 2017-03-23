package pay.weixn.bean;

public class WxPayInfo extends WxPayUnifiedOrderBean {

    /**
     * 返回状态码
     * SUCCESS/FAIL
     * 此字段是通信标识，非交易标识，交易是否成功需要查看result_code来判断
     */
    protected String return_code;

    /**
     * 返回信息
     * 返回信息，如非空，为错误原因
     * 签名失败
     * 参数格式校验错误
     */
    protected String return_msg;

    ///////////////////////////////////// 以下字段在return_code为SUCCESS的时候有返回 /////////////////////////////////////////

    /**
     * 设备号
     * 自定义参数，可以为请求支付的终端设备号等
     */
    protected String device_info;

    /**
     * 业务结果
     * SUCCESS	SUCCESS/FAIL
     */
    protected String result_code;

    /**
     * 错误代码
     *
     * NOAUTH	            商户无此接口权限	商户未开通此接口权限	         请商户前往申请此接口权限
     * NOTENOUGH	        余额不足	        用户帐号余额不足	             用户帐号余额不足，请用户充值或更换支付卡后再支付
     * ORDERPAID	        商户订单已支付	商户订单已支付，无需重复操作	 商户订单已支付，无需更多操作
     * ORDERCLOSED	        订单已关闭	    当前订单已关闭，无法支付	     当前订单已关闭，请重新下单
     * SYSTEMERROR	        系统错误	        系统超时	                     系统异常，请用相同参数重新调用
     * APPID_NOT_EXIST	    APPID不存在	    参数中缺少APPID	             请检查APPID是否正确
     * MCHID_NOT_EXIST	    MCHID不存在	    参数中缺少MCHID	             请检查MCHID是否正确
     * APPID_MCHID_NOT_     MATCH	        appid和mch_id不匹配	         appid和mch_id不匹配	请确认appid和mch_id是否匹配
     * LACK_PARAMS	        缺少参数	        缺少必要的请求参数	             请检查参数是否齐全
     * OUT_TRADE_NO_USED	商户订单号重复	同一笔交易不能多次提交	         请核实商户订单号是否重复提交
     * SIGNERROR	        签名错误	        参数签名结果不正确	             请检查签名参数和方法是否都符合签名算法要求
     * XML_FORMAT_ERROR	XML 格式错误	        XML格式错误	                 请检查XML参数格式是否正确
     * REQUIRE_POST_METHOD	请使用post方法	未使用post传递参数 	         请检查请求参数是否通过post方法提交
     * POST_DATA_EMPTY	    post数据为空	    post数据不能为空	             请检查post数据是否为空
     * NOT_UTF8	            编码格式错误	    未使用指定编码格式	             请使用UTF-8编码格式
     */
    protected String err_code;

    /**
     * 系统错误	错误信息描述
     */
    protected String err_code_des;

    //////////////////////////////////////////// 以下字段在return_code 和result_code都为SUCCESS的时候有返回 ////////////////////////////////////////

    /**
     * 预支付交易会话标识
     */
    protected String prepay_id;

    /**
     * 二维码链接
     */
    protected String code_url;

    public String getReturn_code() {
        return return_code;
    }

    public void setReturn_code(String return_code) {
        this.return_code = return_code;
    }

    public String getReturn_msg() {
        return return_msg;
    }

    public void setReturn_msg(String return_msg) {
        this.return_msg = return_msg;
    }

    public String getDevice_info() {
        return device_info;
    }

    public String getNonce_str() {
        return nonce_str;
    }


    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getResult_code() {
        return result_code;
    }

    public void setResult_code(String result_code) {
        this.result_code = result_code;
    }

    public String getErr_code() {
        return err_code;
    }

    public void setErr_code(String err_code) {
        this.err_code = err_code;
    }

    public String getErr_code_des() {
        return err_code_des;
    }

    public void setErr_code_des(String err_code_des) {
        this.err_code_des = err_code_des;
    }

    public String getTrade_type() {
        return trade_type;
    }

    public String getPrepay_id() {
        return prepay_id;
    }

    public void setPrepay_id(String prepay_id) {
        this.prepay_id = prepay_id;
    }

    public String getCode_url() {
        return code_url;
    }

    public void setCode_url(String code_url) {
        this.code_url = code_url;
    }

    // 交易是否成功
    public boolean isSuccess() {

        return getResult_code().equals("SUCCESS") && getReturn_code().equals("SUCCESS");
    }

    // 返回错误信息
    public String getErrorInfo() {
        if(isSuccess()) return null;
        if(getReturn_msg() != null) {
            return getReturn_msg();
        } else {
            return getErr_code() + "\t" + getErr_code_des();
        }
    }

    @Override
    public String toString() {
        return "PayInfo {" +
                "return_code='" + return_code + '\'' +
                ", return_msg='" + return_msg + '\'' +
                ", device_info='" + device_info + '\'' +
                ", nonce_str='" + nonce_str + '\'' +
                ", sign='" + sign + '\'' +
                ", result_code='" + result_code + '\'' +
                ", err_code='" + err_code + '\'' +
                ", err_code_des='" + err_code_des + '\'' +
                ", trade_type='" + trade_type + '\'' +
                ", prepay_id='" + prepay_id + '\'' +
                ", code_url='" + code_url + '\'' +
                ", openid='" + openid + '\'' +
                '}';
    }
}
