package com.ssoward.model;

import com.ssoward.model.enums.GivesStatusEnum;
import com.ssoward.model.enums.GivesTypeEnum;

import java.util.Date;

/**
 * Created by ssoward on 3/5/14.
 */
public class Give {

    Long id;
    Long praise;
    String user;
    Date receivedDt;
    Date givenDt;
    Date spentDt;
    String receivedBy;
    GivesStatusEnum status;
    GivesTypeEnum type;
    private Long award;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public Date getReceivedDt() {
        return receivedDt;
    }

    public void setReceivedDt(Date receivedDt) {
        this.receivedDt = receivedDt;
    }

    public Date getGivenDt() {
        return givenDt;
    }

    public void setGivenDt(Date givenDt) {
        this.givenDt = givenDt;
    }

    public String getReceivedBy() {
        return receivedBy;
    }

    public void setReceivedBy(String receivedBy) {
        this.receivedBy = receivedBy;
    }

    public GivesStatusEnum getStatus() {
        return status;
    }

    public void setStatus(GivesStatusEnum status) {
        this.status = status;
    }

    public GivesTypeEnum getType() {
        return type;
    }

    public void setType(GivesTypeEnum type) {
        this.type = type;
    }

    public Date getSpentDt() {
        return spentDt;
    }

    public void setSpentDt(Date spentDt) {
        this.spentDt = spentDt;
    }


    public Long getPraise() {
        return praise;
    }

    public void setPraise(Long praise) {
        this.praise = praise;
    }

    public void setAward(Long award) {
        this.award = award;
    }

    public Long getAward() {
        return award;
    }
}
