/*
 * LiquidBounce Hacked Client
 * A free open source mixin-based injection hacked client for Minecraft using Minecraft Forge.
 * https://github.com/CCBlueX/LiquidBounce/
 */

package net.ccbluex.luck.injection.forge.mixins.gui;

import net.minecraft.client.gui.GuiDownloadTerrain;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(GuiDownloadTerrain.class)
public abstract class MixinGuiDownloadTerrain extends MixinGuiScreen {

//    @Inject(method = "initGui", at = @At(value = "RETURN"))
//    private void injectDisconnectButton(CallbackInfo ci) {
//        this.buttonList.add(new GuiButton(0, this.width / 2 - 100, this.height / 4 + 120 + 12, I18n.format("gui.cancel")));
//    }
//
//    @Override
//    protected void injectedActionPerformed(GuiButton button) {
//        if (button.id == 0) {
//            boolean flag = this.mc.isIntegratedServerRunning();
//            boolean flag1 = this.mc.isConnectedToRealms();
//            button.enabled = false;
//            this.mc.theWorld.sendQuittingDisconnectingPacket();
//            this.mc.loadWorld(null);
//
//            if (flag) {
//                this.mc.displayGuiScreen(new GuiMainMenu());
//            } else if (flag1) {
//                RealmsBridge realmsbridge = new RealmsBridge();
//                realmsbridge.switchToRealms(new GuiMainMenu());
//            } else {
//                this.mc.displayGuiScreen(new GuiMultiplayer(new GuiMainMenu()));
//            }
//        }
//
//    }
}
