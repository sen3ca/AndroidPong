package com.example.helloandroid;

import android.graphics.Rect;
import android.graphics.RectF;

public class Paddle {
    public static int HIGHEST_ANGLE = -45;
    public static int LOWEST_ANGLE = 45;
	private int height;
	private int width;
	
	private float upperLeftX;
	private float upperLeftY;
	
	private int paddleNumSections = 10;
	private float [] ySections;
	Rect paddle;
	
	public Paddle(){
		paddle = new Rect();
	}
	
	public Paddle(int height, int width, float upperLeftX, float upperLeftY){
		this.height = height;
		this.width = width;
		this.upperLeftX = upperLeftX;
		this.upperLeftY = upperLeftY;
		ySections = new float [paddleNumSections];
		calculateYSections();
		paddle = new Rect();
	}
	
	// the y coordinates that make up the whole paddle
	public void calculateYSections() {
        for(int i = 0 ; i < paddleNumSections ; i++) {
            ySections[i] = upperLeftY+(i*(height / (paddleNumSections-1) ));
        }
    }

    public Rect calculateAndGetPaddleRect(){
		paddle.set((int)upperLeftX, (int)upperLeftY, (int)upperLeftX+width, (int)upperLeftY+height);
		return paddle;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public float getUpperLeftX() {
		return upperLeftX;
	}

	public void setUpperLeftX(float upperLeftX) {
		this.upperLeftX = upperLeftX;
	}

	public float getUpperLeftY() {
		return upperLeftY;
	}

	public void setUpperLeftY(float upperLeftY) {
		this.upperLeftY = upperLeftY;
	}

	public Rect getPaddle() {
		return paddle;
	}

	public void setPaddle(Rect paddle) {
		this.paddle = paddle;
	}
	
	public int getPaddleNumSections() {
        return paddleNumSections;
    }

    public float[] getySections() {
        return ySections;
    }

}
