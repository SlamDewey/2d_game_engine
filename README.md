## A simple 2D Game Engine written in Java
#### using lwjgl3, and custom physics code

###### A quick rundown of the project structure:
The **src** directory stores the original .java files, and is relatively well organized, but poorly commented.

The **lib** directory stores the dependant .jar files included from LWJGL3.  These dependancies are what gives my java program access to OpenGL programming, and therefore access to a functional graphics engine.  The graphics engine itself was based on the graphics programming tutorial series by ThinMatrix, although adapted as the series only used LWJGL2, and LWJGL3 gives access to GLFW.

The **bin** directory stores the complied binaries of these .java files, and the included .jar files from LWJGL3.
