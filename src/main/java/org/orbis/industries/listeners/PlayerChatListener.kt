package org.orbis.industries.listeners

import org.orbis.industries.chat.ChatMessageFormatter
import com.hypixel.hytale.server.core.event.events.player.PlayerChatEvent

object PlayerChatListener {
    private val FORMATTER = ChatMessageFormatter()

    fun onPlayerChat(event: PlayerChatEvent) {
        event.formatter = FORMATTER
    }
}
