package bet.astral.mojang.utils;

import java.util.UUID;

public class UUIDConverter {

    public static String toDashless(UUID uuid) {
        return uuid.toString().replaceAll("-", "");
    }

    public static UUID fromDashless(String dashlessUUID) throws IllegalArgumentException {
        if (dashlessUUID.length() != 32) {
            throw new IllegalArgumentException("Invalid dashless UUID length. Must be 32 characters.");
        }

        String dashedUUID = dashlessUUID.substring(0, 8) + "-" +
                dashlessUUID.substring(8, 12) + "-" +
                dashlessUUID.substring(12, 16) + "-" +
                dashlessUUID.substring(16, 20) + "-" +
                dashlessUUID.substring(20, 32);
        return UUID.fromString(dashedUUID);
    }
}