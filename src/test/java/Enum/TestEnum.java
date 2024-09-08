package Enum;

import java.util.Arrays;

public enum TestEnum {
    RED("RED", "1"),
    GREEN("GREEN", "2"),
    BLACK("BLACK", "3");

    private String name;
    private String code;

    // 构造方法
    private TestEnum(String name, String code) {
        this.name = name;
        this.code = code;
    }

    // 主方法
    public static void main(String[] args) {
        TestEnum[] values = TestEnum.values();
        System.out.println(Arrays.toString(values));

        // System.out.println(TestEnum.RED.value);

        String str="2";
        for (TestEnum value : values) {
            if (str.equals(value.code)){
                System.out.println(value.name);
                System.out.println(value.code);
                System.out.println(value);
            }
        }

    }
}

