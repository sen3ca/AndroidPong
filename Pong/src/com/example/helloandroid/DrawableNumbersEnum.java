package com.example.helloandroid;

public enum DrawableNumbersEnum {
    ZERO ( 0,  new int [] {1,1,1,
                           1,0,1,
                           1,0,1,      
                           1,0,1,
                           1,1,1}),
    
    ONE ( 1,   new int [] {0,0,1,
                           0,0,1,
                           0,0,1,
                           0,0,1,
                           0,0,1}),

    TWO ( 2,   new int [] {1,1,1,
                           0,0,1,
                           1,1,1,
                           1,0,0,
                           1,1,1}),
                      
    THREE ( 3, new int [] {1,1,1,
                           0,0,1,
                           1,1,1,
                           0,0,1,
                           1,1,1}),
                        
    FOUR ( 4,  new int [] {1,0,1,
                           1,0,1,
                           1,1,1,
                           0,0,1,
                           0,0,1}),
            
    FIVE ( 5,  new int [] {1,1,1,
                           1,0,0,
                           1,1,1,
                           0,0,1,
                           1,1,1}),
                           
    SIX ( 6,   new int [] {1,1,1,
                           1,0,0,
                           1,1,1,
                           1,0,1,
                           1,1,1}),

    SEVEN ( 7, new int [] {1,1,1,
                           0,0,1,
                           0,0,1,
                           0,0,1,
                           0,0,1}),
                           
    EIGHT ( 8, new int [] {1,1,1,
                           1,0,1,
                           1,1,1,
                           1,0,1,
                           1,1,1}),
    
    NINE ( 9,  new int [] {1,1,1,
                           1,0,1,
                           1,1,1,
                           0,0,1,
                           0,0,1});
    
    public static final int TILE_HEIGHT = 5;
    public static final int TILE_WIDTH = 3;
    
    int number;
    int [] tiledNumberDrawable;
    
    DrawableNumbersEnum(int number, int [] tiledNumberDrawable){
        this.number = number;
        this.tiledNumberDrawable = tiledNumberDrawable;
    }

    public static DrawableNumbersEnum getDrawableNumberEnumFromNumber(int number) {
        for(DrawableNumbersEnum drawableNumber : DrawableNumbersEnum.values()) {
            if (drawableNumber.number == number) {
               return drawableNumber; 
            }
        }
        return null;
    }
}
