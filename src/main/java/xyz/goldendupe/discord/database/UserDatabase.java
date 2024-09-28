package xyz.goldendupe.discord.database;

import xyz.goldendupe.discord.user.UserProfile;

import java.util.concurrent.CompletableFuture;

public class UserDatabase {
    private final DriverConnector driverConnector;

    public UserDatabase(DriverConnector driverConnector) {
        this.driverConnector = driverConnector;
    }

    public CompletableFuture<UserProfile> loadDiscordUser(String user){
        return null;
    }
}
