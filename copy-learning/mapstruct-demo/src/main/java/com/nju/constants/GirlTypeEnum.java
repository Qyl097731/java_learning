package com.nju.constants;

import java.util.Arrays;

/**
 * @description
 * @date 2023/7/16 23:26
 * @author: qyl
 */
public enum GirlTypeEnum {
    SOFT(0,"温柔"),
    CUTE(1,"可爱"),
    SEXY(2,"性感"),
    HIGH_CODE(3,"高冷");

    private Integer code;
    private String label;

    GirlTypeEnum(Integer code, String label) {
        this.code = code;
        this.label = label;
    }

    public Integer getCode() {
        return code;
    }

    public String getLabel() {
        return label;
    }

    public static GirlTypeEnum byCode(Integer code){
        return Arrays.stream (values ()).filter(t -> t.getCode().equals(code))
                .findFirst ().orElse (SOFT);
    }
}
