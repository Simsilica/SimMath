Version 1.0.2 (unreleased)
--------------
* Modified TransitionBuffer to have a 'clamp' on getTransition()
    that when 'true' will use the old behavior of returning the
    earliest/latest transition for out-of-range times.  For 'false',
    it will return null for out-of-range times.
* Modified TransitionBuffer to remove the System.out.println() for 
    out-of-range times.               

Version 1.0.1
--------------
* Initial public release with maven artifacts
