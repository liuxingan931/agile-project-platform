package com.agile.common.enums;

public enum SprintStatusEnum {
    PLANNING("PLANNING", "规划中"),
    RUNNING("RUNNING", "进行中"),
    FINISHED("FINISHED", "已完成");

    private String code;
    private String desc;

    SprintStatusEnum(String code, String desc){
        this.code = code;
        this.desc = desc;
    }
    public String getCode(){return code;}
    public String getDesc(){return desc;}

    public static String getDescByCode(String code){
        for(SprintStatusEnum e : values()){
            if(e.code.equals(code)) return e.desc;
        }
        return "未知";
    }
}
