package com.squapl.stark.util;

import lombok.Data;

@Data
public class APIResponseObj {

    private String message;
    private String additionalinfo;

    private Object obj;

// DND
//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
//    private LocalDateTime timestamp;

    public APIResponseObj(String message, String additionalinfo, Object Obj) {
        this.message = message;

        this.additionalinfo = additionalinfo;
        //  timestamp = LocalDateTime.now();
        this.obj = Obj;
    }


// DND
//    public LocalDateTime getTimestamp() {
//        return timestamp;
//    }
}