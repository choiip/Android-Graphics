package com.bennyplo.android_mooc_graphics_3d;

import android.graphics.Canvas;
import android.graphics.Paint;

public class Cube {
    private Cube parent = null;
    private Coordinate[]vertices;//the vertices of a 3D cube
    private Coordinate[]draw_vertices;//the vertices for drawing a 3D cube

    private double tx;
    private double ty;
    private double tz;

    public double getTx() {
        return tx;
    }

    public double getTy() {
        return ty;
    }

    public double getTz() {
        return tz;
    }

    private double angle, ax, ay, az;
    private double rx, ry, rz;
    private double sx, sy, sz;

    public double getSx() {
        return sx;
    }

    public double getSy() {
        return sy;
    }

    public double getSz() {
        return sz;
    }

    public Cube() {
        this(null);
    }

    public Cube(Cube parent) {
        this.parent = parent;
        vertices = new Coordinate[8];
        vertices[0] = new Coordinate(-1, -1, -1, 1);
        vertices[1] = new Coordinate(-1, -1, 1, 1);
        vertices[2] = new Coordinate(-1, 1, -1, 1);
        vertices[3] = new Coordinate(-1, 1, 1, 1);
        vertices[4] = new Coordinate(1, -1, -1, 1);
        vertices[5] = new Coordinate(1, -1, 1, 1);
        vertices[6] = new Coordinate(1, 1, -1, 1);
        vertices[7] = new Coordinate(1, 1, 1, 1);
        setup(1,1,1);
        setTranslate(0,0,0);
        setRotate(0, 0,1,0);
        setRotateOffset(0, 0,0);
    }

    private void DrawLinePairs(Canvas canvas, Coordinate[] vertices, int start, int end, Paint paint)
    {//draw a line connecting 2 points
        //canvas - canvas of the view
        //points - array of points
        //start - index of the starting point
        //end - index of the ending point
        //paint - the paint of the line
        canvas.drawLine((int)vertices[start].x,(int)vertices[start].y,(int)vertices[end].x,(int)vertices[end].y,paint);
    }

    public void setup(double sx, double sy, double sz) {
        this.sx = sx;
        this.sy = sy;
        this.sz = sz;

        vertices=Transformation.scale(vertices,sx,sy,sz);
    }

    public void setTranslate(double tx, double ty, double tz) {
        this.tx = tx;
        this.ty = ty;
        this.tz = tz;
    }
    public void setRotate(double angle, double ax, double ay, double az) {
        this.angle = angle;
        this.ax = ax;
        this.ay = ay;
        this.az = az;
    }
    public void setRotateOffset(double rx, double ry, double rz) {
        this.rx = rx;
        this.ry = ry;
        this.rz = rz;
    }
    public Coordinate[] transform(Coordinate[] vertices) {
        Coordinate[] draw_cube_vertices=vertices;
        draw_cube_vertices=Transformation.translate(draw_cube_vertices,tx,ty,tz);
        draw_cube_vertices=Transformation.translate(draw_cube_vertices,rx,ry,rz);
        draw_cube_vertices=Transformation.rotate(draw_cube_vertices, angle, ax, ay, az);
        draw_cube_vertices=Transformation.translate(draw_cube_vertices,-rx,-ry,-rz);

        if (parent != null) {
            draw_cube_vertices = parent.transform(draw_cube_vertices);
        }
        return draw_cube_vertices;
    }

    public void draw(Canvas canvas, Paint paint)
    {
        draw_vertices = transform(vertices);
        //draw a cube on the screen
        DrawLinePairs(canvas, draw_vertices, 0, 1, paint);
        DrawLinePairs(canvas, draw_vertices, 1, 3, paint);
        DrawLinePairs(canvas, draw_vertices, 3, 2, paint);
        DrawLinePairs(canvas, draw_vertices, 2, 0, paint);
        DrawLinePairs(canvas, draw_vertices, 4, 5, paint);
        DrawLinePairs(canvas, draw_vertices, 5, 7, paint);
        DrawLinePairs(canvas, draw_vertices, 7, 6, paint);
        DrawLinePairs(canvas, draw_vertices, 6, 4, paint);
        DrawLinePairs(canvas, draw_vertices, 0, 4, paint);
        DrawLinePairs(canvas, draw_vertices, 1, 5, paint);
        DrawLinePairs(canvas, draw_vertices, 2, 6, paint);
        DrawLinePairs(canvas, draw_vertices, 3, 7, paint);
    }
}
