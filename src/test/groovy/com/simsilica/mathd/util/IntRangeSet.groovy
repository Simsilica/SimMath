package com.simsilica.mathd.util;

import java.util.Random;

class IntRangeSetTest {

    static class AddTest extends GroovyTestCase {
    
        public void testSparseRandomAdd() {
            int[] values = new int[50];
        
            Random rand = new Random(1);
        
            for( int i = 0; i < values.length; i++ ) {
                values[i] = rand.nextInt();
            }
 
            def test = [] as TreeSet;
 
            IntRangeSet set = new IntRangeSet();
            for( int i : values ) {
                boolean b1 = set.add(i);
                boolean b2 = test.add(i);
                assert b1 == b2 : "different set states for add(" + i + ")";
            }
            
            Iterator<Integer> it1 = set.iterator();
            Iterator<Integer> it2 = test.iterator();
            while( it1.hasNext() || it2.hasNext() ) {
                Integer i1 = it1.next();
                Integer i2 = it2.next();
                assertEquals(i1, i2)
            }
        }
                      
        public void testPackedRandomAdd() {
            int[] values = new int[50];
        
            Random rand = new Random(1);
        
            for( int i = 0; i < values.length; i++ ) {
                values[i] = rand.nextInt(50);
            }
 
            def test = [] as TreeSet;
 
            IntRangeSet set = new IntRangeSet();
            for( int i : values ) {
                boolean b1 = set.add(i);
                boolean b2 = test.add(i);
                assert b1 == b2 : "different set states for add(" + i + ")";
            }
            
            Iterator<Integer> it1 = set.iterator();
            Iterator<Integer> it2 = test.iterator();
            while( it1.hasNext() || it2.hasNext() ) {
                Integer i1 = it1.next();
                Integer i2 = it2.next();
                assertEquals(i1, i2)
            }
        }
                      
        public void testSequentialAdd() {
        
            def test = [] as TreeSet;
 
            IntRangeSet set = new IntRangeSet();
            for( int i = 0; i < 50; i++ ) {
                boolean b1 = set.add(i);
                boolean b2 = test.add(i);
                assert b1 == b2 : "different set states for add(" + i + ")";
            }
            
            Iterator<Integer> it1 = set.iterator();
            Iterator<Integer> it2 = test.iterator();
            while( it1.hasNext() || it2.hasNext() ) {
                Integer i1 = it1.next();
                Integer i2 = it2.next();
                assertEquals(i1, i2)
            }
        }              
    }
 
    static class RemoveTest extends GroovyTestCase {
    
        public void testRandomRemove() {
 
            // Setup sets with 0-49 in them           
            def test = [] as TreeSet;
            IntRangeSet set = new IntRangeSet();
            for( int i = 0; i < 50; i++ ) {
                set.add(i);
                test.add(i);
            }
            
            // Remove some random values from both
            Random rand = new Random(1);
 
            // Ok to remove 50 random values because they will never
            // statistically remove everything from 0-49           
            for( int i = 0; i < 50; i++ ) {
                int toRemove = rand.nextInt(50);
                boolean b1 = set.remove(toRemove);
                boolean b2 = test.remove(toRemove);
                assert b1 == b2 : "different set states for remove(" + toRemove + ")";
            }
            
            Iterator<Integer> it1 = set.iterator();
            Iterator<Integer> it2 = test.iterator();
            while( it1.hasNext() || it2.hasNext() ) {
                Integer i1 = it1.next();
                Integer i2 = it2.next();
                assertEquals(i1, i2)
            }
            
            //set.rangeIterator().each { range ->
            //    println range
            //}
        }
        
        public void testRandomAndSequentialRemoveSomeFwd() {
 
            // Setup sets with 0-49 in them           
            def test = [] as TreeSet;
            IntRangeSet set = new IntRangeSet();
            for( int i = 0; i < 50; i++ ) {
                set.add(i);
                test.add(i);
            }
            
            // Remove some random values from both
            Random rand = new Random(1);
 
            // Ok to remove 50 random values because they will never
            // statistically remove everything from 0-49           
            for( int i = 0; i < 50; i++ ) {
                int toRemove = rand.nextInt(50);
                boolean b1 = set.remove(toRemove);
                boolean b2 = test.remove(toRemove);
                assert b1 == b2 : "different set states for remove(" + toRemove + ")";
            }
 
            // Now remove a fixed block
            for( int i = 0; i < 40; i++ ) {
                boolean b1 = set.remove(i);
                boolean b2 = test.remove(i);
                assert b1 == b2 : "different set states for remove(" + i + ")";
            }
            
            Iterator<Integer> it1 = set.iterator();
            Iterator<Integer> it2 = test.iterator();
            while( it1.hasNext() || it2.hasNext() ) {
                Integer i1 = it1.next();
                Integer i2 = it2.next();
                assertEquals(i1, i2)
            }
            
            //set.rangeIterator().each { range ->
            //    println range
            //}
        }
        
        public void testRandomAndSequentialRemoveAllFwd() {
 
            // Setup sets with 0-49 in them           
            def test = [] as TreeSet;
            IntRangeSet set = new IntRangeSet();
            for( int i = 0; i < 50; i++ ) {
                set.add(i);
                test.add(i);
            }
            
            // Remove some random values from both
            Random rand = new Random(1);
 
            // Ok to remove 50 random values because they will never
            // statistically remove everything from 0-49           
            for( int i = 0; i < 50; i++ ) {
                int toRemove = rand.nextInt(50);
                boolean b1 = set.remove(toRemove);
                boolean b2 = test.remove(toRemove);
                assert b1 == b2 : "different set states for remove(" + toRemove + ")";
            }
 
            // Now remove everything
            for( int i = 0; i < 50; i++ ) {
                boolean b1 = set.remove(i);
                boolean b2 = test.remove(i);
                assert b1 == b2 : "different set states for remove(" + i + ")";
            }
            
            Iterator<Integer> it1 = set.iterator();
            Iterator<Integer> it2 = test.iterator();
            while( it1.hasNext() || it2.hasNext() ) {
                Integer i1 = it1.next();
                Integer i2 = it2.next();
                assertEquals(i1, i2)
            }
            
            //set.rangeIterator().each { range ->
            //    println range
            //}
        }
        
        public void testRandomAndSequentialRemoveSomeRev() {
 
            // Setup sets with 0-49 in them           
            def test = [] as TreeSet;
            IntRangeSet set = new IntRangeSet();
            for( int i = 0; i < 50; i++ ) {
                set.add(i);
                test.add(i);
            }
            
            // Remove some random values from both
            Random rand = new Random(1);
 
            // Ok to remove 50 random values because they will never
            // statistically remove everything from 0-49           
            for( int i = 0; i < 50; i++ ) {
                int toRemove = rand.nextInt(50);
                boolean b1 = set.remove(toRemove);
                boolean b2 = test.remove(toRemove);
                assert b1 == b2 : "different set states for remove(" + toRemove + ")";
            }
 
            // Now remove a fixed block
            for( int i = 49; i > 10; i-- ) {
                boolean b1 = set.remove(i);
                boolean b2 = test.remove(i);
                assert b1 == b2 : "different set states for remove(" + i + ")";
            }
            
            Iterator<Integer> it1 = set.iterator();
            Iterator<Integer> it2 = test.iterator();
            while( it1.hasNext() || it2.hasNext() ) {
                Integer i1 = it1.next();
                Integer i2 = it2.next();
                assertEquals(i1, i2)
            }
            
            //set.rangeIterator().each { range ->
            //    println range
            //}
        }
        
        public void testRandomAndSequentialRemoveAllRev() {
 
            // Setup sets with 0-49 in them           
            def test = [] as TreeSet;
            IntRangeSet set = new IntRangeSet();
            for( int i = 0; i < 50; i++ ) {
                set.add(i);
                test.add(i);
            }
            
            // Remove some random values from both
            Random rand = new Random(1);
 
            // Ok to remove 50 random values because they will never
            // statistically remove everything from 0-49           
            for( int i = 0; i < 50; i++ ) {
                int toRemove = rand.nextInt(50);
                boolean b1 = set.remove(toRemove);
                boolean b2 = test.remove(toRemove);
                assert b1 == b2 : "different set states for remove(" + toRemove + ")";
            }
 
            // Now remove everything
            for( int i = 49; i >= 0; i-- ) {
                boolean b1 = set.remove(i);
                boolean b2 = test.remove(i);
                assert b1 == b2 : "different set states for remove(" + i + ")";
            }
            
            Iterator<Integer> it1 = set.iterator();
            Iterator<Integer> it2 = test.iterator();
            while( it1.hasNext() || it2.hasNext() ) {
                Integer i1 = it1.next();
                Integer i2 = it2.next();
                assertEquals(i1, i2)
            }
            
            //set.rangeIterator().each { range ->
            //    println range
            //}
        }
    }
    
    static class RemoveRangeTest extends GroovyTestCase {
        
        public void testRandomRemove() {
 
            // Setup sets with 0-49 in them           
            def test = [] as TreeSet;
            IntRangeSet set = new IntRangeSet();
            for( int i = 0; i < 50; i++ ) {
                set.add(i);
                test.add(i);
            }
            
            // Remove some random values from both
            Random rand = new Random(1);
 
            // Remove some random ranges
            for( int i = 0; i < 25; i++ ) {
                int start = rand.nextInt(50);
                int size = rand.nextInt(10);
 
                boolean b1 = set.remove(new FixedIntRange(start, start + size - 1));
                boolean b2 = false;
                for( int j = start; j < start + size; j++ ) {
                    if( test.remove(j) ) {
                        b2 = true;
                    }
                }                                
                assert b1 == b2 : "different set states for remove(" + start + ":" + size + ")";
            }
            
            Iterator<Integer> it1 = set.iterator();
            Iterator<Integer> it2 = test.iterator();
            while( it1.hasNext() || it2.hasNext() ) {
                Integer i1 = it1.next();
                Integer i2 = it2.next();
                assertEquals(i1, i2)
            }
            
            //set.rangeIterator().each { range ->
            //    println range
            //}
        }
    }
    
    static class RangeArrayTest extends GroovyTestCase {
        public void testPackedRandomAdd() {
        
            IntRangeSet set = new IntRangeSet();
        
            Random rand = new Random(1);
            for( int i = 0; i < 50; i++ ) {
                set.add(rand.nextInt(50));
            }
            
            def array = set.toRangeArray();
            assertEquals(13, array.length);
            //array.each {
            //    println it 
            //}
        }
        
        public void testSequentialAddAndRemove() {
            
            // Add some values to the end and remove some from the
            // beginning
            IntRangeSet set = new IntRangeSet();
            for( int i = 0; i < 10; i++ ) {
                set.add(i);
            }
            for( int i = 10; i < 50; i++ ) {
                set.add(i);
                set.remove(i - 10);
            }
            
            def array = set.toRangeArray();
            
            assertEquals(1, array.length);
            assertEquals(40, array[0].getMinValue());
            assertEquals(49, array[0].getMaxValue());
        }
    }
        
}

