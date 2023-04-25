package com.example.workouttimer;

public class SharedData {
    private static int setNumber;
    private static int setLength;
    private static int restLength;

    public static int getSetNumber(){
        return setNumber;
    }
    public static void setSetNumber(int mySetNumber) {
        SharedData.setNumber = mySetNumber;
    }
    public static int getSetLength(){
        return setLength;
    }
    public static void setSetLength(int mySetLength){
        SharedData.setLength = mySetLength;
    }
    public static int getRestLength() {
        return restLength;
    }
    public static void setRestLength(int myRestLength){
        SharedData.restLength = myRestLength;
    }
}
