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
        }

        void testLowBitsY() {
        }

        void testLowBitsZ() {
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
        
        void testLowBitsX() {
        }

        void testLowBitsZ() {
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
    
        void testDefaultBits() {
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


