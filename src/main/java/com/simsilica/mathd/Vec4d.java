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

import com.jme3.math.Vector4f;


/**
 *
 *  @version   $Revision: 4026 $
 *  @author    Paul Speed
 */
public final class Vec4d implements Cloneable, java.io.Serializable {

    static final long serialVersionUID = 42L;
    
    public double x;
    public double y;
    public double z;
    public double w;
    
    public Vec4d() {
    }
    
    public Vec4d( double x, double y, double z, double w ) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }
 
    public Vector4f toVector4f() {
        return new Vector4f((float)x, (float)y, (float)z, (float)w);
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
        Vec4d other = (Vec4d)o;
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
    
    public final Vec4d set( double x, double y, double z, double w ) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
        return this;
    }
    
    public final Vec4d set( Vec4d v ) {
        this.x = v.x;
        this.y = v.y;
        this.z = v.z;
        this.w = v.w;
        return this;
    }
 
    @Override
    public final Vec4d clone() {
        return new Vec4d(x,y,z,w);
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
    
    public Vec4d set( int i, double d ) {
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
 
    public final Vec4d add( Vec4d v ) {
        return new Vec4d(x + v.x, y + v.y, z + v.z, w + v.w);
    }

    public final Vec4d add( double vx, double vy, double vz, double vw ) {
        return new Vec4d(x + vx, y + vy, z + vz, w + vw);
    }

    public final Vec4d subtract( Vec4d v ) {
        return new Vec4d(x - v.x, y - v.y, z - v.z, w - v.w);
    }

    public final Vec4d subtract( double vx, double vy, double vz, double vw ) {
        return new Vec4d(x - vx, y - vy, z - vz, w - vw);
    }

    public final Vec4d mult( double s ) {
        return new Vec4d(x * s, y * s, z * s, w * s);
    }
    
    public final Vec4d mult( Vec4d v ) {
        return new Vec4d(x * v.x, y * v.y, z * v.z, w * v.w);
    }

    public final Vec4d divide( double s ) {
        return new Vec4d(x / s, y / s, z / s, w / s);
    }
    
    public final Vec4d divide( Vec4d v ) {
        return new Vec4d(x / v.x, y / v.y, z / v.z, w / v.w);
    }

    public final Vec4d addLocal( Vec4d v ) {
        x += v.x;
        y += v.y;
        z += v.z;
        w += v.w;
        return this;
    }

    public final Vec4d addLocal( double vx, double vy, double vz, double vw ) {
        x += vx;
        y += vy;
        z += vz;
        w += vw;
        return this;
    }

    public final Vec4d subtractLocal( Vec4d v ) {
        x -= v.x;
        y -= v.y;
        z -= v.z;
        w -= v.w;
        return this;
    }

    public final Vec4d subtractLocal( double vx, double vy, double vz, double vw ) {
        x -= vx;
        y -= vy;
        z -= vz;
        w -= vw;
        return this;
    }

    public final Vec4d multLocal( double s ) {
        x *= s;
        y *= s;
        z *= s;
        w *= s;
        return this;
    }
 
    public final Vec4d multLocal( Vec4d v ) {
        x *= v.x;
        y *= v.y;
        z *= v.z;
        w *= v.w;
        return this;
    }

    public final Vec4d divideLocal( double s ) {
        x /= s;
        y /= s;
        z /= s;
        w /= s;
        return this;
    }
 
    public final Vec4d divideLocal( Vec4d v ) {
        x /= v.x;
        y /= v.y;
        z /= v.z;
        w /= v.w;
        return this;
    }
 
    public final double lengthSq() {
        return x * x + y * y + z * z + w * w;
    }
    
    public final double length() {
        return Math.sqrt(lengthSq());                
    }
 
    public final double distanceSq( Vec4d v ) {
        double xs = v.x - x;
        double ys = v.y - y;
        double zs = v.z - z;
        double ws = v.w - w;
        return xs * xs + ys * ys + zs * zs + ws * ws; 
    }

    public final double distance( Vec4d v ) {
        return Math.sqrt(distanceSq(v)); 
    }
 
    public final Vec4d normalize() {
        return mult(1.0 / length());
    }

    public final Vec4d normalizeLocal() {
        return multLocal(1.0 / length());
    }
 
    public final double dot( Vec4d v ) {
        return x * v.x + y * v.y + z * v.z + w * v.w;
    }
 
    public final double dot( double vx, double vy, double vz, double vw ) {
        return x * vx + y * vy + z * vz + w * vw;
    }
 
    public final Vec4d addScaledVectorLocal( Vec4d toAdd, double scale ) {
        x += toAdd.x * scale;
        y += toAdd.y * scale;
        z += toAdd.z * scale;
        w += toAdd.w * scale;
        return this;
    }
 
    public final Vec4d minLocal( Vec4d v ) {
        x = x < v.x ? x : v.x;
        y = y < v.y ? y : v.y;
        z = z < v.z ? z : v.z;
        w = w < v.w ? w : v.w;
        return this;
    }
    
    public final Vec4d maxLocal( Vec4d v ) {
        x = x > v.x ? x : v.x;
        y = y > v.y ? y : v.y;
        z = z > v.z ? z : v.z;
        w = w > v.w ? w : v.w;
        return this;
    }

    public final Vec4d zeroEpsilon( double e ) {
        if( x > -e && x < e )
            x = 0;
        if( y > -e && y < e )
            y = 0;
        if( z > -e && z < e )
            z = 0;
        if( w > -e && w < e )
            w = 0;
        return this;
    }
    
    @Override
    public String toString() {
        return "Vec3[" + x + ", " + y + ", " + z + ", " + w + "]";
    }
}
