package net.keyStrokes;


import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;

public class KeystrokesCommand extends CommandBase {

    @Override
    public String getCommandName() {
        return "kgui";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "/kgui";
    }

    @Override
    public boolean canCommandSenderUseCommand(ICommandSender sender) {
        return true;
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 0;
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) {
        KeystrokesMod.getInstance().showGui();
    }
}