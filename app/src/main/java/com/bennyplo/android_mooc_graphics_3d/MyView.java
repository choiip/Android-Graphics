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
    private final Paint.Style paintStyle = Paint.Style.STROKE;

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


    private double bodyAngle = 0;
    private double direction = 0.1;

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
        //create a 3D cube

        dummy = new Cube(redPaint);
        body = new Cube(redPaint,dummy);
        neck = new Cube(purplePaint, body);
        head = new Cube(bluePaint, neck);
        hip = new Cube(purplePaint, body);

        leftArm1 = new Cube(bluePaint, body);
        leftArm2 = new Cube(greenPaint, leftArm1);
        leftArm3 = new Cube(cyanPaint, leftArm2);

        rightArm1 = new Cube(bluePaint, body);
        rightArm2 = new Cube(greenPaint, rightArm1);
        rightArm3 = new Cube(cyanPaint, rightArm2);

        leftLeg1 = new Cube(bluePaint, hip);
        leftLeg2 = new Cube(greenPaint, leftLeg1);
        leftLeg3 = new Cube(redPaint, leftLeg2);

        rightLeg1 = new Cube(bluePaint, hip);
        rightLeg2 = new Cube(greenPaint, rightLeg1);
        rightLeg3 = new Cube(redPaint, rightLeg2);

        thisview.invalidate();//update the view

        Timer timer=new Timer();
        TimerTask task= new TimerTask() {
            @Override
            public void run() {
                bodyAngle += direction;
                if (bodyAngle > 45) {
                    bodyAngle = 45;
                    direction *= -1;
                } else if (bodyAngle < -45) {
                    bodyAngle = -45;
                    direction *= -1;
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
        body.setup(180, 220, 60);
        body.setTranslate(0, 0, 0);
        neck.setup(40, 30, 20);
        neck.setTranslate(0, -(body.getSy() + neck.getSy()), 0);
        head.setup(80, 80, 80);
        head.setTranslate(0, -(neck.getSy()+head.getSy()), 0);
        hip.setup(body.getSx(), 50, body.getSz());
        hip.setTranslate(0, (body.getSy() + hip.getSy()), 0);

        leftArm1.setup(body.getSx()/3, 100, 40);
        leftArm1.setTranslate(-(body.getSx()+leftArm1.getSx()), -(body.getSy()-leftArm1.getSy()), 0);
        leftArm2.setup(body.getSx()/3, 120, 40);
        leftArm2.setTranslate(0, (leftArm1.getSy()+leftArm2.getSy()), 0);
        leftArm3.setup(body.getSx()/3, 30, 80);
        leftArm3.setTranslate(0, (leftArm2.getSy()+leftArm3.getSy()), 30);

        rightArm1.setup(body.getSx()/3, 100, 40);
        rightArm1.setTranslate(body.getSx()+rightArm1.getSx(), -(body.getSy()-rightArm1.getSy()), 0);
        rightArm2.setup(body.getSx()/3, 120, 40);
        rightArm2.setTranslate(0, (rightArm1.getSy()+rightArm2.getSy()), 0);
        rightArm3.setup(body.getSx()/3, 30, 80);
        rightArm3.setTranslate(0, (rightArm2.getSy()+rightArm3.getSy()), 30);

        leftLeg1.setup(body.getSx()/3, 100, 40);
        leftLeg1.setTranslate(-body.getSx()/3*2, (hip.getSy()+leftLeg1.getSy()), 0);
        leftLeg2.setup(body.getSx()/3, 120, 40);
        leftLeg2.setTranslate(0, (leftLeg1.getSy()+leftLeg2.getSy()), 0);
        leftLeg3.setup(body.getSx()/3, 30, 80);
        leftLeg3.setTranslate(0, (leftLeg2.getSy()+leftLeg3.getSy()), 20);

        rightLeg1.setup(body.getSx()/3, 100, 40);
        rightLeg1.setTranslate(body.getSx()/3*2, (hip.getSy()+rightLeg1.getSy()), 0);
        rightLeg2.setup(body.getSx()/3, 120, 40);
        rightLeg2.setTranslate(0, (rightLeg1.getSy()+rightLeg2.getSy()), 0);
        rightLeg3.setup(body.getSx()/3, 30, 80);
        rightLeg3.setTranslate(0, (rightLeg2.getSy()+rightLeg3.getSy()), 20);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        //draw objects on the screen
        super.onDraw(canvas);


        body.setRotate(bodyAngle, 0, 1, 0);
        body.draw(canvas);//draw a cube onto the screen

        neck.draw(canvas);

        head.draw(canvas);

        hip.draw(canvas);

        leftArm1.draw(canvas);
        leftArm2.draw(canvas);
        leftArm3.draw(canvas);

        rightArm1.draw(canvas);
        rightArm2.draw(canvas);
        rightArm3.draw(canvas);

        leftLeg1.draw(canvas);
        leftLeg2.draw(canvas);
        leftLeg3.draw(canvas);

        rightLeg1.draw(canvas);
        rightLeg2.draw(canvas);
        rightLeg3.draw(canvas);
    }



}