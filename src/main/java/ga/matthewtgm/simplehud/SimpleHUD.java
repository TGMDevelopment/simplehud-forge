package ga.matthewtgm.simplehud;

import ga.matthewtgm.simplehud.command.SimpleHUDCommand;
import ga.matthewtgm.simplehud.elements.ElementManager;
import ga.matthewtgm.simplehud.files.FileHandler;
import ga.matthewtgm.simplehud.gui.GuiMain;
import ga.matthewtgm.simplehud.listener.GuiListener;
import ga.matthewtgm.simplehud.listener.PlayerListener;
import ga.matthewtgm.simplehud.other.VersionChecker;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.ModMetadata;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
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
    private static final VersionChecker VERSION_CHECKER = new VersionChecker();

    private boolean toggled = true;
    private boolean latestVersion;

    public final KeyBinding openGuiKeyBinding = new KeyBinding("Open GUI", Keyboard.KEY_N, "SimpleHUD");
    public GuiScreen configGui;

    @Mod.EventHandler
    protected void onPreInit(FMLPreInitializationEvent event) {
        if(VERSION_CHECKER.getEmergencyStatus()) throw new RuntimeException("PLEASE UPDATE TO THE NEW VERSION OF " + Constants.NAME + "\nTHIS IS AN EMERGENCY!");
        this.latestVersion = VERSION_CHECKER.getVersion().equals(Constants.VER);

        final ModMetadata modMetadata = event.getModMetadata();
        modMetadata.description = "Displays simple information on your screen in a neat little overlay." +
                "\n\nAbout:" +
                "\nSimpleHUD was originally a private mod for MatthewTGM and friends, now being one of his biggest mods yet." +
                "\n\nFeatures:" +
                "\nFPS, CPS, Coordinates, Biome, Combo Display, ArmourHUD, PotionEffectsHUD, Day, Reach Display, Ping, Memory Usage, Time, SimpleText, Server Address" +
                "\n\nExtras:" +
                "\nSimpleHUD's \"SimpleText\" element is derived from a mod that Matthew was originally planning to make seperately.";

        boolean isConfigFileNull = SimpleHUD.getFileHandler().load("main", SimpleHUD.getFileHandler().modDir) == null;
        if (!isConfigFileNull)
            this.toggled = (boolean) getFileHandler().load("main", getFileHandler().modDir).get("full_toggle");
        final JSONObject object = new JSONObject();
        object.put("full_toggle", this.toggled);
        object.put("pause_button", GuiListener.getInstance().mustAddPauseButton());
        getFileHandler().save("main", getFileHandler().modDir, object);
    }

    @Mod.EventHandler
    protected void onInit(FMLInitializationEvent event) {
        this.configGui = new GuiMain();
        getFileHandler().init();
        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(GuiListener.getInstance());
        MinecraftForge.EVENT_BUS.register(new PlayerListener());
        ClientRegistry.registerKeyBinding(openGuiKeyBinding);
        ClientCommandHandler.instance.registerCommand(new SimpleHUDCommand());
        this.getElementManager().init();
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

    public VersionChecker getVersionChecker() {
        return VERSION_CHECKER;
    }

    public boolean isLatestVersion() {
        return latestVersion;
    }

}
