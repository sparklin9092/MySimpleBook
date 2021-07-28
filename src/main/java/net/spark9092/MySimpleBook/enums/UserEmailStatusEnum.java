package net.spark9092.MySimpleBook.enums;

public enum UserEmailStatusEnum {
	
	//目前 User Email 在什麼階段，1: 已寄發、2: 可綁定、3: 已認證
    //1: 已寄發，表示系統已經寄發認證碼給使用者，
    //   而且認證碼目前是可以用的狀態，
    //   還沒有過期、已使用、被停用、被刪除
    
    //2: 可綁定，表示使用者從來沒有索取過認證碼，或是認證碼不可使用了
    
    //3: 已認證，表示使用者已經綁定這個Email了，在user_info.email有值，
    //   而且在user_verify也找的到對應的認證碼，還有認證碼已經被使用了
	
	ISSEND(1),
	CANBIND(2),
	ISBIND(3);

    private int status;

    UserEmailStatusEnum(int status) {
        this.status = status;
    }

    public int getStatus() {
        return this.status;
    }

}
