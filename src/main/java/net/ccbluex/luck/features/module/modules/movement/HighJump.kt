/*
 * LiquidBounce Hacked Client
 * A free open source mixin-based injection hacked client for Minecraft using Minecraft Forge.
 * https://github.com/CCBlueX/LiquidBounce/
 */
package net.ccbluex.luck.features.module.modules.movement

import net.minecraft.util.BlockPos
import net.ccbluex.luck.event.EventTarget
import net.ccbluex.luck.event.JumpEvent
import net.ccbluex.luck.event.MoveEvent
import net.ccbluex.luck.event.UpdateEvent
import net.ccbluex.luck.features.module.Module
import net.ccbluex.luck.features.module.ModuleCategory
import net.ccbluex.luck.features.module.ModuleInfo
import net.ccbluex.luck.utils.MovementUtils
import net.ccbluex.luck.utils.block.BlockUtils.getBlock
import net.ccbluex.luck.value.BoolValue
import net.ccbluex.luck.value.FloatValue
import net.ccbluex.luck.value.ListValue
import net.minecraft.block.BlockPane
import java.util.*

@ModuleInfo(name = "HighJump", description = "Allows you to jump higher.", category = ModuleCategory.MOVEMENT)
class HighJump : Module() {
    private val heightValue = FloatValue("Height", 2f, 1.1f, 5f)
    private val modeValue = ListValue("Mode", arrayOf("Vanilla", "Damage", "AACv3", "DAC", "Mineplex"), "Vanilla")
    private val glassValue = BoolValue("OnlyGlassPane", false)

    @EventTarget
    fun onUpdate(event: UpdateEvent?) {
        val thePlayer = mc.thePlayer!!

        if (glassValue.get() && getBlock(BlockPos(thePlayer.posX, thePlayer.posY, thePlayer.posZ)) !is BlockPane)
            return

        when (modeValue.get().lowercase(Locale.getDefault())) {
            "damage" -> if (thePlayer.hurtTime > 0 && thePlayer.onGround) thePlayer.motionY += 0.42f * heightValue.get()
            "aacv3" -> if (!thePlayer.onGround) thePlayer.motionY += 0.059
            "dac" -> if (!thePlayer.onGround) thePlayer.motionY += 0.049999
            "mineplex" -> if (!thePlayer.onGround) MovementUtils.strafe(0.35f)
        }
    }

    @EventTarget
    fun onMove(event: MoveEvent?) {
        val thePlayer = mc.thePlayer ?: return

        if (glassValue.get() && getBlock(BlockPos(thePlayer.posX, thePlayer.posY, thePlayer.posZ)) !is BlockPane)
            return
        if (!thePlayer.onGround) {
            if ("mineplex" == modeValue.get().lowercase(Locale.getDefault())) {
                thePlayer.motionY += if (thePlayer.fallDistance == 0.0f) 0.0499 else 0.05
            }
        }
    }

    @EventTarget
    fun onJump(event: JumpEvent) {
        val thePlayer = mc.thePlayer ?: return

        if (glassValue.get() && getBlock(BlockPos(thePlayer.posX, thePlayer.posY, thePlayer.posZ)) !is BlockPane)
            return
        when (modeValue.get().lowercase(Locale.getDefault())) {
            "vanilla" -> event.motion = event.motion * heightValue.get()
            "mineplex" -> event.motion = 0.47f
        }
    }

    override val tag: String
        get() = modeValue.get()
}