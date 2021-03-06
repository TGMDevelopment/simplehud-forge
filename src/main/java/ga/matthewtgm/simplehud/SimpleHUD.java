package ga.matthewtgm.simplehud;

import club.sk1er.mods.core.ModCoreInstaller;
import club.sk1er.mods.core.gui.notification.Notifications;
import ga.matthewtgm.lib.TGMLib;
import ga.matthewtgm.lib.util.ModConflicts;
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
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.ModMetadata;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.json.simple.JSONObject;
import org.lwjgl.input.Keyboard;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Mod(name = Constants.NAME, version = Constants.VER, modid = Constants.MODID, clientSideOnly = true)
public class SimpleHUD {

    private static final FileHandler FILE_HANDLER = new FileHandler();
    private static final ElementManager ELEMENT_MANAGER = new ElementManager();
    private static final VersionChecker VERSION_CHECKER = new VersionChecker();
    @Mod.Instance(Constants.MODID)
    protected static SimpleHUD INSTANCE = new SimpleHUD();
    private final ModConflicts conflicts = new ModConflicts();
    private final List<String> bannedUsers = new ArrayList<>();
    public GuiScreen configGui;
    private boolean toggled = true;
    private boolean latestVersion;

    public static SimpleHUD getInstance() {
        return INSTANCE;
    }

    public static FileHandler getFileHandler() {
        return FILE_HANDLER;
    }

    public ModConflicts getConflicts() {
        return conflicts;
    }

    @Mod.EventHandler
    protected void onPreInit(FMLPreInitializationEvent event) {
        TGMLib.getInstance().setModName(Constants.NAME);

        this.getConflicts().add("orangesimplemod");

        this.bannedUsers.add("6ce3d4734bd44d1b8c7b7f7169ad2f00");

        if (VERSION_CHECKER.getEmergencyStatus())
            throw new OutOfDateException("PLEASE UPDATE TO THE NEW VERSION OF " + Constants.NAME + "\nTHIS IS AN EMERGENCY!");
        this.latestVersion = VERSION_CHECKER.getVersion().equals(Constants.VER);

        ModCoreInstaller.initializeModCore(Minecraft.getMinecraft().mcDataDir);

        this.setupModMetadata(event);

        boolean isConfigFileNull = SimpleHUD.getFileHandler().load("main", SimpleHUD.getFileHandler().modDir) == null;
        if (!isConfigFileNull) {
            this.toggled = (boolean) getFileHandler().load("main", getFileHandler().modDir).get("full_toggle");
            GuiListener.getInstance().setAddPauseButton((boolean) getFileHandler().load("main", getFileHandler().modDir).get("pause_button"));
            this.getElementManager().setShowInChat((boolean) getFileHandler().load("main", getFileHandler().modDir).get("show_in_chat"));
        }
        final JSONObject object = new JSONObject();
        object.put("full_toggle", this.toggled);
        object.put("pause_button", GuiListener.getInstance().mustAddPauseButton());
        object.put("show_in_chat", this.getElementManager().isShowInChat());
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
        KeyBindManager.getInstance().addKeyBind(new KeyBind("Open GUI", Keyboard.KEY_N) {
            @Override
            public void onPressed() {
                Minecraft.getMinecraft().displayGuiScreen(SimpleHUD.getInstance().configGui);
            }
        });
        KeyBindManager.getInstance().init(Constants.NAME);
        ClientCommandHandler.instance.registerCommand(new SimpleHUDCommand());
        this.getElementManager().init();
    }

    @Mod.EventHandler
    protected void onPostInit(FMLPostInitializationEvent event) {
        this.conflicts.forEach(c -> {
            Loader.instance().getActiveModList().forEach(m -> {
                if (!this.conflicts.contains(m.getName())) return;
                if (this.conflicts.isConflictLoaded(c)) {
                    Notifications.INSTANCE.pushNotification("SimpleHUD - Mod Conflict", m.getName() + " is incompatible with SimpleHUD");
                }
            });
        });
    }

    public boolean isToggled() {
        return toggled;
    }

    public void setToggled(boolean toggled) {
        this.toggled = toggled;
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

    public List<String> getBannedUsers() {
        return bannedUsers;
    }

    /**
     * Adding this just to make things look less cluttered :D
     *
     * @param event the pre init event, used to get the metadata of the mod.
     */
    private void setupModMetadata(FMLPreInitializationEvent event) {

        final ModMetadata modMetadata = event.getModMetadata();

        modMetadata.name = EnumChatFormatting.LIGHT_PURPLE + "Simple" + EnumChatFormatting.DARK_PURPLE + "HUD";

        modMetadata.credits = "\n" + EnumChatFormatting.LIGHT_PURPLE + "Moulberry " + EnumChatFormatting.GRAY + "(Bug fixing)\n" +
                EnumChatFormatting.WHITE + "Filip " + EnumChatFormatting.GRAY + "(Player View)\n" +
                EnumChatFormatting.RED + "Shoddy " + EnumChatFormatting.GRAY + "(Mass bug reporting)\n";

        modMetadata.authorList = Collections.singletonList(EnumChatFormatting.GOLD + "TGM" + EnumChatFormatting.AQUA + "Development\n");

        modMetadata.description = EnumChatFormatting.GREEN + "Displays simple information on your screen in a neat little overlay.\n\n" +
                EnumChatFormatting.YELLOW + "About:\n" +
                EnumChatFormatting.GOLD + "SimpleHUD was originally a private mod for MatthewTGM and friends, now being one of his biggest mods yet.\n\n" +
                EnumChatFormatting.YELLOW + "Features:\n" +
                EnumChatFormatting.GOLD + "FPS, CPS, Coordinates, Biome, ArmourHUD, PotionEffectsHUD, Day, Reach Display, Ping, Memory Usage, Time, SimpleText, Server Address\n\n" +
                EnumChatFormatting.YELLOW + "Extras:\n" +
                EnumChatFormatting.GOLD + "SimpleHUD's \"SimpleText\" element is derived from a mod that Matthew was originally planning to make seperately.";

    }

}