# ⚠️ Deprecation notice ⚠️

## **As of version [0.3.20](https://modrinth.com/mod/embeddium/version/QmRSmFyB), Embeddium now comes with built-in support for Fabric Rendering APIs, eliminating the need for using Lazurite. Get the latest release of Embeddium [here](https://modrinth.com/mod/embeddium).**

---

<img src="src/main/resources/assets/lazurite/icon.png" width="128">

# Lazurite

[![GitHub Releases](https://img.shields.io/github/v/release/Su5eD/Lazurite?style=flat&label=Release&include_prereleases&sort=semver)](https://github.com/Su5eD/Lazurite/releases/latest)
[![CurseForge](https://cf.way2muchnoise.eu/title/lazurite.svg)](https://legacy.curseforge.com/minecraft/mc-mods/lazurite)
[![Modrinth](https://img.shields.io/modrinth/dt/TkC4Gtkt?color=00AF5C&label=modrinth&style=flat&logo=modrinth)](https://modrinth.com/mod/lazurite)
[![ForgifiedFabricAPI](https://raw.githubusercontent.com/Sinytra/.github/main/badges/forgified-fabric-api/compacter.svg)](https://github.com/Sinytra/ForgifiedFabricAPI)

Lazurite is a Forge port of [Indium](https://github.com/comp500/Indium), created to provide FRAPI support for Fabric
mods running on Forge via [Sinytra Connector](https://github.com/Sinytra/Connector), as well as fixing incompatibilities
with some mods.

It is an addon for the rendering optimisation mod [Embeddium](https://github.com/embeddedt/embeddium), providing
support for the Fabric Rendering API. The Fabric Rendering API is required for many mods that use advanced rendering
effects, and is currently not supported by Embeddium directly. Lazurite is based upon the reference implementation Indigo,
which is part of Fabric API with source available [here](https://github.com/FabricMC/fabric/tree/1.20.1/fabric-renderer-indigo).
(licensed Apache 2.0)

Lazurite is **not supported by Indium**. Please, **do not** report issues encountered with it to their bug tracker
or in their Discord. Use Lazurite's issue tracker for reporting bugs.

The [Forgified Fabric API](https://github.com/Sinytra/ForgifiedFabricAPI) is required to run Lazurite.

## Frequently Asked Questions

### Which mods require Lazurite?

Any mod that uses the Fabric Rendering API will require Lazurite when used with Embeddium. These include: Campanion, Bits and
Chisels, LambdaBetterGrass, Continuity, Packages, and many more. Some of these mods may function without an
implementation of the Fabric Rendering API, but have broken textures and models.

### Does Lazurite affect performance?

Lazurite's impact on performance should be negligible, however mods that use the Fabric Rendering API could themselves
impact performance. Lazurite will not provide a performance benefit over using only Embeddium.

## Downloads

Releases of Lazurite are available from [Modrinth](https://modrinth.com/mod/lazurite)
and [CurseForge](https://www.curseforge.com/minecraft/mc-mods/lazurite), as well
as [GitHub Releases](https://github.com/Su5eD/Lazurite/releases).

## Credits
- comp500 and contributors for creating Indium, making FRAPI support on Sodium possible
- embeddedt for their Forge port of Sodium

## License
Lazurite follows the license of upstream Indium, which is the [Apache-2.0](https://github.com/comp500/Indium/blob/1.20.x/main/LICENSE) license.
