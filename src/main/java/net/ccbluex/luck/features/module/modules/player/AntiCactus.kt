/*
 * LiquidBounce Hacked Client
 * A free open source mixin-based injection hacked client for Minecraft using Minecraft Forge.
 * https://github.com/CCBlueX/LiquidBounce/
 */
package net.ccbluex.luck.features.module.modules.player

import net.ccbluex.luck.event.BlockBBEvent
import net.ccbluex.luck.event.EventTarget
import net.ccbluex.luck.features.module.Module
import net.ccbluex.luck.features.module.ModuleCategory
import net.ccbluex.luck.features.module.ModuleInfo
import net.minecraft.block.BlockCactus
import net.minecraft.util.AxisAlignedBB

@ModuleInfo(name = "AntiCactus", description = "Prevents cactuses from damaging you.", category = ModuleCategory.PLAYER)
class AntiCactus : Module() {

    @EventTarget
    fun onBlockBB(event: BlockBBEvent) {
        if (event.block is BlockCactus)
            event.boundingBox = AxisAlignedBB(event.x.toDouble(), event.y.toDouble(), event.z.toDouble(),
                event.x + 1.0, event.y + 1.0, event.z + 1.0)
    }
}
