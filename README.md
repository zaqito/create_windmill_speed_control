# Create : Windmill Speed Control

A Minecraft Create add-on that introduces a custom windmill bearing, allowing players to visually decouple a windmill's visual rotation speed from its actual mechanical stress output.

This project uses a multiloader architecture powered by Gradle. While currently targeting NeoForge, the codebase is structured to easily support additional platforms (like Fabric) in the future.

## Features & Mechanics
* **Visual Scaling:** Adds an alternate Windmill Bearing with side-controls to adjust visual speed (10% - 100%) and rotation direction.
* **Purely Cosmetic:** Modifies client-side animation rendering only. Mechanical calculations, generated rotational force, and Stress Unit (SU) production remain identical to standard Create behavior.
* **Network Synced:** Settings are fully synchronized between the server and connected clients.
* **Vanilla Friendly:** Preserves all existing Create contraption assembly mechanics without altering game progression or balance.

## Why?
In Create, the rotation speed of a windmill depends on the number of sails attached to the contraption.

Large and decorative windmills often reach the maximum rotational speed very quickly, resulting in blades spinning unrealistically fast. While this is mechanically correct, it can look odd for aesthetic builds.

`Create : Windmill Speed Control` solves this by adding a custom windmill bearing that allows players to reduce the visual rotation speed while preserving the original Create gameplay mechanics.

## Repository Structure
The project is split into a multi-project Gradle layout:

- `common/` – Contains core logic, rendering adjustments, and platform-agnostic code.
- `neoforge/` – NeoForge-specific implementation, configuration, and build logic.
- `forge/` - _empty for now, coming soon_
- `fabric/` - _empty for now, coming soon_

## Development & Building
This project is plug-and-play using standard Gradle wrappers.

## Prerequisites
* Java 21 JDK (or matching target Minecraft version requirements)

## Setup & Compilation
1. Clone the repository:
`git clone https://github.com/yourusername/create-windmill-speed-control.git`

2. Import the root build.gradle into your IDE (IntelliJ IDEA recommended) as a Gradle project.

3. Build the project jars:
`./gradlew build`

Compiled binaries for active platforms will be located in their respective `[platform]/build/libs/ directories`.

## User Installation

1. Install NeoForge 21 (for Minecraft 1.21.1)
2. Install Create [6.0.8, 6.0.10]
3. Place `create_windmill_speed_control-neoforge-1.2A.B-X.Y.Z` jar file in the `mods` folder
4. Launch the game

## Contributing

Contributions are welcome !

The project's primary objective is to improve windmill visuals while preserving Create's original mechanical balance.

Feel free to open an issue or submit a pull request whether you would like to :

* Report a bug
* Suggest an improvement
* Improve documentation
* Submit a fix or a new feature


Before opening a large feature PR, please consider discussing the proposal in an issue first so we can ensure it aligns with the project's goals.

Please try to keep contributions :

* Compatible with the supported Minecraft, NeoForge and Create versions
* Consistent with the existing code style
* Consistent with the project's primary objective
* Focused and well documented

## License

This project is licensed under GPLv3.

By contributing, you agree that your contributions will be distributed under the same license.
