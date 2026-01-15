<!--
SPDX-FileCopyrightText: 2019-2024 MartinTheDragon <martin@ntmr.dev>
SPDX-License-Identifier: GFDL-1.3-or-later

Permission is granted to copy, distribute and/or modify this document under the terms of the GNU Free Documentation License, Version 1.3 or any later version published by the Free Software Foundation;
with no Invariant Sections, no Front-Cover Texts, and no Back-Cover Texts.
A copy of the license is included in the LICENSES directory of this project's root directory.
-->

# Nuclear Tech Mod

**This is a full rewrite of the Nuclear Tech Mod [originally created by HbmMods](https://github.com/HbmMods/Hbm-s-Nuclear-Tech-GIT).**
It is currently being developed for several Minecraft versions.
Everything is still subject to change, even the speed at which blocks get mined. This mod is not ready for general use yet and everything is work in progress.

## Development Builds

But you can still test the current state of the mod by either [building the mod yourself](#compilation-instructions), or downloading one of the [occasional releases published on Codeberg](https://codeberg.org/MartinTheDragon/Nuclear-Tech-Mod-Remake/releases). Finding and reporting bugs is encouraged.

## Contact

There are several communication options like Matrix, Discord or email available.
For more details and information, please look at the [CONTACT.md](CONTACT.md) file.

## Credits

For a (non-exhaustive) list of who worked on this project and the original, look at the [credits file](CREDITS.md).

## Contributing

Want to report a bug? Translate the mod? Make code contributions? We are interested! The [CONTRIBUTING.md](CONTRIBUTING.md) file contains the information for you.

## Compilation Instructions

If you want to be on the razor's edge of things instead of just cutting edge, you can also compile your own versions of the mod.

1. Fetch the source code

   For people who don't have a Git client:

   1. [Download the source code](https://codeberg.org/MartinTheDragon/Nuclear-Tech-Mod-Remake/archive/refs/heads/1.18.zip)
   2. Extract the ZIP-archive

2. Open a terminal interface or [command line](https://www.freecodecamp.org/news/command-line-commands-cli-tutorial/) inside the root directory of the project
3. By default, this project uses JDK auto-provisioning, meaning any necessary JDKs will be downloaded automatically. Should this not work for you, simply install the necessary JDKs manually.
4. Enter the following command to compile the project:

   ```bash
   # On Windows, you may have to use a \ instead of a /
   ./gradlew build
   ```

   **This project supports reproducible builds.** If you received the mod from a 3rd party, you should use this feature to at least partially verify whether they can be trusted. See
   [the section about reproducible builds](#reproducible-builds) on how to do that.

5. Wait for compilation to finish (may take up to a few dozen minutes, heavily depending on internet speed)
6. You may now find freshly-compiled snapshot jars under `targets/<version>/build/libs` (only the non-slim and non-API version works directly as a mod).
7. Install the jar like any other mod

### Reproducible builds

File checksums are provided with each official release, and everyone that wishes to distribute the mod or their own modified versions of it should do so too. If you cannot find any
checksum, do not trust the mod jar file and [create a checksum yourself](#creating-checksums-and-verifying-3rd-party-distributions). More information on that and how to proceed below.

To make a build reproducible, you have to first **ensure that you are indeed compiling the source code of the release**, and not in-development commits that haven't been released yet.
Codeberg gives you the option to download the Git tagged source code of each release on the releases page.
Then you have to add the `release` property as follows:

   ```bash
   ./gradlew build -Prelease
   ```

This command should output jar files **matching exactly** with the published releases, no matter the operating system, time, or 3rd-party or not. If they don't match, **do not trust the distributor**
and send an inquiry to them about it.

### Creating Checksums and Verifying 3rd-party Distributions

A checksum can be described as the fingerprint of a file. Exact copies of a file will all have the same checksum, while minor deviations will lead to an entirely different checksum. It takes only
a byte to be different or out of order for checksums to not match. Therefore, they're great for ensuring file integrity, as network transmissions may sometimes corrupt files. Furthermore, if you
know the file's checksum from a trusted source, then you can verify other copies of the file and find out whether they have been compromised and altered to include malware.

In the case of reproducible builds ([as described above](#compilation-instructions)), that trusted source can and should be you, as building the mod from source must result in byte-to-byte matching outputs. Since you may
be getting the mod from a 3rd party that has modified the source code ([which is allowed through the license](#license)), ensuring that they aren't lying to you about file checksums is important. If
their distributed file doesn't match with your own output of the compiled source code, they might have slipped in some malicious code, and are furthermore probably in violation of the license.

Do note though that matching checksums don't necessarily mean the 3rd-party modification of the mod isn't containing any malware. It only means that they didn't lie to you about the source code's output.
You still have to check the source code for their changes and see if something's off.

#### Windows

On Windows, you can use the following command in a terminal to generate a SHA256 checksum (of course replacing *mod.jar* with the actual file name):

```shell
certutil -hashfile "mod.jar" SHA256
```

If you aren't provided with a SHA256 hash, there are other hash algorithms available, such as MD5 or SHA1. Simply replace SHA256 in the command above with another option.
In case you aren't comfortable using the command line, you can search the internet for more options.

#### Linux

Linux usually comes with a command for each hash algorithm, in the pattern `<alg>sum`.
In order to generate a SHA256 checksum, use the following command, replacing *mod.jar* with the actual file name:

```bash
sha256sum mod.jar
```

## License

Every file comes with its own SPDX license identifier, however most of the code files are licensed under the GPLv3 with the following additional terms under section 7.

Copyright (C) 2019-2025 MartinTheDragon, HbmMods (The Bobcat) and contributors

This modification of Minecraft, hereby referred to as "Mod", is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation; either version 3 of the License, or (at your option) any later version.

This Mod is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.

You should have received a copy of the GNU General Public License along with this Mod; if not, see <https://www.gnu.org/licenses>.

*Additional permission under GNU GPL version 3 section 7*

If you modify this Mod, or any covered work, by linking or combining it with Minecraft (or a modified version of Minecraft), containing parts covered by the terms of the Minecraft EULA, the licensors of this Mod grant you additional permission to convey the resulting work.

Make sure usages and modifications comply with the license, and with the [Minecraft EULA](https://account.mojang.com/documents/minecraft_eula).

## Privacy

The mod won't spy on you or collect any personal data. That would be unethical (yes I'm looking at you, Microsoft).
However, it uses the Forge version checker, which fetches a JSON file with update information from <https://www.ntmr.dev/raw/update.json>.
Currently, this file is hosted on the self-hosted server at <https://ntmr.dev>, which doesn't really do anything with request information other than for the purposes of protection from abuse and spam.
If you still wish to not use that service for the sake of your privacy, you are free to modify the mod as per license by removing the line beginning with `updateJSONURL` in the `mods.toml` (or version-equivalent) files in each target directory and compiling your own version (Forge and lots of other mods also uses that version checker system by the way).
