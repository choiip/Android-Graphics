package com.github.choiip.android_graphics;

public class Transformation {
    //*********************************
    //matrix and transformation functions
    public static double []GetIdentityMatrix()
    {//return an 4x4 identity matrix
        double []matrix=new double[16];
        matrix[0]=1;matrix[1]=0;matrix[2]=0;matrix[3]=0;
        matrix[4]=0;matrix[5]=1;matrix[6]=0;matrix[7]=0;
        matrix[8]=0;matrix[9]=0;matrix[10]=1;matrix[11]=0;
        matrix[12]=0;matrix[13]=0;matrix[14]=0;matrix[15]=1;
        return matrix;
    }
    private static Coordinate transform(Coordinate vertex,double []matrix)
    {//affine transformation with homogeneous coordinates
        //i.e. a vector (vertex) multiply with the transformation matrix
        // vertex - vector in 3D
        // matrix - transformation matrix
        Coordinate result=new Coordinate();
        result.x=matrix[0]*vertex.x+matrix[1]*vertex.y+matrix[2]*vertex.z+matrix[3];
        result.y=matrix[4]*vertex.x+matrix[5]*vertex.y+matrix[6]*vertex.z+matrix[7];
        result.z=matrix[8]*vertex.x+matrix[9]*vertex.y+matrix[10]*vertex.z+matrix[11];
        result.w=matrix[12]*vertex.x+matrix[13]*vertex.y+matrix[14]*vertex.z+matrix[15];
        return result;
    }
    private static Coordinate[]transform(Coordinate []vertices,double []matrix)
    {   //Affine transform a 3D object with vertices
        // vertices - vertices of the 3D object.
        // matrix - transformation matrix
        Coordinate []result=new Coordinate[vertices.length];
        for (int i=0;i<vertices.length;i++)
        {
            result[i]=transform(vertices[i],matrix);
            result[i].Normalise();
        }
        return result;
    }
    //***********************************************************
    //Affine transformation
    public static Coordinate []translate(Coordinate []vertices,double tx,double ty,double tz)
    {
        double []matrix=GetIdentityMatrix();
        matrix[3]=tx;
        matrix[7]=ty;
        matrix[11]=tz;
        return transform(vertices,matrix);
    }
    public static Coordinate[]scale(Coordinate []vertices,double sx,double sy,double sz)
    {
        double []matrix=GetIdentityMatrix();
        matrix[0]=sx;
        matrix[5]=sy;
        matrix[10]=sz;
        return transform(vertices,matrix);
    }
    public static Coordinate[] rotate(Coordinate[] vertices, double angle, double x, double y, double z)
    {
        angle = angle * 3.141 / 180;
        double w = Math.cos(angle/2);
        x *= Math.sin(angle/2);
        y *= Math.sin(angle/2);
        z *= Math.sin(angle/2);

        return quaternion(vertices, w, x, y, z);
    }
    public static Coordinate[]quaternion(Coordinate []vertices,double w, double x, double y, double z)
    {
        double []matrix=GetIdentityMatrix();
        matrix[0]=w*w+x*x-y*y-z*z;
        matrix[1]=2*x*y-2*w*z;
        matrix[2]=2*x*z+2*w*y;
        matrix[3]=0;
        matrix[4]=2*x*y+2*w*z;
        matrix[5]=w*w-x*x+y*y-z*z;;
        matrix[6]=2*y*z-2*w*x;
        matrix[7]=0;
        matrix[8]=2*x*z-2*w*y;
        matrix[9]=2*y*z+2*w*x;
        matrix[10]=w*w-x*x-y*y+z*z;
        matrix[11]=0;
        matrix[12]=0;
        matrix[13]=0;
        matrix[14]=0;
        matrix[15]=1;
        return transform(vertices,matrix);
    }
}
