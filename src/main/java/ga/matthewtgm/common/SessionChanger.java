package ga.matthewtgm.common;

import com.mojang.authlib.Agent;
import com.mojang.authlib.AuthenticationService;
import com.mojang.authlib.UserAuthentication;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import com.mojang.util.UUIDTypeAdapter;
import net.minecraft.client.Minecraft;
import net.minecraft.util.Session;

import java.lang.reflect.Field;
import java.util.UUID;

import static java.lang.reflect.Modifier.FINAL;
import static net.minecraft.client.Minecraft.getMinecraft;

public class SessionChanger {

    private static SessionChanger instance;
    private final UserAuthentication auth;

    public static SessionChanger getInstance() {
        if (instance == null) {
            instance = new SessionChanger();
        }

        return instance;
    }

    //Creates a new Authentication Service.
    private SessionChanger() {
        UUID notSureWhyINeedThis = UUID.randomUUID(); //Idk, needs a UUID. Seems to be fine making it random
        AuthenticationService authService = new YggdrasilAuthenticationService(getMinecraft().getProxy(), notSureWhyINeedThis.toString());
        auth = authService.createUserAuthentication(Agent.MINECRAFT);
        authService.createMinecraftSessionService();
    }


    //Online mode
    //Checks if your already loggin in to the account.
    public void setUser(String email, String password) {
        if(!getMinecraft().getSession().getUsername().equals(email) || getMinecraft().getSession().getToken().equals("0")){

            this.auth.logOut();
            this.auth.setUsername(email);
            this.auth.setPassword(password);
            try {
                this.auth.logIn();
                try {
                    final Field session = Minecraft.class.getDeclaredField("session");
                    final Field modifiers = Field.class.getDeclaredField("modifiers");
                    session.setAccessible(true); modifiers.setAccessible(true);
                    modifiers.setInt(session, session.getModifiers() & ~FINAL);
                    session.set(getMinecraft(), new Session(this.auth.getSelectedProfile().getName(), UUIDTypeAdapter.fromUUID(auth.getSelectedProfile().getId()), this.auth.getAuthenticatedToken(), this.auth.getUserType().getName()));
                } catch (Throwable reason) { throw new RuntimeException(reason); } }
            catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

}