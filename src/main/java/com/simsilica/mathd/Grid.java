/*
 * $Id$
 * 
 * Copyright (c) 2016, Simsilica, LLC
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

package com.simsilica.mathd;

import java.io.*;
import java.lang.reflect.Field;

/**
 *  Represents a 3D partitioning of space into discrete evenly spaced
 *  cells along some fixed integer boundary.  The grid spacing may
 *  be different along each of the axes but is most often the same
 *  along the x,z plane.  Using the Grid, raw world space coordinates
 *  can be converted to/from grid cell coordinates, cell-relative positions
 *  determined, and so on.
 *
 *  @author    Paul Speed
 */
public class Grid implements java.io.Serializable {
 
    static final long serialVersionUID = 42L;

    private final Vec3i gridSpacing;
    private final int dimensions;
 
    private final Mask xMask;
    private final Mask yMask;
    private final Mask zMask; 
 
    /**
     *  Creates a grid with the same cell size along each coordinate
     *  axis.
     */
    public Grid( int spacing ) {
        this(spacing, spacing, spacing);
    }
    
    /**
     *  Creates a grid with the specified cell size for the x and z
     *  axes and 0 for the y axis, indicating that there is no partitioning
     *  in the y axis. ie: it's an essentially 2D grid.
     */
    public Grid( int xSpacing, int zSpacing ) {
        this(xSpacing, 0, zSpacing);
    }
    
    /**
     *  Creates a grid with the specified spacing along the x, y, and z
     *  axes.
     */
    public Grid( int xSpacing, int ySpacing, int zSpacing ) {
        this(new Vec3i(xSpacing, ySpacing, zSpacing), null);
    }
 
    /**
     *  Creates a grid with the specified spacing along the x, y, and z
     *  axes.
     */   
    public Grid( Vec3i gridSpacing ) {
        this(gridSpacing, null);
    }

    public Grid( Vec3i gridSpacing, Vec3i gridBits ) {
        this.gridSpacing = gridSpacing;
        int axes = 0;
        int xBits = 0;
        int yBits = 0;
        int zBits = 0;
        
        if( gridSpacing.x != 0 ) {
            axes++;
            xBits = 1;
        }
        if( gridSpacing.y != 0 ) {
            axes++;
            yBits = 1;
        }
        if( gridSpacing.z != 0 ) {
            axes++;
            zBits = 1;
        }        
        this.dimensions = axes;
        int bits = 64 / axes;
        if( gridBits == null ) {
            xBits *= bits;
            yBits *= bits;
            zBits *= bits;
        } else {
            if( xBits > 0 && gridBits.x == 0 ) {
                throw new IllegalArgumentException("Grid spacing has x but no xBits allocated");
            }
            if( yBits > 0 && gridBits.y == 0 ) {
                throw new IllegalArgumentException("Grid spacing has y but no yBits allocated");
            }
            if( zBits > 0 && gridBits.z == 0 ) {
                throw new IllegalArgumentException("Grid spacing has z but no zBits allocated");
            }
            xBits = gridBits.x;
            yBits = gridBits.y;
            zBits = gridBits.z;
        }
        this.xMask = new Mask(xBits);
        this.yMask = new Mask(yBits);
        this.zMask = new Mask(zBits);
    }   
        
    public final Vec3i getSpacing() {
        return gridSpacing;
    }
 
    public final int getDimensions() {
        return dimensions;
    }
 
    public final Vec3i getIdBits() {
        return new Vec3i(xMask.shift, yMask.shift, zMask.shift);
    }
    
    private int worldToCell( int i, int size ) {
        if( size == 0 ) {
            return 0;  // special case where the dimension is flattened
        }
        if( i < 0 ) {
            // Need to adjust so that, for example:
            // -32 to -1 is -1 instead of part -1 and part 0
            i = (i + 1) / size;
            return i - 1;
        } else {
            return i / size;
        }
    }
 
    private int worldToCell( double d, int size ) {
        return worldToCell((int)Math.floor(d), size);
    }
    
    private int cellToWorld( int i, int size ) {
        return i * size;
    }     

    /**
     *  Returns a GridCell object that represents the section of space that
     *  contains the specified world location.  The Cell
     *  object can be used to query additional values about the enclosing
     *  subspace such as performing worldToLocal/localToWorld conversions,
     *  obtaining the world origin, checking containment, etc.
     */
    public final GridCell getContainingCell( double xWorld, double yWorld, double zWorld ) {
        return new GridCell(this, worldToCell(xWorld, yWorld, zWorld));
    }

    /**
     *  Returns a GridCell object that represents the section of space that
     *  contains the specified world location.  The Cell
     *  object can be used to query additional values about the enclosing
     *  subspace such as performing worldToLocal/localToWorld conversions,
     *  obtaining the world origin, checking containment, etc.
     */
    public final GridCell getContainingCell( Vec3d world ) {
        return getContainingCell(world.x, world.y, world.z);
    }

    /**
     *  Returns a GridCell object that represents the section of space that
     *  contains the specified world location.  The Cell
     *  object can be used to query additional values about the enclosing
     *  subspace such as performing worldToLocal/localToWorld conversions,
     *  obtaining the world origin, checking containment, etc.
     */
    public final GridCell getContainingCell( Vec3i world ) {
        return getContainingCell(world.x, world.y, world.z);
    }
    
    /**
     *  Returns a GridCell object the represents the section of space
     *  for the specified grid cell coordinate.
     */
    public final GridCell getGridCell( Vec3i cell ) {
        return new GridCell(this, cell.clone());
    }

    /**
     *  Returns a GridCell object the represents the section of space
     *  for the specified grid cell coordinate.
     */
    public final GridCell getGridCell( int xCell, int yCell, int zCell ) {
        return new GridCell(this, new Vec3i(xCell, yCell, zCell));
    }
    
    /**
     *  Returns the grid cell coordinate that contains the
     *  specified world space coordinate. 
     */
    public final Vec3i worldToCell( double xWorld, double yWorld, double zWorld ) {
        return worldToCell(xWorld, yWorld, zWorld, new Vec3i());
    }
            
    /**
     *  Returns the grid cell coordinate that contains the
     *  specified world space coordinate, storing it in the specified
     *  store argument.  If the store argument is null then one is created. 
     */
    public final Vec3i worldToCell( double xWorld, double yWorld, double zWorld, Vec3i store ) {
        if( store == null ) {
            store = new Vec3i();
        }
        store.x = worldToCell(xWorld, gridSpacing.x);   
        store.y = worldToCell(yWorld, gridSpacing.y);   
        store.z = worldToCell(zWorld, gridSpacing.z);
        return store;   
    }
    
    /**
     *  Returns the grid cell coordinate that contains the
     *  specified world space coordinate.
     */
    public final Vec3i worldToCell( Vec3d world ) {
        return worldToCell(world.x, world.y, world.z, new Vec3i());
    }
    
    /**
     *  Returns the grid cell coordinate that contains the
     *  specified world space coordinate, storing it in the specified
     *  store argument.  If the store argument is null then one is created. 
     */
    public final Vec3i worldToCell( Vec3d world, Vec3i store ) {
        return worldToCell(world.x, world.y, world.z, store);
    }
    
    /**
     *  Returns the world location of the origin of the specified
     *  grid cell location as a Vec3i.  
     */
    public final Vec3i cellToWorld( int xCell, int yCell, int zCell ) {
        return cellToWorld(xCell, yCell, zCell, new Vec3i());
    }
    
    /**
     *  Returns the world location of the origin of the specified
     *  grid cell location as a Vec3i, storing it in the specified 'store' argument.
     *  If the store argument is null then one is created.  
     */
    public final Vec3i cellToWorld( int xCell, int yCell, int zCell, Vec3i store ) {
        if( store == null ) {
            store = new Vec3i();
        }
        store.x = cellToWorld(xCell, gridSpacing.x);
        store.y = cellToWorld(yCell, gridSpacing.y);
        store.z = cellToWorld(zCell, gridSpacing.z);
        return store;
    }
    
    /**
     *  Returns the world location of the origin of the specified
     *  grid cell location as a Vec3d, storing it in the specified 'store' argument.
     *  If the store argument is null then one is created.  
     */
    public final Vec3d cellToWorld( int xCell, int yCell, int zCell, Vec3d store ) {
        if( store == null ) {
            store = new Vec3d();
        }
        store.x = cellToWorld(xCell, gridSpacing.x);
        store.y = cellToWorld(yCell, gridSpacing.y);
        store.z = cellToWorld(zCell, gridSpacing.z);
        return store;
    }
    
    /**
     *  Returns the world location of the origin of the specified
     *  grid cell location as a Vec3i, storing it in the specified 'store' argument.
     *  If the store argument is null then one is created.  
     */
    public final Vec3i cellToWorld( Vec3i cell ) {
        return cellToWorld(cell.x, cell.y, cell.z, new Vec3i());
    }
    
    /**
     *  Returns the world location of the origin of the specified
     *  grid cell location as a Vec3i, storing it in the specified 'store' argument.
     *  If the store argument is null then one is created.  
     */
    public final Vec3i cellToWorld( Vec3i cell, Vec3i store ) {
        return cellToWorld(cell.x, cell.y, cell.z, store);
    }
    
    /**
     *  Returns the world location of the origin of the specified
     *  grid cell location as a Vec3d, storing it in the specified 'store' argument.
     *  If the store argument is null then one is created.  
     */
    public final Vec3d cellToWorld( Vec3i cell, Vec3d store ) {
        return cellToWorld(cell.x, cell.y, cell.z, store);
    }
    
    /**
     *  Converts the world location to a cell location and then to a cell ID.
     *  This is the equivalent of using cellToId(worldToCell(worldLoc)).  
     */
    public final long worldToId( Vec3d world ) {
        return worldToId(world.x, world.y, world.z);
    }
    
    /**
     *  Converts the world location to a cell location and then to a cell ID.  
     *  This is the equivalent of using cellToId(worldToCell(worldLoc)) but
     *  without the intermediate garbage.  
     */
    public final long worldToId( double xWorld, double yWorld, double zWorld ) {
        int x = worldToCell(xWorld, gridSpacing.x);   
        int y = worldToCell(yWorld, gridSpacing.y);   
        int z = worldToCell(zWorld, gridSpacing.z);
        return cellToId(x, y, z);
    }

    /**
     *  Converts the x, y, z cell location into a single composite long value.  This 
     *  is done using masking and bitshifting to pack the individual values 
     *  together.  Any of the axes that are 'flat' or using 0 spacing are 
     *  skipped giving more overhead to the other dimensions.  Even with three 
     *  dimensions present, that leaves 2^21 bits (represents +/- 2^20) which is over 
     *  one million cells in each direction (+ and -).  That seems pretty reasonable
     *  for a grid that is already reducing space into discrete subspaces.  And if
     *  it's not then calling code should just avoid using composite IDs.    
     */   
    public final long cellToId( Vec3i cell ) {
        return cellToId(cell.x, cell.y, cell.z);
    }
 
    /**
     *  Converts the x, y, z cell location into a single composite long value.  This 
     *  is done using masking and bitshifting to pack the individual values 
     *  together.  Any of the axes that are 'flat' or using 0 spacing are 
     *  skipped giving more overhead to the other dimensions.  Even with three 
     *  dimensions present, that leaves 2^21 bits (represents +/- 2^20) which is over 
     *  one million cells in each direction (+ and -).  That seems pretty reasonable
     *  for a grid that is already reducing space into discrete subspaces.  And if
     *  it's not then calling code should just avoid using composite IDs.    
     */   
    public final long cellToId( int xCell, int yCell, int zCell ) {
        long result = 0;
        result = xMask.apply(xCell, result);
        
        // Shift result for whatever y part we need to add
        result = result << yMask.shift;
        result = yMask.apply(yCell, result);
        
        // Shift result for whatever z part we need to add
        result = result << zMask.shift;
        result = zMask.apply(zCell, result);
        
        return result;        
    } 

    public final Vec3i idToCell( long id, Vec3i store ) {
        int x, y, z;

        z = zMask.extract(id);
        id = id >> zMask.shift;
        
        y = yMask.extract(id);
        id = id >> yMask.shift;
        
        x = xMask.extract(id);
        id = id >> xMask.shift;

        if( store == null ) {
            store = new Vec3i(x, y, z);
        } else {
            store.set(x, y, z);
        }       
        return store;
    }
    
    public final Vec3i idToCell( long id ) {
        return idToCell(id, new Vec3i());
    }
 
    @Override
    public String toString() {
        return "Grid[" + gridSpacing + "]";
    }
 
    private void readObject( ObjectInputStream in ) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        if( xMask == null ) {
            Mask x, y, z;
            
            // This is reading an older version so we need to manufacture the
            // masks from the dimensions and spacing.
            int bits = 64 / dimensions;
            if( gridSpacing.x != 0 ) {
                x = new Mask(bits);
            } else {
                x = new Mask(0);
            }
            if( gridSpacing.y != 0 ) {
                y = new Mask(bits);
            } else {
                y = new Mask(0);
            }
            if( gridSpacing.z != 0 ) {
                z = new Mask(bits);
            } else {
                z = new Mask(0);
            }
 
            // I'm stubborn and I don't want to give up finalized fields.
            // So we'll pay a slight penalty on load of these older grids if anyone
            // actually saved any.  If they get resaved then they no longer pay the penalty.
            // -pspeed:2022-12-20
            try {
                Class c = getClass();
                Field xf = c.getDeclaredField("xMask");
                xf.setAccessible(true);                
                Field yf = c.getDeclaredField("yMask");
                yf.setAccessible(true);  
                Field zf = c.getDeclaredField("zMask");
                zf.setAccessible(true);  
                xf.set(this, x);
                yf.set(this, y);
                zf.set(this, z);
            } catch( Exception e ) {
                throw new IOException("Error deserializing older version", e);
            }
        }
    }
        
    private static final class Mask implements java.io.Serializable {
        static final long serialVersionUID = 1L;
        
        private final int shift;
        private final long mask;
        private final int signCheck;
        private final int signExtend;
    
        public Mask( int bits ) {
            this.shift = bits;

            // We only ever return ints as grid cell components so
            // signExtend can be int.       
            this.signExtend = (int)(-1L << bits);
        
            // Sign check can also be int
            this.signCheck = 0x1 << (bits - 1);
        
            // But the mask needs to be the full long size because we
            // will be using it to mask off parts of a value that comes
            // from the composite long
            this.mask = ~(-1L << bits);

            // From those, we'll use the mask to clear off the hi bits
            // from long parts.  We'll use the signCheck to see if an 
            // unshifted value is negative.  We'll use the signExtend to
            // turn it into a negative int without affecting the part of
            // the number we care about. 
        }
        
        public long apply( int cell, long target ) {
            return target | (cell & mask);
        }
        
        public int extract( long id ) {
            if( shift == 0 ) {
                return 0;
            }
            int result = (int)(id & mask);
            if( (result & signCheck) != 0 ) {
                // Sign extend it
                result = result | signExtend;
            }
            return result;
        } 
    }   
} 

