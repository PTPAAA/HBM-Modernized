<!--
SPDX-FileCopyrightText: 2022-2024 MartinTheDragon <martin@ntmr.dev>
SPDX-License-Identifier: GFDL-1.3-or-later

Permission is granted to copy, distribute and/or modify this document under the terms of the GNU Free Documentation License, Version 1.3 or any later version published by the Free Software Foundation;
with no Invariant Sections, no Front-Cover Texts, and no Back-Cover Texts.
A copy of the license is included in the LICENSES directory of this project's root directory.
-->

## Kotlin

### Don't...

#### Self-reference with a backing field

When you need a subclass to implement a reference to itself (e.g. because of generics), make it an abstract function, not a property.

In testing, an equivalent of the following code broke sometimes because the reference became a **null pointer**, probably after garbage collection.

```kotlin
abstract class Base<T : Base> {
  protected abstract val self: T
}

class Sub : Base<Sub> {
  override val self = this
}
```

Instead, implement it as a function, which will always return a fresh pointer.

```kotlin
abstract class Base<T : Base> {
  protected abstract fun self(): T
}

class Sub : Base<Sub> {
  override fun self() = this
}
```
