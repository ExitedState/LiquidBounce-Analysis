/*
 * LiquidBounce Hacked Client
 * A free open source mixin-based injection hacked client for Minecraft using Minecraft Forge.
 * https://github.com/CCBlueX/LiquidBounce/
 */
package net.ccbluex.luck.features.command.commands

import net.ccbluex.luck.features.command.Command
import net.ccbluex.luck.utils.misc.StringUtils

class SayCommand : Command("say") {
    /**
     * Execute commands with provided [args]
     */
    override fun execute(args: Array<String>) {
        if (args.size > 1) {
            mc.thePlayer!!.sendChatMessage(StringUtils.toCompleteString(args, 1))
//            chat("Message was sent to the chat.")
            return
        }
//        chatSyntax("say <message...>")
    }
}