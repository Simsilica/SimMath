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

package com.simsilica.mathd.trans;

import com.simsilica.mathd.Quatd;
import com.simsilica.mathd.Vec3d;


/**
 *  Represents a start and an end of a time-based transition
 *  with interpolation functions provided.
 *
 *  @author    Paul Speed
 */
public class PositionTransition3d implements Transition<PositionTransition3d> {

    private long startTime;
    private Vec3d startPos;
    private Quatd startRot;
    private boolean startVisible;    
    private final long endTime;
    private final Vec3d endPos;
    private final Quatd endRot;
    private final boolean endVisible;    
    
    public PositionTransition3d( long endTime, Vec3d endPos, Quatd endRot, boolean visible ) {
        this.endTime = endTime;
        this.endPos = endPos;
        this.endRot = endRot;
        this.endVisible = visible;
    }

    public static TransitionBuffer<PositionTransition3d> createBuffer( int history ) {
        return new TransitionBuffer<>(history);
    }
    
    @Override
    public void setPreviousTransition( PositionTransition3d previous ) {
        this.startTime = previous.endTime;
        this.startPos = previous.endPos;
        this.startRot = previous.endRot;
        this.startVisible = previous.endVisible;
        if( startTime > endTime ) {
            throw new IllegalArgumentException( "Frame transitions cannot go backwards." );
        }
    }            
 
    @Override
    public boolean containsTime( long time ) {
        if( time < startTime )
            return false;
        return time <= endTime;
    }
    
    @Override
    public long getStartTime() {
        return startTime;
    }
    
    @Override
    public long getEndTime() {
        return endTime;
    }
 
    protected final double tween( long time ) {
        long length = endTime - startTime;
        if( length == 0 )
            return 0;

        double part = time - startTime;
        if( part > length )
            return 1.0f;
        if( part < 0 )
            return 0.0f;
        return part / length;   
    }
 
    public Vec3d getPosition( long time ) {
        return getPosition(time, false);
    }

    public Vec3d getPosition( long time, boolean clamp ) {
 
        if( startPos == null ) {
            // Need to clone it even for the clamped version because
            // 99% of the time the caller will get their own instance and
            // use it that way.  It causes subtle bugs then to return the
            // actual start or end instance.
            return clamp ? endPos.clone() : null;           
        }

        if( time < startTime ) {
            // Need to clone it even for the clamped version because
            // 99% of the time the caller will get their own instance and
            // use it that way.  It causes subtle bugs then to return the
            // actual start or end instance.
            return clamp ? startPos.clone() : null;
        }
 
        double t = tween(time);
        
        Vec3d result = new Vec3d().interpolateLocal(startPos, endPos, t);     
        return result;
    }
    
    public Quatd getRotation( long time ) {
        return getRotation(time, false);
    }
    
    public Quatd getRotation( long time, boolean clamp ) {
        if( startRot == null ) {
            // Need to clone it even for the clamped version because
            // 99% of the time the caller will get their own instance and
            // use it that way.  It causes subtle bugs then to return the
            // actual start or end instance.
            return clamp ? endRot.clone() : null;
        }
        
        if( time < startTime ) {
            // Need to clone it even for the clamped version because
            // 99% of the time the caller will get their own instance and
            // use it that way.  It causes subtle bugs then to return the
            // actual start or end instance.        
            return clamp ? startRot.clone() : null;
        }
            
        Quatd result = new Quatd().slerpLocal(startRot, endRot, tween(time));
        return result;
    }
    
    public boolean getVisibility( long time ) {
        // No real transition here, for the entire span of
        // time between start and end, the visibility is
        // the start visibility.
        if( time > endTime ) {
            return endVisible;
        }
        return startVisible;
    }
    
    @Override
    public String toString() {
        return "PositionTransition3d[ t:" + startTime + ", pos:" + startPos + ", rot:" + startRot + ", vis:" + startVisible
                                + " -> t:" + endTime + ", pos:" + endPos + ", rot:" + endRot + ", vis:" + endVisible + " ]";
    } 
}
