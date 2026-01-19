package org.orbis.industries

import com.hypixel.hytale.component.system.EcsEvent
import com.hypixel.hytale.server.core.event.events.ecs.UseBlockEvent
import org.orbis.industries.commands.TitleCommand
import org.orbis.industries.listeners.PlayerChatListener
import org.orbis.industries.listeners.PlayerReadyListener
import com.hypixel.hytale.server.core.event.events.player.PlayerChatEvent
import com.hypixel.hytale.server.core.event.events.player.PlayerReadyEvent
import com.hypixel.hytale.server.core.plugin.JavaPlugin
import com.hypixel.hytale.server.core.plugin.JavaPluginInit
import org.orbis.industries.systems.LockedDoorSystem
import javax.annotation.Nonnull

class OrbisIndustries(@Nonnull init: JavaPluginInit) : JavaPlugin(init) {
    override fun setup() {
        // Commands
        commandRegistry.registerCommand(TitleCommand())

        // Events
        eventRegistry.registerGlobal<String?, PlayerChatEvent?>(
            PlayerChatEvent::class.java
        ) { event: PlayerChatEvent? -> PlayerChatListener.onPlayerChat(event!!) }
        eventRegistry.registerGlobal<String?, PlayerReadyEvent?>(
            PlayerReadyEvent::class.java
        ) { event: PlayerReadyEvent? -> PlayerReadyListener.onPlayerReady(event!!) }

        entityStoreRegistry.registerSystem(LockedDoorSystem)
    }
}