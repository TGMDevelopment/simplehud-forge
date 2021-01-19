package ga.matthewtgm.simplehud;

import club.sk1er.mods.core.ModCoreInstaller;
import ga.matthewtgm.lib.util.SessionChanger;
import ga.matthewtgm.lib.util.guiscreens.GuiAppendedButton;
import ga.matthewtgm.lib.util.guiscreens.GuiAppendingManager;
import ga.matthewtgm.lib.util.keybindings.KeyBind;
import ga.matthewtgm.lib.util.keybindings.KeyBindManager;
import ga.matthewtgm.simplehud.command.SimpleHUDCommand;
import ga.matthewtgm.simplehud.elements.ElementManager;
import ga.matthewtgm.simplehud.exceptions.OutOfDateException;
import ga.matthewtgm.simplehud.files.FileHandler;
import ga.matthewtgm.simplehud.gui.GuiMain;
import ga.matthewtgm.simplehud.listener.GuiListener;
import ga.matthewtgm.simplehud.listener.PlayerListener;
import ga.matthewtgm.simplehud.other.VersionChecker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiIngameMenu;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.ModMetadata;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.json.simple.JSONObject;
import org.lwjgl.input.Keyboard;

import java.util.Collections;

@Mod(name = Constants.NAME, version = Constants.VER, modid = Constants.MODID, clientSideOnly = true)
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

    public GuiScreen configGui;

    @Mod.EventHandler
    protected void onPreInit(FMLPreInitializationEvent event) {
        if(VERSION_CHECKER.getEmergencyStatus()) throw new OutOfDateException("PLEASE UPDATE TO THE NEW VERSION OF " + Constants.NAME + "\nTHIS IS AN EMERGENCY!");
        this.latestVersion = VERSION_CHECKER.getVersion().equals(Constants.VER);

        ModCoreInstaller.initializeModCore(Minecraft.getMinecraft().mcDataDir);

        this.setupModMetadata(event);

        boolean isConfigFileNull = SimpleHUD.getFileHandler().load("main", SimpleHUD.getFileHandler().modDir) == null;
        if (!isConfigFileNull) {
            this.toggled = (boolean) getFileHandler().load("main", getFileHandler().modDir).get("full_toggle");
            GuiListener.getInstance().setAddPauseButton((boolean) getFileHandler().load("main", getFileHandler().modDir).get("pause_button"));
        }
        final JSONObject object = new JSONObject();
        object.put("full_toggle", this.toggled);
        object.put("pause_button", GuiListener.getInstance().mustAddPauseButton());
        getFileHandler().save("main", getFileHandler().modDir, object);
    }

    @Mod.EventHandler
    protected void onInit(FMLInitializationEvent event) {
        GuiAppendingManager.getInstance().init();
        this.configGui = new GuiMain();
        getFileHandler().init();
        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(GuiListener.getInstance());
        MinecraftForge.EVENT_BUS.register(new PlayerListener());
        KeyBindManager.getInstance().addKeyBind(new KeyBind() {
            @Override
            public String getDescription() {
                return "Open GUI";
            }

            @Override
            public int getKey() {
                return Keyboard.KEY_N;
            }

            @Override
            public void onPressed() {
                Minecraft.getMinecraft().displayGuiScreen(SimpleHUD.getInstance().configGui);
            }
        });
        KeyBindManager.getInstance().init(Constants.NAME);
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

    /**
     * Adding this just to make things look less cluttered :D
     * @param event the pre init event, used to get the metadata of the mod.
     */
    private void setupModMetadata(FMLPreInitializationEvent event) {

        final ModMetadata modMetadata = event.getModMetadata();

        modMetadata.name = EnumChatFormatting.LIGHT_PURPLE + "Simple" + EnumChatFormatting.DARK_PURPLE + "HUD";

        modMetadata.credits = "\n" + EnumChatFormatting.LIGHT_PURPLE + "Moulberry " + EnumChatFormatting.GRAY + "(Bug fixing)\n" +
                EnumChatFormatting.YELLOW + "Wyvest " + EnumChatFormatting.GRAY + "(Combo Counter)\n" +
                EnumChatFormatting.RED + "Shoddy " + EnumChatFormatting.GRAY + "(Mass bug reporting)\n";

        modMetadata.authorList = Collections.singletonList(EnumChatFormatting.GOLD + "TGM" + EnumChatFormatting.AQUA + "Development\n");

        modMetadata.description = EnumChatFormatting.GREEN + "Displays simple information on your screen in a neat little overlay.\n\n" +
                EnumChatFormatting.YELLOW + "About:\n" +
                EnumChatFormatting.GOLD + "SimpleHUD was originally a private mod for MatthewTGM and friends, now being one of his biggest mods yet.\n\n" +
                EnumChatFormatting.YELLOW + "Features:\n" +
                EnumChatFormatting.GOLD + "FPS, CPS, Coordinates, Biome, Combo Display, ArmourHUD, PotionEffectsHUD, Day, Reach Display, Ping, Memory Usage, Time, SimpleText, Server Address\n\n" +
                EnumChatFormatting.YELLOW + "Extras:\n" +
                EnumChatFormatting.GOLD + "SimpleHUD's \"SimpleText\" element is derived from a mod that Matthew was originally planning to make seperately.";

    }

}