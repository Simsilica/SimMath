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

import org.slf4j.*;

import com.simsilica.mathd.Quatd;
import com.simsilica.mathd.Vec4d;


/**
 *
 *
 *  @author    Paul Speed
 */
public final class QuatBits {
 
    static Logger log = LoggerFactory.getLogger(QuatBits.class);
 
    private final FloatBits componentBits;
    private final int yShift;
    private final int zShift;
    private final int wShift;
    private final int totalBits;
    private long mask;
    
    public QuatBits( int componentBitSize ) {
        this.componentBits = new FloatBits(-1, 1, componentBitSize);
        this.yShift = componentBitSize;
        this.zShift = yShift + componentBitSize;
        this.wShift = zShift + componentBitSize;
        this.totalBits = wShift + componentBitSize;
        if( totalBits > 64 ) {
            throw new IllegalArgumentException("Total bit size exceeds 64");
        }
        this.mask = componentBits.getMask();
        mask |= componentBits.getMask() << yShift;       
        mask |= componentBits.getMask() << zShift;       
        mask |= componentBits.getMask() << wShift;       
        
        if( log.isTraceEnabled() ) {
            log.trace("Bit size:" + totalBits + "  mask:" + Long.toHexString(mask));
        }
    }
 
    public int getComponentBitSize() {
        return componentBits.getBitSize();
    }    

    public int getBitSize() {
        return totalBits;
    }
    
    public long getMask() {
        return mask;
    }
    
    public long toBits( Quatd q ) {
        long x = componentBits.toBits((float)q.x);
        long y = componentBits.toBits((float)q.y);
        long z = componentBits.toBits((float)q.z);
        long w = componentBits.toBits((float)q.w);
                                    
        long result = x;        
        result |= y << yShift;
        result |= z << zShift;
        result |= w << wShift;
        return result;
    }
    
    public Quatd fromBits( long bits ) {
        long x = bits & componentBits.getMask();
        long y = (bits >> yShift) & componentBits.getMask();
        long z = (bits >> zShift) & componentBits.getMask();
        long w = (bits >> wShift) & componentBits.getMask();
        
        float xf = componentBits.fromBits(x);        
        float yf = componentBits.fromBits(y);
        float zf = componentBits.fromBits(z);
        float wf = componentBits.fromBits(w);
        return new Quatd(xf, yf, zf, wf);
    }
    
    public static void main( String... args ) {
    
        Quatd quat = new Quatd();
        QuatBits qBits = new QuatBits(12);
    
        double max = Math.PI * 2;
        double delta = max / 250;
        
        Vec4d minValues = new Vec4d(10, 10, 10, 10);
        Vec4d maxValues = new Vec4d(-10, -10, -10, -10);
        Vec4d temp = new Vec4d();
        
        Vec4d maxError = new Vec4d(0, 0, 0, 0);
        
        long start = System.nanoTime();
        int count = 0;
        for( double x = 0; x < max; x += delta ) {
            for( double y = 0; y < max; y += delta ) {
                for( double z = 0; z < max; z += delta ) {
                    quat.fromAngles(x, y, z);
                    quat.normalizeLocal();
                    temp.set(quat.x, quat.y, quat.z, quat.w);
                    minValues.minLocal(temp);
                    maxValues.maxLocal(temp);
                    
                    //System.out.println("Quat:" + quat);
                    long value = qBits.toBits(quat); 
                    Quatd test = qBits.fromBits(value);
                    long value2 = qBits.toBits(test);
                    if( value != value2 ) {
                        System.out.println("Double convert failed...");
                        System.out.println("original:" + quat); 
                        System.out.println("    test:" + test);
                        return;
                    }  
                    quat.subtractLocal(test);
                    temp.set(Math.abs(quat.x), Math.abs(quat.y), Math.abs(quat.z), Math.abs(quat.w));
                    maxError.maxLocal(temp);
                    
                    count++;                      
                } 
            } 
            System.out.println( (int)((x/max) * 100) + "%");
        }
        System.out.println();
        long end = System.nanoTime();
        System.out.println("Completed in " + ((end - start)/1000000.0) + " ms    per iteration:" + (((end - start)/count)/1000000.0) + " ms"); 
 
        System.out.println("min:" + minValues);
        System.out.println("max:" + maxValues);
        System.out.println("error rate:" + maxError);
    
    }   
}


