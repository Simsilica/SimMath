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

package com.simsilica.mathd.bits;


/**
 *
 *
 *  @author    Paul Speed
 */
public final class FloatBits {

    private final float minValue;
    private final float maxValue;
    private final int bits;
    private final int resolution;
    private final long mask;
    private final float mult;
    private final float invMult;
    
    public FloatBits( float minValue, float maxValue, int bits ) {
        if( minValue > maxValue ) {
            throw new IllegalArgumentException("Min value must be less than max value.");
        }
        if( bits == 0 || bits >= 64 ) {
            // If they want 64 bits then they shouldn't be using this
            // class.
            throw new IllegalArgumentException("Bits must be in the range 0 - 63");
        }
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.bits = bits;
        this.resolution = (int)Math.pow(2, bits);
        this.mask = 0xffffffffffffffffL >>> (64 - bits);
        
        // Resolution is 2^bits but we need to subtract -1
        // for our multiplier.
        // for example, 8 bits means we can represent 256 values
        // but if we want to include 0 (and we do) then it means
        // the max value is really 255.
        this.mult = (resolution - 1) / (maxValue - minValue);
        this.invMult = (maxValue - minValue) / (resolution - 1);        
    }
 
    public float getMinValue() {
        return minValue;
    }
    
    public float getMaxValue() {
        return maxValue;
    }

    public int getBitSize() {
        return bits;
    }
    
    public long getMask() {
        return mask;
    }
    
    /**
     *  Returns the smallest possible difference between two floating
     *  point values for this FloatBits configuration.  FloatBits treats
     *  floating point as a sort of fixed point representation where
     *  the values map directly from min/max across the entire "bit size"
     *  range.  So "float resolution" represents a sort of worst case
     *  accuracy.
     */
    public float getFloatResolution() {
        return invMult;
    }
    
    public long toBits( float value ) {
        if( value < minValue ) {
            System.out.println("!!!! FloatBits *** underflow:" + value + "  under:" + minValue);
            return 0;
        }
        if( value > maxValue ) {
            System.out.println("!!!! FloatBits *** overflow:" + value + "  over:" + maxValue );
            return resolution - 1;
        }
        float f = Math.round((value - minValue) * mult);
        return (long)f;
    }
    
    public float fromBits( long bits ) {
        bits = bits & mask;
        float f = bits * invMult;
        f += minValue;
        return (float)f;
    }
    
    public static void main( String... args ) {
        
        FloatBits test1 = new FloatBits(-35.6f, 35.6f, 8);
        FloatBits test2 = new FloatBits(-35.6f, 35.6f, 16);
        float loopMax = 10;
        for( float f = 0; f <= loopMax; f += 0.001f ) {
            long bits1 = test1.toBits(f);
            long bits2 = test2.toBits(f);
            System.out.println("f:" + f 
                                + "  bits1:" + Long.toHexString(bits1) 
                                + "  rev1:" + test1.fromBits(bits1)
                                + "  bits2:" + Long.toHexString(bits2) 
                                + "  rev2:" + test2.fromBits(bits2)
                                );
        }
        long bits1 = test1.toBits(loopMax);
        long bits2 = test2.toBits(loopMax);
        System.out.println("f:" + loopMax 
                            + "  bits1:" + Long.toHexString(bits1) 
                            + "  rev1:" + test1.fromBits(bits1)
                            + "  bits2:" + Long.toHexString(bits2) 
                            + "  rev2:" + test2.fromBits(bits2)
                            );
    }   
}

