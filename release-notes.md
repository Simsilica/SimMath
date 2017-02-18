Version 1.1.2 (unreleased)
--------------

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
