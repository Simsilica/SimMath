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
 *  Holds a three-dimensional integer coordinate.
 *
 *  @author    Paul Speed
 */
public class Vec3i implements Cloneable, java.io.Serializable {

    static final long serialVersionUID = 42;

    public int x;
    public int y;
    public int z;

    public Vec3i() {
    }

    public Vec3i( int x, int y, int z ) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    
    public Vec3i( Vector3f v ) {
        this((int)v.x, (int)v.y,(int)v.z);
    }

    public Vec3i( Vec3i v ) {
        this(v.x, v.y, v.z);
    }

    public Vector3f toVector3f() {
        return new Vector3f(x,y,z);
    }

    public Vec3d toVec3d() {
        return new Vec3d(x,y,z);
    }

    @Override
    public boolean equals( Object o ) {   
        if( o == this )
            return true;
        if( o == null )
            return false;
        if( o.getClass() != getClass() )
            return false;
        Vec3i other = (Vec3i)o;
        return x == other.x && y == other.y && z == other.z;
    }
    
    @Override
    public int hashCode() {
        int hash = 37;
        hash += 37 * hash + x;
        hash += 37 * hash + y;
        hash += 37 * hash + z;
        return hash;
    }

    @Override
    public Vec3i clone() {
        try {
            return (Vec3i)super.clone();
        } catch( CloneNotSupportedException e ) {
            throw new RuntimeException( "Error cloning", e );
        }
    }

    public final void set( int x, int y, int z ) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public final void set( Vec3i val ) {
        this.x = val.x;
        this.y = val.y;
        this.z = val.z;
    }

    public final int get( int index ) {
        switch( index ) {
            case 0:
                return x;
            case 1:
                return y;
            case 2:
                return z;
            default:
                throw new IndexOutOfBoundsException( String.valueOf(index) );
        }
    }

    public final void set( int index, int value ) {
        switch( index ) {
            case 0:
                this.x = value;
                break;
            case 1:
                this.y = value;
                break;
            case 2:
                this.z = value;
                break;
            default:
                throw new IndexOutOfBoundsException( String.valueOf(index) );
        }
    }

    public final void addLocal( Vec3i v ) {
        x += v.x;
        y += v.y;
        z += v.z;
    }

    public final void addLocal( int i, int j, int k ) {
        x += i;
        y += j;
        z += k;
    }

    public final void subtractLocal( Vec3i v ) {
        x -= v.x;
        y -= v.y;
        z -= v.z;
    }

    public final void subtractLocal( int i, int j, int k ) {
        x -= i;
        y -= j;
        z -= k;
    }
    
    public final Vec3i add( int i, int j, int k ) {
        return new Vec3i( x + i, y + j, z + k );
    }

    public final Vec3i add( Vec3i v ) {
        return new Vec3i( x + v.x, y + v.y, z + v.z );
    }
 
    public final Vec3i subtract( Vec3i v ) {
        return new Vec3i( x - v.x, y - v.y, z - v.z );
    } 
    
    public final Vec3i subtract( int vx, int vy, int vz ) {
        return new Vec3i( x - vx, y - vy, z - vz );
    } 
    
    public final Vec3i mult( int scale ) {
        return new Vec3i( x * scale, y * scale, z * scale );
    }
    
    public final int getDistanceSq( Vec3i v ) {
        int xd = v.x - x;
        int yd = v.y - y;
        int zd = v.z - z;
        
        return xd * xd + yd * yd + zd * zd;
    }
      
    public final double getDistance( Vec3i v ) {
        return Math.sqrt(getDistanceSq(v));
    }  

    public void minLocal( int i, int j, int k ) {
        x = Math.min(x, i);
        y = Math.min(y, j);
        z = Math.min(z, k);
    }
    
    public void maxLocal( int i, int j, int k ) {
        x = Math.max(x, i);
        y = Math.max(y, j);
        z = Math.max(z, k);
    }

    public void minLocal( Vec3i v ) {
        x = Math.min(v.x, x);
        y = Math.min(v.y, y);
        z = Math.min(v.z, z);
    }
    
    public void maxLocal( Vec3i v ) {
        x = Math.max(v.x, x);
        y = Math.max(v.y, y);
        z = Math.max(v.z, z);
    }

    public String toDisplay() {
        return "[" + x + ", " + y + ", " + z + "]";
    }

    @Override
    public String toString() {
        return "Vec3i[" + x + ", " + y + ", " + z + "]";
    }
}
