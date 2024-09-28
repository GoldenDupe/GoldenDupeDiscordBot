package xyz.goldendupe.discord.user;

import java.util.UUID;

public class UserProfile {
    private final long userId;
    private final UUID minecraftId;

    public UserProfile(long userId, UUID minecraftId) {
        this.userId = userId;
        this.minecraftId = minecraftId;
    }
}
