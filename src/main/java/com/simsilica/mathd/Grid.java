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
public class Grid {

    private final Vec3i gridSpacing;
 
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
        this(new Vec3i(xSpacing, ySpacing, zSpacing));
    }
 
    /**
     *  Creates a grid with the specified spacing along the x, y, and z
     *  axes.
     */   
    public Grid( Vec3i gridSpacing ) {
        this.gridSpacing = gridSpacing;
    }
    
    public final Vec3i getSpacing() {
        return gridSpacing;
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
     *  Returns a GridCell object the represents the section of space
     *  for the specified grid cell coordinate.
     */
    public final GridCell getCell( Vec3i cell ) {
        return new GridCell(this, cell.clone());
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
 
    @Override
    public String toString() {
        return "Grid[" + gridSpacing + "]";
    }   
} 


