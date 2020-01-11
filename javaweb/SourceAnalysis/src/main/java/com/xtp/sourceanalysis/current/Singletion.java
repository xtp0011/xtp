package com.xtp.sourceanalysis.current;

public class Singletion {

    private static volatile Singletion singletion;

    private Singletion(){};

    public static Singletion getSingletion(){

        if(singletion==null){
            synchronized(Singletion.class){
                if(singletion==null){
                    return new Singletion();
                }
            }
        }
        return  singletion;
    }


}
