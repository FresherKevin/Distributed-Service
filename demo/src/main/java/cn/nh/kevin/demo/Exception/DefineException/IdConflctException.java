package cn.nh.kevin.demo.Exception.DefineException;

import org.springframework.dao.DuplicateKeyException;

/**
 * 标题: 主键冲突
 * 描述:
 * 版权: Kevin
 * 作者: xck
 * 时间: 2019-09-11 15:00
 */
public class IdConflctException extends DuplicateKeyException {
    private String errorCode;

    public IdConflctException(String msg, String errorCode) {
        super(msg);
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }
}
