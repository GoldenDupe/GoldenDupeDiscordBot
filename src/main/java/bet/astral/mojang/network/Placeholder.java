package bet.astral.mojang.network;

import bet.astral.mojang.profile.Id;
import bet.astral.mojang.profile.Name;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class Placeholder {
    private final String name;
    private final String value;

    @NotNull
    @Contract("_, _ -> new")
    public static Placeholder of(@NotNull String name, @NotNull UUID profileId) {
        return new Placeholder(name, profileId.toString());
    }
    @NotNull
    @Contract("_, _ -> new")
    public static Placeholder of(@NotNull String name, @NotNull Id profileId) {
        return new Placeholder(name, profileId.getUniqueIdString());
    }
    @NotNull
    @Contract("_, _ -> new")
    public static Placeholder of(@NotNull String name, @NotNull Name profileName) {
        return new Placeholder(name, profileName.getName());
    }
    @NotNull
    @Contract("_, _ -> new")
    public static Placeholder of(@NotNull String name, @NotNull String profileName) {
        return new Placeholder(name, profileName);
    }

    private Placeholder(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }
}
