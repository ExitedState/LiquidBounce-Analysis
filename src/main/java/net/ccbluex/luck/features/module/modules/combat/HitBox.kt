/*
 * LiquidBounce Hacked Client
 * A free open source mixin-based injection hacked client for Minecraft using Minecraft Forge.
 * https://github.com/CCBlueX/LiquidBounce/
 */
package net.ccbluex.luck.features.module.modules.combat

import net.ccbluex.luck.features.module.Module
import net.ccbluex.luck.features.module.ModuleCategory
import net.ccbluex.luck.features.module.ModuleInfo
import net.ccbluex.luck.value.FloatValue

@ModuleInfo(name = "HitBox", description = "Makes hitboxes of targets bigger.", category = ModuleCategory.COMBAT)
class HitBox : Module() {

    val sizeValue = FloatValue("Size", 0.4F, 0F, 1F)

}