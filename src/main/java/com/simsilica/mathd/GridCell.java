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

import java.util.Objects;



/**
 *  Represents a single section of partioned space inside of an evenly
 *  spaced Grid.
 *
 *  @author    Paul Speed
 */
public class GridCell {
    private final Grid grid;
    private final Vec3i cell;
    private final Vec3i worldOrigin;
    
    public GridCell( Grid grid, Vec3i cell ) {
        this.grid = grid;
        this.cell = cell;
        this.worldOrigin = grid.cellToWorld(cell);
    }

    public final Grid getGrid() {
        return grid;
    }

    public final Vec3i getWorldOrigin() {
        return worldOrigin;
    }
    
    public final boolean contains( Vec3d world ) {
        return contains(world.x, world.y, world.z);
    }
    
    public final boolean contains( double x, double y, double z ) {
        Vec3i spacing = grid.getSpacing();
        double xLocal = x - worldOrigin.x;
        double yLocal = y - worldOrigin.y;
        double zLocal = z - worldOrigin.z;
        if( spacing.x != 0 ) {
            if( xLocal < 0 || xLocal > spacing.x ) {
                return false;
            }
        }
        if( spacing.y != 0 ) {
            if( yLocal < 0 || yLocal > spacing.y ) {
                return false;
            }
        }
        if( spacing.z != 0 ) {
            if( zLocal < 0 || zLocal > spacing.z ) {
                return false;
            }
        }
        return true;
    }
    
    public final Vec3d localToWorld( Vec3d local ) {
        return localToWorld(local, new Vec3d());
    }
    
    public final Vec3d localToWorld( Vec3d local, Vec3d store ) {
        if( store == null ) {
            store = new Vec3d();
        }
        store.x = worldOrigin.x + local.x;
        store.y = worldOrigin.y + local.y;
        store.z = worldOrigin.z + local.z;
        return store;
    }
    
    public final Vec3i localToWorld( Vec3i local ) {
        return localToWorld(local, new Vec3i());
    }
    
    public final Vec3i localToWorld( Vec3i local, Vec3i store ) {
        if( store == null ) {
            store = new Vec3i();
        }
        store.x = worldOrigin.x + local.x;
        store.y = worldOrigin.y + local.y;
        store.z = worldOrigin.z + local.z;
        return store;
    }
 
    public final Vec3d worldToLocal( Vec3d world ) {
        return worldToLocal(world, null);
    }
    
    public final Vec3d worldToLocal( Vec3d world, Vec3d store ) {
        if( store == null ) {
            store = new Vec3d();
        }
        store.x = world.x - worldOrigin.x;
        store.y = world.y - worldOrigin.y;
        store.z = world.z - worldOrigin.z;
        return store;
    }
       
    public final Vec3d worldToLocal( double x, double y, double z ) {
        return worldToLocal(x, y, z, null);
    }
    
    public final Vec3d worldToLocal( double x, double y, double z, Vec3d store ) {
        if( store == null ) {
            store = new Vec3d();
        }
        store.x = x - worldOrigin.x;
        store.y = y - worldOrigin.y;
        store.z = z - worldOrigin.z;
        return store;
    }
 
    /**
     *  Returns true if the cell location and the parent grid are the same. 
     */      
    @Override
    public boolean equals( Object o ) {
        if( o == this )
            return true;
        if( o == null || o.getClass() != getClass() )
            return false;
        GridCell other = (GridCell)o;
        
        return other.cell.x == cell.x 
            && other.cell.y == cell.y 
            && other.cell.z == cell.z 
            && Objects.equals(other.grid, grid);
    }
        
    @Override
    public int hashCode() {
        return cell.hashCode();
    }
        
    @Override
    public String toString() {
        return "(" + cell.x + ":" + cell.y + ":" + cell.z + ")";
    }               
}

