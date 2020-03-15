package com.bennyplo.android_mooc_graphics_3d;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

import java.util.Comparator;
import java.util.Timer;
import java.util.TimerTask;

import static java.util.Arrays.sort;

public class MyView extends View {
    private Paint redPaint; //paint object for drawing the lines
    private Paint greenPaint; //paint object for drawing the lines
    private Paint bluePaint; //paint object for drawing the lines
    private Paint cyanPaint; //paint object for drawing the lines
    private Paint purplePaint; //paint object for drawing the lines
    private final Paint.Style paintStyle = Paint.Style.FILL_AND_STROKE;

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
    private MovingAngle leftArmAngle;
    private MovingAngle rightArmAngle;

    private MovingParam[] sequence;
    private int cs; // current sequence

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
//////////////////////////////////////////////////////////////////
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
        leftLeg1.setRotateOffset(0, -leftLeg1.getTy() + leftLeg1.getSy(), leftLeg1.getSz());
        leftLeg2.setup(body.getSx()/3, 130, 40);
        leftLeg2.setTranslate(0, (leftLeg1.getSy()+leftLeg2.getSy()), 0);
        leftLeg2.setRotateOffset(0, -leftLeg2.getTy() + leftLeg2.getSy(), leftLeg2.getSz());
        leftLeg3.setup(body.getSx()/3, 30, 80);
        leftLeg3.setTranslate(0, (leftLeg2.getSy()+leftLeg3.getSy()), 20);

        rightLeg1.setup(body.getSx()/3, 100, 40);
        rightLeg1.setTranslate(body.getSx()/3*2, (hip.getSy()+rightLeg1.getSy()), 0);
        rightLeg1.setRotateOffset(0, -rightLeg1.getTy() + rightLeg1.getSy(), rightLeg1.getSz());
        rightLeg2.setup(body.getSx()/3, 130, 40);
        rightLeg2.setTranslate(0, (rightLeg1.getSy()+rightLeg2.getSy()), 0);
        rightLeg2.setRotateOffset(0, -rightLeg2.getTy() + rightLeg2.getSy(), rightLeg2.getSz());
        rightLeg3.setup(body.getSx()/3, 30, 80);
        rightLeg3.setTranslate(0, (rightLeg2.getSy()+rightLeg3.getSy()), 20);
///////////////////////////////////////////////////////////////////

        hipAngle = new MovingAngle(-45, 45, 0.2);
        leftLegAngle = new MovingAngle(0, 45, 1);
        rightLegAngle = new MovingAngle(0, 45, 1);
        rightLegAngle.setPause(true);
        leftArmAngle = new MovingAngle(0, 45, 1);
        rightArmAngle = new MovingAngle(0, 45, 1);
        leftArmAngle.setPause(true);

        sequence = new MovingParam[16];  // LLRRLLRRLLRRLLRR
        for (int i=0; i< sequence.length; i++) {
            sequence[i] = new MovingParam();
        }
        // sequence 1
        sequence[0].ax = 1; sequence[0].ay = 0; sequence[0].az = 0;
        sequence[0].rx = 0; sequence[0].ry = -leftArm1.getTy() + leftArm1.getSy(); sequence[0].rz = -leftArm1.getSz();
        sequence[1].ax = 1; sequence[1].ay = 0; sequence[1].az = 0;
        sequence[1].rx = 0; sequence[1].ry = -leftArm2.getTy() + leftArm2.getSy(); sequence[1].rz = -leftArm2.getSz();

        sequence[2].ax = 1; sequence[2].ay = 0; sequence[2].az = 0;
        sequence[2].rx = 0; sequence[2].ry = -rightArm1.getTy() + rightArm1.getSy(); sequence[2].rz = -rightArm1.getSz();
        sequence[3].ax = 1; sequence[3].ay = 0; sequence[3].az = 0;
        sequence[3].rx = 0; sequence[3].ry = -rightArm2.getTy() + rightArm2.getSy(); sequence[3].rz = -rightArm2.getSz();

        // sequence 2
        sequence[4].ax = 0; sequence[4].ay = 0; sequence[4].az = 1;
        sequence[4].rx = -leftArm1.getTx() - leftArm1.getSx(); sequence[4].ry = -leftArm1.getTy() + leftArm1.getSy(); sequence[4].rz = 0;
        sequence[5].ax = 0; sequence[5].ay = 0; sequence[5].az = 1;
        sequence[5].rx = -leftArm2.getTx() + leftArm2.getSx(); sequence[5].ry = -leftArm2.getTy() + leftArm2.getSy(); sequence[5].rz = 0;

        sequence[6].ax = 0; sequence[6].ay = 0; sequence[6].az = -1;
        sequence[6].rx = -rightArm1.getTx() + rightArm1.getSx(); sequence[6].ry = -rightArm1.getTy() + rightArm1.getSy(); sequence[6].rz = 0;
        sequence[7].ax = 0; sequence[7].ay = 0; sequence[7].az = -1;
        sequence[7].rx = -rightArm2.getTx() - rightArm2.getSx(); sequence[7].ry = -rightArm2.getTy() + rightArm2.getSy(); sequence[7].rz = 0;

        // sequence 3
        sequence[8].ax = 1; sequence[8].ay = 1; sequence[8].az = 0;
        sequence[8].rx = 0; sequence[8].ry = -leftArm1.getTy() + leftArm1.getSy(); sequence[8].rz = 0;
        sequence[9].ax = 1; sequence[9].ay = 1; sequence[9].az = 0;
        sequence[9].rx = 0; sequence[9].ry = -leftArm2.getTy() + leftArm2.getSy(); sequence[9].rz = 0;

        sequence[10].ax = 1; sequence[10].ay = 1; sequence[10].az = 0;
        sequence[10].rx = 0; sequence[10].ry = -rightArm1.getTy() + rightArm1.getSy(); sequence[10].rz = 0;
        sequence[11].ax = 1; sequence[11].ay = 1; sequence[11].az = 0;
        sequence[11].rx = 0; sequence[11].ry = -rightArm2.getTy() + rightArm2.getSy(); sequence[11].rz = 0;

        // sequence 4
        sequence[12].ax = 1; sequence[12].ay = -1; sequence[12].az = 0;
        sequence[12].rx = 0; sequence[12].ry = -leftArm1.getTy() + leftArm1.getSy(); sequence[12].rz = 0;
        sequence[13].ax = 1; sequence[13].ay = -1; sequence[13].az = 0;
        sequence[13].rx = 0; sequence[13].ry = -leftArm2.getTy() + leftArm2.getSy(); sequence[13].rz = 0;

        sequence[14].ax = 1; sequence[14].ay = -1; sequence[14].az = 0;
        sequence[14].rx = 0; sequence[14].ry = -rightArm1.getTy() + rightArm1.getSy(); sequence[14].rz = 0;
        sequence[15].ax = 1; sequence[15].ay = -1; sequence[15].az = 0;
        sequence[15].rx = 0; sequence[15].ry = -rightArm2.getTy() + rightArm2.getSy(); sequence[15].rz = 0;

        cs = 0; // start with first sequence

        thisview.invalidate();//update the view

        Timer timer=new Timer();
        TimerTask task= new TimerTask() {
            @Override
            public void run() {
                hipAngle.advance();
//                hipAngle.setAngle(0);
                leftLegAngle.advance();
                rightLegAngle.advance();
                leftArmAngle.advance();
                rightArmAngle.advance();
                if (cs <= 1) {
                    if (!leftLegAngle.isPause() && leftLegAngle.getAngle() == 0) {
                        rightLegAngle.setPause(false);
                        leftLegAngle.setPause(true);
                    } else if (!rightLegAngle.isPause() && rightLegAngle.getAngle() == 0) {
                        leftLegAngle.setPause(false);
                        rightLegAngle.setPause(true);
                    }
                    if (!rightArmAngle.isPause() && rightArmAngle.getAngle() == 0) {
                        leftArmAngle.setPause(false);
                        rightArmAngle.setPause(true);
                    } else if (!leftArmAngle.isPause() && leftArmAngle.getAngle() == 0) {
                        rightArmAngle.setPause(false);
                        leftArmAngle.setPause(true);
                        cs = (cs + 1) % 4;
                        if (cs >= 2) {
                            leftArmAngle.setPause(false);
                            leftLegAngle.setPause(true);
                            rightLegAngle.setPause(true);
                        }
                    }
                } else {
                    if (leftArmAngle.getAngle() == 0) {
                        cs = (cs + 1) % 4;
                        if (cs == 0) {
                            leftArmAngle.setPause(true);
                            rightLegAngle.setPause(false);
                        }
                    }
                }

                hip.setRotate(hipAngle.getAngle(), 0, 1, 0);

                leftArm1.setRotate(leftArmAngle.getAngle(), sequence[4*cs].ax, sequence[4*cs].ay, sequence[4*cs].az);
                leftArm1.setRotateOffset(sequence[4*cs].rx, sequence[4*cs].ry, sequence[4*cs].rz);
                leftArm2.setRotate(leftArmAngle.getAngle() * 2, sequence[4*cs+1].ax, sequence[4*cs+1].ay, sequence[4*cs+1].az);
                leftArm2.setRotateOffset(sequence[4*cs+1].rx, sequence[4*cs+1].ry, sequence[4*cs+1].rz);
                rightArm1.setRotate(rightArmAngle.getAngle(), sequence[4*cs+2].ax, sequence[4*cs+2].ay, sequence[4*cs+2].az);
                rightArm1.setRotateOffset(sequence[4*cs+2].rx, sequence[4*cs+2].ry, sequence[4*cs+2].rz);
                rightArm2.setRotate(rightArmAngle.getAngle() * 2, sequence[4*cs+3].ax, sequence[4*cs+3].ay, sequence[4*cs+3].az);
                rightArm2.setRotateOffset(sequence[4*cs+3].rx, sequence[4*cs+3].ry, sequence[4*cs+3].rz);

                leftLeg1.setRotate(leftLegAngle.getAngle(), 1, 0, 0);
                leftLeg2.setRotate(leftLegAngle.getAngle(), -1, 0, 0);
                rightLeg1.setRotate(rightLegAngle.getAngle(), 1, 0, 0);
                rightLeg2.setRotate(rightLegAngle.getAngle(), -1, 0, 0);

                thisview.invalidate();//update the view
            }
        };
        timer.scheduleAtFixedRate(task,100,4);
    }

    @Override
    public void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        screenWidth = w;
        screenHeight = h;

        dummy.setup(screenWidth/4, screenHeight/4, 1);
        dummy.setTranslate(screenWidth/2, screenHeight/2, 0);
        dummy.setRotate(180, 0,1,0);
        dummy.setRotateOffset(-dummy.getTx(), -dummy.getTy(), 0);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        //draw objects on the screen
        super.onDraw(canvas);

        hip.preDraw(purplePaint);
        body.preDraw(redPaint);
        neck.preDraw(purplePaint);
        head.preDraw(bluePaint);
        leftArm1.preDraw(bluePaint);
        leftArm2.preDraw(greenPaint);
        leftArm3.preDraw(cyanPaint);
        rightArm1.preDraw(bluePaint);
        rightArm2.preDraw(greenPaint);
        rightArm3.preDraw(cyanPaint);
        leftLeg1.preDraw(bluePaint);
        leftLeg2.preDraw(greenPaint);
        leftLeg3.preDraw(redPaint);
        rightLeg1.preDraw(bluePaint);
        rightLeg2.preDraw(greenPaint);
        rightLeg3.preDraw(redPaint);

        Cube[] renderCubes = {
                hip,
                body,
                neck,
                head,
                leftArm1, leftArm2, leftArm3,
                rightArm1, rightArm2, rightArm3,
                leftLeg1, leftLeg2, leftLeg3,
                rightLeg1, rightLeg2, rightLeg3,
        };
        sort(renderCubes, new Comparator<Cube>() {
            @Override
            public int compare(Cube o1, Cube o2) {
                return new Double(o2.getMaxZ()).compareTo(o1.getMaxZ());
            }
        });
        for (Cube c: renderCubes) {
            c.draw(canvas);
        }
    }
}