package net.ccbluex.keyStrokes.overlay;

import net.ccbluex.keyStrokes.KeyConfiguration;
import net.ccbluex.keyStrokes.KeystrokesMod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.List;

public class RenderedCPSKey extends RenderedKey {

    private final List<Long> cpsList = new ArrayList<>(15);

    public RenderedCPSKey(KeyBinding keyBinding, KeyConfiguration configuration) {
        super(keyBinding, configuration);
    }

    @Override
    protected void onPress() {
        super.onPress();
        cpsList.add(KeystrokesMod.getCurrentTick());
    }

    @Override
    protected void onTick() {
        super.onTick();
        cpsList.removeIf(l -> KeystrokesMod.getCurrentTick() - l > 20);
    }

    @Override
    protected void renderText() {
        int textHeight = Minecraft.getMinecraft().fontRendererObj.FONT_HEIGHT;
        float scale = 0.75f;

        GL11.glPushMatrix();
        GL11.glTranslatef(0, (textHeight / 2f + textHeight / 2f * scale) / 2f, 0);

        float t = -configuration.height / 2f + 3;

        if (configuration.style != RenderStyle.RECTANGLE)
            t += (configuration.height - 20) / 5.0;

        GL11.glTranslatef(0, t, 0);
        super.renderText();
        GL11.glTranslatef(0, -t, 0);

        GL11.glScaled(scale, scale, 1);

        String text = cpsList.size() + " CPS";
        int textWidth = Minecraft.getMinecraft().fontRendererObj.getStringWidth(text);
        Minecraft.getMinecraft().fontRendererObj.drawString(text, -textWidth / 2.0f + 0.5f, -textHeight / 2.0f * scale + 1, getTextColor(), configuration.textShadow);

        GL11.glPopMatrix();
    }
}