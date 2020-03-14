package com.bennyplo.android_mooc_graphics_3d;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

import java.util.Timer;
import java.util.TimerTask;

public class MyView extends View {
    private Paint redPaint; //paint object for drawing the lines
    private Paint greenPaint; //paint object for drawing the lines
    private Paint bluePaint; //paint object for drawing the lines
    private Paint cyanPaint; //paint object for drawing the lines
    private Paint purplePaint; //paint object for drawing the lines
    private final Paint.Style paintStyle = Paint.Style.FILL;

    double screenWidth = 0;
    double screenHeight = 0;

    private Cube dummy;
    private Cube head;
    private Cube neck;
    private Cube body;
    private Cube hip;
    private Cube leftArm1;
    private Cube leftArm2;
    private Cube leftArm3;
    private Cube rightArm1;
    private Cube rightArm2;
    private Cube rightArm3;
    private Cube leftLeg1;
    private Cube leftLeg2;
    private Cube leftLeg3;
    private Cube rightLeg1;
    private Cube rightLeg2;
    private Cube rightLeg3;

    private MovingAngle hipAngle;
    private MovingAngle leftLegAngle;
    private MovingAngle rightLegAngle;

    public MyView(Context context) {
        super(context, null);
        final MyView thisview=this;
        //create the paint object
        redPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        redPaint.setStyle(paintStyle);//Stroke
        redPaint.setColor(Color.RED);
        redPaint.setStrokeWidth(2);
        greenPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        greenPaint.setStyle(paintStyle);//Stroke
        greenPaint.setColor(Color.GREEN);
        greenPaint.setStrokeWidth(2);
        bluePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        bluePaint.setStyle(paintStyle);//Stroke
        bluePaint.setColor(Color.BLUE);
        bluePaint.setStrokeWidth(2);
        cyanPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        cyanPaint.setStyle(paintStyle);//Stroke
        cyanPaint.setColor(Color.CYAN);
        cyanPaint.setStrokeWidth(2);
        purplePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        purplePaint.setStyle(paintStyle);//Stroke
        purplePaint.setColor(Color.MAGENTA);
        purplePaint.setStrokeWidth(2);

        hipAngle = new MovingAngle(-45, 45, 0.1);
        leftLegAngle = new MovingAngle(0, 45, 0.5);
        rightLegAngle = new MovingAngle(0, 45, 0.5);
        rightLegAngle.setPause(true);

                dummy = new Cube();
        hip = new Cube(dummy);
        body = new Cube(hip);
        neck = new Cube(body);
        head = new Cube(neck);

        leftArm1 = new Cube(body);
        leftArm2 = new Cube(leftArm1);
        leftArm3 = new Cube(leftArm2);

        rightArm1 = new Cube(body);
        rightArm2 = new Cube(rightArm1);
        rightArm3 = new Cube(rightArm2);

        leftLeg1 = new Cube(hip);
        leftLeg2 = new Cube(leftLeg1);
        leftLeg3 = new Cube(leftLeg2);

        rightLeg1 = new Cube(hip);
        rightLeg2 = new Cube(rightLeg1);
        rightLeg3 = new Cube(rightLeg2);

        thisview.invalidate();//update the view

        Timer timer=new Timer();
        TimerTask task= new TimerTask() {
            @Override
            public void run() {
                hipAngle.advance();
                leftLegAngle.advance();
                rightLegAngle.advance();
                if (!leftLegAngle.isPause() && leftLegAngle.getAngle() == 0) {
                    rightLegAngle.setPause(false);
                    leftLegAngle.setPause(true);
                } else if (!rightLegAngle.isPause() && rightLegAngle.getAngle() == 0) {
                    leftLegAngle.setPause(false);
                    rightLegAngle.setPause(true);
                }
                //add your code to rotate the object about the axis
                thisview.invalidate();//update the view
            }
        };
        timer.scheduleAtFixedRate(task,100,2);
    }

    @Override
    public void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        screenWidth = w;
        screenHeight = h;

        dummy.setup(screenWidth/4, screenHeight/4, 1);
        dummy.setTranslate(screenWidth/2, screenHeight/2, 0);
        hip.setup(180, 50, 60);
        hip.setTranslate(0, 0, 0);
        body.setup(hip.getSx(), 220, hip.getSz());
        body.setTranslate(0, -(body.getSy() + hip.getSy()), 0);
        neck.setup(40, 30, 20);
        neck.setTranslate(0, -(body.getSy() + neck.getSy()), 0);
        head.setup(80, 80, 80);
        head.setTranslate(0, -(neck.getSy()+head.getSy()), 0);

        leftArm1.setup(body.getSx()/3, 90, 40);
        leftArm1.setTranslate(-(body.getSx()+leftArm1.getSx()), -(body.getSy()-leftArm1.getSy()), 0);
        leftArm2.setup(body.getSx()/3, 110, 40);
        leftArm2.setTranslate(0, (leftArm1.getSy()+leftArm2.getSy()), 0);
        leftArm3.setup(body.getSx()/3, 30, 80);
        leftArm3.setTranslate(0, (leftArm2.getSy()+leftArm3.getSy()), 30);

        rightArm1.setup(body.getSx()/3, 90, 40);
        rightArm1.setTranslate(body.getSx()+rightArm1.getSx(), -(body.getSy()-rightArm1.getSy()), 0);
        rightArm2.setup(body.getSx()/3, 110, 40);
        rightArm2.setTranslate(0, (rightArm1.getSy()+rightArm2.getSy()), 0);
        rightArm3.setup(body.getSx()/3, 30, 80);
        rightArm3.setTranslate(0, (rightArm2.getSy()+rightArm3.getSy()), 30);

        leftLeg1.setup(body.getSx()/3, 100, 40);
        leftLeg1.setTranslate(-body.getSx()/3*2, (hip.getSy()+leftLeg1.getSy()), 0);
        leftLeg2.setup(body.getSx()/3, 130, 40);
        leftLeg2.setTranslate(0, (leftLeg1.getSy()+leftLeg2.getSy()), 0);
        leftLeg2.setRotateOffset(0, -leftLeg2.getSy()/2, leftLeg2.getSz()/2);
        leftLeg3.setup(body.getSx()/3, 30, 80);
        leftLeg3.setTranslate(0, (leftLeg2.getSy()+leftLeg3.getSy()), 20);

        rightLeg1.setup(body.getSx()/3, 100, 40);
        rightLeg1.setTranslate(body.getSx()/3*2, (hip.getSy()+rightLeg1.getSy()), 0);
        rightLeg2.setup(body.getSx()/3, 130, 40);
        rightLeg2.setTranslate(0, (rightLeg1.getSy()+rightLeg2.getSy()), 0);
        rightLeg2.setRotateOffset(0, -rightLeg2.getSy()/2, rightLeg2.getSz()/2);
        rightLeg3.setup(body.getSx()/3, 30, 80);
        rightLeg3.setTranslate(0, (rightLeg2.getSy()+rightLeg3.getSy()), 20);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //draw objects on the screen
        super.onDraw(canvas);


        hip.setRotate(hipAngle.getAngle(), 0, 1, 0);
        hip.draw(canvas, purplePaint);

        body.draw(canvas, redPaint);//draw a cube onto the screen

        neck.draw(canvas, purplePaint);

        head.draw(canvas, bluePaint);

        leftArm1.draw(canvas, bluePaint);
        leftArm2.draw(canvas, greenPaint);
        leftArm3.draw(canvas, cyanPaint);

        rightArm1.draw(canvas, bluePaint);
        rightArm2.draw(canvas, greenPaint);
        rightArm3.draw(canvas, cyanPaint);

        leftLeg1.setRotate(leftLegAngle.getAngle(), 1, 0, 0);
        leftLeg1.draw(canvas, bluePaint);
        leftLeg2.setRotate(leftLegAngle.getAngle(), -1, 0, 0);
        leftLeg2.draw(canvas, greenPaint);
        leftLeg3.draw(canvas, redPaint);

        rightLeg1.setRotate(rightLegAngle.getAngle(), 1, 0, 0);
        rightLeg1.draw(canvas, bluePaint);
        rightLeg2.setRotate(rightLegAngle.getAngle(), -1, 0, 0);
        rightLeg2.draw(canvas, greenPaint);
        rightLeg3.draw(canvas, redPaint);
    }



}