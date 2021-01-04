package ga.matthewtgm.simplehud;

import ga.matthewtgm.simplehud.command.SimpleHudCommand;
import ga.matthewtgm.simplehud.elements.ElementManager;
import ga.matthewtgm.simplehud.enums.Colour;
import ga.matthewtgm.simplehud.files.FileHandler;
import ga.matthewtgm.simplehud.gui.GuiConfiguration;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import org.lwjgl.input.Keyboard;

@Mod(name = Constants.NAME, version = Constants.VER, modid = Constants.MODID)
public class SimpleHUD {

    @Mod.Instance(Constants.MODID)
    private static SimpleHUD INSTANCE = new SimpleHUD();
    public static SimpleHUD getInstance() {
        return INSTANCE;
    }

    private static final FileHandler FILE_HANDLER = new FileHandler();
    private static ElementManager ELEMENT_MANAGER;

    private final KeyBinding openGuiKeyBinding = new KeyBinding("Open GUI", Keyboard.KEY_N, "SimpleHUD");
    public GuiScreen configGui;

    @Mod.EventHandler
    protected void onInit(FMLInitializationEvent event) {
        ELEMENT_MANAGER = new ElementManager();
        this.configGui = new GuiConfiguration(null);
        getFileHandler().init();
        MinecraftForge.EVENT_BUS.register(this);
        ClientRegistry.registerKeyBinding(openGuiKeyBinding);
        ClientCommandHandler.instance.registerCommand(new SimpleHudCommand());
        this.getElementManager().init();
    }

    @SubscribeEvent
    protected void onKeyPressed(InputEvent.KeyInputEvent event) {
        if(openGuiKeyBinding.isPressed()) Minecraft.getMinecraft().displayGuiScreen(configGui);
    }

    public static FileHandler getFileHandler() {
        return FILE_HANDLER;
    }

    public ElementManager getElementManager() {
        return ELEMENT_MANAGER;
    }

}