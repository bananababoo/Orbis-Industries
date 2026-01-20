package org.orbis.industries.ui

import com.hypixel.hytale.server.core.entity.entities.player.hud.CustomUIHud
import com.hypixel.hytale.server.core.ui.builder.UICommandBuilder
import com.hypixel.hytale.server.core.universe.PlayerRef
import java.lang.reflect.InvocationTargetException
import java.lang.reflect.Method

class MultipleCustomUIHud(playerRef: PlayerRef, val customHuds: HashMap<String?, CustomUIHud?>) :
    CustomUIHud(playerRef) {
    override fun build(uiCommandBuilder: UICommandBuilder) {
        for (key in this.customHuds.keys) {
            val hud = this.customHuds.get(key)

            try {
                if (BUILD_METHOD != null) {
                    BUILD_METHOD!!.invoke(hud, uiCommandBuilder)
                }
            } catch (e: InvocationTargetException) {
                throw RuntimeException(e)
            } catch (e: IllegalAccessException) {
                throw RuntimeException(e)
            }
        }
    }

    companion object {
        private var BUILD_METHOD: Method? = null

        init {
            try {
                BUILD_METHOD = CustomUIHud::class.java.getDeclaredMethod("build", UICommandBuilder::class.java)
                BUILD_METHOD!!.setAccessible(true)
            } catch (_: NoSuchMethodException) {
                BUILD_METHOD = null
            }
        }
    }
}