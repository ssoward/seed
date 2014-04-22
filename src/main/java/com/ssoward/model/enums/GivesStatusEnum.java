package com.ssoward.model.enums;

/**
 * Defines the state of this give.
 * Created by ssoward on 3/5/14.
 */
public enum GivesStatusEnum {
    TOBE_GIVEN("toBeGiven", 1l),
    GIVEN("given", 2l),
    GIVEN_SPENT("givenAndSpent", 3l),
    USED_IN_COMBINATION("was used in combination with another give to purchase award whos cost was greater than one buck.", 4l),
    AWARD_RECEIVED("award has been distributed", 4l),
    EXPIRED("expiredBeforeUse", 4l);

    private String label;
    private Long id;

    GivesStatusEnum(String l, Long i){
        this.label = l;
        this.id = i;

    }

    public static GivesStatusEnum getForName(String status) {
        for(GivesStatusEnum gse: GivesStatusEnum.values()){
            if(gse.name().equals(status)){
                return gse;
            }
        }
        return null;
    }

//    public String getLabel() {
//        return label;
//    }
//
//    public Long getId() {
//        return id;
//    }
}
