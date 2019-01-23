package com.inerun.courier.Exception;
public class PrinterExceptions extends Exception {
    /**
     *
     */
    private String message="";

    public static final long serialVersionUID = 3785433837426670516L;


    public PrinterExceptions(String message) {
        this.message = message;
    }
//    private static final int  FAILED_TO_DISCONNECT= 10;

    public String toString() {
        return message;
    }


}
