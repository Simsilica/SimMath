/*
 * $Id$
 * 
 * Copyright (c) 2021, Simsilica, LLC
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

import java.util.Objects;

import com.jme3.math.Ray;

/**
 *  A mathematical "ray" consisting of an origin point and a direction.
 *
 *  @author    Paul Speed
 */
public class Rayd implements Cloneable, java.io.Serializable {

    static final long serialVersionUID = 42;
 
    // The reason these are not public is because they are each composite
    // objects and there are certain rules as to what they are allowed to be.
    // The class overall is more useful if they are non-null and unit length
    // of direction is enforced... at least as much as we can.
    private Vec3d origin;
    private Vec3d direction;
 
    /**
     *  Provided only for certain types of serialization that require 
     *  no-arg constructors.
     */   
    protected Rayd() {
    }
    
    /**
     *  Creates a double-precision ray with the specified origin and direction.
     *  @throws IllegalArgumentException if origin or direction are null or
     *  direction is non-unit in length.
     */
    public Rayd( Vec3d origin, Vec3d direction ) {
        setOrigin(origin);
        setDirection(direction);
    }
    
    /**
     *  Creates a double-precision ray with the same double versions of 
     *  origin and direction from the supplied single precision Ray.
     */
    public Rayd( Ray ray ) {
        this(new Vec3d(ray.origin), new Vec3d(ray.direction));
    }
    
    public final void setOrigin( Vec3d origin ) {
        if( origin == null ) {
            throw new IllegalArgumentException("Origin cannot be null");
        }
        this.origin = origin; 
    }

    public Vec3d getOrigin() {
        return origin;
    }
    
    public final void setDirection( Vec3d direction ) {
        if( direction == null ) {
            throw new IllegalArgumentException("Direction cannot be null");
        } 
        if( direction.lengthSq() > 0.0001 ) {
            throw new IllegalArgumentException("Direction is not of unit length:" + direction);
        } 
        this.direction = direction;
    }
    
    public Vec3d getDirection() {
        return direction;
    }    
       
    public final Rayd set( Vec3d origin, Vec3d direction ) {
        setOrigin(origin);
        setDirection(direction);
        return this;
    }
 
    public final Rayd set( Rayd r ) {
        return set(r.origin.clone(), r.direction.clone());
    } 
    
    public final Rayd set( Ray r ) {
        return set(new Vec3d(r.origin), new Vec3d(r.direction));
    } 
 
    /**
     *  Creates a deep clone of this Ray that has its own origin and direction.
     */
    @Override
    public final Rayd clone() {
        return new Rayd(origin.clone(), direction.clone());
    }

    /**
     *  Returns a single-precision version of this ray.
     */
    public Ray toRay() {
        return new Ray(origin.toVector3f(), direction.toVector3f());
    }

    @Override
    public int hashCode() {
        return Objects.hash(origin, direction);
    }
    
    @Override
    public boolean equals( Object o ) {
        if( o == this )
            return true;
        if( o == null || o.getClass() != getClass() )
            return false;
        Rayd other = (Rayd)o;
        if( !Objects.equals(other.direction, direction) ) {
            return false;
        }
        if( !Objects.equals(other.origin, origin) ) {
            return false;
        }
        return true;
    }
    
    /**
     *  Returns true if this Quatd is similar to the specified Quatd within
     *  some value of epsilon.
     */
    public boolean isSimilar( Rayd other, double epsilon ) {
        if( other == null ) {
            return false;
        }
        if( !origin.isSimilar(other.origin, epsilon) ) {
            return false;
        }
        if( !direction.isSimilar(other.direction, epsilon) ) {
            return false;
        }
        return true;
    }
 
    /**
     *  Returns the point on this ray that is closest to the specified
     *  point, clamped to the origin and the specified limit value.
     *  If limit is less than or equal to zero then it is ignored as if it were infinity.  
     *  Stores the result in target, creating it if null.
     */   
    public Vec3d getClosestPoint( Vec3d point, double limit, Vec3d target ) {
        // Find the projected distance down the ray of the point
        // by doing a dot product of the origin-relative point.
        if( target == null ) {
            target = new Vec3d(point).subtractLocal(origin);
        } else {
            target.set(point).subtractLocal(origin);
        }
        double proj = direction.dot(target);
        if( limit > 0 ) {
            proj = Math.min(proj, limit);
        }
        if( proj > 0 ) {
            // Projected point lies on the ray
            target.set(direction).multLocal(proj).addLocal(origin);
        } else {
            // Projected point is "behind" the ray's origin so the closest
            // distance is to the origin itself
            target.set(origin);
        }
        return target;        
    }
 
    /**
     *  Returns the squared-distance from the specified point to this
     *  ray, where the ray length is limited to the specified limit. 
     *  If limit is less than or equal to zero then it is ignored as if it were infinity.  
     */   
    public double distanceSq( Vec3d point, double limit ) {        
        return getClosestPoint(point, limit, null).distanceSq(origin);       
    }
    
    @Override
    public String toString() {
        return "Rayd[origin:" + origin + ", direction:" + direction + "]";
    }   
}
