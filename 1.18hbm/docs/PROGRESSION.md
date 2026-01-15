<!--
SPDX-FileCopyrightText: 2022-2024 MartinTheDragon <martin@ntmr.dev>
SPDX-License-Identifier: GFDL-1.3-or-later

Permission is granted to copy, distribute and/or modify this document under the terms of the GNU Free Documentation License, Version 1.3 or any later version published by the Free Software Foundation;
with no Invariant Sections, no Front-Cover Texts, and no Back-Cover Texts.
A copy of the license is included in the LICENSES directory of this project's root directory.
-->

## NTM Progression

If the lines are too complicated for you, just read the **rectangular** boxes from left to right. They're in order.

### Progression Part 1: Assembler

```mermaid
flowchart LR
  anvil1[Anvil Tier 1] & press[Press]
  press & anvil1 --> difurnace[Blast Furnace]
  difurnace --> steel[/Steel\] & redcopper[/Red Copper\]
  redcopper & steel --> combustiongen[Combustion Generator] -.-> energy[(Energy)]
  steel & anvil1 --> anvil2[Anvil Tier 2]
  steel & press --> circuits[/Basic Circuit/]
  assembler{Assembler}
  energy -. Most machines will<br>need energy<br>from this point on .-> assembler
  redcopper ---> assembler
  steel --> assembler
  circuits <--> assembler
  anvil2 --> assembler
```

Status: Implemented

### Progression Part 2: Chemical Plant

```mermaid
flowchart LR
  assembler[Assembler]
  steel[/Steel\]
  redcopper[/Red Copper\]
  circuits1[/Basic Circuit/]
  circuits2[/Enhanced Circuit/]
  circuits3[/Advanced Circuit/]
  shredder[Shredder]
  redcopper & steel <--> shredder
  shredder -.-> oredupe([Ore Duplication])
  shredder --> circuits3 & circuits2
  redcopper --> circuits3
  circuits1 ---> circuits2 --> circuits3
  assembler & steel & circuits2 & circuits3 --> chemplant{Chemical Plant}
  assembler --> shredder
```
Note: Assembler omitted sometimes

Status: Implemented

### General Machine Progression

Consecutive machines take previous ones as implied dependencies.

```mermaid
flowchart TB
  Press & Anvil1[Anvil Tier 1] ==> DiFurnace[Blast Furnace]
  Anvil1 & Press -.-> EFurnace[Electric Furnace]
  DiFurnace --> Cables
  DiFurnace ==> Anvil2[Anvil Tier 2]
  DiFurnace -.-> CombustionGen[Combustion Generator]
  CombustionGen -.-> Energy[(Energy)] --> Assembler & EFurnace
  Anvil2 ==> Assembler ==> Shredder ==> ChemPlant[Chemical Plant]
  Shredder -.-> Siren
```
