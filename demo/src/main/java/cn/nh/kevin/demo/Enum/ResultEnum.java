package cn.nh.kevin.demo.Enum;

/**
 * 标题:
 * 描述:
 * 版权: Kevin
 * 作者: xck
 * 时间: 2019-08-27 09:59
 */
public enum ResultEnum {
    SUCCESS("100", "成功"), FAIL("400", "失败");
    private String code;
    private String text;

    ResultEnum(String code, String text) {
        this.code = code;
        this.text = text;
    }

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
}
