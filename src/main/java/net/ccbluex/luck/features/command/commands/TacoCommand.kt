/*
 * LiquidBounce Hacked Client
 * A free open source mixin-based injection hacked client for Minecraft using Minecraft Forge.
 * https://github.com/CCBlueX/LiquidBounce/
 */
package net.ccbluex.luck.features.command.commands

import net.ccbluex.luck.LiquidBounce
import net.ccbluex.luck.event.EventTarget
import net.ccbluex.luck.event.Listenable
import net.ccbluex.luck.event.Render2DEvent
import net.ccbluex.luck.event.UpdateEvent
import net.ccbluex.luck.features.command.Command
import net.ccbluex.luck.utils.ClientUtils
import net.ccbluex.luck.utils.render.RenderUtils
import net.minecraft.client.gui.ScaledResolution
import net.minecraft.util.ResourceLocation

class TacoCommand : Command("taco"), Listenable {
    private var toggle = false
    private var image = 0
    private var running = 0f
    private val tacoTextures = arrayOf(
        ResourceLocation("luck/taco/1.png"),
        ResourceLocation("luck/taco/2.png"),
        ResourceLocation("luck/taco/3.png"),
        ResourceLocation("luck/taco/4.png"),
        ResourceLocation("luck/taco/5.png"),
        ResourceLocation("luck/taco/6.png"),
        ResourceLocation("luck/taco/7.png"),
        ResourceLocation("luck/taco/8.png"),
        ResourceLocation("luck/taco/9.png"),
        ResourceLocation("luck/taco/10.png"),
        ResourceLocation("luck/taco/11.png"),
        ResourceLocation("luck/taco/12.png")
    )

    init {
        LiquidBounce.eventManager.registerListener(this)
    }

    /**
     * Execute commands with provided [args]
     */
    override fun execute(args: Array<String>) {
        toggle = !toggle
        ClientUtils.displayChatMessage(if (toggle) "§aTACO TACO TACO. :)" else "§cYou made the little taco sad! :(")
    }

    @EventTarget
    fun onRender2D(event: Render2DEvent) {
        if (!toggle)
            return

        running += 0.15f * RenderUtils.deltaTime
        val scaledResolution = ScaledResolution(mc)
        RenderUtils.drawImage(tacoTextures[image], running.toInt(), scaledResolution.scaledHeight - 60, 64, 32)
        if (scaledResolution.scaledWidth <= running)
            running = -64f
    }

    @EventTarget
    fun onUpdate(event: UpdateEvent) {
        if (!toggle) {
            image = 0
            return
        }

        image++
        if (image >= tacoTextures.size) image = 0
    }

    override fun handleEvents() = true

    override fun tabComplete(args: Array<String>): List<String> {
        return listOf("TACO")
    }
}