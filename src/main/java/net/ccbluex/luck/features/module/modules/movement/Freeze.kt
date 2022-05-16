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

@ModuleInfo(name = "Freeze", description = "Allows you to stay stuck in mid air.", category = ModuleCategory.MOVEMENT)
class Freeze : Module() {
    @EventTarget
    fun onUpdate(event: UpdateEvent) {
        val thePlayer = mc.thePlayer!!

        thePlayer.isDead = true
        thePlayer.rotationYaw = thePlayer.cameraYaw
        thePlayer.rotationPitch = thePlayer.cameraPitch
    }

    override fun onDisable() {
        mc.thePlayer?.isDead = false
    }
}
