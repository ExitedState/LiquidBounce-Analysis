package net.ccbluex.luck.features.command.special

import net.ccbluex.luck.LiquidBounce
import net.ccbluex.luck.chat.packet.packets.ServerRequestJWTPacket
import net.ccbluex.luck.features.command.Command
import net.ccbluex.luck.features.module.modules.misc.LiquidChat
import net.ccbluex.luck.utils.misc.StringUtils
import java.awt.Toolkit
import java.awt.datatransfer.StringSelection
import java.util.*

class ChatTokenCommand : Command("chattoken") {

    private val lChat = LiquidBounce.moduleManager.getModule(LiquidChat::class.java) as LiquidChat

    /**
     * Execute commands with provided [args]
     */
    override fun execute(args: Array<String>) {
        if (args.size > 1) {
            when {
                args[1].equals("set", true) -> {
                    if (args.size > 2) {
                        LiquidChat.jwtToken = StringUtils.toCompleteString(args, 2)
                        lChat.jwtValue.set(true)

                        if (lChat.state) {
                            lChat.state = false
                            lChat.state = true
                        }
                    } else
                        chatSyntax("chattoken set <token>")
                }

                args[1].equals("generate", true) -> {
                    if (!lChat.state) {
                        chat("§cError: §7LiquidChat is disabled!")
                        return
                    }

                    lChat.client.sendPacket(ServerRequestJWTPacket())
                }

                args[1].equals("copy", true) -> {
                    if (LiquidChat.jwtToken.isEmpty()) {
                        chat("§cError: §7No token set! Generate one first using '${LiquidBounce.commandManager.prefix}chattoken generate'.")
                        return
                    }
                    val stringSelection = StringSelection(LiquidChat.jwtToken)
                    Toolkit.getDefaultToolkit().systemClipboard.setContents(stringSelection, stringSelection)
                    chat("§aCopied to clipboard!")
                }
            }
        } else
            chatSyntax("chattoken <set/copy/generate>")
    }

    override fun tabComplete(args: Array<String>): List<String> {
        if (args.isEmpty())
            return emptyList()

        return when (args.size) {
            1 -> {
                arrayOf("set", "generate", "copy")
                        .map { it.lowercase(Locale.getDefault()) }
                        .filter { it.startsWith(args[0], true) }
            }
            else -> emptyList()
        }
    }

}