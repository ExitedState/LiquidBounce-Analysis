/*
 * LiquidBounce Hacked Client
 * A free open source mixin-based injection hacked client for Minecraft using Minecraft Forge.
 * https://github.com/CCBlueX/LiquidBounce/
 */
package net.ccbluex.luck.features.module.modules.movement.speeds.aac

import net.ccbluex.luck.LiquidBounce
import net.ccbluex.luck.event.MoveEvent
import net.ccbluex.luck.features.module.modules.movement.Speed
import net.ccbluex.luck.features.module.modules.movement.speeds.SpeedMode
import net.ccbluex.luck.utils.MovementUtils
import net.minecraft.network.play.client.C03PacketPlayer

class AACGround : SpeedMode("AACGround") {
    override fun onUpdate() {
        if (!MovementUtils.isMoving)
            return

        mc.timer.timerSpeed = (LiquidBounce.moduleManager.getModule(Speed::class.java) as Speed?)!!.aacGroundTimerValue.get()
        mc.netHandler.addToSendQueue(
            C03PacketPlayer.C04PacketPlayerPosition(
                mc.thePlayer!!.posX,
                mc.thePlayer!!.posY,
                mc.thePlayer!!.posZ,
                true
            )
        )
    }

    override fun onMotion() {}
    override fun onMove(event: MoveEvent) {}
    override fun onDisable() {
        mc.timer.timerSpeed = 1f
    }
}