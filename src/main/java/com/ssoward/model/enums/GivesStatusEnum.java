package com.ssoward.model.enums;

/**
 * Defines the state of this give.
 * Created by ssoward on 3/5/14.
 */
public enum GivesStatusEnum {
    TOBE_GIVEN("toBeGiven", 1l),
    GIVEN("given", 2l),
    GIVEN_SPENT("givenAndSpent", 3l);

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
