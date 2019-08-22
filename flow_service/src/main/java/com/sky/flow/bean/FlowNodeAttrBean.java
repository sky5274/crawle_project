package com.sky.flow.bean;

import java.util.Date;

import lombok.Data;

@Data
public class FlowNodeAttrBean {
    private String id;

    private String key;

    private String name;

    private String value;

    private String flowId;

    private String nodeId;

    private Integer version;

    private Date ts;
}