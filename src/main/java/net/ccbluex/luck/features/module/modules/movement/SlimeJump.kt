/*
 * LiquidBounce Hacked Client
 * A free open source mixin-based injection hacked client for Minecraft using Minecraft Forge.
 * https://github.com/CCBlueX/LiquidBounce/
 */
package net.ccbluex.luck.features.module.modules.movement

import net.ccbluex.luck.event.EventTarget
import net.ccbluex.luck.event.JumpEvent
import net.ccbluex.luck.features.module.Module
import net.ccbluex.luck.features.module.ModuleCategory
import net.ccbluex.luck.features.module.ModuleInfo
import net.ccbluex.luck.utils.block.BlockUtils.getBlock
import net.ccbluex.luck.value.FloatValue
import net.ccbluex.luck.value.ListValue
import net.minecraft.block.BlockSlime
import java.util.*

@ModuleInfo(name = "SlimeJump", description = "Allows you to to jump higher on slime blocks.", category = ModuleCategory.MOVEMENT)
class SlimeJump : Module() {
    private val motionValue = FloatValue("Motion", 0.42f, 0.2f, 1f)
    private val modeValue = ListValue("Mode", arrayOf("Set", "Add"), "Add")

    @EventTarget
    fun onJump(event: JumpEvent) {
        val thePlayer = mc.thePlayer ?: return

        if (mc.thePlayer != null && mc.theWorld != null && getBlock(thePlayer.position.down()) is BlockSlime) {
            event.cancelEvent()

            when (modeValue.get().lowercase(Locale.getDefault())) {
                "set" -> thePlayer.motionY = motionValue.get().toDouble()
                "add" -> thePlayer.motionY += motionValue.get()
            }
        }
    }
}