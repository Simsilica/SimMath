/*
 * $Id$
 * 
 * Copyright (c) 2015, Simsilica, LLC
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions 
 * are met:
 * 
 * 1. Redistributions of source code must retain the above copyright 
 *    notice, this list of conditions and the following disclaimer.
 * 
 * 2. Redistributions in binary form must reproduce the above copyright 
 *    notice, this list of conditions and the following disclaimer in 
 *    the documentation and/or other materials provided with the 
 *    distribution.
 * 
 * 3. Neither the name of the copyright holder nor the names of its 
 *    contributors may be used to endorse or promote products derived 
 *    from this software without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS 
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT 
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS 
 * FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE 
 * COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, 
 * INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES 
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR 
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) 
 * HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, 
 * STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) 
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED 
 * OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package com.simsilica.mathd;

/**
 *
 *  @author    Paul Speed
 */
public class Matrix4d implements Cloneable, java.io.Serializable {

    static final long serialVersionUID = 42L;

    public double m00, m01, m02, m03;    
    public double m10, m11, m12, m13;    
    public double m20, m21, m22, m23;    
    public double m30, m31, m32, m33;    
    
    public Matrix4d() {
        makeIdentity();
    }
 
    public Matrix4d( double m00, double m01, double m02, double m03,
                     double m10, double m11, double m12, double m13,
                     double m20, double m21, double m22, double m23, 
                     double m30, double m31, double m32, double m33 ) {
        this.m00 = m00;
        this.m01 = m01;
        this.m02 = m02;
        this.m03 = m03;
        this.m10 = m10;
        this.m11 = m11;
        this.m12 = m12;
        this.m13 = m13;
        this.m20 = m20;
        this.m21 = m21;
        this.m22 = m22;
        this.m23 = m23;
        this.m30 = m30;
        this.m31 = m31;
        this.m32 = m32;
        this.m33 = m33;
    } 
 
    public Matrix4d clone() {
        return new Matrix4d(m00, m01, m02, m03,  
                            m10, m11, m12, m13,
                            m20, m21, m22, m23,
                            m30, m31, m32, m33);
    }
 
    public void makeIdentity() {
        m01 = m02 = m03 = 0;
        m10 = m12 = m13 = 0;
        m20 = m21 = m23 = 0;
        m30 = m31 = m32 = 0;
        m00 = m11 = m22 = m33 = 1;
    }

    public void setTransform( Vec3d pos, Matrix3d rot ) {
        m00 = rot.m00;
        m01 = rot.m01;
        m02 = rot.m02;
        m03 = pos.x;
        
        m10 = rot.m10;
        m11 = rot.m11;
        m12 = rot.m12;
        m13 = pos.y;
        
        m20 = rot.m20;
        m21 = rot.m21;
        m22 = rot.m22;
        m23 = pos.z;
        
        m30 = 0;
        m31 = 0;
        m32 = 0;
        m33 = 1;        
    }
   
    public Matrix4d mult( Matrix4d mat ) {
        double temp00 = m00 * mat.m00
                + m01 * mat.m10
                + m02 * mat.m20
                + m03 * mat.m30;
        double temp01 = m00 * mat.m01
                + m01 * mat.m11
                + m02 * mat.m21
                + m03 * mat.m31;
        double temp02 = m00 * mat.m02
                + m01 * mat.m12
                + m02 * mat.m22
                + m03 * mat.m32;
        double temp03 = m00 * mat.m03
                + m01 * mat.m13
                + m02 * mat.m23
                + m03 * mat.m33;

        double temp10 = m10 * mat.m00
                + m11 * mat.m10
                + m12 * mat.m20
                + m13 * mat.m30;
        double temp11 = m10 * mat.m01
                + m11 * mat.m11
                + m12 * mat.m21
                + m13 * mat.m31;
        double temp12 = m10 * mat.m02
                + m11 * mat.m12
                + m12 * mat.m22
                + m13 * mat.m32;
        double temp13 = m10 * mat.m03
                + m11 * mat.m13
                + m12 * mat.m23
                + m13 * mat.m33;

        double temp20 = m20 * mat.m00
                + m21 * mat.m10
                + m22 * mat.m20
                + m23 * mat.m30;
        double temp21 = m20 * mat.m01
                + m21 * mat.m11
                + m22 * mat.m21
                + m23 * mat.m31;
        double temp22 = m20 * mat.m02
                + m21 * mat.m12
                + m22 * mat.m22
                + m23 * mat.m32;
        double temp23 = m20 * mat.m03
                + m21 * mat.m13
                + m22 * mat.m23
                + m23 * mat.m33;

        double temp30 = m30 * mat.m00
                + m31 * mat.m10
                + m32 * mat.m20
                + m33 * mat.m30;
        double temp31 = m30 * mat.m01
                + m31 * mat.m11
                + m32 * mat.m21
                + m33 * mat.m31;
        double temp32 = m30 * mat.m02
                + m31 * mat.m12
                + m32 * mat.m22
                + m33 * mat.m32;
        double temp33 = m30 * mat.m03
                + m31 * mat.m13
                + m32 * mat.m23
                + m33 * mat.m33;

        return new Matrix4d( temp00, temp01, temp02, temp03,
                             temp10, temp11, temp12, temp13,
                             temp20, temp21, temp22, temp23,
                             temp30, temp31, temp32, temp33 );
    }
    
    public Vec3d mult( Vec3d v ) {
        double x = v.x;
        double y = v.y;
        double z = v.z;
        
        // this is conceptually extending the vector with an extra 1...
        // but we don't have to calculate all that.
        double xr = (m00 * x) + (m01 * y) + (m02 * z) + m03;
        double yr = (m10 * x) + (m11 * y) + (m12 * z) + m13;
        double zr = (m20 * x) + (m21 * y) + (m22 * z) + m23;
        
        return new Vec3d(xr,yr,zr);
    }

    public Vec3d mult( Vec3d v, Vec3d result ) {
        double x = v.x;
        double y = v.y;
        double z = v.z;
        
        // this is conceptually extending the vector with an extra 1...
        // but we don't have to calculate all that.
        double xr = (m00 * x) + (m01 * y) + (m02 * z) + m03;
        double yr = (m10 * x) + (m11 * y) + (m12 * z) + m13;
        double zr = (m20 * x) + (m21 * y) + (m22 * z) + m23;
 
        if( result == null ) {       
            return new Vec3d(xr,yr,zr);
        } else {
            result.set(xr, yr, zr);
            return result;
        }
    }

    public double determinant() {
        double a0 = m00 * m11 - m01 * m10;
        double a1 = m00 * m12 - m02 * m10;
        double a2 = m00 * m13 - m03 * m10;
        double a3 = m01 * m12 - m02 * m11;
        double a4 = m01 * m13 - m03 * m11;
        double a5 = m02 * m13 - m03 * m12;
        double b0 = m20 * m31 - m21 * m30;
        double b1 = m20 * m32 - m22 * m30;
        double b2 = m20 * m33 - m23 * m30;
        double b3 = m21 * m32 - m22 * m31;
        double b4 = m21 * m33 - m23 * m31;
        double b5 = m22 * m33 - m23 * m32;
        return a0 * b5 - a1 * b4 + a2 * b3 + a3 * b2 - a4 * b1 + a5 * b0;
    }
    
    public Matrix4d invert() {
        double a0 = m00 * m11 - m01 * m10;
        double a1 = m00 * m12 - m02 * m10;
        double a2 = m00 * m13 - m03 * m10;
        double a3 = m01 * m12 - m02 * m11;
        double a4 = m01 * m13 - m03 * m11;
        double a5 = m02 * m13 - m03 * m12;
        double b0 = m20 * m31 - m21 * m30;
        double b1 = m20 * m32 - m22 * m30;
        double b2 = m20 * m33 - m23 * m30;
        double b3 = m21 * m32 - m22 * m31;
        double b4 = m21 * m33 - m23 * m31;
        double b5 = m22 * m33 - m23 * m32;
        double d = a0 * b5 - a1 * b4 + a2 * b3 + a3 * b2 - a4 * b1 + a5 * b0;    
        if( d == 0 )
            return new Matrix4d(); // questionable
 

        double rm00 = +m11 * b5 - m12 * b4 + m13 * b3;
        double rm10 = -m10 * b5 + m12 * b2 - m13 * b1;
        double rm20 = +m10 * b4 - m11 * b2 + m13 * b0;
        double rm30 = -m10 * b3 + m11 * b1 - m12 * b0;
        double rm01 = -m01 * b5 + m02 * b4 - m03 * b3;
        double rm11 = +m00 * b5 - m02 * b2 + m03 * b1;
        double rm21 = -m00 * b4 + m01 * b2 - m03 * b0;
        double rm31 = +m00 * b3 - m01 * b1 + m02 * b0;
        double rm02 = +m31 * a5 - m32 * a4 + m33 * a3;
        double rm12 = -m30 * a5 + m32 * a2 - m33 * a1;
        double rm22 = +m30 * a4 - m31 * a2 + m33 * a0;
        double rm32 = -m30 * a3 + m31 * a1 - m32 * a0;
        double rm03 = -m21 * a5 + m22 * a4 - m23 * a3;
        double rm13 = +m20 * a5 - m22 * a2 + m23 * a1;
        double rm23 = -m20 * a4 + m21 * a2 - m23 * a0;
        double rm33 = +m20 * a3 - m21 * a1 + m22 * a0;
 
        
        double s = 1.0 / d;
 
        return new Matrix4d( rm00 * s, rm01 * s, rm02 * s, rm03 * s,
                             rm10 * s, rm11 * s, rm12 * s, rm13 * s,              
                             rm20 * s, rm21 * s, rm22 * s, rm23 * s, 
                             rm30 * s, rm31 * s, rm32 * s, rm33 * s );
    }
    
    public Matrix4d transpose() {
        return new Matrix4d( m00, m10, m20, m30, 
                             m01, m11, m21, m31, 
                             m02, m12, m22, m32,
                             m03, m13, m23, m33 );
    }

    public Matrix3d toRotationMatrix() {
        return new Matrix3d( m00, m01, m02, m10, m11, m12, m20, m21, m22 );
    }

    @Override
    public boolean equals( Object o ) {
        if( o == this )
            return true;
        if( o == null || o.getClass() != getClass() )
            return false;

        Matrix4d other = (Matrix4d)o;
        if( Double.compare(m00, other.m00) != 0 )
            return false;
        if( Double.compare(m01, other.m01) != 0 )
            return false;
        if( Double.compare(m02, other.m02) != 0 )
            return false;
        if( Double.compare(m03, other.m03) != 0 )
            return false;
        if( Double.compare(m10, other.m10) != 0 )
            return false;
        if( Double.compare(m11, other.m11) != 0 )
            return false;
        if( Double.compare(m12, other.m12) != 0 )
            return false;
        if( Double.compare(m13, other.m13) != 0 )
            return false;
        if( Double.compare(m20, other.m20) != 0 )
            return false;
        if( Double.compare(m21, other.m21) != 0 )
            return false;
        if( Double.compare(m22, other.m22) != 0 )
            return false;
        if( Double.compare(m23, other.m23) != 0 )
            return false;
        if( Double.compare(m30, other.m30) != 0 )
            return false;
        if( Double.compare(m31, other.m31) != 0 )
            return false;
        if( Double.compare(m32, other.m32) != 0 )
            return false;
        if( Double.compare(m33, other.m33) != 0 )
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        long bits = Double.doubleToLongBits(m00);
        bits ^= Double.doubleToLongBits(m01) * 19L;
        bits ^= Double.doubleToLongBits(m02) * 19L;
        bits ^= Double.doubleToLongBits(m03) * 19L;
        bits ^= Double.doubleToLongBits(m10) * 19L;
        bits ^= Double.doubleToLongBits(m11) * 19L;
        bits ^= Double.doubleToLongBits(m12) * 19L;
        bits ^= Double.doubleToLongBits(m13) * 19L;
        bits ^= Double.doubleToLongBits(m20) * 19L;
        bits ^= Double.doubleToLongBits(m21) * 19L;
        bits ^= Double.doubleToLongBits(m22) * 19L;
        bits ^= Double.doubleToLongBits(m23) * 19L;
        bits ^= Double.doubleToLongBits(m30) * 19L;
        bits ^= Double.doubleToLongBits(m31) * 19L;
        bits ^= Double.doubleToLongBits(m32) * 19L;
        bits ^= Double.doubleToLongBits(m33) * 19L;

        return ((int)bits) ^ ((int)(bits >> 32));
    }

    public String toString() {
        return "Matrix4d[{" + m00 + ", " + m01 + ", " + m02 + ", " + m03 + "}, {"
                            + m10 + ", " + m11 + ", " + m12 + ", " + m13 + "}, {"
                            + m20 + ", " + m21 + ", " + m22 + ", " + m23 + "}, {"
                            + m30 + ", " + m31 + ", " + m32 + ", " + m33 + "}]";
    }
}



