package me.superning.nettychat.utils;

/**
 * @author superning
 */
public class jsonRescult {
    private Integer status;
    private String msg;
    private Object data;
    private String ok;

    public jsonRescult(Integer status, String msg, Object data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }
}
