package org.orbis.industries.scoreboard

import com.hypixel.hytale.server.core.entity.entities.Player
import com.hypixel.hytale.server.core.entity.entities.player.hud.CustomUIHud
import com.hypixel.hytale.server.core.ui.builder.UICommandBuilder
import com.hypixel.hytale.server.core.universe.PlayerRef
import org.orbis.industries.ui.HudManagerService

class ScoreboardHUD(playerRef: PlayerRef): CustomUIHud(playerRef) {

    override fun build(builder: UICommandBuilder) {
        builder.append("Pages/VictoryPointScoreboard.ui")
        builder.set("#Team1VictoryPoints.Text", "3")
        builder.set("#Team2VictoryPoints.Text", "5")
    }

    fun refresh(player: Player){
        HudManagerService.setCustomHud(player, playerRef, "BetterScoreBoard", this)
    }

    fun hide(player: Player){
        HudManagerService.hideCustomHud(player, playerRef, "BetterScoreBoard")
    }

}