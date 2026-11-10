package com.agile.common.enums;

public enum TaskStatusEnum {
    TODO("TODO", "待办"),
    DOING("DOING", "进行中"),
    DONE("DONE", "已完成");

    private String code;
    private String desc;

    TaskStatusEnum(String code, String desc){
        this.code = code;
        this.desc = desc;
    }
    public String getCode(){return code;}
    public String getDesc(){return desc;}

    public static String getDescByCode(String code){
        for(TaskStatusEnum e : values()){
            if(e.code.equals(code)) return e.desc;
        }
        return "未知";
    }
}
