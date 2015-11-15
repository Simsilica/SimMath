/*
 * $Id: Vec3Bits.java 4027 2015-05-27 09:17:37Z pspeed $
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

package com.simsilica.mathd.bits;

import com.simsilica.mathd.Vec3d;


/**
 *
 *
 *  @author    Paul Speed
 */
public final class Vec3Bits {
 
    private final FloatBits xBits;
    private final FloatBits yBits;
    private final int yShift;
    private final FloatBits zBits;
    private final int zShift;
    private final int totalBits;
    private final long mask;
 
    public Vec3Bits( float minValue, float maxValue, int bitSize ) {
        this(new FloatBits(minValue, maxValue, bitSize),
             new FloatBits(minValue, maxValue, bitSize),
             new FloatBits(minValue, maxValue, bitSize));
    }
    
    public Vec3Bits( FloatBits xBits, FloatBits yBits, FloatBits zBits ) {
        this.xBits = xBits;
        this.yShift = xBits.getBitSize();
        this.yBits = yBits;
        this.zShift = yShift + yBits.getBitSize();
        this.zBits = zBits;
        this.totalBits = zShift + zBits.getBitSize();
        if( totalBits > 64 ) {
            throw new IllegalArgumentException("Total bit size exceeds 64");
        }
        long temp = xBits.getMask();
        temp |= yBits.getMask() << yShift;       
        temp |= zBits.getMask() << zShift;       
        this.mask = temp;
        
        System.out.println("Bit size:" + totalBits + "  mask:" + Long.toHexString(mask));
    }
 
    public FloatBits getXBits() {
        return xBits;
    }    

    public FloatBits getYBits() {
        return yBits;
    }    

    public FloatBits getZBits() {
        return zBits;
    }    
 
    public int getBitSize() {
        return totalBits;
    }
    
    public long getMask() {
        return mask;
    }
    
    public long toBits( Vec3d v ) {
        long x = xBits.toBits((float)v.x);
        long y = yBits.toBits((float)v.y);
        long z = zBits.toBits((float)v.z);
        long result = x;
        result |= y << yShift;
        result |= z << zShift;
        return result;
    }
    
    public Vec3d fromBits( long bits ) {
        long x = bits & xBits.getMask();
        long y = (bits >> yShift) & yBits.getMask();
        long z = (bits >> zShift) & zBits.getMask();
        float xf = xBits.fromBits(x);
        float yf = yBits.fromBits(y);
        float zf = zBits.fromBits(z);
        return new Vec3d(xf, yf, zf);
    }
    
    public static void main( String... args ) {
        FloatBits float16 = new FloatBits(-35.6f, 35.6f, 16);
        FloatBits float18 = new FloatBits(0, 256, 18);
        
        Vec3Bits posBits = new Vec3Bits(float16, float18, float16);
        
        java.util.Random rand = new java.util.Random(1);
        
        float range16 = 35.6f - -35.6f;
        for( int i = 0; i < 10; i++ ) {
            float x = rand.nextFloat() * range16 -35.6f;
            float y = rand.nextFloat() * 256;
            float z = rand.nextFloat() * range16 -35.6f;
            Vec3d v = new Vec3d(x, y, z);
            long bits = posBits.toBits(v);
            Vec3d rev = posBits.fromBits(bits); 
            System.out.println("pos:" + v 
                                + "   bits:" + Long.toHexString(bits)
                                + "\nrev:" + rev
                                + "\nerr:" + rev.subtract(v)
                                );
        }        
    }   
}


