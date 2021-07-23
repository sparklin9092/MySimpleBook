package net.spark9092.MySimpleBook.enums;

public enum SystemEnum {

    SYSTEM_USER_ID(0);

    private int id;

    SystemEnum(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }
}
