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

import com.jme3.math.Quaternion;


/**
 *
 *  @version   $Revision: 3951 $
 *  @author    Paul Speed
 */
public final class Quatd implements Cloneable {

    public double x;
    public double y;
    public double z;
    public double w;

    public Quatd() {
        this( 0, 0, 0, 1 );
    }
    
    public Quatd( double x, double y, double z, double w ) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;        
    }

    @Override
    public final Quatd clone() {
        return new Quatd(x,y,z,w);
    }

    public Quaternion toQuaternion() {
        return new Quaternion((float)x, (float)y, (float)z, (float)w);
    }

    public final void set( double x, double y, double z, double w ) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;        
    } 

    public final void set( Quatd q ) {
        this.x = q.x;
        this.y = q.y;
        this.z = q.z;
        this.w = q.w;        
    } 

    public final Quatd add( Quatd q ) {
        return new Quatd(x + q.x, y + q.y, z + q.z, w + q.w);
    }

    public final Quatd addLocal( Quatd q ) {
        x += q.x;
        y += q.y;
        z += q.z;
        w += q.w;
        return this;
    }

    public final Quatd subtract( Quatd q ) {
        return new Quatd(x - q.x, y - q.y, z - q.z, w - q.w);
    }

    public final Quatd subtractLocal( Quatd q ) {
        x -= q.x;
        y -= q.y;
        z -= q.z;
        w -= q.w;
        return this;
    }

    public final void addScaledVectorLocal( Vec3d v, double scale ) {
        // Represent the vector as a quat
        Quatd q = new Quatd(v.x * scale, v.y * scale, v.z * scale, 0);
                
        q.multLocal(this);
               
        x = x + q.x * 0.5f;
        y = y + q.y * 0.5f;
        z = z + q.z * 0.5f;
        w = w + q.w * 0.5f;
    } 

    public final Quatd mult( Quatd q ) {
        double qx = q.x;
        double qy = q.y;
        double qz = q.z;
        double qw = q.w;
        
        double xr = x * qw + y * qz - z * qy + w * qx;
        double yr = -x * qz + y * qw + z * qx + w * qy;
        double zr = x * qy - y * qx + z * qw + w * qz;
        double wr = -x * qx - y * qy - z * qz + w * qw;
        
        return new Quatd(xr, yr, zr, wr);
    }
    
    public final Quatd multLocal( Quatd q ) {
        double qx = q.x;
        double qy = q.y;
        double qz = q.z;
        double qw = q.w;
        
        double xr = x * qw + y * qz - z * qy + w * qx;
        double yr = -x * qz + y * qw + z * qx + w * qy;
        double zr = x * qy - y * qx + z * qw + w * qz;
        double wr = -x * qx - y * qy - z * qz + w * qw;
 
        x = xr;
        y = yr;
        z = zr;
        w = wr;       
        return this;
    }

    public Vec3d mult( Vec3d v ) {
        if( v.x == 0 && v.y == 0 && v.z == 0 )
            return new Vec3d();
        
        double vx = v.x;
        double vy = v.y;
        double vz = v.z;
        
        double rx = w * w * vx + 2 * y * w * vz - 2 * z * w * vy + x * x
                    * vx + 2 * y * x * vy + 2 * z * x * vz - z * z * vx - y
                    * y * vx; 
        double ry = 2 * x * y * vx + y * y * vy + 2 * z * y * vz + 2 * w
                    * z * vx - z * z * vy + w * w * vy - 2 * x * w * vz - x
                    * x * vy;
        double rz = 2 * x * z * vx + 2 * y * z * vy + z * z * vz - 2 * w
                    * y * vx - y * y * vz + 2 * w * x * vy - x * x * vz + w
                    * w * vz;

        return new Vec3d(rx, ry, rz);                    
    }

    public Vec3d mult( Vec3d v, Vec3d result ) {
        if( v.x == 0 && v.y == 0 && v.z == 0 ) {
            if( v != result )
                result.set(0,0,0);
            return result;
        }
        
        double vx = v.x;
        double vy = v.y;
        double vz = v.z;
        
        double rx = w * w * vx + 2 * y * w * vz - 2 * z * w * vy + x * x
                    * vx + 2 * y * x * vy + 2 * z * x * vz - z * z * vx - y
                    * y * vx; 
        double ry = 2 * x * y * vx + y * y * vy + 2 * z * y * vz + 2 * w
                    * z * vx - z * z * vy + w * w * vy - 2 * x * w * vz - x
                    * x * vy;
        double rz = 2 * x * z * vx + 2 * y * z * vy + z * z * vz - 2 * w
                    * y * vx - y * y * vz + 2 * w * x * vy - x * x * vz + w
                    * w * vz;

        result.set(rx, ry, rz);                    
        return result;
    }

    public final double lengthSq() {
        return (x * x) + (y * y) + (z * z) + (w * w);
    }

    public final Quatd normalizeLocal() {
        double d = lengthSq();
        if( d == 0 ) {
            w = 1;
            return this;
        }
            
        double s = 1.0 / Math.sqrt(d);
        x *= s;
        y *= s;
        z *= s;
        w *= s;
        
        return this;
    }

  /*  public Matrix3d toRotationMatrix() {
        double d = lengthSq();        
        double s = 2 / d;
 
        // Premultiply for better performance       
        double xs = x * s;
        double ys = y * s;
        double zs = z * s;
        double xx = x * xs;
        double xy = x * ys;
        double xz = x * zs;
        double xw = w * xs;
        double yy = y * ys;
        double yz = y * zs;
        double yw = w * ys;
        double zz = z * zs;
        double zw = w * zs;

        // using s=2/norm (instead of 1/norm) saves 9 multiplications by 2 here
        double m00 = 1 - (yy + zz);
        double m01 = (xy - zw);
        double m02 = (xz + yw);
        double m10 = (xy + zw);
        double m11 = 1 - (xx + zz);
        double m12 = (yz - xw);
        double m20 = (xz - yw);
        double m21 = (yz + xw);
        double m22 = 1 - (xx + yy);

        return new Matrix3d( m00, m01, m02, 
                             m10, m11, m12,
                             m20, m21, m22 );
    }*/

    public Quatd inverse() {
        double norm = lengthSq();
        if( norm <= 0 )
            return null;
       
        double inv = 1 / norm;
        return new Quatd(-x * inv, -y * inv, -z * inv, w * inv); 
    }

    /**
     * Builds a Quaternion from the Euler rotation angles (x,y,z) aka 
     * (pitch, yaw, roll)).  They are applyed in order: (y, z, x) aka (yaw, roll, pitch).
     * @see <a href="http://www.euclideanspace.com/maths/geometry/rotations/conversions/eulerToQuaternion/index.htm">http://www.euclideanspace.com/maths/geometry/rotations/conversions/eulerToQuaternion/index.htm</a>
     */
    public Quatd fromAngles( double xAngle, double yAngle, double zAngle ) {
        double a;       
        double sinY, sinZ, sinX, cosY, cosZ, cosX;
        
        a = zAngle * 0.5f;
        sinZ = Math.sin(a);
        cosZ = Math.cos(a);
        a = yAngle * 0.5f;
        sinY = Math.sin(a);
        cosY = Math.cos(a);
        a = xAngle * 0.5f;
        sinX = Math.sin(a);
        cosX = Math.cos(a);

        // premultiply some reused stuff
        double cosYXcosZ = cosY * cosZ;
        double sinYXsinZ = sinY * sinZ;
        double cosYXsinZ = cosY * sinZ;
        double sinYXcosZ = sinY * cosZ;

        w = (cosYXcosZ * cosX - sinYXsinZ * sinX);
        x = (cosYXcosZ * sinX + sinYXsinZ * cosX);
        y = (sinYXcosZ * cosX + cosYXsinZ * sinX);
        z = (cosYXsinZ * cosX - sinYXcosZ * sinX);

        normalizeLocal();
        return this;
    }

    @Override
    public String toString() {
        return "Quatd[" + x + ", " + y + ", " + z + ", " + w + "]";
    }
}
