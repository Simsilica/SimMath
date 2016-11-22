Version 1.0.2 (unreleased)
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
