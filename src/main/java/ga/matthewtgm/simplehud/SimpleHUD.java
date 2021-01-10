package ga.matthewtgm.simplehud;

import ga.matthewtgm.common.SessionChanger;
import ga.matthewtgm.simplehud.command.SimpleHUDCommand;
import ga.matthewtgm.simplehud.elements.ElementManager;
import ga.matthewtgm.simplehud.files.FileHandler;
import ga.matthewtgm.simplehud.gui.GuiMain;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.CommandEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import org.json.simple.JSONObject;
import org.lwjgl.input.Keyboard;

@Mod(name = Constants.NAME, version = Constants.VER, modid = Constants.MODID)
public class SimpleHUD {

    @Mod.Instance(Constants.MODID)
    protected static SimpleHUD INSTANCE = new SimpleHUD();
    public static SimpleHUD getInstance() {
        return INSTANCE;
    }

    private static final FileHandler FILE_HANDLER = new FileHandler();
    private static final ElementManager ELEMENT_MANAGER = new ElementManager();

    private boolean toggled = true;

    public final KeyBinding openGuiKeyBinding = new KeyBinding("Open GUI", Keyboard.KEY_N, "SimpleHUD");
    public GuiScreen configGui;

    @Mod.EventHandler
    protected void onPreInit(FMLPreInitializationEvent event) {
        boolean isConfigFileNull = SimpleHUD.getFileHandler().load("main", SimpleHUD.getFileHandler().modDir) == null;
        if(!isConfigFileNull) this.toggled = (boolean) getFileHandler().load("main", getFileHandler().modDir).get("full_toggle");
        final JSONObject object = new JSONObject();
        object.put("full_toggle", this.toggled);
        getFileHandler().save("main", getFileHandler().modDir, object);
    }

    @Mod.EventHandler
    protected void onInit(FMLInitializationEvent event) {
        this.configGui = new GuiMain();
        getFileHandler().init();
        MinecraftForge.EVENT_BUS.register(this);
        //MinecraftForge.EVENT_BUS.register(new PlayerListener());
        ClientRegistry.registerKeyBinding(openGuiKeyBinding);
        ClientCommandHandler.instance.registerCommand(new SimpleHUDCommand());
        this.getElementManager().init();
    }

    @SubscribeEvent
    protected void onKeyPressed(InputEvent.KeyInputEvent event) {
        if (openGuiKeyBinding.isPressed()) Minecraft.getMinecraft().displayGuiScreen(configGui);
    }

    @SubscribeEvent
    protected void onCommandRan(CommandEvent event) {
        if(event.command instanceof SimpleHUDCommand) {
            Minecraft.getMinecraft().displayGuiScreen(this.configGui);
        }
    }

    public boolean isToggled() {
        return toggled;
    }

    public void setToggled(boolean toggled) {
        this.toggled = toggled;
    }

    public static FileHandler getFileHandler() {
        return FILE_HANDLER;
    }

    public ElementManager getElementManager() {
        return ELEMENT_MANAGER;
    }

}
