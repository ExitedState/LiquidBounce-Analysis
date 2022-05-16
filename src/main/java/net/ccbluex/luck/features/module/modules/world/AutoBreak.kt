/*
 * LiquidBounce Hacked Client
 * A free open source mixin-based injection hacked client for Minecraft using Minecraft Forge.
 * https://github.com/CCBlueX/LiquidBounce/
 */
package net.ccbluex.luck.features.module.modules.world

import net.ccbluex.luck.event.EventTarget
import net.ccbluex.luck.event.UpdateEvent
import net.ccbluex.luck.features.module.Module
import net.ccbluex.luck.features.module.ModuleCategory
import net.ccbluex.luck.features.module.ModuleInfo
import net.minecraft.init.Blocks

@ModuleInfo(name = "AutoBreak", description = "Automatically breaks the block you are looking at.", category = ModuleCategory.WORLD)
class AutoBreak : Module() {

    @EventTarget
    fun onUpdate(event: UpdateEvent) {
        if (mc.objectMouseOver == null || mc.objectMouseOver!!.blockPos == null || mc.theWorld == null)
            return

        mc.gameSettings.keyBindAttack.pressed = mc.theWorld.getBlockState(mc.objectMouseOver.blockPos).block != Blocks.air
    }

    override fun onDisable() {
        if (!mc.gameSettings.keyBindAttack.pressed)
            mc.gameSettings.keyBindAttack.pressed = false
    }
}
