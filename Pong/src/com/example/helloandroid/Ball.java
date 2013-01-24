package com.example.helloandroid;

import android.graphics.Rect;
import android.util.Log;

public class Ball {
	private int height;
	private int width;
	
	private float upperLeftX;
	private float upperLeftY;
	
	private int speed;
	int angleInDegrees;
	double angleInRadians;

	private float velocityX;
    private float velocityY;

    public boolean ignoreTopBorderCollision = false;
    public boolean ignoreBottomBorderCollision = false;
    public boolean ignoreFirstPlayerPaddleCollision = false;
    public boolean ignoreSecondPlayerPaddleCollision = false;
    
	Rect ball;
	
	public Ball(){
		ball = new Rect();
	}
	
	public Ball(int height, int width, float upperLeftX, float upperLeftY){
		this.height = height;
		this.width = width;
		this.upperLeftX = upperLeftX;
		this.upperLeftY = upperLeftY;
		ball = new Rect();
	}
	
	public Rect calculateAndGetBallRect(){
		ball.set((int)upperLeftX, (int)upperLeftY, (int)upperLeftX+width, (int)upperLeftY+height);
		return ball;
	}
	
	public void calculateNewVelocity(){
	    velocityX = (float) (speed * Math.cos(angleInRadians));
        velocityY = (float) (speed * Math.sin(angleInRadians));
    }

	public float getCenterY() {
	    return upperLeftY+(height/2);
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
	
	public float getBottomY() {
        return upperLeftY+height;
    }

	public void setUpperLeftY(float upperLeftY) {
		this.upperLeftY = upperLeftY;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public int getAngleInDegrees() {
		return angleInDegrees;
	}
	
	public void setAngleInRadians(float angleInRadians) {
        this.angleInRadians = angleInRadians;
    }

	public void setAngleInDegrees(int angleInDegrees) {
	    this.angleInDegrees = normalizeAngle(angleInDegrees);
	    Log.d("Whatever" , "sent in as : "+angleInDegrees);
	    Log.d("Whatever" , "normalized : "+normalizeAngle(angleInDegrees));
	    //this.angleInDegrees = angleInDegrees;
		angleInRadians = angleInDegrees * (Math.PI / 180);
		calculateNewVelocity();
	}
	
	public Rect getBall() {
		return ball;
	}

	public void setBall(Rect ball) {
		this.ball = ball;
	}
	
	public static int normalizeAngle(int degree){
	    if(degree < 0){
            do{
                degree+=360;
            }while (degree < 0);
        } else {
            if(degree > 360){
                do{
                    degree-=360;
                }while (degree > 360);
            }
        }
        return degree;
    }
	
	@Override
	public String toString() {
	    String LINE_BREAK = "\n";
	    String ballProperties = "";
	    ballProperties+="Height: "+height;
	    ballProperties+=LINE_BREAK;
	    ballProperties+="Width: "+width;
	    ballProperties+=LINE_BREAK;
	    ballProperties+="upperLeftX: "+upperLeftX;
	    ballProperties+=LINE_BREAK;
	    ballProperties+="upperLeftY: "+upperLeftY;
	    ballProperties+=LINE_BREAK;
	    ballProperties+="speed: "+speed;
	    ballProperties+=LINE_BREAK;
	    ballProperties+="angleInDegrees: "+angleInDegrees;
	    ballProperties+=LINE_BREAK;
	    ballProperties+="angleInRadians: "+angleInRadians;
	    ballProperties+=LINE_BREAK;
	    return ballProperties;
	}

    public float getVelocityX() {
        return velocityX;
    }

    public void setVelocityX(float velocityX) {
        this.velocityX = velocityX;
    }

    public float getVelocityY() {
        return velocityY;
    }

    public void setVelocityY(float velocityY) {
        this.velocityY = velocityY;
    }
}
