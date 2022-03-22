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
public final class Quatd implements Cloneable, java.io.Serializable {

    static final long serialVersionUID = 42L;

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

    public Quatd( Quatd quat ) {
        this(quat.x, quat.y, quat.z, quat.w);
    }

    public Quatd( Quaternion quat ) {
        this.x = quat.getX();
        this.y = quat.getY();
        this.z = quat.getZ();
        this.w = quat.getW();
    }

    @Override
    public final Quatd clone() {
        return new Quatd(x,y,z,w);
    }

    public Quaternion toQuaternion() {
        return new Quaternion((float)x, (float)y, (float)z, (float)w);
    }

    @Override
    public int hashCode() {
        long bits = Double.doubleToLongBits(x);
        bits ^= Double.doubleToLongBits(y) * 31;
        bits ^= Double.doubleToLongBits(z) * 31;
        bits ^= Double.doubleToLongBits(w) * 31;

        return ((int)bits) ^ ((int)(bits >> 32));
    }

    @Override
    public boolean equals( Object o ) {
        if( o == this )
            return true;
        if( o == null || o.getClass() != getClass() )
            return false;
        Quatd other = (Quatd)o;
        if( other.x != x )
            return false;
        if( other.y != y )
            return false;
        if( other.z != z )
            return false;
        if( other.w != w )
            return false;
        return true;
    }

    /**
     *  Returns true if this Quatd is similar to the specified Quatd within
     *  some value of epsilon.
     */
    public boolean isSimilar( Quatd other, double epsilon ) {
        if( other == null ) {
            return false;
        }
        if( Double.compare(Math.abs(other.x - x), epsilon) > 0 ) {
            return false;
        }
        if( Double.compare(Math.abs(other.y - y), epsilon) > 0 ) {
            return false;
        }
        if( Double.compare(Math.abs(other.z - z), epsilon) > 0 ) {
            return false;
        }
        if( Double.compare(Math.abs(other.w - w), epsilon) > 0 ) {
            return false;
        }
        return true;
    }

    public final Quatd set( double x, double y, double z, double w ) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
        return this;
    }

    public final Quatd set( Quatd q ) {
        this.x = q.x;
        this.y = q.y;
        this.z = q.z;
        this.w = q.w;
        return this;
    }

    public final Quatd set( Quaternion quat ) {
        this.x = quat.getX();
        this.y = quat.getY();
        this.z = quat.getZ();
        this.w = quat.getW();
        return this;
    }

    public double get( int i ) {
        switch( i ) {
            case 0:
                return x;
            case 1:
                return y;
            case 2:
                return z;
            case 3:
                return w;
            default:
                throw new IndexOutOfBoundsException( "Index:" + i );
        }
    }

    public Quatd set( int i, double d ) {
        switch( i ) {
            case 0:
                this.x = d;
                break;
            case 1:
                this.y = d;
                break;
            case 2:
                this.z = d;
                break;
            case 3:
                this.w = d;
                break;
            default:
                throw new IndexOutOfBoundsException( "Index:" + i );
        }
        return this;
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

    public final Quatd addScaledVectorLocal( Vec3d v, double scale ) {
        // Represent the vector as a quat
        Quatd q = new Quatd(v.x * scale, v.y * scale, v.z * scale, 0);

        q.multLocal(this);

        x = x + q.x * 0.5f;
        y = y + q.y * 0.5f;
        z = z + q.z * 0.5f;
        w = w + q.w * 0.5f;

        return this;
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

    public Matrix3d toRotationMatrix() {
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
    }

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
    public Quatd fromAngles( double[] angles ) {
        return fromAngles(angles[0], angles[1], angles[2]);
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

    /**
     *  Returns this quaternion converted to Euler rotation angles (yaw,roll,pitch).
     *  Note that the result is not always 100% accurate due to the implications of euler angles.
     *  @see <a href="http://www.euclideanspace.com/maths/geometry/rotations/conversions/quaternionToEuler/index.htm">http://www.euclideanspace.com/maths/geometry/rotations/conversions/quaternionToEuler/index.htm</a>
     *
     *  @param angles the double[] in which the angles should be stored, or null if
     *            you want a new double[] to be created
     *  @return the double[] in which the angles are stored.
     */
    public double[] toAngles( double[] angles ) {
        if( angles == null ) {
            angles = new double[3];
        } else if( angles.length != 3 ) {
            throw new IllegalArgumentException("Angles array must have three elements");
        }

        double sqw = w * w;
        double sqx = x * x;
        double sqy = y * y;
        double sqz = z * z;
        double unit = sqx + sqy + sqz + sqw; // if normalized is one, otherwise
        // is correction factor
        double test = x * y + z * w;
        if( test > 0.499 * unit ) { // singularity at north pole
            angles[1] = 2 * Math.atan2(x, w);
            angles[2] = Math.PI * 0.5;
            angles[0] = 0;
        } else if( test < -0.499 * unit ) { // singularity at south pole
            angles[1] = -2 * Math.atan2(x, w);
            angles[2] = -Math.PI * 0.5;
            angles[0] = 0;
        } else {
            angles[1] = Math.atan2(2 * y * w - 2 * x * z, sqx - sqy - sqz + sqw); // roll or heading
            angles[2] = Math.asin(2 * test / unit); // pitch or attitude
            angles[0] = Math.atan2(2 * x * w - 2 * y * z, -sqx + sqy - sqz + sqw); // yaw or bank
        }
        return angles;
    }

    /**
     *  Sets this Quatd's value to the linear interpolation of start
     *  and end using the mix value as the amount to interpolate.
     */
    public Quatd slerpLocal( Quatd start, Quatd end, double mix ) {
        // If they are already the same then just set the values
        if( start.x == end.x && start.y == end.y && start.z == end.z && start.w == end.w ) {
            set(start);
            return this;
        }

        double endx = end.x;
        double endy = end.y;
        double endz = end.z;
        double endw = end.w;

        double dot = (start.x * endx)
                    + (start.y * endy)
                    + (start.z * endz)
                    + (start.w * endw);

        if( dot < 0.0 ) {
            // Negate the second quaternion and the result of the dot
            // product.  (Note: JME actually modifies the second quaternion
            // which is mathematically ok but to me not ok as a software dev.)
            endx = -endx;
            endy = -endy;
            endz = -endz;
            endw = -endw;
            dot = -dot;
        }

        double scale1 = 1 - mix;
        double scale2 = mix;

        // If the angle between the two quaternions is big enough
        // then we will do a more complicated interpolation.  Basically,
        // for anything less than about 25.8 degrees difference then we'll
        // do regular lerp else we'll do a circular lerp.
        // JME does (1 - result) > 0.1f which to me is the same
        // as result < 0.9, no?  Maybe they wanted the comparison to
        // look more like "compare to a small angle" but to me dot = 1
        // already means "angles are equal"... so dot < 0.9 is already
        // "angles are not almost equal".
        if( dot < 0.9 ) {
            // Calculate the angle between the 4D vectors using the dot (cos)
            double theta = Math.acos(dot);

            // Calculate new scales by interpolating the sines of the angle
            double invSinTheta = 1.0 / Math.sin(theta);
            scale1 = Math.sin(scale1 * theta) * invSinTheta;
            scale2 = Math.sin(scale2 * theta) * invSinTheta;
        }

        this.x = (scale1 * start.x) + (scale2 * endx);
        this.y = (scale1 * start.y) + (scale2 * endy);
        this.z = (scale1 * start.z) + (scale2 * endz);
        this.w = (scale1 * start.w) + (scale2 * endw);

        return this;
    }

    @Override
    public String toString() {
        return "Quatd[" + x + ", " + y + ", " + z + ", " + w + "]";
    }
}
