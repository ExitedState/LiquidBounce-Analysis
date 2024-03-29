/*
 * LiquidBounce Hacked Client
 * A free open source mixin-based injection hacked client for Minecraft using Minecraft Forge.
 * https://github.com/CCBlueX/LiquidBounce/
 */
package net.ccbluex.luck.features.command

import net.ccbluex.luck.LiquidBounce
import net.ccbluex.luck.features.command.commands.*
import net.ccbluex.luck.features.command.shortcuts.Shortcut
import net.ccbluex.luck.features.command.shortcuts.ShortcutParser
import net.ccbluex.luck.features.command.special.*
import net.ccbluex.luck.utils.ClientUtils
import net.ccbluex.luck.utils.MinecraftInstance
import net.ccbluex.luck.utils.misc.StringUtils

class CommandManager {
    val commands = mutableListOf<Command>()
    var latestAutoComplete: Array<String> = emptyArray()

    var prefix = '\\'

    /**
     * Register all default commands
     */
    fun registerCommands() {
        registerCommand(BindCommand())
        registerCommand(VClipCommand())
        registerCommand(HClipCommand())
        registerCommand(HelpCommand())
        registerCommand(SayCommand())
        registerCommand(FriendCommand())
        registerCommand(AutoSettingsCommand())
        registerCommand(LocalAutoSettingsCommand())
        registerCommand(ServerInfoCommand())
        registerCommand(ToggleCommand())
        registerCommand(HurtCommand())
        registerCommand(GiveCommand())
        registerCommand(UsernameCommand())
        registerCommand(TargetCommand())
        registerCommand(TacoCommand())
        registerCommand(BindsCommand())
        registerCommand(HoloStandCommand())
        registerCommand(PanicCommand())
        registerCommand(PingCommand())
        registerCommand(RenameCommand())
        registerCommand(EnchantCommand())
        registerCommand(ReloadCommand())
        registerCommand(ScriptManagerCommand())
        registerCommand(RemoteViewCommand())
        registerCommand(PrefixCommand())
        registerCommand(ShortcutCommand())
        registerCommand(HideCommand())
        registerCommand(XrayCommand())
        registerCommand(LiquidChatCommand())
        registerCommand(PrivateChatCommand())
        registerCommand(ChatTokenCommand())
        registerCommand(ChatAdminCommand())
    }

    /**
     * Execute command by given [input]
     */
    fun executeCommands(input: String) {
        if(input=="\\" || input=="\\\\" || input=="\\0"){
//            ClientUtils.displayChatMessage(input)
//            val temp = arrayOf(input.split(" "))
            val args: Array<String> = input.split(" ").toTypedArray()
            MinecraftInstance.mc.thePlayer!!.sendChatMessage(StringUtils.toCompleteString(args, 0))
            return
        }
        for (command in commands) {
            val args = input.split(" ").toTypedArray()

            if (args[0].equals(prefix.toString()+ command.command, ignoreCase = true)) {
                command.execute(args)
                return
            }

            for (alias in command.alias) {
                if (!args[0].equals(prefix.toString()+ alias, ignoreCase = true))
                    continue

                command.execute(args)
                return
            }
        }

        ClientUtils.displayChatMessage("§cUnknown command. Type /help for a list of commands")
    }

    /**
     * Updates the [latestAutoComplete] array based on the provided [input].
     *
     * @param input text that should be used to check for auto completions.
     * @author NurMarvin
     */
    fun autoComplete(input: String): Boolean {
        this.latestAutoComplete = this.getCompletions(input) ?: emptyArray()
        return input.startsWith(this.prefix) && this.latestAutoComplete.isNotEmpty()
    }

    /**
     * Returns the auto completions for [input].
     *
     * @param input text that should be used to check for auto completions.
     * @author NurMarvin
     */
    private fun getCompletions(input: String): Array<String>? {
//        if (input.isNotEmpty() && input.toCharArray()[0] == this.prefix) {
//            val args = input.split(" ")
//
//            return if (args.size > 1) {
//                val command = getCommand(args[0].substring(1))
//                val tabCompletions = command?.tabComplete(args.drop(1).toTypedArray())
//
//                tabCompletions?.toTypedArray()
//            } else {
//                val rawInput = input.substring(1)
//                commands
//                    .filter {
//                        it.command.startsWith(rawInput, true)
//                            || it.alias.any { alias -> alias.startsWith(rawInput, true) }
//                    }
//                    .map {
//                        val alias: String = if (it.command.startsWith(rawInput, true))
//                            it.command
//                        else {
//                            it.alias.first { alias -> alias.startsWith(rawInput, true) }
//                        }
//
//                        this.prefix + alias
//                    }
//                    .toTypedArray()
//            }
//        }
        return null
    }

    /**
     * Get command instance by given [name]
     */
    fun getCommand(name: String): Command? {
        return commands.find {
            it.command.equals(name, ignoreCase = true)
                || it.alias.any { alias -> alias.equals(name, true) }
        }
    }

    /**
     * Register [command] by just adding it to the commands registry
     */
    fun registerCommand(command: Command) = commands.add(command)

    fun registerShortcut(name: String, script: String) {
        if (getCommand(name) == null) {
            registerCommand(Shortcut(name, ShortcutParser.parse(script).map {
                val command = getCommand(it[0]) ?: throw IllegalArgumentException("Command ${it[0]} not found!")

                Pair(command, it.toTypedArray())
            }))

            LiquidBounce.fileManager.saveConfig(LiquidBounce.fileManager.shortcutsConfig)
        } else {
            throw IllegalArgumentException("Command already exists!")
        }
    }

    fun unregisterShortcut(name: String): Boolean {
        val removed = commands.removeIf {
            it is Shortcut && it.command.equals(name, ignoreCase = true)
        }

        LiquidBounce.fileManager.saveConfig(LiquidBounce.fileManager.shortcutsConfig)

        return removed
    }

    /**
     * Unregister [command] by just removing it from the commands registry
     */
    fun unregisterCommand(command: Command?) = commands.remove(command)
}
