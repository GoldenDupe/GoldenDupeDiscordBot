package bet.astral.mojang.profile;

import bet.astral.mojang.network.Link;
import bet.astral.mojang.network.Parameter;
import bet.astral.mojang.network.Profile;
import bet.astral.mojang.property.Property;

import java.util.ArrayList;
import java.util.List;

public class UUIDProfile implements Profile, Id, Name {
    public static final Link LINK = Link.automatic("https://sessionserver.mojang.com/session/minecraft/profile/%uuid%");
    public static final Link LINK_UNSIGNED = Link.automatic("https://sessionserver.mojang.com/session/minecraft/profile/%uuid%?unsgined=true");
    @Parameter(name="name")
    private final String name;
    @Parameter(name="uuid")
    private final String id;
    private final boolean legacy = false;
    private final List<Property> properties = new ArrayList<>();
    private final List<ProfileAction> profileActions = new ArrayList<>();
    private UUIDProfile() {
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

    public List<ProfileAction> getProfileActions() {
        return profileActions;
    }

    public List<Property> getProperties() {
        return properties;
    }

    public boolean isLegacy() {
        return legacy;
    }

    public enum ProfileAction {
        FORCED_NAME_CHANGE,
        USING_BANNED_SKIN
    }
}
