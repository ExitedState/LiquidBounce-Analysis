/*
 * LiquidBounce Hacked Client
 * A free open source mixin-based injection hacked client for Minecraft using Minecraft Forge.
 * https://github.com/CCBlueX/LiquidBounce/
 */
package net.ccbluex.luck.features.command.commands

import net.ccbluex.luck.features.command.Command
import net.ccbluex.luck.utils.MovementUtils

class HClipCommand : Command("hclip") {
    /**
     * Execute commands with provided [args]
     */
    override fun execute(args: Array<String>) {
        if (args.size > 1) {
            try {
                MovementUtils.forward(args[1].toDouble())
                chat("You were teleported.")
            } catch (exception: NumberFormatException) {
                chatSyntaxError()
            }
            return
        }

        chatSyntax("hclip <value>")
    }
}