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

@ModuleInfo(name = "PerfectHorseJump", description = "Automatically jumps when the jump bar of a horse is filled up completely.", category = ModuleCategory.MOVEMENT)
class PerfectHorseJump : Module() {

    @EventTarget
    fun onUpdate(event: UpdateEvent) {
        val thePlayer = mc.thePlayer ?: return

        thePlayer.horseJumpPowerCounter = 9
        thePlayer.horseJumpPower = 1.0f
    }
}
