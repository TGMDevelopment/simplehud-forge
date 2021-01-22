import ga.matthewtgm.simplehud.Constants;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.util.Locale;
import java.util.jar.JarFile;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;

public class SimpleHUDInstaller {

    private static final JPanel panel = new JPanel();

    private static final JButton buttonInstall = new JButton();
    private static final JButton buttonOpenModsFolder = new JButton();
    private static final JButton buttonClose = new JButton();

    public static void main(String[] args) throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
        createWindow(new JFrame(Constants.NAME + " Installer"), 500, 80);
    }

    private static void createWindow(JFrame frame, int width, int height) throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        frame.setSize(width, height);
        frame.setVisible(true);
        frame.setFocusable(true);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    frame.setIconImage(new ImageIcon(ImageIO.read(new URL(Constants.LOGO_URL).openStream())).getImage());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        addButtonsToWindow(frame);
    }

    private static void addButtonsToWindow(JFrame frame) {
        buttonInstall.setPreferredSize(new Dimension(150, 25));
        buttonOpenModsFolder.setPreferredSize(new Dimension(150, 25));
        buttonClose.setPreferredSize(new Dimension(150, 25));

        buttonInstall.setBounds(0, 0, 440, 560);

        buttonInstall.setText("Install");
        buttonOpenModsFolder.setText("Open Mods Folder");
        buttonClose.setText("Close");

        buttonInstall.addActionListener(e -> {
            tryToInstall(getThisAsFile(), getModsFolder());
        });

        buttonOpenModsFolder.addActionListener(e -> {
            openModsFolder();
        });

        buttonClose.addActionListener(e -> {
            System.exit(0);
        });

        panel.setPreferredSize(new Dimension(440, 560));
        panel.setBounds(0, 0, 440, 560);

        frame.add(panel);

        panel.add(buttonInstall);
        panel.add(buttonOpenModsFolder);
        panel.add(buttonClose);

        panel.revalidate();
        panel.repaint();
    }

    private static void tryToInstall(File modFile, File modsFolder) {
        System.out.println("1");
        if (modFile != null) {
            boolean isInASubFolder = false;
            if (Pattern.compile("1\\.8\\.9[/\\\\]?$").matcher(modsFolder.getPath()).find()) {
                isInASubFolder = true;
            }

            System.out.println("2");

            boolean deletingOldFailed = false;
            if (modsFolder.isDirectory()) {
                boolean hasFailed = findSimpleHUDAndDeleteOld(modsFolder.listFiles());
                if (hasFailed) deletingOldFailed = true;
                System.out.println("3");
            }
            if (isInASubFolder) {
                if (modsFolder.getParentFile().isDirectory()) {
                    boolean hasFailed = findSimpleHUDAndDeleteOld(modsFolder.listFiles());
                    if (hasFailed) deletingOldFailed = true;
                    System.out.println("4");
                }
            } else {
                File sub = new File(modsFolder, "1.8.9");
                if (sub.exists() && sub.isDirectory()) {
                    boolean hasFailed = findSimpleHUDAndDeleteOld(modsFolder.listFiles());
                    if (hasFailed) deletingOldFailed = true;
                    System.out.println("5");
                }
            }

            if (deletingOldFailed) return;
            System.out.println("6");
            if (modFile.isDirectory()) return;
            System.out.println("7");

            try {
                Files.copy(modFile.toPath(), new File(modsFolder, modFile.getName()).toPath());
                System.out.println("8");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static boolean findSimpleHUDAndDeleteOld(File[] files) {
        if (files == null) return false;

        for (File file : files) {
            if (!file.isDirectory() && file.getPath().endsWith(".jar")) {
                try {
                    JarFile jarFile = new JarFile(file);
                    ZipEntry mcModInfo = jarFile.getEntry("mcmod.info");
                    if (mcModInfo != null) {
                        InputStream inputStream = jarFile.getInputStream(mcModInfo);
                        String modID = getModIDFromInputStream(inputStream);
                        if (modID.equals("simplehud")) {
                            jarFile.close();
                            try {
                                boolean deleted = file.delete();
                                if (!deleted) {
                                    throw new Exception();
                                }
                            } catch (Exception ex) {
                                ex.printStackTrace();
                                return true;
                            }
                            continue;
                        }
                    }
                    jarFile.close();
                } catch (Exception ignored) {
                }
            }
        }
        return false;
    }

    private static String getModIDFromInputStream(InputStream inputStream) {
        String version = "";
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            while ((version = bufferedReader.readLine()) != null) {
                if (version.contains("\"modid\": \"")) {
                    version = version.split(Pattern.quote("\"modid\": \""))[1];
                    version = version.substring(0, version.length() - 2);
                    break;
                }
            }
        } catch (Exception ignored) {
        }
        return version;
    }

    private static void addFinishedInstallingButtonsToWindow(JFrame frame) {

    }

    private static void openModsFolder() {
        try {
            Desktop.getDesktop().open(getModsFolder());
        } catch (Exception ignored) {
        }
    }

    private static File getModsFolder() {
        String userHome = System.getProperty("user.home", ".");

        File modsFolder = getFile(userHome, "minecraft/mods/1.8.9");
        if (!modsFolder.exists()) {
            modsFolder = getFile(userHome, "minecraft/mods");
        }

        if (!modsFolder.exists() && !modsFolder.mkdirs()) {
            throw new RuntimeException("The working directory could not be created: " + modsFolder);
        }
        return modsFolder;
    }

    private static File getFile(String userHome, String minecraftPath) {
        File workingDirectory;
        switch (getOperatingSystem()) {
            case LINUX:
            case SOLARIS: {
                workingDirectory = new File(userHome, '.' + minecraftPath + '/');
                break;
            }
            case WINDOWS: {
                String applicationData = System.getenv("APPDATA");
                if (applicationData != null) {
                    workingDirectory = new File(applicationData, "." + minecraftPath + '/');
                    break;
                }
                workingDirectory = new File(userHome, '.' + minecraftPath + '/');
                break;
            }
            case MACOS: {
                workingDirectory = new File(userHome, "Library/Application Support/" + minecraftPath);
                break;
            }
            default: {
                workingDirectory = new File(userHome, minecraftPath + '/');
                break;
            }
        }
        return workingDirectory;
    }

    private static OperatingSystem getOperatingSystem() {
        String osName = System.getProperty("os.name").toLowerCase(Locale.US);
        if (osName.contains("win")) {
            return OperatingSystem.WINDOWS;

        } else if (osName.contains("mac")) {
            return OperatingSystem.MACOS;

        } else if (osName.contains("solaris") || osName.contains("sunos")) {

            return OperatingSystem.SOLARIS;
        } else if (osName.contains("linux") || osName.contains("unix")) {

            return OperatingSystem.LINUX;
        }
        return OperatingSystem.UNKNOWN;
    }

    private static File getThisAsFile() {
        try {
            return new File(SimpleHUDInstaller.class.getProtectionDomain().getCodeSource().getLocation().toURI());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private enum OperatingSystem {
        LINUX,
        SOLARIS,
        WINDOWS,
        MACOS,
        UNKNOWN
    }

}