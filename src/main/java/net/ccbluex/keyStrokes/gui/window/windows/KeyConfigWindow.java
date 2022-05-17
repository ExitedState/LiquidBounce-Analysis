package net.ccbluex.keyStrokes.gui.window.windows;


import net.ccbluex.keyStrokes.KeystrokesMod;
import net.ccbluex.keyStrokes.gui.window.Window;
import net.ccbluex.keyStrokes.gui.window.WindowManager;
import net.ccbluex.keyStrokes.gui.window.components.BooleanComponent;
import net.ccbluex.keyStrokes.gui.window.components.ButtonComponent;
import net.ccbluex.keyStrokes.overlay.RenderedKey;


public class KeyConfigWindow extends Window {

    private RenderedKey key;
    private final BaseOptionsWindow baseOptionsWindow;
    private final TextOptionsWindow textOptionsWindow;
    private final ParticleOptionsWindow particleOptionsWindow;

    public KeyConfigWindow(WindowManager windowManager, RenderedKey k) {
        super(windowManager, "Config for " + k.getDisplayText());

        this.key = k;
        this.baseOptionsWindow = new BaseOptionsWindow(windowManager, k);
        this.textOptionsWindow = new TextOptionsWindow(windowManager, k);
        this.particleOptionsWindow = new ParticleOptionsWindow(windowManager, k);

        setWidth(120);
        setHeight(80);

        boolean cpsOption = KeystrokesMod.getInstance().keys().contains(k);

        addComponents(cpsOption ? Window.ALIGN_LEFT : Window.ALIGN_CENTER, 0, 2,
                new BooleanComponent("Visible", key.getConfiguration().visible, b -> key.getConfiguration().visible = b)
        );

        if (cpsOption) {
            addComponents(Window.ALIGN_RIGHT, 0, 2,
                    new BooleanComponent("Count CPS", key.getConfiguration().countCPS, b -> {
                        this.key.getConfiguration().countCPS = b;
                        this.key = KeystrokesMod.getInstance().updateKeyType(key);
                        this.baseOptionsWindow.setKey(key);
                        this.textOptionsWindow.setKey(key);
                        this.particleOptionsWindow.setKey(key);
                    })

            );
        }

        addComponents(Window.ALIGN_CENTER, 0, 17,
                new ButtonComponent("Base Options", () -> {
                    baseOptionsWindow.setX(getX());
                    baseOptionsWindow.setY(getY());
                    windowManager.openWindow(baseOptionsWindow);
                }),
                new ButtonComponent("Text Options", () -> {
                    textOptionsWindow.setX(getX());
                    textOptionsWindow.setY(getY());
                    windowManager.openWindow(textOptionsWindow);
                }),
                new ButtonComponent("Particle Options", () -> {
                    particleOptionsWindow.setX(getX());
                    particleOptionsWindow.setY(getY());
                    windowManager.openWindow(particleOptionsWindow);
                })
        );
    }

    @Override
    public void closeWindow() {
        baseOptionsWindow.closeWindow();
        textOptionsWindow.closeWindow();
        particleOptionsWindow.closeWindow();
        super.closeWindow();
    }
}