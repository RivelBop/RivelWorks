![alt text][logo]

[logo]: rivelworks.png "RivelWorks Logo"
# RivelWorks
RivelWorks is a cross-platform Java game engine based on [libGDX](https://libgdx.com/ "libGDX Website"), designed for cross-platform support for Windows, Linux, MacOS, Android, HTML, and IOS.
## Open Source
RivelWorks is released under the [MIT License](https://opensource.org/license/mit "MIT License Obligations"), offering unrestricted usage in both commercial and non-commercial projects. If you plan to build on top of the tools provided or want to use them to make a game, we appreciate any credit given to RivelWorks (credit is not mandatory).
## Features
Most of the features we provide within RivelWorks are simplified wrappers of various libGDX classes, allowing users to create games in a more efficient manner. Some of the features provided are built off of entirely different libraries (WIP, future updates).
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
 * And More...(Font, Batch, Interpolation, and more Wrappers)
 * SteamWorks Integration (COMING SOON)
 ## Getting Started
Thankfully, libGDX handles most of the setup process for you, without the need to download the framework for yourself. They offer a convenient [setup tool](https://libgdx.com/dev/#how-to-get-started-with-libgdx "libGDX Setup Page") that automates most of the project creation process. Once you have opened the setup tool, insert your project's properties and ensure you have ALL OF THE OFFICIAL EXTENSIONS SELECTED to ensure that RivelWorks will have all the libraries necessary to function. For the additional libraries, check out our [build.gradle](build.gradle).
 
Once your project is created, download the latest [release](https://github.com/RivelBop/RivelWorks/releases) of RivelWorks, extract the source code, head into the "core/src/" directory of the source code, copy the folder starting with "com", head into the "core/src" directory of your newly generated project, and paste the copied folder.

Optionally, you can simply just download the entire game engine and build your project off of it, no setup process necessary!
 
Once the steps above are completed, simply import your project into the IDE of your choosing and enjoy!
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
