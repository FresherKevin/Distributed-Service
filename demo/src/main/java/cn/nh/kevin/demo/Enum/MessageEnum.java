package cn.nh.kevin.demo.Enum;

/**
 * 标题: 提示信息枚举类
 * 描述:
 * 版权: Kevin
 * 作者: xck
 * 时间: 2019-09-06 10:26
 */
public enum MessageEnum {
    indexMessage("k101","初始化"),
    loginSuccessMessage("k102","登录成功"),
    loginFailMessage("k103","登录失败"),
    idConflictMessage("k104","id冲突"),
    registerFailMessage("k105","注册失败"),
    registerSuccessMessage("k106","注册成功"),
    inputEmptyMessage("k107","输入为空"),
    idEmptyMessage("k108","id为空"),
    passwordEmptyMessage("k109","密码为空"),
    checkSuccessMessage("k110","验证成功"),
    idNotFindMessage("k111","用户不存在，请先注册"),
    passwordErrorMessage("k112","密码错误"),
    channelErrorMessage("k113","渠道非法"),
    wrongTooMuchMessage("k114","密码错误失败次数过多，请一分钟之后再试"),

    noClearErrorMessage("k999","未知错误");


    private String code;
    private String text;

    MessageEnum(String code, String text) {
        this.code = code;
        this.text = text;
    }

    /**
     * getter
     *
     * setter
     */

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }


    public static MessageEnum getMessageEnumByCode(String code){
        for (MessageEnum messageEnum : MessageEnum.values()) {
            if (messageEnum.getCode().equals(code))
                return messageEnum;
        }
        return null;
    }

}
