/*
 * LiquidBounce Hacked Client
 * A free open source mixin-based injection hacked client for Minecraft using Minecraft Forge.
 * https://github.com/CCBlueX/LiquidBounce/
 */
package net.ccbluex.luck.injection.forge.mixins.gui;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.multiplayer.GuiConnecting;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(GuiConnecting.class)
@SideOnly(Side.CLIENT)
public abstract class MixinGuiConnecting extends GuiScreen {
//
//    @Inject(method = "connect", at = @At("HEAD"))
//    private void headConnect(final String ip, final int port, CallbackInfo callbackInfo) {
//        ServerUtils.serverData = new ServerData("", ip + ":" + port, false);
//    }
//
//    /**
//     * @author CCBlueX
//     */
//    @Overwrite
//    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
//        ScaledResolution scaledResolution = new ScaledResolution(Minecraft.getMinecraft());
//
//        this.drawDefaultBackground();
//
//        RenderUtils.drawLoadingCircle(scaledResolution.getScaledWidth() / 2, scaledResolution.getScaledHeight() / 4 + 70);
//
//        String ip = "Unknown";
//
//        final ServerData serverData = mc.getCurrentServerData();
//        if(serverData != null)
//            ip = serverData.serverIP;
//
//        Fonts.font40.drawCenteredString("Connecting to", scaledResolution.getScaledWidth() / 2, scaledResolution.getScaledHeight() / 4 + 110, 0xFFFFFF, true);
//        Fonts.font35.drawCenteredString(ip, scaledResolution.getScaledWidth() / 2, scaledResolution.getScaledHeight() / 4 + 120, 0x5281FB, true);
//
//        super.drawScreen(mouseX, mouseY, partialTicks);
//    }
}