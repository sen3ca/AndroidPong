package com.example.helloandroid;

import android.graphics.Canvas;
import android.util.Log;

public class CollisionHelper {
    public static boolean detectBallAndRightPaddleCollisionAndSetVelocity(Paddle paddle, Ball ball){
        if (!ball.ignoreSecondPlayerPaddleCollision) {
            if((ball.getBall().right >= paddle.getPaddle().left && ball.getBall().right <= paddle.getPaddle().right) &&
               (
                   (ball.getUpperLeftY() >= paddle.getPaddle().top && ball.getUpperLeftY() <= paddle.getPaddle().bottom) || 
                   (ball.getUpperLeftY()+ball.getHeight() >= paddle.getPaddle().top && ball.getUpperLeftY()+ball.getHeight() <= paddle.getPaddle().bottom)
               )
              ){       
                boolean sectionFound = false;
                paddle.calculateYSections();
                for(int i = 0 ; i < paddle.getPaddleNumSections()-1 && sectionFound == false; i++) {
                    float ballXpoint = 0;
                    if(i == 0) {
                        ballXpoint = ball.getUpperLeftY()+ball.getHeight();
                    } else if(i == paddle.getPaddleNumSections()-2) {
                        ballXpoint = ball.getUpperLeftY();
                    }
                    
                    if(
                       ball.getCenterY() >= paddle.getySections()[i] && ball.getCenterY() <= paddle.getySections()[i+1] ||
                       i == 0 && ballXpoint >= paddle.getySections()[i] && ballXpoint <= paddle.getySections()[i+1] ||
                       i == paddle.getPaddleNumSections()-2 && ballXpoint >= paddle.getySections()[i] && ballXpoint <= paddle.getySections()[i+1]                       
                      ){
                        
                        // RIGHT RIGHT RIGHT RIGHT
                        int highestAngle = 225;
                        int lowestAngle = 135;
                        int newAngleAfterBounce = Ball.normalizeAngle(540-ball.getAngleInDegrees());
                        Log.d("Whatever", "i IS : "+i);
                        int sectionOfNinetyDegrees = 90/(paddle.getPaddleNumSections()-2);
                        
                        int degreeToApply =  45 - ( sectionOfNinetyDegrees * i);
                    
                        newAngleAfterBounce += degreeToApply;
                        newAngleAfterBounce = Ball.normalizeAngle(newAngleAfterBounce);
                        if(newAngleAfterBounce > highestAngle && newAngleAfterBounce < 360){
                            newAngleAfterBounce = highestAngle;
                        }
                        
                        if(newAngleAfterBounce < lowestAngle && newAngleAfterBounce > 0){
                            newAngleAfterBounce = lowestAngle;
                        }
                        
                        ball.setAngleInDegrees(newAngleAfterBounce);
                        
                        ball.ignoreSecondPlayerPaddleCollision = true;
                        sectionFound = true;
                    }
                }
                return true;
            }
        }
        return false;
    }
    
    public static boolean detectBallAndLeftPaddleCollisionAndSetVelocity(Paddle paddle, Ball ball){
        if (!ball.ignoreFirstPlayerPaddleCollision) {
            /*
            if((ball.getBall().left >= paddle.getPaddle().left && ball.getBall().left <= paddle.getPaddle().right) &&
               (
                   (ball.getUpperLeftY() >= paddle.getPaddle().top && ball.getUpperLeftY() <= paddle.getPaddle().bottom) || 
                   (ball.getUpperLeftY()+ball.getHeight() >= paddle.getPaddle().top && ball.getUpperLeftY()+ball.getHeight() <= paddle.getPaddle().bottom)
               )
              */ 
            if(ball.getBall().left >= paddle.getPaddle().left && ball.getBall().left <= paddle.getPaddle().right &&
               ball.getBall().bottom > paddle.getPaddle().top && ball.getBall().top < paddle.getPaddle().bottom
              ){
                boolean sectionFound = false;
                paddle.calculateYSections();
                for(int i = 0 ; i < paddle.getPaddleNumSections()-1 && sectionFound == false; i++) {
                    float ballXpoint = 0;
                    
                    // if looking at the first (top) section of the paddle, collide based on bottom point of ball
                    if(i == 0) {
                        ballXpoint = ball.getUpperLeftY()+ball.getHeight();
                    } else if(i == paddle.getPaddleNumSections()-2) {
                        ballXpoint = ball.getUpperLeftY();
                    }
                    
                    if(
                       ball.getCenterY() >= paddle.getySections()[i] && ball.getCenterY() <= paddle.getySections()[i+1] ||
                       i == 0 && ballXpoint >= paddle.getySections()[i] && ballXpoint <= paddle.getySections()[i+1] ||
                       i == paddle.getPaddleNumSections()-1 && ballXpoint >= paddle.getySections()[i] && ballXpoint <= paddle.getySections()[i+1]                       
                      ){
                        
                        // LEFT LEFT LEFT LEFT LEFT
                        int highestAngle = 315;
                        int lowestAngle = 45;
                        int newAngleAfterBounce = Ball.normalizeAngle(540-ball.getAngleInDegrees());
                        Log.d("Whatever", "i IS : "+i);
                        int sectionOfNinetyDegrees = 90/(paddle.getPaddleNumSections()-2);
                        
                        int degreeToApply =  Ball.normalizeAngle(45 - ( sectionOfNinetyDegrees * i));
                        
                        newAngleAfterBounce -= degreeToApply;
                        
                        newAngleAfterBounce = Ball.normalizeAngle(newAngleAfterBounce);  
                        
                        if(newAngleAfterBounce < highestAngle && newAngleAfterBounce >=180){
                            newAngleAfterBounce = highestAngle;
                        }
                        
                        if(newAngleAfterBounce > lowestAngle && newAngleAfterBounce < 180){
                            newAngleAfterBounce = lowestAngle;
                        }                        
                        
                        ball.setAngleInDegrees(newAngleAfterBounce);
                        
                        ball.ignoreFirstPlayerPaddleCollision = true;
                        sectionFound=true;
                    }
                }
                return true;
            }
        }
        return false;
    }
    
    public static boolean detectBallCollidesWithBottomBorder(Ball ball, Canvas canvas, int borderTopBottomGap, int borderWidth){
        if (!ball.ignoreBottomBorderCollision) {
            if (ball.getUpperLeftY() + ball.getHeight() > canvas.getHeight()-(borderTopBottomGap + borderWidth)) {
                ball.setAngleInDegrees(-ball.getAngleInDegrees());
                ball.calculateNewVelocity();
                ball.ignoreBottomBorderCollision = true;
                return true;
            }
        }
        return false;
    }
    
    public static boolean detectBallCollidesWithTopBorder(Ball ball, Canvas canvas, int borderTopBottomGap, int borderWidth){
        if (!ball.ignoreTopBorderCollision) {
            if (ball.getUpperLeftY() < borderTopBottomGap + borderWidth) {
                ball.setAngleInDegrees(-ball.getAngleInDegrees());
                ball.calculateNewVelocity();
                ball.ignoreTopBorderCollision = true;
                return true;
            }
        }
        return false;
    }
    
    public static boolean detectSecondPlayerScore(Ball ball){
        if(ball.getUpperLeftX() < 0){
            return true;
        }
        return false;
    }
    
    public static double angleInRadians(int degree){
        return degree * (Math.PI / 180);
    }
    
    public static boolean detectFirstPlayerScore(Ball ball, Canvas canvas){
        if(ball.getUpperLeftX() > canvas.getWidth()) {
            return true;
        }
        return false;
    }
}
