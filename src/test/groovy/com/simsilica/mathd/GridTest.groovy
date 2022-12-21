package com.simsilica.mathd;

class GridTest {

    // Uncomment to write samples for the current serialization version
    //static class SerializationSetupTest extends GroovyTestCase {
    //    void testSaveCurrent() {
    //        Grid grid = new Grid(32, 32, 32);
    //        def f = new File(Grid.class.getName() + ".ser");
    //        def out = new ObjectOutputStream(new FileOutputStream(f));
    //        try {
    //            out.writeObject(grid);
    //        } finally {
    //            out.close();
    //        }
    //    }
    //}
    
    static class SerializationTest extends GroovyTestCase {
        void testVersion42() {
            def u = getClass().getResource(Grid.class.name + "-2022-12-20.ser");
            def oin = new ObjectInputStream(u.openStream());
            try {
                def grid = oin.readObject();
                assertEquals new Vec3i(21, 21, 21), grid.getIdBits();
            } finally {
                oin.close();
            }
        }
    } 

    static class CellToIdXyzTest extends GroovyTestCase {
    
        void testDefaultBits() {
            Grid grid = new Grid(32, 32, 32); 
            
            assertEquals 0, grid.cellToId(0, 0, 0);
 
            assertEquals 4398046511104L, grid.cellToId(1, 0, 0) 
            assertEquals 2097152L      , grid.cellToId(0, 1, 0)
            assertEquals 4398048608256L, grid.cellToId(1, 1, 0)
            assertEquals 1L            , grid.cellToId(0, 0, 1)
            assertEquals 4398046511105L, grid.cellToId(1, 0, 1)
            assertEquals 2097153L      , grid.cellToId(0, 1, 1)
            assertEquals 4398048608257L, grid.cellToId(1, 1, 1)
 
            assertEquals 9223367638808264704L, grid.cellToId(-1, 0, 0) 
            assertEquals 4398044413952L      , grid.cellToId(0, -1, 0)
            assertEquals 9223372036852678656L, grid.cellToId(-1, -1, 0)
            assertEquals 2097151L            , grid.cellToId(0, 0, -1)
            assertEquals 9223367638810361855L, grid.cellToId(-1, 0, -1)
            assertEquals 4398046511103L      , grid.cellToId(0, -1, -1)
            assertEquals 9223372036854775807L, grid.cellToId(-1, -1, -1)

            assertEquals 439804651110400L, grid.cellToId(100, 0, 0) 
            assertEquals 209715200L      , grid.cellToId(0, 100, 0)
            assertEquals 439804860825600L, grid.cellToId(100, 100, 0)
            assertEquals 100L            , grid.cellToId(0, 0, 100)
            assertEquals 439804651110500L, grid.cellToId(100, 0, 100)
            assertEquals 209715300L      , grid.cellToId(0, 100, 100)
            assertEquals 439804860825700L, grid.cellToId(100, 100, 100)

            assertEquals 9222932232203665408L, grid.cellToId(-100, 0, 0) 
            assertEquals 4397836795904L      , grid.cellToId(0, -100, 0)
            assertEquals 9222936630040461312L, grid.cellToId(-100, -100, 0)
            assertEquals 2097052L            , grid.cellToId(0, 0, -100)
            assertEquals 9222932232205762460L, grid.cellToId(-100, 0, -100)
            assertEquals 4397838892956L      , grid.cellToId(0, -100, -100)
            assertEquals 9222936630042558364L, grid.cellToId(-100, -100, -100)
        }
        
        void testLowBitsX() {
            Grid grid = new Grid(32, 32, 32, new Vec3i(8, 28, 28)); 
            
            assertEquals 0, grid.cellToId(0, 0, 0);

            //             xxyyyyyyyzzzzzzz
            assertEquals 0x0100000000000000L, grid.cellToId(1, 0, 0) 
            assertEquals 0x0000000010000000L, grid.cellToId(0, 1, 0)
            assertEquals 0x0100000010000000L, grid.cellToId(1, 1, 0)
            assertEquals 0x0000000000000001L, grid.cellToId(0, 0, 1)
            assertEquals 0x0100000000000001L, grid.cellToId(1, 0, 1)
            assertEquals 0x0000000010000001L, grid.cellToId(0, 1, 1)
            assertEquals 0x0100000010000001L, grid.cellToId(1, 1, 1)

            //             xxyyyyyyyzzzzzzz
            assertEquals 0xff00000000000000L, grid.cellToId(-1, 0, 0) 
            assertEquals 0x00fffffff0000000L, grid.cellToId(0, -1, 0)
            assertEquals 0xfffffffff0000000L, grid.cellToId(-1, -1, 0)
            assertEquals 0x000000000fffffffL, grid.cellToId(0, 0, -1)
            assertEquals 0xff0000000fffffffL, grid.cellToId(-1, 0, -1)
            assertEquals 0x00ffffffffffffffL, grid.cellToId(0, -1, -1)
            assertEquals 0xffffffffffffffffL, grid.cellToId(-1, -1, -1)


            //             xxyyyyyyyzzzzzzz
            assertEquals 0x6400000000000000L, grid.cellToId(100, 0, 0) 
            assertEquals 0x0000000640000000L, grid.cellToId(0, 100, 0)
            assertEquals 0x6400000640000000L, grid.cellToId(100, 100, 0)
            assertEquals 0x0000000000000064L, grid.cellToId(0, 0, 100)
            assertEquals 0x6400000000000064L, grid.cellToId(100, 0, 100)
            assertEquals 0x0000000640000064L, grid.cellToId(0, 100, 100)
            assertEquals 0x6400000640000064L, grid.cellToId(100, 100, 100)

            //             xxyyyyyyyzzzzzzz
            assertEquals 0x9c00000000000000L, grid.cellToId(-100, 0, 0) 
            assertEquals 0x00fffff9c0000000L, grid.cellToId(0, -100, 0)
            assertEquals 0x9cfffff9c0000000L, grid.cellToId(-100, -100, 0)
            assertEquals 0x000000000fffff9cL, grid.cellToId(0, 0, -100)
            assertEquals 0x9c0000000fffff9cL, grid.cellToId(-100, 0, -100)
            assertEquals 0x00fffff9cfffff9cL, grid.cellToId(0, -100, -100)
            assertEquals 0x9cfffff9cfffff9cL, grid.cellToId(-100, -100, -100)
        }

        void testLowBitsY() {
            Grid grid = new Grid(32, 32, 32, new Vec3i(28, 8, 28)); 
            
            assertEquals 0, grid.cellToId(0, 0, 0);

            //             xxxxxxxyyzzzzzzz
            assertEquals 0x0000001000000000L, grid.cellToId(1, 0, 0) 
            assertEquals 0x0000000010000000L, grid.cellToId(0, 1, 0)
            assertEquals 0x0000001010000000L, grid.cellToId(1, 1, 0)
            assertEquals 0x0000000000000001L, grid.cellToId(0, 0, 1)
            assertEquals 0x0000001000000001L, grid.cellToId(1, 0, 1)
            assertEquals 0x0000000010000001L, grid.cellToId(0, 1, 1)
            assertEquals 0x0000001010000001L, grid.cellToId(1, 1, 1)

            //             xxxxxxxyyzzzzzzz
            assertEquals 0xfffffff000000000L, grid.cellToId(-1, 0, 0) 
            assertEquals 0x0000000ff0000000L, grid.cellToId(0, -1, 0)
            assertEquals 0xfffffffff0000000L, grid.cellToId(-1, -1, 0)
            assertEquals 0x000000000fffffffL, grid.cellToId(0, 0, -1)
            assertEquals 0xfffffff00fffffffL, grid.cellToId(-1, 0, -1)
            assertEquals 0x0000000fffffffffL, grid.cellToId(0, -1, -1)
            assertEquals 0xffffffffffffffffL, grid.cellToId(-1, -1, -1)


            //             xxxxxxxyyzzzzzzz
            assertEquals 0x0000064000000000L, grid.cellToId(100, 0, 0) 
            assertEquals 0x0000000640000000L, grid.cellToId(0, 100, 0)
            assertEquals 0x0000064640000000L, grid.cellToId(100, 100, 0)
            assertEquals 0x0000000000000064L, grid.cellToId(0, 0, 100)
            assertEquals 0x0000064000000064L, grid.cellToId(100, 0, 100)
            assertEquals 0x0000000640000064L, grid.cellToId(0, 100, 100)
            assertEquals 0x0000064640000064L, grid.cellToId(100, 100, 100)

            //             xxxxxxxyyzzzzzzz
            assertEquals 0xfffff9c000000000L, grid.cellToId(-100, 0, 0) 
            assertEquals 0x00000009c0000000L, grid.cellToId(0, -100, 0)
            assertEquals 0xfffff9c9c0000000L, grid.cellToId(-100, -100, 0)
            assertEquals 0x000000000fffff9cL, grid.cellToId(0, 0, -100)
            assertEquals 0xfffff9c00fffff9cL, grid.cellToId(-100, 0, -100)
            assertEquals 0x00000009cfffff9cL, grid.cellToId(0, -100, -100)
            assertEquals 0xfffff9c9cfffff9cL, grid.cellToId(-100, -100, -100)
        }

        void testLowBitsZ() {
            Grid grid = new Grid(32, 32, 32, new Vec3i(28, 28, 8)); 
            
            assertEquals 0, grid.cellToId(0, 0, 0);

            //             xxxxxxxyyyyyyyzz
            assertEquals 0x0000001000000000L, grid.cellToId(1, 0, 0) 
            assertEquals 0x0000000000000100L, grid.cellToId(0, 1, 0)
            assertEquals 0x0000001000000100L, grid.cellToId(1, 1, 0)
            assertEquals 0x0000000000000001L, grid.cellToId(0, 0, 1)
            assertEquals 0x0000001000000001L, grid.cellToId(1, 0, 1)
            assertEquals 0x0000000000000101L, grid.cellToId(0, 1, 1)
            assertEquals 0x0000001000000101L, grid.cellToId(1, 1, 1)

            //             xxxxxxxyyyyyyyzz
            assertEquals 0xfffffff000000000L, grid.cellToId(-1, 0, 0) 
            assertEquals 0x0000000fffffff00L, grid.cellToId(0, -1, 0)
            assertEquals 0xffffffffffffff00L, grid.cellToId(-1, -1, 0)
            assertEquals 0x00000000000000ffL, grid.cellToId(0, 0, -1)
            assertEquals 0xfffffff0000000ffL, grid.cellToId(-1, 0, -1)
            assertEquals 0x0000000fffffffffL, grid.cellToId(0, -1, -1)
            assertEquals 0xffffffffffffffffL, grid.cellToId(-1, -1, -1)


            //             xxxxxxxyyyyyyyzz
            assertEquals 0x0000064000000000L, grid.cellToId(100, 0, 0) 
            assertEquals 0x0000000000006400L, grid.cellToId(0, 100, 0)
            assertEquals 0x0000064000006400L, grid.cellToId(100, 100, 0)
            assertEquals 0x0000000000000064L, grid.cellToId(0, 0, 100)
            assertEquals 0x0000064000000064L, grid.cellToId(100, 0, 100)
            assertEquals 0x0000000000006464L, grid.cellToId(0, 100, 100)
            assertEquals 0x0000064000006464L, grid.cellToId(100, 100, 100)

            //             xxxxxxxyyyyyyyzz
            assertEquals 0xfffff9c000000000L, grid.cellToId(-100, 0, 0) 
            assertEquals 0x0000000fffff9c00L, grid.cellToId(0, -100, 0)
            assertEquals 0xfffff9cfffff9c00L, grid.cellToId(-100, -100, 0)
            assertEquals 0x000000000000009cL, grid.cellToId(0, 0, -100)
            assertEquals 0xfffff9c00000009cL, grid.cellToId(-100, 0, -100)
            assertEquals 0x0000000fffff9c9cL, grid.cellToId(0, -100, -100)
            assertEquals 0xfffff9cfffff9c9cL, grid.cellToId(-100, -100, -100)
        }
    }
    
    static class CellToIdToCellXyzTest extends GroovyTestCase {
        static List testCells = [
            new Vec3i(0, 0, 0),
            
            new Vec3i(1, 0, 0),
            new Vec3i(0, 1, 0),
            new Vec3i(1, 1, 0),
            new Vec3i(0, 0, 1),
            new Vec3i(1, 0, 1),
            new Vec3i(0, 1, 1),
            new Vec3i(1, 1, 1),

            new Vec3i(-1, 0, 0),
            new Vec3i(0, -1, 0),
            new Vec3i(-1, -1, 0),
            new Vec3i(0, 0, -1),
            new Vec3i(-1, 0, -1),
            new Vec3i(0, -1, -1),
            new Vec3i(-1, -1, -1),

            new Vec3i(1, 0, 0).multLocal(100),
            new Vec3i(0, 1, 0).multLocal(100),
            new Vec3i(1, 1, 0).multLocal(100),
            new Vec3i(0, 0, 1).multLocal(100),
            new Vec3i(1, 0, 1).multLocal(100),
            new Vec3i(0, 1, 1).multLocal(100),
            new Vec3i(1, 1, 1).multLocal(100),

            new Vec3i(-1, 0, 0).multLocal(100),
            new Vec3i(0, -1, 0).multLocal(100),
            new Vec3i(-1, -1, 0).multLocal(100),
            new Vec3i(0, 0, -1).multLocal(100),
            new Vec3i(-1, 0, -1).multLocal(100),
            new Vec3i(0, -1, -1).multLocal(100),
            new Vec3i(-1, -1, -1).multLocal(100)
        ];
    
        void testDefaultBits() {
            Grid grid = new Grid(32, 32, 32);
            
            for( Vec3i v : testCells ) {
                long id = grid.cellToId(v);
                Vec3i result = grid.idToCell(id, null);
                assertEquals v, result
            }
        } 
    }

    static class CellToIdXzTest extends GroovyTestCase {
         
        void testDefaultBits() {
        
            Grid grid = new Grid(32, 0, 32); 
            
            assertEquals 0, grid.cellToId(0, 0, 0);

            assertEquals 4294967296L,   grid.cellToId(1, 0, 0) 
            assertEquals 0L,            grid.cellToId(0, 1, 0)
            assertEquals 4294967296L,   grid.cellToId(1, 1, 0)
            assertEquals 1L,            grid.cellToId(0, 0, 1)
            assertEquals 4294967297L,   grid.cellToId(1, 0, 1)
            assertEquals 1L,            grid.cellToId(0, 1, 1)
            assertEquals 4294967297L,   grid.cellToId(1, 1, 1)
 
            assertEquals 0-4294967296L, grid.cellToId(-1, 0, 0) 
            assertEquals 0,             grid.cellToId(0, -1, 0);
            assertEquals 0-4294967296L, grid.cellToId(-1, -1, 0)
            assertEquals 4294967295L,   grid.cellToId(0, 0, -1)
            assertEquals 0-1L,          grid.cellToId(-1, 0, -1)
            assertEquals 4294967295L,   grid.cellToId(0, -1, -1)
            assertEquals 0-1L,          grid.cellToId(-1, -1, -1) 

            assertEquals 429496729600L, grid.cellToId(100, 0, 0) 
            assertEquals 0L      , grid.cellToId(0, 100, 0)
            assertEquals 429496729600L, grid.cellToId(100, 100, 0)
            assertEquals 100L            , grid.cellToId(0, 0, 100)
            assertEquals 429496729700L, grid.cellToId(100, 0, 100)
            assertEquals 100L      , grid.cellToId(0, 100, 100)
            assertEquals 429496729700L, grid.cellToId(100, 100, 100)

            assertEquals 0-429496729600L,   grid.cellToId(-100, 0, 0) 
            assertEquals 0,                 grid.cellToId(0, -100, 0)
            assertEquals 0-429496729600L,   grid.cellToId(-100, -100, 0)
            assertEquals 4294967196L,       grid.cellToId(0, 0, -100)
            assertEquals 0-425201762404L,   grid.cellToId(-100, 0, -100)
            assertEquals 4294967196L,       grid.cellToId(0, -100, -100)
            assertEquals 0-425201762404L,   grid.cellToId(-100, -100, -100)
        }
    }

    static class CellToIdToCellXzTest extends GroovyTestCase {
        static List testCells = [
            new Vec3i(0, 0, 0),
            
            new Vec3i(1, 0, 0),
            new Vec3i(0, 1, 0),
            new Vec3i(1, 1, 0),
            new Vec3i(0, 0, 1),
            new Vec3i(1, 0, 1),
            new Vec3i(0, 1, 1),
            new Vec3i(1, 1, 1),

            new Vec3i(-1, 0, 0),
            new Vec3i(0, -1, 0),
            new Vec3i(-1, -1, 0),
            new Vec3i(0, 0, -1),
            new Vec3i(-1, 0, -1),
            new Vec3i(0, -1, -1),
            new Vec3i(-1, -1, -1),

            new Vec3i(1, 0, 0).multLocal(100),
            new Vec3i(0, 1, 0).multLocal(100),
            new Vec3i(1, 1, 0).multLocal(100),
            new Vec3i(0, 0, 1).multLocal(100),
            new Vec3i(1, 0, 1).multLocal(100),
            new Vec3i(0, 1, 1).multLocal(100),
            new Vec3i(1, 1, 1).multLocal(100),

            new Vec3i(-1, 0, 0).multLocal(100),
            new Vec3i(0, -1, 0).multLocal(100),
            new Vec3i(-1, -1, 0).multLocal(100),
            new Vec3i(0, 0, -1).multLocal(100),
            new Vec3i(-1, 0, -1).multLocal(100),
            new Vec3i(0, -1, -1).multLocal(100),
            new Vec3i(-1, -1, -1).multLocal(100)
        ];

        void testDefaultBitsXyz() {
            Grid grid = new Grid(32, 32, 32);
            
            for( Vec3i v : testCells ) {
                long id = grid.cellToId(v);
                Vec3i result = grid.idToCell(id, null);                
                assertEquals v, result
            }
        } 

        void testXyzLowBitsX() {
            Grid grid = new Grid(32, 32, 32, new Vec3i(8, 28, 28));
            
            for( Vec3i v : testCells ) {
                long id = grid.cellToId(v);
                Vec3i result = grid.idToCell(id, null);                
                assertEquals v, result
            }
        } 

        void testXyzLowBitsY() {
            Grid grid = new Grid(32, 32, 32, new Vec3i(28, 8, 28));
            
            for( Vec3i v : testCells ) {
                long id = grid.cellToId(v);
                Vec3i result = grid.idToCell(id, null);                
                assertEquals v, result
            }
        } 

        void testXyzLowBitsZ() {
            Grid grid = new Grid(32, 32, 32, new Vec3i(28, 28, 8));
            
            for( Vec3i v : testCells ) {
                long id = grid.cellToId(v);
                Vec3i result = grid.idToCell(id, null);                
                assertEquals v, result
            }
        } 
    
        void testDefaultBitsXz() {
            Grid grid = new Grid(32, 0, 32);
            
            for( Vec3i v : testCells ) {
                long id = grid.cellToId(v);
                Vec3i result = grid.idToCell(id, null);
                
                // Need to clear the missing component or they won't match.
                // I want to still test the ID part but we know already that
                // we will not get the missing component back from a 2D grid.
                def expected = v.clone();
                expected.y = 0;
                assertEquals expected, result
            }
        } 
    }
    
}


