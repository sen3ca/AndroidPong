package com.example.helloandroid;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;

public class TiledNumberDrawer {
    
    public static final int TILE_WIDTH = 10;
    public static final int TILE_HEIGHT = 10;
    public static final int SPACING_BETWEEN_NUMBERS = 8;
    
    public static class Tile {
        int upperLeftX;
        int upperLeftY;
        int height;
        int width;
        
        Rect tile;
        
        public Tile(int height, int width, int upperLeftX, int upperLeftY){
            this.height = height;
            this.width = width;
            this.upperLeftX = upperLeftX;
            this.upperLeftY = upperLeftY;
            this.tile = new Rect();
        }
        
        public Rect calculateAndGetRect(){
            tile.set(upperLeftX, upperLeftY, upperLeftX+width, upperLeftY+height);
            return tile;
        }
    }
    
    
    
    public static void drawNumber(int number, int digits, Point upperLeft, Canvas canvas){
        DrawableNumbersEnum[] drawableNumbers = new DrawableNumbersEnum[digits];
        
        Paint color = new Paint();
        color.setColor(Color.WHITE);
        
        nullOutArray(drawableNumbers);
        
        String stringedDigits = Integer.toString(number);
        if( stringedDigits.length() > digits) {
            throw new RuntimeException("Too many digits");
        } else {
            for(int i = stringedDigits.length()-1 ; i >= 0 ; i-- ) {
                drawableNumbers[i] = DrawableNumbersEnum.getDrawableNumberEnumFromNumber(Character.getNumericValue(stringedDigits.charAt(i)));
            }
        }
        
        Point currentPoint;
        for (int numberCnt = 0 ; numberCnt < drawableNumbers.length ; numberCnt++) {
            currentPoint = new Point(upperLeft.x + (DrawableNumbersEnum.TILE_WIDTH * TILE_WIDTH * numberCnt), upperLeft.y);
            if(numberCnt > 0){
                currentPoint.x += SPACING_BETWEEN_NUMBERS;
            }
            if(drawableNumbers[numberCnt] != null) {
                for (int tileCnt = 0 ; tileCnt < DrawableNumbersEnum.TILE_HEIGHT * DrawableNumbersEnum.TILE_WIDTH ; tileCnt++) {
                    // if starting a new row on the tile
                    if(tileCnt > 0 && tileCnt % DrawableNumbersEnum.TILE_WIDTH == 0){
                        currentPoint.y += TILE_HEIGHT;
                        currentPoint.x = upperLeft.x + (DrawableNumbersEnum.TILE_WIDTH * TILE_WIDTH * numberCnt);
                        if(numberCnt > 0){
                            currentPoint.x += SPACING_BETWEEN_NUMBERS;
                        }
                    }
                    
                    if(drawableNumbers[numberCnt].tiledNumberDrawable[tileCnt] == 1){
                        TiledNumberDrawer.Tile tile = new Tile(TILE_HEIGHT, TILE_WIDTH, currentPoint.x, currentPoint.y);
                        canvas.drawRect(tile.calculateAndGetRect(), color);
                    }
                    currentPoint.x += TILE_WIDTH;
                }
            }
        }
        
        
    }



    private static void nullOutArray(DrawableNumbersEnum[] drawableNumbers) {
        for(int i = 0 ; i < drawableNumbers.length ; i++ ) {
            drawableNumbers[i] = null;
        }        
    }    
}
