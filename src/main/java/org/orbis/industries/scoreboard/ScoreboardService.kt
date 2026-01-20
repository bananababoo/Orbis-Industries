package org.orbis.industries.scoreboard

import com.hypixel.hytale.server.core.entity.entities.Player
import com.hypixel.hytale.server.core.event.events.player.PlayerDisconnectEvent
import com.hypixel.hytale.server.core.event.events.player.PlayerReadyEvent
import com.hypixel.hytale.server.core.universe.PlayerRef
import org.orbis.industries.executeOnWorldThread
import org.orbis.industries.getComponent
import org.orbis.industries.logging.Log
import java.util.*
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.TimeUnit

object ScoreboardService {

    val huds: ConcurrentHashMap<UUID, TrackedHud> = ConcurrentHashMap()
    lateinit var refresher: ScheduledExecutorService

    fun start() {
        refresher = Executors.newSingleThreadScheduledExecutor { runnable ->
            val thread = Thread(runnable, "Industries-ScoreboardRefresher")
            thread.isDaemon = true
            thread
        }
        Log.info(Log.Type.SCOREBOARD, "starting up")
        refresher.scheduleAtFixedRate({ refreshAll() }, 1, 1, TimeUnit.SECONDS)
    }

    fun refreshAll(){
        val hudEntries = huds.entries.iterator()
        while(hudEntries.hasNext()){
            val entry = hudEntries.next()
            refreshSingle(entry.key)
        }
    }

    fun refreshSingle(id: UUID){
        val hudTracker = huds[id]
        if(hudTracker != null){
            if(!hudTracker.player.wasRemoved()) {
                hudTracker.player.executeOnWorldThread {
                    hudTracker.hud.refresh(hudTracker.player)
                }
            } else {
                huds.remove(id)
            }
        }
    }

    fun onPlayerReady(event: PlayerReadyEvent){
        val player = event.player
        player.executeOnWorldThread {
            val playerRef = event.playerRef.getComponent(PlayerRef.getComponentType())!!
            huds[playerRef.uuid] = TrackedHud(player, ScoreboardHUD(playerRef))
            refreshSingle(playerRef.uuid)
        }
    }

    fun onPlayerQuit(event: PlayerDisconnectEvent){
        huds.remove(event.playerRef.uuid)
    }

}

data class TrackedHud(val player: Player, val hud: ScoreboardHUD)