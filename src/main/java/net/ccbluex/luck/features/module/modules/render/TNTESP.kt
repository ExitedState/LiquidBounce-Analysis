/*
 * LiquidBounce Hacked Client
 * A free open source mixin-based injection hacked client for Minecraft using Minecraft Forge.
 * https://github.com/CCBlueX/LiquidBounce/
 */
package net.ccbluex.luck.features.module.modules.render

import net.ccbluex.luck.event.EventTarget
import net.ccbluex.luck.event.Render3DEvent
import net.ccbluex.luck.features.module.Module
import net.ccbluex.luck.features.module.ModuleCategory
import net.ccbluex.luck.features.module.ModuleInfo
import net.ccbluex.luck.utils.render.RenderUtils
import net.minecraft.entity.item.EntityTNTPrimed
import java.awt.Color

@ModuleInfo(name = "TNTESP", description = "Allows you to see ignited TNT blocks through walls.", category = ModuleCategory.RENDER)
class TNTESP : Module() {

    @EventTarget
    fun onRender3D(event : Render3DEvent) {
        mc.theWorld.loadedEntityList.filterIsInstance<EntityTNTPrimed>().forEach { RenderUtils.drawEntityBox(it, Color.RED, false) }
    }
}