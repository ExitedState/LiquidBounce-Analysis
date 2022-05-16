/*
 * LiquidBounce Hacked Client
 * A free open source mixin-based injection hacked client for Minecraft using Minecraft Forge.
 * https://github.com/CCBlueX/LiquidBounce/
 */
package net.ccbluex.luck.features.module.modules.misc

import net.ccbluex.luck.LiquidBounce
import net.ccbluex.luck.event.EventTarget
import net.ccbluex.luck.event.TextEvent
import net.ccbluex.luck.features.module.Module
import net.ccbluex.luck.features.module.ModuleCategory
import net.ccbluex.luck.features.module.ModuleInfo
import net.ccbluex.luck.utils.misc.StringUtils
import net.ccbluex.luck.utils.render.ColorUtils.translateAlternateColorCodes
import net.ccbluex.luck.value.BoolValue
import net.ccbluex.luck.value.TextValue

@ModuleInfo(name = "NameProtect", description = "Changes playernames clientside.", category = ModuleCategory.MISC)
class NameProtect : Module() {
    @JvmField
    val allPlayersValue = BoolValue("AllPlayers", false)

    @JvmField
    val skinProtectValue = BoolValue("SkinProtect", true)
    private val fakeNameValue = TextValue("FakeName", "&cMe")

    @EventTarget(ignoreCondition = true)
    fun onText(event: TextEvent) {
        val thePlayer = mc.thePlayer

        if (thePlayer == null || event.text!!.contains("§8[§9§l" + LiquidBounce.CLIENT_NAME + "§8] §3"))
            return

        for (friend in LiquidBounce.fileManager.friendsConfig.friends)
            event.text = StringUtils.replace(event.text, friend.playerName, translateAlternateColorCodes(friend.alias) + "§f")

        if (!state)
            return
        event.text = StringUtils.replace(event.text, thePlayer.name, translateAlternateColorCodes(fakeNameValue.get()) + "§f")

        if (allPlayersValue.get()) {
            for (playerInfo in mc.netHandler.playerInfoMap)
                event.text = StringUtils.replace(event.text, playerInfo.gameProfile.name, "Protected User")
        }
    }
}