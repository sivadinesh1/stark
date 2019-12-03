package com.squapl.stark.model;

import lombok.Data;

@Data
public class PaytmDTO {
    private String mid;
    private String order_id;
    private String cust_id;
    private String industry_type_id;
    private String channel_id;
    private String txn_amount;
    private String website;
    private String email;
    private String mobile_no;
    private String callback_url;
    private String checksumhash;

}

