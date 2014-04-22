package com.ssoward.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ssoward.model.enums.GivesStatusEnum;
import com.ssoward.model.enums.GivesTypeEnum;

import java.util.Date;

/**
 * Created by ssoward on 3/5/14.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class Give {

    public Long            id;
    public String          user;
    public Date            receivedDt;
    public Date            awardReceivedDt;
    public Date            givenDt;
    public String          receivedBy;
    public GivesStatusEnum status;
    public GivesTypeEnum   type;
    public Date            spentDt;
    public Long            complement;
    public Award           award;
    public String          givenTo;
    public String          comment;
    public String          complementName;
    public Long combinationPurchase;
    public Boolean distributed;

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

    public Long getComplement() {
        return complement;
    }

    public void setComplement(Long complement) {
        this.complement = complement;
    }

    public Award getAward() {
        return award;
    }

    public void setAward(Award award) {
        this.award = award;
    }

    public String getGivenTo() {
        return givenTo;
    }

    public void setGivenTo(String givenTo) {
        this.givenTo = givenTo;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getComplementName() {
        return complementName;
    }

    public void setComplementName(String complementName) {
        this.complementName = complementName;
    }

    public void setCombinationPurchase(Long combinationPurchase) {
        this.combinationPurchase = combinationPurchase;
    }

    public Long getCombinationPurchase() {
        return combinationPurchase;
    }

    public Date getAwardReceivedDt() {
        return awardReceivedDt;
    }

    public void setAwardReceivedDt(Date awardReceivedDt) {
        this.awardReceivedDt = awardReceivedDt;
    }

    public Boolean getDistributed() {
        return distributed;
    }

    public void setDistributed(Boolean distributed) {
        this.distributed = distributed;
    }
}
