/*
 * $Id$
 * 
 * Copyright (c) 2019, Simsilica, LLC
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

package com.simsilica.mathd.util;

import java.util.*;

/**
 *  A Set of integers built of range blocks such that a set
 *  full of clumps of integers is stored more efficiently than
 *  n number of separate integers.  This is useful for storing
 *  sets of integers that are mostly in contiguous ranges.
 *  It is a technically a SortedSet but does not (yet) implement
 *  the SortedSet interface.
 *
 *  @author    Paul Speed
 */
public class IntSpanSet extends AbstractSet<Integer> {

    private Span head;
 
    public IntSpanSet() {
    } 

    public Range[] toRangeArray() {
        // Counting them first is better than allocating a list or 
        // something to accumulate them, I think.  Traversing the linked
        // list is not tricky.
        int count = 0;
        for( Span span = head; span != null; span = span.next ) {
            count++;
        }
        Range[] result = new Range[count];
        int index = 0;
        for( Span span = head; span != null; span = span.next ) {
            result[index++] = new FixedRange(span);
        }
        return result;
    }

    public Iterator<Range> rangeIterator() {
        return new RangeIterator(head);
    }
 
    @Override   
    public Iterator<Integer> iterator() {
        return new IntegerIterator(head);
    }

    @Override   
    public boolean isEmpty() {
        return head == null;
    }

    @Override   
    public void clear() {
        this.head = null;
    }

    @Override   
    public boolean contains( Object o ) {
        if( o instanceof Integer ) {
            return contains(((Integer)o).intValue());
        }
        return false;
    }
    
    public boolean contains( int value ) {
        for( Span span = head; span != null; span = span.next ) {
            if( span.contains(value) ) {
                return true;
            }
        }
        return false;
    }
 
    @Override   
    public int size() {
        int total = 0;
        for( Span span = head; span != null; span = span.next ) {
            total += span.size;
        }
        return total;        
    }
 
    @Override   
    public boolean add( Integer value ) {
        if( value == null ) {
            // Docs say set should throw NPE if it doesn't accept nulls
            // but at least we'll include a message.
            throw new NullPointerException("Cannot add nulls");
        }
        return add(value.intValue());        
    }
 
    @Override   
    public boolean remove( Object value ) {
        if( value instanceof Integer ) {
            return remove(((Integer)value).intValue());
        } 
        if( value == null ) {
            // Docs say set should throw NPE if it doesn't accept nulls
            // but at least we'll include a message.
            throw new NullPointerException("Cannot add nulls");
        }
        return false;        
    }
    
    public boolean add( int value ) {
        if( head == null ) {
            head = new Span(value);
            return true;
        }

        Span prev = null;       
        for( Span span = head; span != null; prev = span, span = span.next ) { 
        
            // Are we non-contiguously before this span?
            if( value < span.min - 1 ) {
                // Need to insert a new span
                Span insert = new Span(value);
                insert.next = span;

                if( prev == null ) {
                    head = insert;
                } else {
                    prev.next = insert;
                }
                
                // Done
                return true;
            }
 
            // If we are immediately before then we can just grow this span
            if( value == span.min - 1 ) {
                span.min = value;
                span.size++;
                
                // Done
                return true;
            }
        
            if( span.contains(value) ) {
                // We already have it
                return false;
            }
            
            // If we are immediately after then we can just grow this span
            if( value == span.min + span.size ) {
                span.size++;
                
                // And we may be able to merge with the next one
                if( span.next != null && value == span.next.min - 1 ) {
                    span.size += span.next.size;
                    span.next = span.next.next;
                }
                return true;
            }
 
            // Else try the next span           
        }
 
        // If we reached the end then we can just 'insert' onto the end
        // 'prev' should never be null here
        Span insert = new Span(value);
        prev.next = insert;
        
        return true;                
    }
    
    public boolean remove( int i ) {
    
        if( head == null ) {
            // We're empty
            return false;
        }
        
        Span prev = null;       
        for( Span span = head; span != null; prev = span, span = span.next ) {
            if( i < span.min ) {
                // This set doesn't contain the value
                return false;
            }
            if( !span.contains(i) ) {
                // Try the next span
                continue;
            }
            
            // If it's at the beginning then we can just shrink forward
            if( span.min == i ) {
                if( span.size > 1 ) {
                    span.min++;
                    span.size--;
                    return true;
                } else {
                    // Need to delete the span completely
                    if( prev == null ) {
                        head = span.next;
                    } else {
                        prev.next = span.next;
                    }
                    return true;
                }
            }
            
            // If it's at the end then we can just shrink back
            if( span.getMaxValue() == i ) {
                // Span size should always be more than one else we'd
                // have hit the span.min == i block above.
                span.size--;
                return true;
            }
 
            // Else we need to split the span
            Span right = new Span(i + 1, span.getMaxValue());
            right.next = span.next;
            
            span.setMaxValue(i - 1);
            span.next = right;
 
            return true; 
        }
        
        return false; 
    }
     
    public interface Range {
        public int getMinValue();
        public int getMaxValue();
        public int getLength();
    }      
    
    private static class Span implements Range {
        Span next;
        int min;
        int size;
        
        public Span( int min ) {
            this.min = min;
            this.size = 1;
        }
 
        public Span( int min, int max ) {
            this.min = min;
            this.size = max - min + 1;
        }
        
        protected void setMaxValue( int max ) {
            this.size = max - min + 1;
        }
        
        protected boolean contains( int value ) {
            if( value < min ) {
                return false;
            }
            if( value > getMaxValue() ) {
                return false;
            }
            return true;
        }
 
        @Override
        public int getMinValue() {
            return min;
        }
        
        @Override
        public int getMaxValue() {
            return min + size - 1;
        }
        
        @Override
        public int getLength() {
            return size;
        }
 
        @Override       
        public String toString() {
            return "Range[" + getMinValue() + ":" + getMaxValue() + "]";
        }       
    }
 
    // For returning in arrays to be more compact   
    private class FixedRange implements Range {
        int min;
        int size;

        public FixedRange( Span span ) {
            this.min = span.min;
            this.size = span.size;
        }
        
        @Override
        public int getMinValue() {
            return min;
        }
        
        @Override
        public int getMaxValue() {
            return min + size - 1;
        }
        
        @Override
        public int getLength() {
            return size;
        }
 
        @Override       
        public String toString() {
            return "Range[" + getMinValue() + ":" + getMaxValue() + "]";
        }       
    }
    
    private class IntegerIterator implements Iterator<Integer> {
        private Span current;
        private Integer nextValue;
        
        public IntegerIterator( Span start ) {
            this.current = start;
            fetch();
        }
 
        protected void fetch() {
        
            if( current == null ) {
                nextValue = null;
                return;
            }
            
            if( nextValue == null ) {
                nextValue = current.min;
                return;
            }
            
            int next = nextValue + 1;
            if( current.contains(next) ) {
                nextValue = next;
                return;
            }
 
            // Else we'll hit the next span and try again
            current = current.next;
            nextValue = null;
            fetch();
        }
        
        public boolean hasNext() {
            return nextValue != null;
        }
        
        public Integer next() {
            if( nextValue == null ) {
                throw new NoSuchElementException();
            }
            Integer result = nextValue;
            fetch();
            return result;
        }
        
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
    
    private class RangeIterator implements Iterator<Range> {
        private Span current;
        
        public RangeIterator( Span current ) {
            this.current = current;
        }
        
        public boolean hasNext() {
            return current != null;
        }
        
        public Range next() {
            if( !hasNext() ) {
                throw new NoSuchElementException();   
            }
            Range result = current;
            current = current.next;
            return result;
        }
        
        public void remove() {
            throw new UnsupportedOperationException();
        }
    } 
}
