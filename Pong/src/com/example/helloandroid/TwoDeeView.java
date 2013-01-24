package com.example.helloandroid;

import java.util.Random;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.media.AudioManager;
import android.media.SoundPool;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class TwoDeeView extends View /*implements OnTouchListener*/ {
	private final static int START_DRAGGING = 0;
	private final static int STOP_DRAGGING = 1;
	private final static int PADDLE_HEIGHT = 70;
	private final static int PADDLE_WIDTH = 10;
	private final static int BORDER_FOR_TOUCH = 120;
	private final static int BORDER_TOP_BOTTOM_GAP = 5;
	private final static int BORDER_WIDTH = 5;
	private final static int BORDER_DOTTED_LENGTH = 10;
	private final static int BORDER_DOTTED_GAP = 7;
	
	private int status;
	
	Paint rectPaint;
	
	Paddle leftPaddle = null;
	Paddle rightPaddle = null;
	Ball ball = null;
	
	boolean firstPlayerServe = false;
	
	int firstPlayerScore;
	int secondPlayerScore;
	
	private static final Random RNG = new Random();
	
	private SoundPool sounds;
	private int soundCollide;
	private int soundOffBorder;
	
	public TwoDeeView(Context context) {
		super(context);
		
		setKeepScreenOn(true);
		firstPlayerScore = 0;
		secondPlayerScore = 0;
		
		rectPaint = new Paint();
        rectPaint.setColor(Color.WHITE);
        
		sounds = new SoundPool(10, AudioManager.STREAM_MUSIC,0);
		soundCollide = sounds.load(context, R.raw.collide_standard, 1);
		soundOffBorder = sounds.load(context, R.raw.off_border, 1);
		
		update();
	}
	
	/**
     * Create a simple handler that we can use to cause animation to happen.  We
     * set ourselves as a target and we can use the sleep()
     * function to cause an update/invalidate to occur at a later date.
     */
    //private RefreshHandler mRedrawHandler = new RefreshHandler();

    /*
    class RefreshHandler extends Handler {

        @Override
        public void handleMessage(Message msg) {
        	//Log.d("Whatever", "Handle Message");
        	TwoDeeView.this.update();
        	TwoDeeView.this.invalidate();
        }

        public void sleep(long delayMillis) {
        	this.removeMessages(0);
            sendMessageDelayed(obtainMessage(0), delayMillis);
        }
    };
    */
    
    /**
     * Handles the basic update loop, checking to see if we are in the running
     * state, determining if a move should be made, updating the snake's location.
     */
    public void update() {
    	//mRedrawHandler.sleep(0);    	
    }
    
	@Override
	protected void onDraw(Canvas canvas) {
		if(ball == null){
			initializePaddles(canvas);
			initializeBall(canvas);
		}
		
		resetCollisions(ball, canvas);
		
		if(ball.getVelocityX() < 0 && CollisionHelper.detectBallAndLeftPaddleCollisionAndSetVelocity(leftPaddle, ball)) {
		    sounds.play(soundCollide, 1.0f, 1.0f, 0, 0, 1.5f);
		} else if(ball.getVelocityX() > 0 && CollisionHelper.detectBallAndRightPaddleCollisionAndSetVelocity(rightPaddle, ball)){
		    sounds.play(soundCollide, 1.0f, 1.0f, 0, 0, 1.5f);
		} else if(CollisionHelper.detectFirstPlayerScore(ball, canvas)) {
		    sounds.play(soundOffBorder, 1.0f, 1.0f, 0, 0, 1.5f);
		    firstPlayerScore++;
		    firstPlayerServe = true;
			initializeBall(canvas);
		} else if(CollisionHelper.detectSecondPlayerScore(ball)){
		    sounds.play(soundOffBorder, 1.0f, 1.0f, 0, 0, 1.5f);
		    secondPlayerScore++;
		    firstPlayerServe = false;
		    initializeBall(canvas);
		} else if(CollisionHelper.detectBallCollidesWithTopBorder(ball, canvas, BORDER_TOP_BOTTOM_GAP, BORDER_WIDTH)){
		    sounds.play(soundCollide, 1.0f, 1.0f, 0, 0, 1.5f);
		} else if(CollisionHelper.detectBallCollidesWithBottomBorder(ball, canvas, BORDER_TOP_BOTTOM_GAP, BORDER_WIDTH)){
            sounds.play(soundCollide, 1.0f, 1.0f, 0, 0, 1.5f);
        }  
		
		ball.setUpperLeftX((ball.getUpperLeftX()+ball.getVelocityX()));
		ball.setUpperLeftY((ball.getUpperLeftY()+ball.getVelocityY()));
		
		// draw border
		drawBorder(canvas);
		
		// draw right paddle
		canvas.drawRect(rightPaddle.calculateAndGetPaddleRect(), rectPaint );
		
		// draw left paddle
		canvas.drawRect(leftPaddle.calculateAndGetPaddleRect(), rectPaint );
		
		// draw ball
		canvas.drawRect( ball.calculateAndGetBallRect(), rectPaint );
		
		//draw middle line
		Rect middleLine = new Rect(canvas.getWidth()/2 - 3, 
		                           BORDER_TOP_BOTTOM_GAP+BORDER_WIDTH, 
		                           canvas.getWidth()/2 + 3, 
		                           canvas.getHeight()-(BORDER_TOP_BOTTOM_GAP+BORDER_WIDTH));
		
		canvas.drawRect( middleLine, rectPaint );
		
		TiledNumberDrawer.drawNumber(secondPlayerScore, 2, new Point(canvas.getWidth()/2+50,25), canvas);
		TiledNumberDrawer.drawNumber(firstPlayerScore, 2, new Point(canvas.getWidth()/2-100,25), canvas);
		
		invalidate();
	}
	
	private void resetCollisions(Ball ball, Canvas canvas) {
	    // check first player paddle collision safeguard
	    if(ball.ignoreFirstPlayerPaddleCollision){
    	    if(ball.getVelocityX() > 0 && ball.getUpperLeftX() > BORDER_FOR_TOUCH+PADDLE_WIDTH) {
    	        ball.ignoreFirstPlayerPaddleCollision = false;
    	    }
	    }
	    
	    // check second player paddle collision safeguard
	    if(ball.ignoreSecondPlayerPaddleCollision) {
            if(ball.getVelocityX() < 0 && ball.getUpperLeftX() < canvas.getWidth() - (BORDER_FOR_TOUCH+PADDLE_WIDTH) ) {
                ball.ignoreSecondPlayerPaddleCollision = false;
            }
	    }
        
	    // check top border collision
	    if(ball.ignoreTopBorderCollision) {
            if(ball.getVelocityY() > 0 && ball.getUpperLeftY() > BORDER_TOP_BOTTOM_GAP+BORDER_WIDTH) {
                ball.ignoreTopBorderCollision = false;
            }
	    }

	    // check bottom border collision
        if(ball.ignoreBottomBorderCollision) {
            if(ball.getVelocityY() < 0 && ball.getUpperLeftY()+ball.getHeight() < canvas.getHeight()-(BORDER_TOP_BOTTOM_GAP+BORDER_WIDTH)) {
                ball.ignoreBottomBorderCollision = false;
            }
        }
    }

    private void drawBorder(Canvas canvas) {
	    drawTopBorder(canvas);
	    drawBottomBorder(canvas);
    }

    private void drawBottomBorder(Canvas canvas) {
        for(int leftBorder = BORDER_FOR_TOUCH ; leftBorder < canvas.getWidth() - BORDER_FOR_TOUCH ; leftBorder+=BORDER_DOTTED_LENGTH) {
            Rect borderDot = new Rect(leftBorder, 
                                      canvas.getHeight()-(BORDER_TOP_BOTTOM_GAP+BORDER_WIDTH), 
                                      leftBorder+BORDER_DOTTED_LENGTH, 
                                      canvas.getHeight()-BORDER_TOP_BOTTOM_GAP);
            canvas.drawRect(borderDot, rectPaint);
            leftBorder+=BORDER_DOTTED_GAP;
        }        
    }

    private void drawTopBorder(Canvas canvas) {
        for(int leftBorder = BORDER_FOR_TOUCH ; leftBorder < canvas.getWidth() - BORDER_FOR_TOUCH ; leftBorder+=BORDER_DOTTED_LENGTH) {
            Rect borderDot = new Rect(leftBorder, 
                                      BORDER_TOP_BOTTOM_GAP, 
                                      leftBorder+BORDER_DOTTED_LENGTH, 
                                      BORDER_TOP_BOTTOM_GAP+BORDER_WIDTH);
            canvas.drawRect(borderDot, rectPaint);
            leftBorder+=BORDER_DOTTED_GAP;
        }
    }

    private void initializePaddles(Canvas canvas) {
		leftPaddle = new Paddle(PADDLE_HEIGHT, PADDLE_WIDTH, BORDER_FOR_TOUCH, (canvas.getHeight()/2)-(PADDLE_HEIGHT/2) );
		//rightPaddle = new Paddle(PADDLE_HEIGHT, PADDLE_WIDTH, canvas.getWidth()-BORDER_FOR_TOUCH-PADDLE_WIDTH, (canvas.getHeight()/2)-(PADDLE_HEIGHT/2) );
		rightPaddle = new Paddle(PADDLE_HEIGHT, PADDLE_WIDTH, canvas.getWidth()-BORDER_FOR_TOUCH - PADDLE_WIDTH, 90 );
	}

	private void initializeBall(Canvas canvas){
	    int serveAngle;
	    
	    if(firstPlayerServe) {
	        ball = new Ball(10,10, 0, 200);
	        serveAngle = 340 + (int)(Math.random() * ((380 - 340) + 1));
	        if(serveAngle > 360) {
	            serveAngle-=360;
	        }
	        ball.setAngleInDegrees(serveAngle);
	        Log.d("Whatever", "SERVE ANGLE : "+serveAngle);
	    } else {
	        ball = new Ball(10, 10, canvas.getWidth()-20, 200);
	        serveAngle = 160 + (int)(Math.random() * ((200 - 160) + 1));
            ball.setAngleInDegrees(serveAngle);
            Log.d("Whatever", "SERVE ANGLE : "+serveAngle);
        }
	    
        ball.setAngleInDegrees(serveAngle);
                
		ball.setSpeed(5);
		ball.calculateNewVelocity();
		
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			status = START_DRAGGING;
		}
		if (event.getAction() == MotionEvent.ACTION_UP) {
			status = STOP_DRAGGING;
		} else if (event.getAction() == MotionEvent.ACTION_MOVE) {
			if (status == START_DRAGGING) {
				int x0 = (int) event.getX(0);
				int y0 = (int) event.getY(0);
				int x1 = (int) event.getX(1);
				int y1 = (int) event.getY(1);
				
				if(event.getPointerCount() == 1){
					x1=-1000;
					y1=-1000;
				}
				
				//displayPointerLogs(event);
				
				if(x0 < getWidth()/2){
					leftPaddle.setUpperLeftY(y0-(PADDLE_HEIGHT/2));
				} else {
					rightPaddle.setUpperLeftY(y0-(PADDLE_HEIGHT/2));
				}
				
				if(x1 > -999){
					if(x1 < getWidth()/2){
						leftPaddle.setUpperLeftY(y1-(PADDLE_HEIGHT/2));
					} else {
						rightPaddle.setUpperLeftY(y1-(PADDLE_HEIGHT/2));
					}
				}
			}
		}
		//return false;
		return super.onTouchEvent(event);
	}

	private void displayPointerLogs(MotionEvent event) {
		Log.d("Whatever", "x0: "+event.getX(0));
		Log.d("Whatever", "x1: "+event.getX(1));
		Log.d("Whatever", "y0: "+event.getY(0));
		Log.d("Whatever", "y1: "+event.getY(1));
		Log.d("Pointer Count", ""+event.getPointerCount());
	}
	
}
