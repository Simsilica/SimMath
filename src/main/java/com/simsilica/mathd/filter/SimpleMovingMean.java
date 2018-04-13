/*
 * $Id$
 * 
 * Copyright (c) 2018, Simsilica, LLC
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

package com.simsilica.mathd.filter;


/**
 *
 *
 *  @author    Paul Speed
 */
public class SimpleMovingMean implements Filterd {
 
    private int filterSize;
    private double[] values;
    private int current;
    private double total;
    private int count;
 
    public SimpleMovingMean( int filterSize ) {
        this.filterSize = filterSize;
        this.values = new double[filterSize];
    }
    
    @Override
    public void addValue( double d ) {
        // Take off the tail
        total -= values[current];
        
        // Set the new data value and add it to the total
        values[current] = d;
        total += d;
        
        // If we haven't filled the max window then increase the
        // window size.
        if( count < filterSize ) {
            count++;
        }
 
        // Round-robin the current index       
        current++;
        if( current >= filterSize ) {
            current -= filterSize;
        }
    }
 
    /**
     *  Returns the current mean of the series of data values
     *  up to this point. 
     */   
    @Override
    public double getFilteredValue() {
        return count == 0 ? 0 : total / count;
    }
}
