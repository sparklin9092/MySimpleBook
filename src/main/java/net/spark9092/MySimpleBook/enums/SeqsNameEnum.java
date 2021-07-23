package net.spark9092.MySimpleBook.enums;

public enum SeqsNameEnum {

	GUEST("guest");

    private String name;

    SeqsNameEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
    
}
