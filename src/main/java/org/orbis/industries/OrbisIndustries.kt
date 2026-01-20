package org.orbis.industries

import com.hypixel.hytale.server.core.HytaleServer
import com.hypixel.hytale.server.core.event.events.player.PlayerChatEvent
import com.hypixel.hytale.server.core.event.events.player.PlayerDisconnectEvent
import com.hypixel.hytale.server.core.event.events.player.PlayerReadyEvent
import com.hypixel.hytale.server.core.plugin.JavaPlugin
import com.hypixel.hytale.server.core.plugin.JavaPluginInit
import org.orbis.industries.commands.TitleCommand
import org.orbis.industries.listeners.PlayerChatListener
import org.orbis.industries.listeners.PlayerReadyListener
import org.orbis.industries.logging.Log
import org.orbis.industries.scoreboard.ScoreboardService
import org.orbis.industries.systems.LockedDoorSystem
import javax.annotation.Nonnull


class OrbisIndustries(@Nonnull init: JavaPluginInit) : JavaPlugin(init) {

    override fun setup() {
        // Commands
        commandRegistry.registerCommand(TitleCommand())

        // Events
        val bus = HytaleServer.get().eventBus
        bus.registerGlobal(PlayerChatEvent::class.java,PlayerChatListener::onPlayerChat)
        bus.registerGlobal(PlayerReadyEvent::class.java,PlayerReadyListener::onPlayerReady)

        bus.registerGlobal(PlayerReadyEvent::class.java,ScoreboardService::onPlayerReady)
        bus.registerGlobal(PlayerDisconnectEvent::class.java,ScoreboardService::onPlayerQuit)

        entityStoreRegistry.registerSystem(LockedDoorSystem)

        ScoreboardService.start()
        Log.info(Log.Type.SCOREBOARD,"Started")

    }
}