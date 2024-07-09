![alt text][logo]

[logo]: rivelworks.png "RivelWorks Logo"
[![](https://jitpack.io/v/RivelBop/RivelWorks.svg)](https://jitpack.io/#RivelBop/RivelWorks)
# RivelWorks
RivelWorks is a cross-platform Java game engine based on [libGDX](https://libgdx.com/ "libGDX Website"), designed for cross-platform support for Windows, Linux, MacOS, Android, HTML, and IOS.
## Open Source
RivelWorks is released under the [MIT License](https://opensource.org/license/mit "MIT License Obligations"), offering unrestricted usage in both commercial and non-commercial projects. If you plan to build on top of the tools provided or want to use them to make a game, we appreciate any credit given to RivelWorks (credit is not mandatory).
## Features
Most of the features we provide within RivelWorks are simplified wrappers of various libGDX classes, allowing users to create games in a more efficient manner. Some of the features provided are built off of entirely different libraries.
 * 2D Animation Wrappers (Animated Sprites, Atlas Animations, etc.)
 * 2D Physics Wrappers (Static, Dynamic, Kinematic Bodies, Joints, etc.)
 * Tile Map Wrappers (Orthogonal and Isometric)
 * 3D 'Model' Wrappers (Models, Billboards, etc.)
 * 3D Particle Wrappers (Model, Billboard, and Point Particles)
 * 3D Shape Wrappers (Capsules, Cones, Cubes, Cylinders, Spheres, etc.)
 * 3D Physics Wrappers (Physics/Collision Worlds/Bodies)
 * 3D Model Support (OBJ, GLTF, FBX/G3DJ)
 * Post-Processing
 * Noise Generation
 * GamePad/Controller Support
 * Networking (Kryonet)
 * AssetManager Security Protocol
 * ProGuard Desktop Obfuscation
 * Occlusion Culling
 * Flexible Logging System
 * And More...(Font, Batch, Interpolation, and more Wrappers)
 * SteamWorks Integration (COMING SOON)
 ## Getting Started
Thankfully, libGDX handles most of the setup process for you, they offer a convenient [setup tool](https://libgdx.com/dev/#how-to-get-started-with-libgdx "libGDX Setup Page") that automates project creation. Once you have opened the setup tool, insert your project's properties. To import all the necessary libraries and the engine itself, implement our [JitPack](https://jitpack.io/#RivelBop/RivelWorks "JitPack website") library or check out our [build.gradle](build.gradle).

Optionally, you can simply just download the entire game engine and open it in an IDE, no setup process necessary!
## Documentation
All documentation is provided in the form of a [JavaDoc](javadoc/index.html "RivelWorks JavaDoc"), which provides all the descriptions necessary for each class/method.
## Credit
The following are sources from where some of the code of RivelWorks is derived:
* Framework: [libGDX](https://libgdx.com/ "libGDX Website")
* Noise Generation: [FastNoiseLite](https://github.com/Auburn/FastNoiseLite "FastNoiseLite Github")
* Camera Shaker: [libGDX-cameraShake](https://github.com/antzGames/libGDX-cameraShake "libGDX-cameraShake Github")
* Post Processing: [gdx-postprocessing](https://github.com/Anuken/gdx-postprocessing "gdx-postprocessing Github")
* Networking: [kryonet](https://github.com/crykn/kryonet "kryonet Github")
* Advanced Screen Manager: [libgdx-screenmanager](https://github.com/crykn/libgdx-screenmanager "libgdx-screenmanager Github")
* Advanced Audio: [TuningFork](https://github.com/Hangman/TuningFork "TuningFork Github")
* GLTF Support: [gdx-gltf](https://github.com/mgsx-dev/gdx-gltf "gdx-gltf Github")
* Obfuscation: [proguard](https://www.guardsquare.com/proguard "ProGuard Website")
* TexturePacker/StartupHelper: [libgdx-texturepacker](https://github.com/tommyettinger/libgdx-texturepacker "libgdx-texturepacker Github")
* More Post Processing: [gdx-vfx](https://github.com/crashinvaders/gdx-vfx "gdx-vfx Github")
