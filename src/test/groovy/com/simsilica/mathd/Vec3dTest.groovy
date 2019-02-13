package com.simsilica.mathd;

class Vec3dTest {

    static class IsSimilarTest extends GroovyTestCase {
        
        void testSimilarX() {
            Vec3d a = new Vec3d(1, 2, 3);
            Vec3d b = new Vec3d(1.01, 2, 3);
            
            assert a.isSimilar(b, 0.1)
        }
        
        void testSimilarY() {
            Vec3d a = new Vec3d(1, 2, 3);
            Vec3d b = new Vec3d(1, 2.01, 3);
            
            assert a.isSimilar(b, 0.1)
        }
        
        void testSimilarZ() {
            Vec3d a = new Vec3d(1, 2, 3);
            Vec3d b = new Vec3d(1, 2, 3.01);
            
            assert a.isSimilar(b, 0.1)
        }
        
        void testNotSimilarX() {
            Vec3d a = new Vec3d(1, 2, 3);
            Vec3d b = new Vec3d(1.5, 2, 3);
            
            assert !a.isSimilar(b, 0.1)
        }

        void testNotSimilarY() {
            Vec3d a = new Vec3d(1, 2, 3);
            Vec3d b = new Vec3d(1, 2.5, 3);
            
            assert !a.isSimilar(b, 0.1)
        }

        void testNotSimilarZ() {
            Vec3d a = new Vec3d(1, 2, 3);
            Vec3d b = new Vec3d(1, 2, 3.5);
            
            assert !a.isSimilar(b, 0.1)
        }
        
        void testNanRight() {
            Vec3d a = new Vec3d(1, 2, 3);
            Vec3d b = new Vec3d(Double.NaN, 2, 3);
            
            assert !a.isSimilar(b, 0.1)
        }
        
        void testNanLeft() {
            Vec3d a = new Vec3d(Double.NaN, 2, 3);
            Vec3d b = new Vec3d(1, 2, 3);
            
            assert !a.isSimilar(b, 0.1)
        }
    }
    
    static class EqualsTest extends GroovyTestCase {
        void testEquals() {
            Vec3d a = new Vec3d(1, 2, 3);
            Vec3d b = new Vec3d(1, 2, 3);
            
            assert a.equals(b);
        }
                
        void testNotEqualsX() {
            Vec3d a = new Vec3d(1, 2, 3);
            Vec3d b = new Vec3d(1.1, 2, 3);
            
            assert !a.equals(b);
        }
                
        void testNotEqualsY() {
            Vec3d a = new Vec3d(1, 2, 3);
            Vec3d b = new Vec3d(1, 2.1, 3);
            
            assert !a.equals(b);
        }
                
        void testNotEqualsZ() {
            Vec3d a = new Vec3d(1, 2, 3);
            Vec3d b = new Vec3d(1, 2, 3.1);
            
            assert !a.equals(b);
        }
        
        void testEqualsNanLeft() {
            Vec3d a = new Vec3d(Double.NaN, 2, 3);
            Vec3d b = new Vec3d(1, 2, 3);
            
            assert !a.equals(b);
        }
                
        void testEqualsNanRight() {
            Vec3d a = new Vec3d(1, 2, 3);
            Vec3d b = new Vec3d(Double.NaN, 2, 3);
            
            assert !a.equals(b);
        }
        
        void testEqualsNanBoth() {
            Vec3d a = new Vec3d(Double.NaN, 2, 3);
            Vec3d b = new Vec3d(Double.NaN, 2, 3);
            
            assert !a.equals(b);
        }        
    }
}
