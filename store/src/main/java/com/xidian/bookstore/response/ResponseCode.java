package com.xidian.bookstore.response;

public enum ResponseCode {
    SUCCESS(100,"Successful"),
    FAILED(200,"INTERNAL_SERVER_ERROR"),
    CODE_WRONG(101,"Wrong verificationCode"),
    USER_NOTS(102,"User does not exist"),
    USER_EXISTED(103,"User has registered"),
    CODE_EXPIRED(104,"VerificationCode is expired"),
    PASSWORD_WRONG(105,"Wrong password"),
    ADDRESS_NULL(107,"Address does not exist "),
    PARAM_NULL(106,"Parameter is null"),
    TAG_NULL(109,"The tag does not exist"),
    BOOK_NULL(110,"The book is null"),
    INVENTORY_LESS(111,"The amount more than inventory"),
    ORDEER_NULL(112,"The order is null"),
    LOGISTICS_NULL(113,"The logistics is null"),
    PAYMENT_NULL(114,"The payment is null"),
    MONEY_LESS(115,"The money is not enough"),
    UNITPRICE_NULL(116,"The unitPrice is null"),
    BORROW_NULL(117,"The borrow is null"),
    CATEGORY_NULL(108,"The category does not exist");
    int code;
    String msg;
    private ResponseCode(int code, String msg){
        this.code = code;
        this.msg = msg;
    }

    public int getCode(){
        return code;
    }
    public String getMsg(){
        return msg;
    }
}
