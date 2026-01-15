<!--
SPDX-FileCopyrightText: 2023-2024 MartinTheDragon <martin@ntmr.dev>
SPDX-License-Identifier: GFDL-1.3-or-later

Permission is granted to copy, distribute and/or modify this document under the terms of the GNU Free Documentation License, Version 1.3 or any later version published by the Free Software Foundation;
with no Invariant Sections, no Front-Cover Texts, and no Back-Cover Texts.
A copy of the license is included in the LICENSES directory of this project's root directory.
-->

## Troubleshooting

This document should over time collect information about issues when using the project's source and how to resolve them.

### Gradle setup

- General weird Gradle error with dependencies

  Can happen after some dependency updated and an instance of the Gradle Daemon that was still running in the background got corrupted because of it.<br>
  It can also happen because you haven't yet rebuilt your run configurations after a dependency update.<br>
  To fix both those cases, first, open the terminal in the project folder and run `.\gradlew --stop` to kill the daemon. Then run `.\gradlew genIntellijRuns`.

- All or some dependencies aren't being resolved correctly in code

  That happens when indexes aren't built correctly by IntelliJ when a Gradle import happens.
  In the Gradle tab (on the right side by default), click on the refresh icon in the top left of the tool window. When finished, do `File > Invalidate Caches... > Invalidate and Restart`.<br>
  After IntelliJ loaded up again and refreshed the indexes, your issue should be fixed.

### Kotlin compiler

- [KT-52757](https://youtrack.jetbrains.com/issue/KT-52757/Type-inference-for-builders-fails-if-inferred-from-a-function)

  If you get a strange compile error with the message `Could not load module <Error module>`, that's a bug with Kotlin's type inference for builder-type lambdas.
  I encountered it when I made the codecs for the Meteorite world-gen features.
  Simply specify the type explicitly, for example by pressing Alt + Enter on the declaration in IntelliJ and using one of the suggested quick-fixes, and it'll be fixed.
  This was in Kotlin 1.8.0, might be fixed in future versions.
