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
 *
 *  @author    Paul Speed
 */
public class Matrix3d implements Cloneable, java.io.Serializable {

    static final long serialVersionUID = 42L;
    
    public double m00, m01, m02;    
    public double m10, m11, m12;    
    public double m20, m21, m22;
    
    public Matrix3d() {
        makeIdentity();
    }
 
    public Matrix3d( double m00, double m01, double m02,
                     double m10, double m11, double m12,
                     double m20, double m21, double m22 ) {
        this.m00 = m00;
        this.m01 = m01;
        this.m02 = m02;
        this.m10 = m10;
        this.m11 = m11;
        this.m12 = m12;
        this.m20 = m20;
        this.m21 = m21;
        this.m22 = m22;
    } 
 
    public Matrix3d clone() {
        return new Matrix3d(m00, m01, m02,  
                            m10, m11, m12,
                            m20, m21, m22);
    }
 
    public Matrix3d set( Matrix3d mat ) {
        this.m00 = mat.m00;
        this.m01 = mat.m01;
        this.m02 = mat.m02;
        this.m10 = mat.m10;
        this.m11 = mat.m11;
        this.m12 = mat.m12;
        this.m20 = mat.m20;
        this.m21 = mat.m21;
        this.m22 = mat.m22;
        return this;
    }
 
    public Matrix3d makeIdentity() {
        m01 = m02 = m10 = m12 = m20 = m21 = 0;
        m00 = m11 = m22 = 1;
        return this;
    }

    public Vec3d getColumn( int i ) {
        switch( i ) {
            case 0:
                return new Vec3d(m00,m10,m20);
            case 1:
                return new Vec3d(m01,m11,m21);
            case 2:
                return new Vec3d(m02,m12,m22);
        }
        return null;            
    }        

    public Matrix3d setColumn( int i, Vec3d col ) {
        switch( i ) {
            case 0:
                m00 = col.x;
                m10 = col.y;
                m20 = col.z;
                break;
            case 1:
                m01 = col.x;
                m11 = col.y;
                m21 = col.z;
                break;
            case 2:
                m02 = col.x;
                m12 = col.y;
                m22 = col.z;
                break;
            default:
                throw new IllegalArgumentException( "Column does not exist:" + i );
        }
        return this;            
    }
     
    public Matrix3d mult( Matrix3d mat ) {
        double temp00 = m00 * mat.m00 + m01 * mat.m10 + m02 * mat.m20;
        double temp01 = m00 * mat.m01 + m01 * mat.m11 + m02 * mat.m21;
        double temp02 = m00 * mat.m02 + m01 * mat.m12 + m02 * mat.m22;
        double temp10 = m10 * mat.m00 + m11 * mat.m10 + m12 * mat.m20;
        double temp11 = m10 * mat.m01 + m11 * mat.m11 + m12 * mat.m21;
        double temp12 = m10 * mat.m02 + m11 * mat.m12 + m12 * mat.m22;
        double temp20 = m20 * mat.m00 + m21 * mat.m10 + m22 * mat.m20;
        double temp21 = m20 * mat.m01 + m21 * mat.m11 + m22 * mat.m21;
        double temp22 = m20 * mat.m02 + m21 * mat.m12 + m22 * mat.m22;
        
        return new Matrix3d( temp00, temp01, temp02,
                             temp10, temp11, temp12,
                             temp20, temp21, temp22 );
    }

    public Vec3d mult( Vec3d v ) {
        double x = v.x;
        double y = v.y;
        double z = v.z;
        
        double xr = (m00 * x) + (m01 * y) + (m02 * z);
        double yr = (m10 * x) + (m11 * y) + (m12 * z);
        double zr = (m20 * x) + (m21 * y) + (m22 * z);
        
        return new Vec3d(xr,yr,zr);
    }

    public Matrix3d multLocal( Matrix3d mat ) {
        double temp00 = m00 * mat.m00 + m01 * mat.m10 + m02 * mat.m20;
        double temp01 = m00 * mat.m01 + m01 * mat.m11 + m02 * mat.m21;
        double temp02 = m00 * mat.m02 + m01 * mat.m12 + m02 * mat.m22;
        double temp10 = m10 * mat.m00 + m11 * mat.m10 + m12 * mat.m20;
        double temp11 = m10 * mat.m01 + m11 * mat.m11 + m12 * mat.m21;
        double temp12 = m10 * mat.m02 + m11 * mat.m12 + m12 * mat.m22;
        double temp20 = m20 * mat.m00 + m21 * mat.m10 + m22 * mat.m20;
        double temp21 = m20 * mat.m01 + m21 * mat.m11 + m22 * mat.m21;
        double temp22 = m20 * mat.m02 + m21 * mat.m12 + m22 * mat.m22;
        
        this.m00 = temp00;
        this.m01 = temp01;
        this.m02 = temp02;
        this.m10 = temp10;
        this.m11 = temp11;
        this.m12 = temp12;
        this.m20 = temp20;
        this.m21 = temp21;
        this.m22 = temp22;
        
        return this;
    }
    
    public Matrix3d multLocal( double scale ) {
        this.m00 *= scale;
        this.m01 *= scale;
        this.m02 *= scale;
        this.m10 *= scale;
        this.m11 *= scale;
        this.m12 *= scale;
        this.m20 *= scale;
        this.m21 *= scale;
        this.m22 *= scale;
        
        return this;
    }
    
    public double determinant() {
        double co00 = (m11 * m22) - (m12 * m21);
        double co10 = (m12 * m20) - (m10 * m22);
        double co20 = (m10 * m21) - (m11 * m20);
        return m00 * co00 + m01 * co10 + m02 * co20;
    }
    
    public Matrix3d invert() {
        double d = determinant();
        if( d == 0 )
            return new Matrix3d(); // questionable
        
        double rm00 = m11 * m22 - m12 * m21;
        double rm01 = m02 * m21 - m01 * m22;
        double rm02 = m01 * m12 - m02 * m11;
        double rm10 = m12 * m20 - m10 * m22;
        double rm11 = m00 * m22 - m02 * m20;
        double rm12 = m02 * m10 - m00 * m12;
        double rm20 = m10 * m21 - m11 * m20;
        double rm21 = m01 * m20 - m00 * m21;
        double rm22 = m00 * m11 - m01 * m10;
 
        double s = 1.0 / d;
 
        return new Matrix3d( rm00 * s, rm01 * s, rm02 * s,
                             rm10 * s, rm11 * s, rm12 * s,              
                             rm20 * s, rm21 * s, rm22 * s );
    }
    
    public Matrix3d transpose() {
        return new Matrix3d( m00, m10, m20, m01, m11, m21, m02, m12, m22 );
    }

    public Matrix3d setSkewSymmetric( Vec3d v ) {
        m00 = 0; 
        m11 = 0; 
        m22 = 0;
        
        m01 = -v.z;
        m02 = v.y;
        m10 = v.z;
        m12 = -v.x;
        m20 = -v.y;
        m21 = v.x;
        
        return this;
    }
 
    public Matrix3d addLocal( Matrix3d add ) {
        m00 = m00 + add.m00;
        m01 = m01 + add.m01;
        m02 = m02 + add.m02;
        m10 = m10 + add.m10;
        m11 = m11 + add.m11;
        m12 = m12 + add.m12;
        m20 = m20 + add.m20;
        m21 = m21 + add.m21;
        m22 = m22 + add.m22;
        
        return this;
    }
    
    public String toString() {
        return "Matrix3d[{" + m00 + ", " + m01 + ", " + m02 + "}, {"
                            + m10 + ", " + m11 + ", " + m12 + "}, {"
                            + m20 + ", " + m21 + ", " + m22 + "}]";
    }
}


