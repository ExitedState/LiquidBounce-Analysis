/*
 * LiquidBounce Hacked Client
 * A free open source mixin-based injection hacked client for Minecraft using Minecraft Forge.
 * https://github.com/CCBlueX/LiquidBounce/
 */
package net.ccbluex.luck.features.module.modules.movement.speeds

import net.ccbluex.luck.LiquidBounce
import net.ccbluex.luck.event.MoveEvent
import net.ccbluex.luck.features.module.modules.movement.Speed
import net.ccbluex.luck.utils.MinecraftInstance

abstract class SpeedMode(val modeName: String) : MinecraftInstance() {
    val isActive: Boolean
        get() {
            val speed = LiquidBounce.moduleManager.getModule(Speed::class.java) as Speed?
            return speed != null && !mc.thePlayer!!.isSneaking && speed.state && speed.modeValue.get() == modeName
        }

    abstract fun onMotion()
    abstract fun onUpdate()
    abstract fun onMove(event: MoveEvent)
    open fun onTick() {}
    open fun onEnable() {}
    open fun onDisable() {}

}