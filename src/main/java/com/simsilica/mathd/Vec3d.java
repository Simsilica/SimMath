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

import com.jme3.math.Vector3f;


/**
 * A vector composed of 3 double-precision components, used to represent
 * locations, offsets, velocities, and directions in 3-dimensional space.
 * <p>
 * Methods with names ending in "Local" modify the current instance. They are
 * used to avoid creating temporary vectors.
 * <p>
 * The conventional order of components is (X, Y, Z).
 *
 *  @version   $Revision: 3951 $
 *  @author    Paul Speed
 */
public class Vec3d implements Cloneable, java.io.Serializable {

    static final long serialVersionUID = 42L;
    
    public static final Vec3d UNIT_X = new Vec3d(1,0,0);
    public static final Vec3d UNIT_Y = new Vec3d(0,1,0);
    public static final Vec3d UNIT_Z = new Vec3d(0,0,1);
    public static final Vec3d ZERO = new Vec3d();
    
    public double x;
    public double y;
    public double z;
 
    /**
     * Instantiates an all-zero vector (0,0,0).
     */
    public Vec3d() {
    }
    
    /**
     * Instantiates a vector with the specified components.
     *
     * @param x the desired X component
     * @param y the desired Y component
     * @param z the desired Z component
     */
    public Vec3d( double x, double y, double z ) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * Instantiates a copy of the argument.
     *
     * @param v the Vec3d to copy (not null, unaffected)
     */
    public Vec3d( Vec3d v ) {
        this(v.x, v.y, v.z);
    }

    public Vec3d( Vec3i v ) {
        this(v.x, v.y, v.z);
    }

    /**
     * Instantiates a double-precision copy of the argument.
     *
     * @param v the input Vector3f (not null, unaffected)
     */
    public Vec3d( Vector3f v ) {
        this(v.x, v.y, v.z);
    }
 
    /**
     * Creates a single-precision copy of the current instance.
     *
     * @return a new Vector3f
     */
    public Vector3f toVector3f() {
        return new Vector3f((float)x, (float)y, (float)z);
    }
 
    /**
     *  Returns true if any of the x,y,z elements are NaN.
     */
    public boolean isNaN() {
        return Double.isNaN(x) || Double.isNaN(y) || Double.isNaN(z);
    }
 
    @Override
    public int hashCode() {
        long bits = Double.doubleToLongBits(x);
        bits ^= Double.doubleToLongBits(y) * 31;
        bits ^= Double.doubleToLongBits(z) * 31;
        
        return ((int)bits) ^ ((int)(bits >> 32));
    }
    
    @Override
    public boolean equals( Object o ) {
        if( o == this )
            return true;
        if( o == null || o.getClass() != getClass() )
            return false;
        Vec3d other = (Vec3d)o;
        if( Double.compare(x, other.x) != 0 )
            return false;
        if( Double.compare(y, other.y) != 0 )
            return false;
        if( Double.compare(z, other.z) != 0 )
            return false;
        return true;
    }

    /**
     *  Returns true if this Vec3d is similar to the specified Vec3d within
     *  some value of epsilon.
     */
    public boolean isSimilar( Vec3d other, double epsilon ) {
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
        return true;
    }

    /**
     * Tests for finite components. The vector is unaffected.
     *
     * @return false if any component is infinite or NaN, otherwise true
     */
    public boolean isFinite() {
        if( Double.isInfinite(x) || Double.isNaN(x)
                || Double.isInfinite(y) || Double.isNaN(y)
                || Double.isInfinite(z) || Double.isNaN(z) )
            return false;
        else
            return true;
    }

    /**
     * Sets all 3 components to specified values.
     *
     * @param x the desired X component
     * @param y the desired Y component
     * @param z the desired Z component
     * @return the (modified) current instance (for chaining)
     */
    public final Vec3d set( double x, double y, double z ) {
        this.x = x;
        this.y = y;
        this.z = z;
        return this;
    }
    
    /**
     * Copies all 3 components from the argument.
     *
     * @param v the Vec3d to copy (not null, unaffected)
     * @return the (modified) current instance (for chaining)
     */
    public final Vec3d set( Vec3d v ) {
        this.x = v.x;
        this.y = v.y;
        this.z = v.z;
        return this;
    }

    public final Vec3d set( Vec3i v ) {
        this.x = v.x;
        this.y = v.y;
        this.z = v.z;
        return this;
    }

    /**
     * Copies all 3 components from the argument, converting them to
     * double precision.
     *
     * @param v the Vector3f to copy (not null, unaffected)
     * @return the (modified) current instance (for chaining)
     */
    public final Vec3d set( Vector3f v ) {
        this.x = v.x;
        this.y = v.y;
        this.z = v.z;
        return this;
    }
 
    /**
     *  Returns the raw (int) cast version of this Vec3d as a Vec3i.
     */
    public final Vec3i toVec3i() {
        return new Vec3i((int)x, (int)y, (int)z);
    }
    
    /**
     *  Returns the Math.floor() version of this Vec3d as a Vec3i.
     */
    public final Vec3i floor() {
        return new Vec3i((int)Math.floor(x), (int)Math.floor(y), (int)Math.floor(z));
    }

    /**
     *  Returns the Math.ceil() version of this Vec3d as a Vec3i.
     */
    public final Vec3i ceil() {
        return new Vec3i((int)Math.ceil(x), (int)Math.ceil(y), (int)Math.ceil(z));
    }
 
    @Override
    public final Vec3d clone() {
        return new Vec3d(x,y,z);
    }
 
    /**
     * Returns the indexed component. The vector is unaffected.
     *
     * @param i 0, 1, or 2
     * @return the X component if i=0, the Y component if i=1, or the Z
     * component if i=2
     * @throws IndexOutOfBoundsException if {@code i} is not 0, 1, or 2
     */
    public double get( int i ) {
        switch( i ) {
            case 0:
                return x;
            case 1:
                return y;
            case 2:
                return z;
            default:
                throw new IndexOutOfBoundsException( "Index:" + i );
        }
    }
    
    /**
     * Alters the indexed component.
     *
     * @param i which component to set: 0 &rarr; the X component, 1 &rarr;
     * the Y component, 2 &rarr; the Z component
     * @param d the desired component value
     * @return the (modified) current instance (for chaining)
     * @throws IllegalArgumentException if {@code i} is not 0, 1, or 2
     */
    public Vec3d set( int i, double d ) {
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
            default:
                throw new IndexOutOfBoundsException( "Index:" + i );
        }
        return this;
    }
 
    /**
     * Adds the argument and returns the sum as a new instance. The current
     * instance is unaffected.
     *
     * @param v the vector to add (not null, unaffected)
     * @return a new Vec3d
     */
    public final Vec3d add( Vec3d v ) {
        return new Vec3d(x + v.x, y + v.y, z + v.z);
    }

    public final Vec3d add( Vec3i v ) {
        return new Vec3d(x + v.x, y + v.y, z + v.z);
    }

    /**
     * Adds specified amounts to the vector's components and returns the sum as
     * a new instance. The current instance is unaffected.
     *
     * @param vx the amount to add to the X component
     * @param vy the amount to add to the Y component
     * @param vz the amount to add to the Z component
     * @return a new Vec3d
     */
    public final Vec3d add( double vx, double vy, double vz ) {
        return new Vec3d(x + vx, y + vy, z + vz);
    }

    /**
     * Subtracts the argument and returns the difference as a new instance. The
     * current instance is unaffected.
     *
     * @param v the vector to subtract (not null, unaffected)
     * @return a new Vec3d
     */
    public final Vec3d subtract( Vec3d v ) {
        return new Vec3d(x - v.x, y - v.y, z - v.z);
    }

    public final Vec3d subtract( Vec3i v ) {
        return new Vec3d(x - v.x, y - v.y, z - v.z);
    }

    /**
     * Subtracts specified amounts from the vector's components and returns the
     * difference as a new instance. The current instance is unaffected.
     *
     * @param vx the amount to add to the X component
     * @param vy the amount to add to the Y component
     * @param vz the amount to add to the Z component
     * @return a new Vec3d
     */
    public final Vec3d subtract( double vx, double vy, double vz ) {
        return new Vec3d(x - vx, y - vy, z - vz);
    }

    /**
     * Multiplies with the scalar argument and returns the product as a new
     * instance. The current instance is unaffected.
     *
     * @param s the scaling factor
     * @return a new Vec3d
     */
    public final Vec3d mult( double s ) {
        return new Vec3d(x * s, y * s, z * s);
    }
    
    /**
     * Multiplies component-wise with the argument and returns the product as a
     * new instance. The current instance is unaffected.
     *
     * @param v the scale vector (not null, unaffected)
     * @return a new Vec3d
     */
    public final Vec3d mult( Vec3d v ) {
        return new Vec3d(x * v.x, y * v.y, z * v.z);
    }

    public final Vec3d mult( Vec3i v ) {
        return new Vec3d(x * v.x, y * v.y, z * v.z);
    }

    /**
     * Divides by the scalar argument and returns the quotient as a new
     * instance. The current instance is unaffected.
     *
     * @param s the divisor
     * @return a new Vec3d
     */
    public final Vec3d divide( double s ) {
        return new Vec3d(x / s, y / s, z / s);
    }
    
    /**
     * Divides component-wise by the argument and returns the quotient as a
     * new instance. The current instance is unaffected.
     *
     * @param v the inverse scale vector (not null, unaffected)
     * @return a new Vec3d
     */
    public final Vec3d divide( Vec3d v ) {
        return new Vec3d(x / v.x, y / v.y, z / v.z);
    }

    public final Vec3d divide( Vec3i v ) {
        return new Vec3d(x / v.x, y / v.y, z / v.z);
    }

    /**
     * Adds the argument and returns the (modified) current instance.
     * <p>
     * It IS safe for {@code v} and {@code this} to be the same object.
     *
     * @param v the vector to add (not null, unaffected unless it's
     * {@code this})
     * @return the (modified) current instance (for chaining)
     */
    public final Vec3d addLocal( Vec3d v ) {
        x += v.x;
        y += v.y;
        z += v.z;
        return this;
    }

    /**
     * Adds the Vec3i argument and returns the (modified) current instance.
     *
     * @param v the vector to add (not null, unaffected)
     * @return the (modified) current instance (for chaining)
     */
    public final Vec3d addLocal( Vec3i v ) {
        x += v.x;
        y += v.y;
        z += v.z;
        return this;
    }

    /**
     * Adds specified amounts to the vector's components and returns the
     * (modified) current instance.
     *
     * @param vx the amount to add to the X component
     * @param vy the amount to add to the Y component
     * @param vz the amount to add to the Z component
     * @return the (modified) current instance (for chaining)
     */
    public final Vec3d addLocal( double vx, double vy, double vz ) {
        x += vx;
        y += vy;
        z += vz;
        return this;
    }

    /**
     * Subtracts the argument and returns the (modified) current instance.
     * <p>
     * It IS safe for {@code v} and {@code this} to be the same object.
     *
     * @param v the vector to subtract (not null, unaffected unless it's
     * {@code this})
     * @return the (modified) current instance (for chaining)
     */
    public final Vec3d subtractLocal( Vec3d v ) {
        x -= v.x;
        y -= v.y;
        z -= v.z;
        return this;
    }

    public final Vec3d subtractLocal( Vec3i v ) {
        x -= v.x;
        y -= v.y;
        z -= v.z;
        return this;
    }

    /**
     * Subtracts the specified amounts from the vector's components and returns
     * the (modified) current instance.
     *
     * @param vx the amount to subtract from the X component
     * @param vy the amount to subtract from the Y component
     * @param vz the amount to subtract from the Z component
     * @return the (modified) current instance (for chaining)
     */
    public final Vec3d subtractLocal( double vx, double vy, double vz ) {
        x -= vx;
        y -= vy;
        z -= vz;
        return this;
    }

    /**
     * Multiplies by the scalar argument and returns the (modified) current
     * instance.
     *
     * @param s the scaling factor
     * @return the (modified) current instance (for chaining)
     */
    public final Vec3d multLocal( double s ) {
        x *= s;
        y *= s;
        z *= s;
        return this;
    }

    /**
     * Multiplies component-wise by the argument and returns the (modified)
     * current instance.
     * <p>
     * It IS safe for {@code v} and {@code this} to be the same object.
     *
     * @param v the scale vector (not null, unaffected unless it's {@code this})
     * @return the (modified) current instance (for chaining)
     */
    public final Vec3d multLocal( Vec3d v ) {
        x *= v.x;
        y *= v.y;
        z *= v.z;
        return this;
    }

    public final Vec3d multLocal( Vec3i v ) {
        x *= v.x;
        y *= v.y;
        z *= v.z;
        return this;
    }

    /**
     * Divides by the argument and returns the (modified) current instance.
     *
     * @param s the divisor
     * @return the (modified) current instance (for chaining)
     */
    public final Vec3d divideLocal( double s ) {
        x /= s;
        y /= s;
        z /= s;
        return this;
    }

    /**
     * Divides component-wise by the argument and returns the (modified) current
     * instance.
     * <p>
     * It IS safe for {@code v} and {@code this} to be the same object.
     *
     * @param v the divisor (not null, unaffected unless it's {@code this})
     * @return the (modified) current instance (for chaining)
     */
    public final Vec3d divideLocal( Vec3d v ) {
        x /= v.x;
        y /= v.y;
        z /= v.z;
        return this;
    }
 
    /**
     * Returns the square of the length. The current instance is unaffected.
     *
     * @return the sum of the squared components (not negative)
     */
    public final double lengthSq() {
        return x * x + y * y + z * z;
    }
    
    /**
     * Returns the length (or magnitude). The current instance is unaffected.
     *
     * @return the root-sum of the squared components (not negative)
     */
    public final double length() {
        return Math.sqrt(lengthSq());                
    }
 
    public final double distanceSq( Vec3d v ) {
        double xs = v.x - x;
        double ys = v.y - y;
        double zs = v.z - z;
        return xs * xs + ys * ys + zs * zs; 
    }

    public final double distanceSq( Vec3i v ) {
        double xs = v.x - x;
        double ys = v.y - y;
        double zs = v.z - z;
        return xs * xs + ys * ys + zs * zs; 
    }

    public final double distance( Vec3d v ) {
        return Math.sqrt(distanceSq(v)); 
    }

    public final double distanceSq( double vx, double vy, double vz ) {
        double xs = vx - x;
        double ys = vy - y;
        double zs = vz - z;
        return xs * xs + ys * ys + zs * zs; 
    }

    public final double distance( double vx, double vy, double vz ) {
        return Math.sqrt(distanceSq(vx, vy, vz)); 
    }
 
    public final Vec3d normalize() {
        return mult(1.0 / length());
    }

    /**
     * Normalize this vector in place. For a zero vector, the result is
     * undefined.
     *
     * @return the (normalized) current instance (for chaining)
     */
    public final Vec3d normalizeLocal() {
        return multLocal(1.0 / length());
    }
 
    /**
     * Returns the dot (or inner) product with the argument. The current
     * instance is unaffected.
     *
     * @param v the vector to multiply (not null, unaffected)
     * @return the dot product
     */
    public final double dot( Vec3d v ) {
        return x * v.x + y * v.y + z * v.z;
    }

    public final double dot( Vec3i v ) {
        return x * v.x + y * v.y + z * v.z;
    }
 
    public final double dot( double vx, double vy, double vz ) {
        return x * vx + y * vy + z * vz;
    }
 
    /**
     * Calculates a cross product with the argument and returns the product as a
     * new instance. The current instance is unaffected.
     *
     * @param v the right factor (not null, unaffected)
     * @return {@code this} cross {@code v} (a new Vec3d)
     */
    public final Vec3d cross( Vec3d v ) {
        double xNew = (y * v.z) - (z * v.y);
        double yNew = (z * v.x) - (x * v.z);
        double zNew = (x * v.y) - (y * v.x);
        return new Vec3d(xNew, yNew, zNew);         
    }

    public final Vec3d cross( double vx, double vy, double vz ) {
        double xNew = (y * vz) - (z * vy);
        double yNew = (z * vx) - (x * vz);
        double zNew = (x * vy) - (y * vx);
        return new Vec3d(xNew, yNew, zNew);         
    }

    public final Vec3d crossLocal( Vec3d v ) {
        double xNew = (y * v.z) - (z * v.y);
        double yNew = (z * v.x) - (x * v.z);
        double zNew = (x * v.y) - (y * v.x);
        
        x = xNew;
        y = yNew;
        z = zNew;
        return this;
    }

    public final Vec3d addScaledVectorLocal( Vec3d toAdd, double scale ) {
        x += toAdd.x * scale;
        y += toAdd.y * scale;
        z += toAdd.z * scale;
        return this;
    }
 
    public final Vec3d minLocal( Vec3d v ) {
        x = x < v.x ? x : v.x;
        y = y < v.y ? y : v.y;
        z = z < v.z ? z : v.z;
        return this;
    }
    
    public final Vec3d maxLocal( Vec3d v ) {
        x = x > v.x ? x : v.x;
        y = y > v.y ? y : v.y;
        z = z > v.z ? z : v.z;
        return this;
    }
 
    public final Vec3d zeroEpsilon( double e ) {
        if( x > -e && x < e )
            x = 0;
        if( y > -e && y < e )
            y = 0;
        if( z > -e && z < e )
            z = 0;
        return this;
    }
 
    /**
     *  Sets the value of this Vec3d to be the linearly interpolated
     *  value of start and end using the mix value as the 0 to 1 position
     *  between start and end.  Basically, this = (1 - min) * start + mix * end.
     */   
    public final Vec3d interpolateLocal( Vec3d start, Vec3d end, double mix ) {
        this.x = (1 - mix) * start.x + mix * end.x;
        this.y = (1 - mix) * start.y + mix * end.y;
        this.z = (1 - mix) * start.z + mix * end.z;
        return this;
    } 
    
    public final Vec3d xzy() {
        return new Vec3d(x, z, y);
    }
    
    @Override
    public String toString() {
        return "Vec3d[" + x + ", " + y + ", " + z + "]";
    }
}
