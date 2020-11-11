package org.jeeasy.common.core.exception;

import lombok.Getter;
import org.jeeasy.common.core.vo.R;

/**
 * Jeeasy 自定义异常
 *
 * @author AlpsDDJ
 * @date 2020/11/11
 */
public class JeeasyException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    @Getter
    private int code = R.SC_INTERNAL_SERVER_ERROR_500;

    public JeeasyException(String message) {
        super(message);
    }

    public JeeasyException(int code, String message) {
        super(message);
        this.code = code;
    }

    public JeeasyException(Throwable cause) {
        super(cause);
    }

    public JeeasyException(String message, Throwable cause) {
        super(message, cause);
    }

    public JeeasyException(int code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }
}
