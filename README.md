# Create - Windmill Speed Control

A small Create add-on that lets players adjust the **rotation speed** of windmills **without** affecting their mechanical output.

## Quick Facts

✔ Cosmetic only – does not affect SU production

✔ Multiplayer compatible

✔ Dedicated server compatible

✔ Existing Create mechanics preserved

✔ NeoForge 1.21.1

✔ Create 6.0.10

## Why?

In Create, the rotation speed of a windmill depends on the number of sails attached to the contraption.

Large and decorative windmills often reach the maximum rotational speed very quickly, resulting in blades spinning unrealistically fast. While this is mechanically correct, it can look odd for aesthetic builds.

`Create - Windmill Speed Control` solves this by adding a custom windmill bearing that allows players to reduce the visual rotation speed while preserving the original Create gameplay mechanics.

## Features

* Custom windmill bearing based on Create's vanilla windmill bearing
* Adjustable visual speed factor (10% - 100%)
* Independent rotation direction control
* No impact on generated Stress Units (SU)
* No impact on rotational power production
* Fully compatible with standard Create windmill contraptions
* Multiplayer compatible

## How It Works

**The custom bearing behaves exactly like a normal Create windmill bearing.**

Additional controls are available on the side of the block:

* **Rotation Direction**: clockwise / counter-clockwise
* **Visual Speed Factor**: controls how fast the windmill appears to rotate

Only the visual animation speed is modified.

Mechanical calculations, generated rotational force, and Stress Units remain unchanged.

## Multiplayer Compatibility

`Create - Windmill Speed Control` is fully compatible with multiplayer environments.

The visual speed factor and rotation direction settings are synchronized between clients and the server, ensuring that all players see the same windmill behavior.

Tested in:

* Singleplayer
* Dedicated NeoForge servers
* Multiplayer client synchronization
* World save and reload scenarios

Both the server and connected clients should have the mod installed.

## Requirements

* Minecraft 1.21.1
* NeoForge
* Create 6.0.10

## Compatibility

This mod is purely cosmetic and does not alter Create's progression or balance.

Existing Create contraptions continue to work normally.

## Installation

1. Install NeoForge for Minecraft 1.21.1
2. Install Create 6.0.10
3. Place Create - Windmill Speed Control jar file in the `mods` folder
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
