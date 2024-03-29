/*
 * LiquidBounce Hacked Client
 * A free open source mixin-based injection hacked client for Minecraft using Minecraft Forge.
 * https://github.com/CCBlueX/LiquidBounce/
 */
package net.ccbluex.luck.features.module.modules.render

import net.ccbluex.luck.event.EventTarget
import net.ccbluex.luck.event.Render2DEvent
import net.ccbluex.luck.event.Render3DEvent
import net.ccbluex.luck.features.module.Module
import net.ccbluex.luck.features.module.ModuleCategory
import net.ccbluex.luck.features.module.ModuleInfo
import net.ccbluex.luck.utils.ClientUtils
import net.ccbluex.luck.utils.render.ColorUtils.rainbow
import net.ccbluex.luck.utils.render.RenderUtils
import net.ccbluex.luck.utils.render.shader.shaders.GlowShader
import net.ccbluex.luck.utils.render.shader.shaders.OutlineShader
import net.ccbluex.luck.value.BoolValue
import net.ccbluex.luck.value.FloatValue
import net.ccbluex.luck.value.IntegerValue
import net.ccbluex.luck.value.ListValue
import net.minecraft.entity.item.EntityItem
import net.minecraft.entity.projectile.EntityArrow
import java.awt.Color
import java.util.*

@ModuleInfo(name = "ItemESP", description = "Allows you to see items through walls.", category = ModuleCategory.RENDER)
class ItemESP : Module() {
    private val modeValue = ListValue("Mode", arrayOf("Box", "OtherBox", "ShaderOutline", "ShaderGlow"), "Box")
    private val shaderRadiusValue = FloatValue("ShaderRadius", 2f, 0.5f, 5f)
    private val colorRedValue = IntegerValue("R", 0, 0, 255)
    private val colorGreenValue = IntegerValue("G", 255, 0, 255)
    private val colorBlueValue = IntegerValue("B", 0, 0, 255)
    private val colorRainbow = BoolValue("Rainbow", true)

    private fun getColor():Color{
        return if (colorRainbow.get()) rainbow() else Color(colorRedValue.get(), colorGreenValue.get(), colorBlueValue.get())
    }

    @EventTarget
    fun onRender3D(event: Render3DEvent?) {
        val color=getColor()
        for (entity in mc.theWorld!!.loadedEntityList) {
            if (!(entity is EntityItem || entity is EntityArrow)) continue
            when (modeValue.get().lowercase(Locale.getDefault())) {
                "box" -> RenderUtils.drawEntityBox(entity, color, true)
                "otherbox" -> RenderUtils.drawEntityBox(entity, color, false)
            }
        }
    }

    @EventTarget
    fun onRender2D(event: Render2DEvent) {
        val shader = (if (modeValue.get().equals("shaderoutline", ignoreCase = true)) OutlineShader.OUTLINE_SHADER else if (modeValue.get().equals("shaderglow", ignoreCase = true)) GlowShader.GLOW_SHADER else null)
            ?: return
        val partialTicks = event.partialTicks

        shader.startDraw(partialTicks)

        try {
            for (entity in mc.theWorld!!.loadedEntityList) {
                if (!(entity is EntityItem || entity is EntityArrow)) continue
                mc.renderManager.renderEntityStatic(entity, event.partialTicks, true)
            }
        } catch (ex: Exception) {
            ClientUtils.getLogger().error("An error occurred while rendering all item entities for shader esp", ex)
        }

        shader.stopDraw(getColor(),shaderRadiusValue.get(),1f)
    }
}