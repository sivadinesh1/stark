package com.squapl.stark.util;

import lombok.Data;

@Data
public class ErrorMessage {
    private String errcode;
    private String errmsg;

    public ErrorMessage(String errcode, String errmsg) {
        super();
        this.errcode = errcode;
        this.errmsg = errmsg;
    }
}
