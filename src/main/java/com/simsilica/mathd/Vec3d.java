/*
 * $Id: Vec3d.java 3951 2015-04-07 06:40:56Z pspeed $
 *
 * Copyright (c) 2011, Paul Speed
 * All rights reserved.
 */

package com.simsilica.mathd;

import com.jme3.math.Vector3f;


/**
 *
 *  @version   $Revision: 3951 $
 *  @author    Paul Speed
 */
public final class Vec3d implements Cloneable
{
    public static final Vec3d UNIT_X = new Vec3d(1,0,0);
    public static final Vec3d UNIT_Y = new Vec3d(0,1,0);
    public static final Vec3d UNIT_Z = new Vec3d(0,0,1);
    
    public double x;
    public double y;
    public double z;
    
    public Vec3d() {
    }
    
    public Vec3d( double x, double y, double z ) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
 
    public Vector3f toVector3f() {
        return new Vector3f((float)x, (float)y, (float)z);
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
        if( other.x != x )
            return false;
        if( other.y != y )
            return false;
        if( other.z != z )
            return false;
        return true;
    }
    
    public final void set( double x, double y, double z ) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    
    public final void set( Vec3d v ) {
        this.x = v.x;
        this.y = v.y;
        this.z = v.z;
    }
 
    @Override
    public final Vec3d clone() {
        return new Vec3d(x,y,z);
    }
 
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
    
    public void set( int i, double d ) {
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
    }
 
    public final Vec3d add( Vec3d v ) {
        return new Vec3d(x + v.x, y + v.y, z + v.z);
    }

    public final Vec3d add( double vx, double vy, double vz ) {
        return new Vec3d(x + vx, y + vy, z + vz);
    }

    public final Vec3d subtract( Vec3d v ) {
        return new Vec3d(x - v.x, y - v.y, z - v.z);
    }

    public final Vec3d subtract( double vx, double vy, double vz ) {
        return new Vec3d(x - vx, y - vy, z - vz);
    }

    public final Vec3d mult( double s ) {
        return new Vec3d(x * s, y * s, z * s);
    }
    
    public final Vec3d mult( Vec3d v ) {
        return new Vec3d(x * v.x, y * v.y, z * v.z);
    }

    public final Vec3d addLocal( Vec3d v ) {
        x += v.x;
        y += v.y;
        z += v.z;
        return this;
    }

    public final Vec3d addLocal( double vx, double vy, double vz ) {
        x += vx;
        y += vy;
        z += vz;
        return this;
    }

    public final Vec3d subtractLocal( Vec3d v ) {
        x -= v.x;
        y -= v.y;
        z -= v.z;
        return this;
    }

    public final Vec3d subtractLocal( double vx, double vy, double vz ) {
        x -= vx;
        y -= vy;
        z -= vz;
        return this;
    }

    public final Vec3d multLocal( double s ) {
        x *= s;
        y *= s;
        z *= s;
        return this;
    }
 
    public final double lengthSq() {
        return x * x + y * y + z * z;
    }
    
    public final double length() {
        return Math.sqrt(lengthSq());                
    }
 
    public final double distanceSq( Vec3d v ) {
        double xs = v.x - x;
        double ys = v.y - y;
        double zs = v.z - z;
        return xs * xs + ys * ys + zs * zs; 
    }

    public final double distance( Vec3d v ) {
        return Math.sqrt(distanceSq(v)); 
    }
 
    public final Vec3d normalize() {
        return mult(1.0 / length());
    }

    public final Vec3d normalizeLocal() {
        return multLocal(1.0 / length());
    }
 
    public final double dot( Vec3d v ) {
        return x * v.x + y * v.y + z * v.z;
    }
 
    public final double dot( double vx, double vy, double vz ) {
        return x * vx + y * vy + z * vz;
    }
 
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
 
    public final void zeroEpsilon( double e ) {
        if( x > -e && x < e )
            x = 0;
        if( y > -e && y < e )
            y = 0;
        if( z > -e && z < e )
            z = 0;
    }
    
    @Override
    public String toString() {
        return "Vec3[" + x + ", " + y + ", " + z + "]";
    }
}
