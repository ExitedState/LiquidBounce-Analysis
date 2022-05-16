/*
 * LiquidBounce Hacked Client
 * A free open source mixin-based injection hacked client for Minecraft using Minecraft Forge.
 * https://github.com/CCBlueX/LiquidBounce/
 */
package net.ccbluex.luck.features.module.modules.world

import net.ccbluex.luck.features.module.Module
import net.ccbluex.luck.features.module.ModuleCategory
import net.ccbluex.luck.features.module.ModuleInfo
import net.ccbluex.luck.value.BoolValue

@ModuleInfo(name = "NoSlowBreak", description = "Automatically adjusts breaking speed when using modules that influence it.", category = ModuleCategory.WORLD)
class NoSlowBreak : Module() {
    val airValue = BoolValue("Air", true)
    val waterValue = BoolValue("Water", false)
}
