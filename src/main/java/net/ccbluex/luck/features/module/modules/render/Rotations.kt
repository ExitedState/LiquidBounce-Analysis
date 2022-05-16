/*
 * LiquidBounce Hacked Client
 * A free open source mixin-based injection hacked client for Minecraft using Minecraft Forge.
 * https://github.com/CCBlueX/LiquidBounce/
 */
package net.ccbluex.luck.features.module.modules.render

import net.ccbluex.luck.LiquidBounce
import net.ccbluex.luck.event.EventTarget
import net.ccbluex.luck.event.PacketEvent
import net.ccbluex.luck.event.Render3DEvent
import net.ccbluex.luck.features.module.Module
import net.ccbluex.luck.features.module.ModuleCategory
import net.ccbluex.luck.features.module.ModuleInfo
import net.ccbluex.luck.features.module.modules.`fun`.Derp
import net.ccbluex.luck.features.module.modules.combat.BowAimbot
import net.ccbluex.luck.features.module.modules.combat.KillAura
import net.ccbluex.luck.features.module.modules.world.*
import net.ccbluex.luck.utils.RotationUtils
import net.ccbluex.luck.value.BoolValue
import net.minecraft.network.play.client.C03PacketPlayer

@ModuleInfo(name = "Rotations", description = "Allows you to see server-sided head and body rotations.", category = ModuleCategory.RENDER)
class Rotations : Module() {

    private val bodyValue = BoolValue("Body", true)

    private var playerYaw: Float? = null

    @EventTarget
    fun onRender3D(event: Render3DEvent) {
        if (RotationUtils.serverRotation != null && !bodyValue.get())
            mc.thePlayer?.rotationYawHead = RotationUtils.serverRotation.yaw
    }

    @EventTarget
    fun onPacket(event: PacketEvent) {
        val thePlayer = mc.thePlayer

        if (!bodyValue.get() || !shouldRotate() || thePlayer == null)
            return

        val packet = event.packet

        if (packet is C03PacketPlayer.C06PacketPlayerPosLook || packet is C03PacketPlayer.C05PacketPlayerLook) {
            playerYaw = (packet as C03PacketPlayer).yaw
            mc.thePlayer.renderYawOffset = packet.getYaw()
            mc.thePlayer.rotationYawHead = packet.getYaw()
        } else {
            if (playerYaw != null)
                thePlayer.renderYawOffset = this.playerYaw!!

            thePlayer.rotationYawHead = thePlayer.renderYawOffset
        }
    }

    private fun getState(module: Class<*>) = LiquidBounce.moduleManager[module]!!.state

    private fun shouldRotate(): Boolean {
        val killAura = LiquidBounce.moduleManager.getModule(KillAura::class.java) as KillAura
        return getState(Scaffold::class.java) || getState(Tower::class.java) ||
                (getState(KillAura::class.java) && killAura.target != null) ||
                getState(Derp::class.java) || getState(BowAimbot::class.java) ||
                getState(Fucker::class.java) || getState(CivBreak::class.java) || getState(Nuker::class.java) ||
                getState(ChestAura::class.java)
    }
}
