package net.keyStrokes.gui.window.windows;


import net.keyStrokes.gui.window.Window;
import net.keyStrokes.gui.window.WindowManager;
import net.keyStrokes.gui.window.components.BooleanComponent;
import net.keyStrokes.gui.window.components.ButtonComponent;
import net.keyStrokes.overlay.RenderedKey;
import net.minecraft.client.Minecraft;


public class ParticleOptionsWindow extends SubConfigWindow {

    private Window currentChild;

    public ParticleOptionsWindow(WindowManager windowManager, RenderedKey k) {
        super(windowManager);
        setKey(k);

        setWidth(Math.max(120, Minecraft.getMinecraft().fontRendererObj.getStringWidth(k.getDisplayText()) + 80));
        setHeight(60);

        addComponents(Window.ALIGN_CENTER,
                new BooleanComponent("Particles", key.getConfiguration().particles, b -> key.getConfiguration().particles = b),
                new BooleanComponent("Rainbow", key.getConfiguration().rainbowParticles, b -> key.getConfiguration().rainbowParticles = b),
                new ButtonComponent("Particle Color", () -> {
                    if (currentChild != null) currentChild.closeWindow();

                    currentChild = new ColorPickerWindow(windowManager, "Particle Color",
                            key.getConfiguration().particleColor,
                            color -> key.getConfiguration().particleColor = color
                    );

                    currentChild.setX(getX());
                    currentChild.setY(getY());
                    windowManager.openWindow(currentChild);
                })
        );
    }

    @Override
    public void setKey(RenderedKey key) {
        super.setKey(key);
        this.title = "Particle Options for " + key.getDisplayText();
    }

    @Override
    public void closeWindow() {
        if (currentChild != null) currentChild.closeWindow();
        super.closeWindow();
    }
}
