/*
 * LiquidBounce Hacked Client
 * A free open source mixin-based injection hacked client for Minecraft using Minecraft Forge.
 * https://github.com/CCBlueX/LiquidBounce/
 */
package net.ccbluex.luck.features.module.modules.render

import net.ccbluex.luck.event.EventTarget
import net.ccbluex.luck.event.UpdateEvent
import net.ccbluex.luck.features.module.Module
import net.ccbluex.luck.features.module.ModuleCategory
import net.ccbluex.luck.features.module.ModuleInfo

@ModuleInfo(name = "NoBob", description = "Disables the view bobbing effect.", category = ModuleCategory.RENDER)
class NoBob : Module() {

    @EventTarget
    fun onUpdate(event: UpdateEvent) {
        mc.thePlayer?.distanceWalkedModified = 0f
    }
}
