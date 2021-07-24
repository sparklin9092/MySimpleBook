package net.spark9092.MySimpleBook.enums;

public enum WhiteListTypeEnum {

	WHITE_IP("white-ip");

    private String type;

    WhiteListTypeEnum(String type) {
        this.type = type;
    }

    public String getType() {
        return this.type;
    }
}
