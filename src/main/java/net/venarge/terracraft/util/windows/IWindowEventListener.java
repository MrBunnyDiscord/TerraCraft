package net.venarge.terracraft.util.windows;

import net.venarge.terracraft.util.side.Dist;
import net.venarge.terracraft.util.side.OnlyIn;

@OnlyIn (Dist.CLIENT)
public interface IWindowEventListener {
    void setWindowsActive(boolean var1);

    void resizeDisplay();

    void cursorEntered();
}
