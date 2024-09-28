package bet.astral.mojang.profile;

import bet.astral.mojang.utils.UUIDConverter;

import java.util.UUID;

public interface Id {
    default UUID getUniqueId() {
        return UUIDConverter.fromDashless(getUniqueIdString());
    }
    String getUniqueIdString();
}
