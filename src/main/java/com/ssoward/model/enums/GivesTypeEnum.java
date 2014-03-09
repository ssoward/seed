package com.ssoward.model.enums;

/**
 * Defines how this give was received by the users.
 * Created by ssoward on 3/6/14.
 */
public enum GivesTypeEnum {
    MONTHLY("monthly", 1l),
    ADMIN("admin", 2l);

    String label;
    Long id;

    GivesTypeEnum(String l, Long i){
        this.label = l;
        this.id = i;
    }


    public static GivesTypeEnum getForLabel(String status) {
        for(GivesTypeEnum gse: GivesTypeEnum.values()){
            if(gse.label.equals(status)){
                return gse;
            }
        }
        return null;
    }

    public String getLabel() {
        return label;
    }

    public Long getId() {
        return id;
    }
}
