package bet.astral.mojang.profile;

import bet.astral.mojang.network.*;

public class NameProfile implements Profile, Name, Id {
    public static final Link LINK = Link.automatic("https://api.mojang.com/users/profiles/minecraft/%name%");
    @Parameter(name="name")
    private final String name;
    @Parameter(name="uuid")
    private final String id;
    private NameProfile() {
        this.name = null;
        this.id = null;
    }

    @Override
    public String getUniqueIdString() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }
}
