package net.spark9092.MySimpleBook.enums;

public enum VerifyTypeEnum {
	
	EMAIL("mail"),
	PHONE("phone");

    private String type;

    VerifyTypeEnum(String type) {
        this.type = type;
    }

    public String getType() {
        return this.type;
    }

}
