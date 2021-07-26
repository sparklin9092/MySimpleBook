package net.spark9092.MySimpleBook.enums;

import java.io.Serializable;

public enum SessinNameEnum implements Serializable {

    USER_INFO("user-info"),
    RICH_CODE("rich-code"),
    GUEST_DATA_COUNT("guest-data-count"),
    USER_MAIL("user-mail");

    private String name;

    SessinNameEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

}
