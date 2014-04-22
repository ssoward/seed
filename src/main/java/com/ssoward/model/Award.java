package com.ssoward.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.sql.Date;

/**
 * Created by ssoward on 3/17/14.
 */

@JsonIgnoreProperties(ignoreUnknown = true)

public class Award {

    Long id;
    String name;
    Date createdDt;
    private String createdBy;
    private String title;
    private String cost;

    public Award() {

    }
    public Award(Long l) {
        this.id = l;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCreatedDt(Date createdDt) {
        this.createdDt = createdDt;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getCost() {
        return cost;
    }
}
