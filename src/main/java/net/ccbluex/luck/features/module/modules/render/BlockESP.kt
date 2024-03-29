/*
 * LiquidBounce Hacked Client
 * A free open source mixin-based injection hacked client for Minecraft using Minecraft Forge.
 * https://github.com/CCBlueX/LiquidBounce/
 */
package net.ccbluex.luck.features.module.modules.render

import net.ccbluex.luck.event.EventTarget
import net.ccbluex.luck.event.Render3DEvent
import net.ccbluex.luck.event.UpdateEvent
import net.ccbluex.luck.features.module.Module
import net.ccbluex.luck.features.module.ModuleCategory
import net.ccbluex.luck.features.module.ModuleInfo
import net.ccbluex.luck.utils.block.BlockUtils.getBlock
import net.ccbluex.luck.utils.block.BlockUtils.getBlockName
import net.ccbluex.luck.utils.render.ColorUtils.rainbow
import net.ccbluex.luck.utils.render.RenderUtils
import net.ccbluex.luck.utils.timer.MSTimer
import net.ccbluex.luck.value.BlockValue
import net.ccbluex.luck.value.BoolValue
import net.ccbluex.luck.value.IntegerValue
import net.ccbluex.luck.value.ListValue
import net.minecraft.block.Block
import net.minecraft.init.Blocks
import net.minecraft.util.BlockPos
import java.awt.Color
import java.util.*
import kotlin.collections.ArrayList

@ModuleInfo(name = "BlockESP", description = "Allows you to see a selected block through walls.", category = ModuleCategory.RENDER)
class BlockESP : Module() {
    private val modeValue = ListValue("Mode", arrayOf("Box", "2D"), "Box")
    private val blockValue = BlockValue("Block", 168)
    private val radiusValue = IntegerValue("Radius", 40, 5, 120)
    private val blockLimitValue = IntegerValue("BlockLimit", 256, 0, 2056)
    private val colorRedValue = IntegerValue("R", 255, 0, 255)
    private val colorGreenValue = IntegerValue("G", 179, 0, 255)
    private val colorBlueValue = IntegerValue("B", 72, 0, 255)
    private val colorRainbow = BoolValue("Rainbow", false)
    private val searchTimer = MSTimer()
    private val posList: MutableList<BlockPos> = ArrayList()
    private var thread: Thread? = null

    @EventTarget
    fun onUpdate(event: UpdateEvent?) {
        if (searchTimer.hasTimePassed(1000L) && (thread == null || !thread!!.isAlive)) {
            val radius = radiusValue.get()
            val selectedBlock = Block.getBlockById(blockValue.get());

            if (selectedBlock == null || selectedBlock == Blocks.air)
                return;

            thread = Thread(Runnable {
                val blockList: MutableList<BlockPos> = ArrayList()

                for (x in -radius until radius) {
                    for (y in radius downTo -radius + 1) {
                        for (z in -radius until radius) {
                            val thePlayer = mc.thePlayer!!

                            val xPos = thePlayer.posX.toInt() + x
                            val yPos = thePlayer.posY.toInt() + y
                            val zPos = thePlayer.posZ.toInt() + z

                            val blockPos = BlockPos(xPos, yPos, zPos)
                            val block = getBlock(blockPos)

                            if (block == selectedBlock && blockList.size < blockLimitValue.get()) blockList.add(blockPos)
                        }
                    }
                }
                searchTimer.reset()

                synchronized(posList) {
                    posList.clear()
                    posList.addAll(blockList)
                }
            }, "BlockESP-BlockFinder")

            thread!!.start()
        }
    }

    @EventTarget
    fun onRender3D(event: Render3DEvent?) {
        synchronized(posList) {
            val color = if (colorRainbow.get()) rainbow() else Color(colorRedValue.get(), colorGreenValue.get(), colorBlueValue.get())
            for (blockPos in posList) {
                when (modeValue.get().lowercase(Locale.getDefault())) {
                    "box" -> RenderUtils.drawBlockBox(blockPos, color, true)
                    "2d" -> RenderUtils.draw2D(blockPos, color.rgb, Color.BLACK.rgb)
                }
            }
        }
    }

    override val tag: String
        get() = getBlockName(blockValue.get())
}