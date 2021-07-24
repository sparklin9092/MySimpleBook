package net.spark9092.MySimpleBook.enums;

public enum SessinNameEnum {

    USER_INFO("user-info"),
    RICH_CODE("rich-code"),
    GUEST_DATA_COUNT("guest-data-count");

    private String name;

    SessinNameEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

}
