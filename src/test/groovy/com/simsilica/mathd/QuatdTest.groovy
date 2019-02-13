package com.simsilica.mathd;

class QuatdTest {

    static class IsSimilarTest extends GroovyTestCase {
        
        void testSimilarX() {
            Quatd a = new Quatd(1, 2, 3, 4);
            Quatd b = new Quatd(1.01, 2, 3, 4);
            
            assert a.isSimilar(b, 0.1)
        }
        
        void testSimilarY() {
            Quatd a = new Quatd(1, 2, 3, 4);
            Quatd b = new Quatd(1, 2.01, 3, 4);
            
            assert a.isSimilar(b, 0.1)
        }
        
        void testSimilarZ() {
            Quatd a = new Quatd(1, 2, 3, 4);
            Quatd b = new Quatd(1, 2, 3.01, 4);
            
            assert a.isSimilar(b, 0.1)
        }

        void testSimilarW() {
            Quatd a = new Quatd(1, 2, 3, 4);
            Quatd b = new Quatd(1, 2, 3, 4.01);
            
            assert a.isSimilar(b, 0.1)
        }
        
        void testNotSimilarX() {
            Quatd a = new Quatd(1, 2, 3, 4);
            Quatd b = new Quatd(1.5, 2, 3, 4);
            
            assert !a.isSimilar(b, 0.1)
        }

        void testNotSimilarY() {
            Quatd a = new Quatd(1, 2, 3, 4);
            Quatd b = new Quatd(1, 2.5, 3, 4);
            
            assert !a.isSimilar(b, 0.1)
        }

        void testNotSimilarZ() {
            Quatd a = new Quatd(1, 2, 3, 4);
            Quatd b = new Quatd(1, 2, 3.5, 4);
            
            assert !a.isSimilar(b, 0.1)
        }

        void testNotSimilarW() {
            Quatd a = new Quatd(1, 2, 3, 4);
            Quatd b = new Quatd(1, 2, 3, 4.5);
            
            assert !a.isSimilar(b, 0.1)
        }
        
        void testNanRight() {
            Quatd a = new Quatd(1, 2, 3, 4);
            Quatd b = new Quatd(Double.NaN, 2, 3, 4);
            
            assert !a.isSimilar(b, 0.1)
        }
        
        void testNanLeft() {
            Quatd a = new Quatd(Double.NaN, 2, 3, 4);
            Quatd b = new Quatd(1, 2, 3, 4);
            
            assert !a.isSimilar(b, 0.1)
        }
    }
    
    static class EqualsTest extends GroovyTestCase {
        void testEquals() {
            Quatd a = new Quatd(1, 2, 3, 4);
            Quatd b = new Quatd(1, 2, 3, 4);
            
            assert a.equals(b);
        }
                
        void testNotEqualsX() {
            Quatd a = new Quatd(1, 2, 3, 4);
            Quatd b = new Quatd(1.1, 2, 3, 4);
            
            assert !a.equals(b);
        }
                
        void testNotEqualsY() {
            Quatd a = new Quatd(1, 2, 3, 4);
            Quatd b = new Quatd(1, 2.1, 3, 4);
            
            assert !a.equals(b);
        }
                
        void testNotEqualsZ() {
            Quatd a = new Quatd(1, 2, 3, 4);
            Quatd b = new Quatd(1, 2, 3.1, 4);
            
            assert !a.equals(b);
        }

        void testNotEqualsW() {
            Quatd a = new Quatd(1, 2, 3, 4);
            Quatd b = new Quatd(1, 2, 3, 4.1);
            
            assert !a.equals(b);
        }
        
        void testEqualsNanLeft() {
            Quatd a = new Quatd(Double.NaN, 2, 3, 4);
            Quatd b = new Quatd(1, 2, 3, 4);
            
            assert !a.equals(b);
        }
                
        void testEqualsNanRight() {
            Quatd a = new Quatd(1, 2, 3, 4);
            Quatd b = new Quatd(Double.NaN, 2, 3, 4);
            
            assert !a.equals(b);
        }
        
        void testEqualsNanBoth() {
            Quatd a = new Quatd(Double.NaN, 2, 3, 4);
            Quatd b = new Quatd(Double.NaN, 2, 3, 4);
            
            assert !a.equals(b);
        }        
    }
}
