Version 1.5.1 (unreleased)
--------------
* Fixed an issue with how Quatd.equals() and Vec3d.equals() dealt with
    NaN and -0.  Thanks, sgold.


Version 1.5.0 (latest)
--------------
* Added Quatd.get(int) and Quatd.set(int, double) methods to be similar
    to Vec3d in allowing individual components to be set by index.
* Published to sonatype/maven central


Version 1.4.1
--------------
* Added some copy constructors to Vec3d, Vec4d, and Quatd.
* Added getters for the start/end pos, rotation, visibility of
    PositionTransition3d and PositionTransition3f
* Added Grid.worldToId(Vec3d), Grid.worldToId(double, double, double)
    and Grid.cellToId(Vec3i)
* Added Vec3d.set(Vec3i)
* Added GridCell.getId() that maps to Grid.cellToId()
* Added Vec3d.toVec3i(), floor(), and ceil() for Vec3d to Vec3i conversion.
* Added Grid.getContainingCell(Vec3i)
* Added Vec3d.distance(x,y,z) and Vec3d.distanceSq(x,y,z)
* Added Vec3i.length() and Vec3i.lengthSq()
* Added a Vec3d.ZERO 'constant'
* Added Rayd, a double-precision Ray class.


Version 1.4.0
--------------
* Added an IntRange interface and default FixedIntRange implementation to
    represent a range of ints.
* Added an IntRangeSet which is a Set<Integer> (and effectively Set<int))
    that is space-optimized for sets of integers that consist of packed ranges.


Version 1.3.0
--------------
* Added Quatd.fromAngles(double[])
* Added Quatd.equals() and Quatd.hashCode()
* Added Vec3d.isSimilar() and Quatd.isSimilar() that allow for
    comparison of values within some epsilon.
* Converted a System.out.println() in Vec3Bits to a log.debug() call.
* Added Vec3d.interpolateLocal().
* Added Quatd.slerpLocal().
* Added PositionTransition3f and PositionTransition3d.
* Deprecated PositionTransition in favor of PositionTransition3f or
    PositionTransition3d.
* Deprecated PositionTransition.getFrameVelocity() and did not port it
    to PositionTransition3f or PositionTransition3d.


Version 1.2.0
--------------
* Added Vec4i.toVec3d()
* Added Vec3d.set(Vector3f)
* Modified Vec3d.set(Vec3d) and set(index, val) to return Vec3d (this)
* Added Quatd.set(Quaternion)
* Added a Vec3d.xzy() swizzle method
* Added filter package with Filterd interface and initial
    SimpleMovingMean filter implementation
* Modified all Quatd.set() methods to return Quatd (this)
* Modified Quatd.addScaledVectorLocal() to return Quatd (this)
* Modified Vec3d.zeroEpsilon() and Vec4d.zeroEpsilon() to return
    Vec3d and Vec4d resepctively (this)
* Modified all Vec3i void-return methods to return Vec3i (this)
* Added Vec3d.divide() and divideLocal() methods.
* Added Vec3d.multLocal(Vec3d) method
* Added Vec4d.divide() and divideLocal() methods.
* Added Vec4d.multLocal(Vec3d) method
* Suppressed some 'unchecked' warnings in TransitionBuffer since we
    know we're doing generic-voodoo in there.
* Set sourceCompatibility to 1.7 and turned on detailed 'unchecked' warnings



Version 1.1.1
--------------
* Added Quaternion.toAngles()


Version 1.1.0
--------------
* Added an alternate AaBBox.setCenter(double, double, double) method.
* Added Matrix3d.addLocal(Matrix3d).
* Added Matrix3d.setSkewSymmetric(Vec3d).
* Modified Matrix3d.set() and Matrix3d.makeIdentity() to return 'this'
    Matrix3d.
* Modified Vec3d.set() to return 'this' Vec3d.
* Added Vec3d.isNaN() which will return true if any of the components
    are NaN.
* Added a Vec3d constructor that takes a JME Vector3f as a reciprocal to
    the existing toVector3f() method.
* Added Grid and GridCell classes for standardizing the partitioning of
    3D space into regularly spaced cells.
* Made several of the classes Serializable: Quatd, Vec3d, Vec3d, Matrix3d,
    Matrix4d, and AaBBox.
* Made Matrix3d, Matrix4d, and AaBBox cloneable.
* Added a Quatd constructor that takes a JME Quaternion as a reciprocal to
    the existing toQuaternion() method.


Version 1.0.2
--------------
* Modified TransitionBuffer to have a 'clamp' on getTransition()
    that when 'true' will use the old behavior of returning the
    earliest/latest transition for out-of-range times.  For 'false',
    it will return null for out-of-range times.
* Modified TransitionBuffer to remove the System.out.println() for
    out-of-range times.
* Modified the build.gradle to replace the JME version with a specific
    version instead of letting it float.  I think alpha4 is generally
    the minimum accepted 3.1 version at this point.


Version 1.0.1
--------------
* Initial public release with maven artifacts
