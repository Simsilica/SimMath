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

package com.simsilica.mathd.trans;

import java.util.Arrays;


/**
 *  Holds a set of transitions and can return a transition
 *  spanning a specific time index.  This data structure is
 *  semi-thread safe in that it will support one writer and
 *  many readers with very low overhead (no internal synchronization
 *  is used).
 *
 *  @author    Paul Speed
 */
public class TransitionBuffer<T extends Transition> {

    // Round-robin array-based queue where head points to the oldest entry
    // and tail points to the newest.  There is a blank entry between them
    // to help with the shuffling. 
    private final Transition[] array;
    private int count;
    
    // Head is where we start to pull from... like a queue.  This is
    // the oldest time-based entry.
    private volatile int head = 0;
    
    // Tail is where we add new items... like a queue.
    private volatile int tail = 0;

    public TransitionBuffer( int size ) {
        // There's always one 'dead' entry
        array = new Transition[size+1];
    }
 
    public int getSize() {
        return array.length - 1;
    }
 
    public void addTransition( Transition transition ) {
    
        // Predict what the next tail will be to see if we need
        // to advance head.  Even though there is a blank entry,
        // we err on the side of caution and always advance head
        // before tail.  It also means that tail will never equal
        // head if count is more than 0.  This is a useful truth 
        // that is nice to test elsewhere for sanity.
        int nextTail = next(tail);
        if( nextTail == head ) {
            // clear out the old head which will be our new dead spot
            array[head] = null;
        
            head = next(head);
        }            
            
        // Now we are free to do what we like 
                    
        if( count == 0 ) {
            // Nothing in the buffer at all, this is the first frame
            array[tail] = transition;
        } else {
            Transition last = array[previous(tail)];
            transition.setPreviousTransition(last);
            array[tail] = transition;
            
            //System.out.println("transition length:" + (transition.getEndTime() - transition.getStartTime()) + " nanos");            
        }
            
        // Because tail and head are volatile, they create a memory
        // barrier upon access that should flush the above changes
        // from thread cache to main memory.            
        tail = nextTail;
        if( count < array.length - 1 ) {
            count++;
        }
    } 
 
    public boolean isFilled() {
        return count == array.length - 1;
    }
 
    private int next( int index ) {
        return (index+1) % array.length;
    }
    
    private int previous( int index ) {
        if( index > 0 )
            return index-1;
        return array.length-1;
    }
    
    public T getTransition( long time ) {
    
        // We run forward from head to tail so that
        // the loop moving underneath us matters less.
        // There is still the slight chance that head
        // and tail will both advance far enough that
        // we get a value that is too new, ie: we
        // get the newest data instead of iterating through
        // the older data first.  For this to happen,
        // data would have to be piling into the array
        // fast enough that we add two frames between
        // i = head and dereferencing array[i].  Considering
        // that data is sometimes pumped in as blocks of
        // three values, this might be pretty common if we
        // are performing lots of old queries.  The buffer
        // zone could be increased or the backlog could be
        // increased to minimize this.  They amount to the
        // same thing but the caller has control over the history
        // size.

        // Grab our own copies of the values in case they move
        // while we are looping.  I'm going this to try to prevent
        // a null return that I believe was being caused by h == t
        // in the loop below even thuogh the initial check passed.
        int h = head;
        int t = tail;
        
        if( h == t && count > 0 ) {
            // See above.  This should be fixed in add but I'm leaving
            // this check here just in case.
            System.err.println( "**** TimeBuffer inconsistency.  This shouldn't happen." );
            //start = next(start);
        }

        Transition last = null;        
        for( int i = h; i != t; i = next(i) ) {
            Transition ft = array[i];
            if( ft == null ) {
                // It's possible that head gets cleared because we nuke it now... should
                // be ok to just skip it.
                System.out.println("element is null:" + i + "  head:" + h + " tail:" + t);
                continue;
            }            
            
            if( time < ft.getStartTime() ) {
System.out.println("!!!!!<<<<< Time:" + time + "  earlier than ft:" + ft);                
                // The only way we could have gotten here is if we
                // asked for a time before anything we have... because
                // otherwise, time would have been contained in the
                // previous entry
                return (T)ft;
            }
 
            if( time <= ft.getEndTime() ) {
                return (T)ft;
            }
           
            last = ft;                
        }
 
System.out.println("!!!!!>>>>> Time:" + time + "  later than last:" + last + "  h:" + h + "  t:" + t + "  count:" + count);                
        // The only way we could have gotten here is if we asked
        // for a time after this buffer contains... in which case
        // we'll just return the last value.           
        return (T)last;
    }
 
    @Override
    public String toString() {
        return "TransitionBuffer[ h:" + head + ", t:" + tail + ", array:" + Arrays.asList(array) + "]";
    }
}
