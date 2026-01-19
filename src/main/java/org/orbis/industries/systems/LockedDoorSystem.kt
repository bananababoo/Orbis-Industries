package org.orbis.industries.systems

import com.hypixel.hytale.component.Archetype
import com.hypixel.hytale.component.ArchetypeChunk
import com.hypixel.hytale.component.CommandBuffer
import com.hypixel.hytale.component.Store
import com.hypixel.hytale.component.query.Query
import com.hypixel.hytale.component.system.EntityEventSystem
import com.hypixel.hytale.server.core.Message
import com.hypixel.hytale.server.core.entity.entities.Player
import com.hypixel.hytale.server.core.event.events.ecs.UseBlockEvent
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore
import org.orbis.industries.message

object LockedDoorSystem : EntityEventSystem<EntityStore, UseBlockEvent.Pre>(UseBlockEvent.Pre::class.java) {

    override fun handle(
        index: Int,
        archetypeChunk: ArchetypeChunk<EntityStore?>,
        store: Store<EntityStore?>,
        commandBuffer: CommandBuffer<EntityStore?>,
        event: UseBlockEvent.Pre
    ) {
        val player = store.getComponent(event.context.entity, Player.getComponentType())

        if(player != null && event.blockType.id.startsWith("*Door_Unlock")){
            val handItem = player.inventory.itemInHand
            val doorId = event.blockType.id.substringAfter("*Door_Unlock").substringBefore("_")
            player.sendMessage(event.blockType.id.message())
            val isOpen = event.blockType.id.substringAfter("State_Definitions_").substringBefore("Door") == "Open"
            if(handItem?.itemId?.equals("Door_Key$doorId") == true && !isOpen) {
                player.sendMessage("You clicked a door! State: $isOpen".message())
                val newItem = handItem.withQuantity(handItem.quantity - 1)
                player.inventory.hotbar.setItemStackForSlot(player.inventory.activeHotbarSlot.toShort(),newItem)
            } else {
                event.isCancelled = true
            }
        }
    }

    override fun getQuery(): Query<EntityStore?>? {
        return Archetype.empty()
    }
}