package com.agile.common.enums;

public enum StoryStatusEnum {
    TODO("TODO", "待办"),
    DOING("DOING", "进行中"),
    DONE("DONE", "已完成");

    private String code;
    private String desc;

    StoryStatusEnum(String code, String desc){
        this.code = code;
        this.desc = desc;
    }
    public String getCode(){return code;}
    public String getDesc(){return desc;}

    public static String getDescByCode(String code){
        for(StoryStatusEnum e : values()){
            if(e.code.equals(code)) return e.desc;
        }
        return "未知";
    }
}
