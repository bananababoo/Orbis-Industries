package org.orbis.industries

import com.hypixel.hytale.component.Component
import com.hypixel.hytale.component.ComponentType
import com.hypixel.hytale.component.Ref
import com.hypixel.hytale.server.core.Message
import com.hypixel.hytale.server.core.entity.entities.Player
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore


fun String.message(): Message = Message.raw(this)

fun Player.executeOnWorldThread(task: Player.() -> Unit) {
    world!!.execute {
        task.invoke(this)
    }
}

fun <T : Component<EntityStore>> Ref<EntityStore>.getComponent(type: ComponentType<EntityStore, T>): T?{
    return store.getComponent<T>(this,type)
}