//package com.ssoward.model;
//
//import com.fasterxml.jackson.annotation.JsonAnySetter;
//import java.util.Date;
//import java.util.logging.Logger;
//
///**
// * Created by ssoward on 3/1/14.
// */
//
//public class Praise {
//    private static Logger logger = Logger.getLogger(Praise.class.getName());
//REMOVE THIS CLASS!
//    Long id;
//    String praiser;
//    String praisee;
//    String comment;
//    String praise;
//    Date praiseDt;
//
//    public Praise(Long praise) {
//        this.id = praise;
//    }
//
//    public Praise() {
//
//    }
//
//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public String getPraiser() {
//        return praiser;
//    }
//
//    public void setPraiser(String praiser) {
//        this.praiser = praiser;
//    }
//
//    public String getPraisee() {
//        return praisee;
//    }
//
//    public void setPraisee(String praisee) {
//        this.praisee = praisee;
//    }
//
//    public String getComment() {
//        return comment;
//    }
//
//    public void setComment(String comment) {
//        this.comment = comment;
//    }
//
//    public String getPraise() {
//        return praise;
//    }
//
//    public void setPraise(String praise) {
//        this.praise = praise;
//    }
//
//    public Date getPraiseDt() {
//        return praiseDt;
//    }
//
//    public void setPraiseDt(Date praiseDt) {
//        this.praiseDt = praiseDt;
//    }
//
//    @JsonAnySetter
//    public void handleUnknown(String key, Object value) {
//        logger.warning("Unknown JSON property: " + key);
//    }
//}
