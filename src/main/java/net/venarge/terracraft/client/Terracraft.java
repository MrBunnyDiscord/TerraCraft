package net.venarge.terracraft.client;

import net.venarge.terracraft.util.side.Dist;
import net.venarge.terracraft.util.side.OnlyIn;
import net.venarge.terracraft.util.windows.IWindowEventListener;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@OnlyIn(Dist.CLIENT)
public class Terracraft implements IWindowEventListener {
    static Terracraft instance;
    private static final Logger LOGGER = LogManager.getLogger ();
    private static final int MAX_TICKS_PER_UPDATE = 10;




}
