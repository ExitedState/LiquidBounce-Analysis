package net.keyStrokes.gui.window.windows;


import net.keyStrokes.gui.window.Window;
import net.keyStrokes.gui.window.WindowManager;
import net.keyStrokes.gui.window.components.BooleanComponent;
import net.keyStrokes.gui.window.components.ButtonComponent;
import net.keyStrokes.gui.window.components.SliderComponent;
import net.keyStrokes.overlay.RenderedKey;

public class TextOptionsWindow extends SubConfigWindow {

    private Window currentChild;

    public TextOptionsWindow(WindowManager windowManager, RenderedKey k) {
        super(windowManager);
        setKey(k);

        setWidth(200);
        setHeight(75);

        addComponents(Window.ALIGN_LEFT,
                new BooleanComponent("Rainbow", key.getConfiguration().rainbowText, b -> key.getConfiguration().rainbowText = b),
                new BooleanComponent("Shadow", key.getConfiguration().textShadow, b -> key.getConfiguration().textShadow = b),
                new ButtonComponent("Text Color", () -> {
                    if (currentChild != null) currentChild.closeWindow();

                    currentChild = new ColorPickerWindow(windowManager, "Text Color",
                            key.getConfiguration().textColorInactive,
                            color -> key.getConfiguration().textColorInactive = color
                    );

                    currentChild.setX(getX());
                    currentChild.setY(getY());
                    windowManager.openWindow(currentChild);
                }),
                new ButtonComponent("Text Color (held)", () -> {
                    if (currentChild != null) currentChild.closeWindow();

                    currentChild = new ColorPickerWindow(windowManager, "Text Color (held)",
                            key.getConfiguration().textColorActive,
                            color -> key.getConfiguration().textColorActive = color
                    );

                    currentChild.setX(getX());
                    currentChild.setY(getY());
                    windowManager.openWindow(currentChild);
                })
        );

        addComponents(Window.ALIGN_RIGHT, -10, 0,
                new SliderComponent("Text Scale", 0.25, 5, 0.25, key.getConfiguration().textScale, val -> key.getConfiguration().textScale = val)
        );
    }

    @Override
    public void setKey(RenderedKey key) {
        super.setKey(key);
        this.title = "Text Options for " + key.getDisplayText();
    }

    @Override
    public void closeWindow() {
        if (currentChild != null) currentChild.closeWindow();
        super.closeWindow();
    }
}