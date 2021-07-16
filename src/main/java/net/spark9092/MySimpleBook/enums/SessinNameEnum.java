package net.spark9092.MySimpleBook.enums;

public enum SessinNameEnum {

    USER_INFO("user-info");

    private String name;

    SessinNameEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

}
