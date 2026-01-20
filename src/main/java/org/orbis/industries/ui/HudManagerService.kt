package org.orbis.industries.ui

import com.hypixel.hytale.server.core.entity.entities.Player
import com.hypixel.hytale.server.core.entity.entities.player.hud.CustomUIHud
import com.hypixel.hytale.server.core.universe.PlayerRef

object HudManagerService {
    fun setCustomHud(player: Player, playerRef: PlayerRef, hudIdentifier: String, customHud: CustomUIHud) {
        val currentCustomHud = player.hudManager.customHud
        if (currentCustomHud is MultipleCustomUIHud) {
            currentCustomHud.customHuds[hudIdentifier] = customHud
            player.hudManager.setCustomHud(playerRef, currentCustomHud)
            currentCustomHud.show()
        } else {
            val huds: HashMap<String?, CustomUIHud?> = hashMapOf()
            huds[hudIdentifier] = customHud
            if (currentCustomHud != null) {
                huds["Unknown"] = currentCustomHud
            }

            player.hudManager.setCustomHud(playerRef, MultipleCustomUIHud(playerRef, huds))
        }
    }

    fun hideCustomHud(player: Player, playerRef: PlayerRef, hudIdentifier: String) {
        val currentCustomHud = player.hudManager.customHud
        if (currentCustomHud is MultipleCustomUIHud) {
            currentCustomHud.customHuds.remove(hudIdentifier)
            player.hudManager.setCustomHud(playerRef, currentCustomHud)
            currentCustomHud.show()
        }
    }
}