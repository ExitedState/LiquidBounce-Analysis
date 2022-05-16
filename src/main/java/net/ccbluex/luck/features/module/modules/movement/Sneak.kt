/*
 * LiquidBounce Hacked Client
 * A free open source mixin-based injection hacked client for Minecraft using Minecraft Forge.
 * https://github.com/CCBlueX/LiquidBounce/
 */
package net.ccbluex.luck.features.module.modules.movement

import net.ccbluex.luck.event.EventState
import net.ccbluex.luck.event.EventTarget
import net.ccbluex.luck.event.MotionEvent
import net.ccbluex.luck.event.WorldEvent
import net.ccbluex.luck.features.module.Module
import net.ccbluex.luck.features.module.ModuleCategory
import net.ccbluex.luck.features.module.ModuleInfo
import net.ccbluex.luck.utils.MovementUtils
import net.ccbluex.luck.value.BoolValue
import net.ccbluex.luck.value.ListValue
import net.minecraft.client.settings.GameSettings
import net.minecraft.network.play.client.C0BPacketEntityAction
import java.util.*

@ModuleInfo(name = "Sneak", description = "Automatically sneaks all the time.", category = ModuleCategory.MOVEMENT)
class Sneak : Module() {

    @JvmField
    val modeValue = ListValue("Mode", arrayOf("Legit", "Vanilla", "Switch", "MineSecure"), "MineSecure")
    @JvmField
    val stopMoveValue = BoolValue("StopMove", false)

    private var sneaking = false

    @EventTarget
    fun onMotion(event: MotionEvent) {
        if (stopMoveValue.get() && MovementUtils.isMoving) {
            if (sneaking)
                onDisable()
            return
        }

        when (modeValue.get().lowercase(Locale.getDefault())) {
            "legit" -> mc.gameSettings.keyBindSneak.pressed = true
            "vanilla" -> {
                if (sneaking)
                    return

                mc.netHandler.addToSendQueue(
                    C0BPacketEntityAction(
                        mc.thePlayer!!,
                        C0BPacketEntityAction.Action.START_SNEAKING
                    )
                )
            }

            "switch" -> {
                when (event.eventState) {
                    EventState.PRE -> {
                        mc.netHandler.addToSendQueue(
                            C0BPacketEntityAction(
                                mc.thePlayer!!,
                                C0BPacketEntityAction.Action.START_SNEAKING
                            )
                        )
                        mc.netHandler.addToSendQueue(
                            C0BPacketEntityAction(
                                mc.thePlayer!!,
                                C0BPacketEntityAction.Action.STOP_SNEAKING
                            )
                        )
                    }
                    EventState.POST -> {
                        mc.netHandler.addToSendQueue(
                            C0BPacketEntityAction(
                                mc.thePlayer!!,
                                C0BPacketEntityAction.Action.STOP_SNEAKING
                            )
                        )
                        mc.netHandler.addToSendQueue(
                            C0BPacketEntityAction(
                                mc.thePlayer!!,
                                C0BPacketEntityAction.Action.START_SNEAKING
                            )
                        )
                    }
                }
            }

            "minesecure" -> {
                if (event.eventState == EventState.PRE)
                    return

                mc.netHandler.addToSendQueue(
                    C0BPacketEntityAction(
                        mc.thePlayer!!,
                        C0BPacketEntityAction.Action.START_SNEAKING
                    )
                )
            }
        }
    }

    @EventTarget
    fun onWorld(worldEvent: WorldEvent) {
        sneaking = false
    }

    override fun onDisable() {
        val player = mc.thePlayer ?: return

        when (modeValue.get().lowercase(Locale.getDefault())) {
            "legit" -> {
                if (!GameSettings.isKeyDown(mc.gameSettings.keyBindSneak)) {
                    mc.gameSettings.keyBindSneak.pressed = false
                }
            }
            "vanilla", "switch", "minesecure" -> mc.netHandler.addToSendQueue(
                C0BPacketEntityAction(
                    player,
                    C0BPacketEntityAction.Action.STOP_SNEAKING
                )
            )
        }
        sneaking = false
    }
}