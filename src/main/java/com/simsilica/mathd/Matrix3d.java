/*
 * $Id$
 * 
 * Copyright (c) 2015, Simsilica, LLC
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
 * A square matrix composed of 9 double-precision elements, used to represent
 * linear transformations of 3-dimensional vectors.
 * <p>
 * Methods with names ending in "Local" modify the current instance. They are
 * used to avoid creating temporary objects.
 * <p>
 * By convention, indices start from zero. The conventional order of indices is
 * (row, column).
 *
 *  @author    Paul Speed
 */
public class Matrix3d implements Cloneable, java.io.Serializable {

    static final long serialVersionUID = 42L;
    /**
     * The element in row 0, column 0.
     */
    public double m00;
    /**
     * The element in row 0, column 1.
     */
    public double m01;
    /**
     * The element in row 0, column 2.
     */
    public double m02;
    /**
     * The element in row 1, column 0.
     */
    public double m10;
    /**
     * The element in row 1, column 1.
     */
    public double m11;
    /**
     * The element in row 1, column 2.
     */
    public double m12;
    /**
     * The element in row 2, column 0.
     */
    public double m20;
    /**
     * The element in row 2, column 1.
     */
    public double m21;
    /**
     * The element in row 2, column 2.
     */
    public double m22;
    
    /**
     * Instantiates an identity matrix (diagonals = 1, other elements = 0).
     */
    public Matrix3d() {
        makeIdentity();
    }
 
    /**
     * Instantiates a matrix with the specified elements.
     *
     * @param m00 the desired value for row 0, column 0
     * @param m01 the desired value for row 0, column 1
     * @param m02 the desired value for row 0, column 2
     * @param m10 the desired value for row 1, column 0
     * @param m11 the desired value for row 1, column 1
     * @param m12 the desired value for row 1, column 2
     * @param m20 the desired value for row 2, column 0
     * @param m21 the desired value for row 2, column 1
     * @param m22 the desired value for row 2, column 2
     */
    public Matrix3d( double m00, double m01, double m02,
                     double m10, double m11, double m12,
                     double m20, double m21, double m22 ) {
        this.m00 = m00;
        this.m01 = m01;
        this.m02 = m02;
        this.m10 = m10;
        this.m11 = m11;
        this.m12 = m12;
        this.m20 = m20;
        this.m21 = m21;
        this.m22 = m22;
    } 
 
    /**
     * Creates a copy of the current instance.
     *
     * @return a new instance, equivalent to this one
     */
    @Override
    public Matrix3d clone() {
        return new Matrix3d(m00, m01, m02,  
                            m10, m11, m12,
                            m20, m21, m22);
    }
 
    /**
     * Copies all elements of the argument to the current instance.
     *
     * @param mat the desired value (not null, unaffected)
     * @return the (modified) current instance (for chaining)
     */
    public Matrix3d set( Matrix3d mat ) {
        this.m00 = mat.m00;
        this.m01 = mat.m01;
        this.m02 = mat.m02;
        this.m10 = mat.m10;
        this.m11 = mat.m11;
        this.m12 = mat.m12;
        this.m20 = mat.m20;
        this.m21 = mat.m21;
        this.m22 = mat.m22;
        return this;
    }
 
    /**
     * Configures the matrix as an identity matrix (diagonals = 1, other
     * elements = 0).
     * 
     * @return the (modified) current instance (for chaining)
     */
    public Matrix3d makeIdentity() {
        m01 = m02 = m10 = m12 = m20 = m21 = 0;
        m00 = m11 = m22 = 1;
        return this;
    }

    /**
     * Tests for identity. The matrix is unaffected.
     *
     * @return true if all diagonal elements are 1 and all other elements are
     * 0 or -0, otherwise false
     */
    public boolean isIdentity() {
        if( m00 == 1. && m01 == 0. && m02 == 0. && m10 == 0. && m11 == 1.
                && m12 == 0. && m20 == 0. && m21 == 0. && m22 == 1. )
            return true;
        else
            return false;
    }

    /**
     * Returns the indexed column. The matrix is unaffected.
     *
     * @param i 0, 1, or 2
     * @return a copy of the {@code i}th column
     * @throws IndexOutOfBoundsException if {@code i} is not 0, 1, or 2
     */
    public Vec3d getColumn( int i ) {
        switch( i ) {
            case 0:
                return new Vec3d(m00,m10,m20);
            case 1:
                return new Vec3d(m01,m11,m21);
            case 2:
                return new Vec3d(m02,m12,m22);
        }
        return null;            
    }        

    /**
     * Alters the indexed column and returns the (modified) current instance.
     *
     * @param i 0, 1, or 2
     * @param col the desired value (not null, unaffected)
     * @return the current instance (for chaining)
     * @throws IndexOutOfBoundsException if {@code i} is not 0, 1, or 2
     */
    public Matrix3d setColumn( int i, Vec3d col ) {
        switch( i ) {
            case 0:
                m00 = col.x;
                m10 = col.y;
                m20 = col.z;
                break;
            case 1:
                m01 = col.x;
                m11 = col.y;
                m21 = col.z;
                break;
            case 2:
                m02 = col.x;
                m12 = col.y;
                m22 = col.z;
                break;
            default:
                throw new IllegalArgumentException( "Column does not exist:" + i );
        }
        return this;            
    }
     
    /**
     * Multiplies by the argument and returns the product as a new instance. The
     * current instance is unaffected.
     *
     * @param mat the right factor (not null, unaffected)
     * @return a new Vec3d
     */
    public Matrix3d mult( Matrix3d mat ) {
        double temp00 = m00 * mat.m00 + m01 * mat.m10 + m02 * mat.m20;
        double temp01 = m00 * mat.m01 + m01 * mat.m11 + m02 * mat.m21;
        double temp02 = m00 * mat.m02 + m01 * mat.m12 + m02 * mat.m22;
        double temp10 = m10 * mat.m00 + m11 * mat.m10 + m12 * mat.m20;
        double temp11 = m10 * mat.m01 + m11 * mat.m11 + m12 * mat.m21;
        double temp12 = m10 * mat.m02 + m11 * mat.m12 + m12 * mat.m22;
        double temp20 = m20 * mat.m00 + m21 * mat.m10 + m22 * mat.m20;
        double temp21 = m20 * mat.m01 + m21 * mat.m11 + m22 * mat.m21;
        double temp22 = m20 * mat.m02 + m21 * mat.m12 + m22 * mat.m22;
        
        return new Matrix3d( temp00, temp01, temp02,
                             temp10, temp11, temp12,
                             temp20, temp21, temp22 );
    }

    /**
     * Multiplies by the specified column vector and returns the product as a
     * new column vector. The current instance is unaffected.
     *
     * @param v the right factor (not null, unaffected)
     * @return a new Vec3d
     */
    public Vec3d mult( Vec3d v ) {
        double x = v.x;
        double y = v.y;
        double z = v.z;
        
        double xr = (m00 * x) + (m01 * y) + (m02 * z);
        double yr = (m10 * x) + (m11 * y) + (m12 * z);
        double zr = (m20 * x) + (m21 * y) + (m22 * z);
        
        return new Vec3d(xr,yr,zr);
    }

    /**
     * Multiplies by the argument and returns the (modified) current instance.
     * <p>
     * It IS safe for {@code mat} and {@code this} to be the same object.
     *
     * @param mat the right factor (not null, unaffected unless it's
     * {@code this})
     * @return the (modified) current instance (for chaining)
     */
    public Matrix3d multLocal( Matrix3d mat ) {
        double temp00 = m00 * mat.m00 + m01 * mat.m10 + m02 * mat.m20;
        double temp01 = m00 * mat.m01 + m01 * mat.m11 + m02 * mat.m21;
        double temp02 = m00 * mat.m02 + m01 * mat.m12 + m02 * mat.m22;
        double temp10 = m10 * mat.m00 + m11 * mat.m10 + m12 * mat.m20;
        double temp11 = m10 * mat.m01 + m11 * mat.m11 + m12 * mat.m21;
        double temp12 = m10 * mat.m02 + m11 * mat.m12 + m12 * mat.m22;
        double temp20 = m20 * mat.m00 + m21 * mat.m10 + m22 * mat.m20;
        double temp21 = m20 * mat.m01 + m21 * mat.m11 + m22 * mat.m21;
        double temp22 = m20 * mat.m02 + m21 * mat.m12 + m22 * mat.m22;
        
        this.m00 = temp00;
        this.m01 = temp01;
        this.m02 = temp02;
        this.m10 = temp10;
        this.m11 = temp11;
        this.m12 = temp12;
        this.m20 = temp20;
        this.m21 = temp21;
        this.m22 = temp22;
        
        return this;
    }
    
    /**
     * Multiplies by the scalar argument and returns the (modified) current
     * instance.
     *
     * @param scale the scaling factor
     * @return the (modified) current instance (for chaining)
     */
    public Matrix3d multLocal( double scale ) {
        this.m00 *= scale;
        this.m01 *= scale;
        this.m02 *= scale;
        this.m10 *= scale;
        this.m11 *= scale;
        this.m12 *= scale;
        this.m20 *= scale;
        this.m21 *= scale;
        this.m22 *= scale;
        
        return this;
    }
    
    /**
     * Returns the determinant. The matrix is unaffected.
     *
     * @return the determinant
     */
    public double determinant() {
        double co00 = (m11 * m22) - (m12 * m21);
        double co10 = (m12 * m20) - (m10 * m22);
        double co20 = (m10 * m21) - (m11 * m20);
        return m00 * co00 + m01 * co10 + m02 * co20;
    }
    
    /**
     * Returns the multiplicative inverse. If the current instance is singular,
     * an identity matrix is returned. The current instance is unaffected.
     *
     * @return a new Matrix3d
     */
    public Matrix3d invert() {
        double d = determinant();
        if( d == 0 )
            return new Matrix3d(); // questionable
        
        double rm00 = m11 * m22 - m12 * m21;
        double rm01 = m02 * m21 - m01 * m22;
        double rm02 = m01 * m12 - m02 * m11;
        double rm10 = m12 * m20 - m10 * m22;
        double rm11 = m00 * m22 - m02 * m20;
        double rm12 = m02 * m10 - m00 * m12;
        double rm20 = m10 * m21 - m11 * m20;
        double rm21 = m01 * m20 - m00 * m21;
        double rm22 = m00 * m11 - m01 * m10;
 
        double s = 1.0 / d;
 
        return new Matrix3d( rm00 * s, rm01 * s, rm02 * s,
                             rm10 * s, rm11 * s, rm12 * s,              
                             rm20 * s, rm21 * s, rm22 * s );
    }
    
    /**
     * Returns the transpose. The current instance is unaffected.
     *
     * @return a new Matrix3d
     */
    public Matrix3d transpose() {
        return new Matrix3d( m00, m10, m20, m01, m11, m21, m02, m12, m22 );
    }

    public Matrix3d setSkewSymmetric( Vec3d v ) {
        m00 = 0; 
        m11 = 0; 
        m22 = 0;
        
        m01 = -v.z;
        m02 = v.y;
        m10 = v.z;
        m12 = -v.x;
        m20 = -v.y;
        m21 = v.x;
        
        return this;
    }
 
    /**
     * Adds the argument and returns the (modified) current instance.
     * <p>
     * It IS safe for {@code add} and {@code this} to be the same object.
     *
     * @param add the matrix to add (not null, unaffected unless it's
     * {@code this})
     * @return the (modified) current instance (for chaining)
     */
    public Matrix3d addLocal( Matrix3d add ) {
        m00 = m00 + add.m00;
        m01 = m01 + add.m01;
        m02 = m02 + add.m02;
        m10 = m10 + add.m10;
        m11 = m11 + add.m11;
        m12 = m12 + add.m12;
        m20 = m20 + add.m20;
        m21 = m21 + add.m21;
        m22 = m22 + add.m22;
        
        return this;
    }

    /**
     * Tests for exact equality with the argument, distinguishing -0 from 0. If
     * {@code o} is null, false is returned. Either way, the current instance is
     * unaffected.
     *
     * @param o the object to compare (may be null, unaffected)
     * @return true if {@code this} and {@code o} have identical values,
     * otherwise false
     */
    @Override
    public boolean equals( Object o ) {
        if( o == this )
            return true;
        if( o == null || o.getClass() != getClass() )
            return false;

        Matrix3d other = (Matrix3d)o;
        if( Double.compare(m00, other.m00) != 0 )
            return false;
        if( Double.compare(m01, other.m01) != 0 )
            return false;
        if( Double.compare(m02, other.m02) != 0 )
            return false;
        if( Double.compare(m10, other.m10) != 0 )
            return false;
        if( Double.compare(m11, other.m11) != 0 )
            return false;
        if( Double.compare(m12, other.m12) != 0 )
            return false;
        if( Double.compare(m20, other.m20) != 0 )
            return false;
        if( Double.compare(m21, other.m21) != 0 )
            return false;
        if( Double.compare(m22, other.m22) != 0 )
            return false;

        return true;
    }

    /**
     * Returns a hash code. If two matrices have identical values, they
     * will have the same hash code. The matrix is unaffected.
     *
     * @return a 32-bit value for use in hashing
     */
    @Override
    public int hashCode() {
        long bits = Double.doubleToLongBits(m00);
        bits ^= Double.doubleToLongBits(m01) * 2L;
        bits ^= Double.doubleToLongBits(m02) * 3L;
        bits ^= Double.doubleToLongBits(m10) * 4L;
        bits ^= Double.doubleToLongBits(m11) * 5L;
        bits ^= Double.doubleToLongBits(m12) * 6L;
        bits ^= Double.doubleToLongBits(m20) * 7L;
        bits ^= Double.doubleToLongBits(m21) * 8L;
        bits ^= Double.doubleToLongBits(m22) * 9L;

        return ((int)bits) ^ ((int)(bits >> 32));
    }

    /**
     * Returns a string representation of the matrix, which is unaffected. For
     * example, an identity matrix would be represented by:
     * <pre>
     * Matrix3d[{1.0, 0.0, 0.0}, {0.0, 1.0, 0.0}, {0.0, 0.0, 1.0}]
     * </pre>
     *
     * @return a descriptive string of text (not null, not empty)
     */
    @Override
    public String toString() {
        return "Matrix3d[{" + m00 + ", " + m01 + ", " + m02 + "}, {"
                            + m10 + ", " + m11 + ", " + m12 + "}, {"
                            + m20 + ", " + m21 + ", " + m22 + "}]";
    }
}


