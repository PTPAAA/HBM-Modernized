package dev.ntmr.nucleartech.content.block.entity

import dev.ntmr.nucleartech.content.menu.NTechContainerMenu

interface ContainerSyncableBlockEntity {
    fun trackContainerMenu(menu: NTechContainerMenu<*>)
}
