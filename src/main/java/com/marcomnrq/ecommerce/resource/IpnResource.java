package com.marcomnrq.ecommerce.resource;

import lombok.Data;

import java.util.Date;

@Data
public class IpnResource {
    private Integer id;
    private Boolean live_mode;
    private String type;
    private Date date_created;
    private Long application_id;
    private Integer user_id;
    private Integer version;
    private String api_version;
    private String action;
}
