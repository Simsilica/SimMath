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
 *  Axis-aligned bounding box with some convenience methods.
 *
 *  @author    Paul Speed
 */
public final class AaBBox {
    private final Vec3d min = new Vec3d();
    private final Vec3d max = new Vec3d();
    
    public AaBBox() {
    }
    
    public AaBBox( double radius ) {
        setForRadius(new Vec3d(), radius);
    }
    
    public AaBBox( Vec3d center, double radius ) {
        setForRadius(center, radius);
    }
    
    public AaBBox( Vec3d min, Vec3d max ) {
        set(min, max);
    }
 
    public void set( Vec3d min, Vec3d max ) {
        this.min.set(min);
        this.max.set(max);
    }
    
    public void setForRadius( Vec3d center, double radius ) {
        min.set(center.x - radius, center.y - radius, center.z - radius);
        max.set(center.x + radius, center.y + radius, center.z + radius);
    }

    public void setForRadius( double xCenter, double yCenter, double zCenter, double radius ) {
        min.set(xCenter - radius, yCenter - radius, zCenter - radius);
        max.set(xCenter + radius, yCenter + radius, zCenter + radius);
    }

    public void setForExtents( Vec3d center, Vec3d extents ) {
        min.set(center.x, center.y, center.z);
        min.subtractLocal(extents);
        max.set(center.x, center.y, center.z);
        max.addLocal(extents);
    }
 
    public Vec3d getExtents() {
        return getExtents(null);
    }
    
    public Vec3d getExtents( Vec3d target ) {
        if( target == null ) {
            target = new Vec3d();
        }
        target.set(max);
        target.subtractLocal(min);
        target.multLocal(0.5);
        return target;
    }
 
    public void setCenter( Vec3d center ) {
        // Avoid creating new objects... at the slight cost
        // of cut-paste code.
        double x = center.x - ((max.x + min.x) * 0.5);
        double y = center.y - ((max.y + min.y) * 0.5);
        double z = center.z - ((max.z + min.z) * 0.5);
        min.addLocal(x, y, z);
        max.addLocal(x, y, z);        
    }

    public void setCenter( double xCenter, double yCenter, double zCenter ) {
        // Avoid creating new objects... at the slight cost
        // of cut-paste code.
        double x = xCenter - ((max.x + min.x) * 0.5);
        double y = yCenter - ((max.y + min.y) * 0.5);
        double z = zCenter - ((max.z + min.z) * 0.5);
        min.addLocal(x, y, z);
        max.addLocal(x, y, z);        
    }
    
    public Vec3d getCenter() {
        return getCenter(null);   
    }

    public Vec3d getCenter( Vec3d target ) {
        if( target == null ) {
            target = new Vec3d();
        }
        target.set(min);
        target.addLocal(max);
        target.multLocal(0.5);
        return target;   
    }
 
    public void setMin( Vec3d min ) {
        this.min.set(min);
    }
    
    public Vec3d getMin() {
        return min;
    }
    
    public void setMax( Vec3d max ) {
        this.max.set(max);
    }    
    
    public Vec3d getMax() {
        return max;
    }
 
    public static void main( String... args ) {
        AaBBox box = new AaBBox(new Vec3d(5, 5, 0), 2);
        System.out.println("box:" + box + "   center:" + box.getCenter() + "  extents:" + box.getExtents());
        
        box.setCenter(new Vec3d(7, 2, 1));
        System.out.println("box:" + box + "   center:" + box.getCenter() + "  extents:" + box.getExtents());
    }
 
    @Override   
    public String toString() {
        return getClass().getName() + "[min=" + min + ", max=" + max + "]";
    } 
}
