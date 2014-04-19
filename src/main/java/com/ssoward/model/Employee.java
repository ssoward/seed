package com.ssoward.model;

import com.ssoward.model.enums.GivesStatusEnum;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: ssoward
 * Date: 1/18/14
 * Time: 5:05 PM
 * To change this template use File | Settings | File Templates.
 */

public class Employee {

    public Long id;
    public String firstName;
    public String lastName;
    public String auth;
    public String email;
    public String password;
    public List<Give> gives;
    public List<Give> bucks;
    public Integer remainingGives;  //points are given to other employees
    public Integer unspentBucks;    //this count represents bucks to spend on awards

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAuth() {
        return auth;
    }

    public void setAuth(String auth) {
        this.auth = auth;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Give> getGives() {
        return gives;
    }

    public void setGives(List<Give> gives) {
        this.gives = gives;
    }

    public Integer getRemainingGives() {
        return remainingGives;
    }

    public void setRemainingGives(Integer remainingGives) {
        this.remainingGives = remainingGives;
    }

    public Integer getUnspentBucks() {
        return unspentBucks;
    }

    public void setUnspentBucks(Integer unspentBucks) {
        this.unspentBucks = unspentBucks;
    }

    public List<Give> getBucks() {
        return bucks;
    }

    public void setBucks(List<Give> bucks) {
        this.bucks = bucks;
    }
}
