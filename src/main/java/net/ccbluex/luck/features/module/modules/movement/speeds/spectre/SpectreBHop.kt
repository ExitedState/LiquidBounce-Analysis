/*
 * LiquidBounce Hacked Client
 * A free open source mixin-based injection hacked client for Minecraft using Minecraft Forge.
 * https://github.com/CCBlueX/LiquidBounce/
 */
package net.ccbluex.luck.features.module.modules.movement.speeds.spectre

import net.ccbluex.luck.event.MoveEvent
import net.ccbluex.luck.features.module.modules.movement.speeds.SpeedMode
import net.ccbluex.luck.utils.MovementUtils

class SpectreBHop : SpeedMode("SpectreBHop") {
    override fun onMotion() {
        if (!MovementUtils.isMoving || mc.thePlayer!!.movementInput.jump) return
        if (mc.thePlayer!!.onGround) {
            MovementUtils.strafe(1.1f)
            mc.thePlayer!!.motionY = 0.44
            return
        }
        MovementUtils.strafe()
    }

    override fun onUpdate() {}
    override fun onMove(event: MoveEvent) {}
}