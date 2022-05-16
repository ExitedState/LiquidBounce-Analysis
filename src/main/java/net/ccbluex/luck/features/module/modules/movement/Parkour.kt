/*
 * LiquidBounce Hacked Client
 * A free open source mixin-based injection hacked client for Minecraft using Minecraft Forge.
 * https://github.com/CCBlueX/LiquidBounce/
 */
package net.ccbluex.luck.features.module.modules.movement

import net.ccbluex.luck.event.EventTarget
import net.ccbluex.luck.event.UpdateEvent
import net.ccbluex.luck.features.module.Module
import net.ccbluex.luck.features.module.ModuleCategory
import net.ccbluex.luck.features.module.ModuleInfo
import net.ccbluex.luck.utils.MovementUtils

@ModuleInfo(name = "Parkour", description = "Automatically jumps when reaching the edge of a block.", category = ModuleCategory.MOVEMENT)
class Parkour : Module() {

    @EventTarget
    fun onUpdate(event: UpdateEvent) {
        val thePlayer = mc.thePlayer ?: return

        if (MovementUtils.isMoving && thePlayer.onGround && !thePlayer.isSneaking && !mc.gameSettings.keyBindSneak.isKeyDown && !mc.gameSettings.keyBindJump.isKeyDown &&
                mc.theWorld!!.getCollidingBoundingBoxes(thePlayer, thePlayer.entityBoundingBox
                        .offset(0.0, -0.5, 0.0).expand(-0.001, 0.0, -0.001)).isEmpty())
            thePlayer.jump()
    }
}
