package bet.astral.mojang.network;

import java.lang.reflect.Field;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Link {
    private final String url;
    private final Set<String> requiredPlaceholders;
    private static final Pattern PLACEHOLDER = Pattern.compile("%[A-Za-z0-9_]+%");
    private static final Map<Class<? extends Profile>, Map<Field, String>> mappedProfiles = new HashMap<>();

    public Link(String url, Set<String> requiredPlaceholders) {
        this.url = url;
        this.requiredPlaceholders = requiredPlaceholders;
    }

    public static Link of(String url, String... requiredPlaceholders) {
        return new Link(url, Arrays.stream(requiredPlaceholders).collect(Collectors.toSet()));
    }

    public static Link automatic(String url) {
        Set<String> placeholders = new HashSet<>();
        Matcher matcher = PLACEHOLDER.matcher(url);
        while (matcher.find()) {
            placeholders.add(matcher.group().toLowerCase().replace("%", ""));
        }
        return new Link(url, placeholders);
    }

    public String create(Placeholder... placeholders) throws IllegalArgumentException {
        String clone = url;
        Set<String> usedPlaceholders = new HashSet<>();

        for (Placeholder placeholder : placeholders) {
            usedPlaceholders.add(placeholder.getName().toLowerCase());
            clone = replacePlaceholder(clone, placeholder.getName().toLowerCase(), placeholder.getValue());
        }

        for (String placeholder : requiredPlaceholders) {
            if (!usedPlaceholders.contains(placeholder.toLowerCase())) {
                throw new IllegalArgumentException("Couldn't find required placeholder for name: " + placeholder.toLowerCase());
            }
        }
        return clone;
    }

    public String create(Profile profile, Placeholder... placeholders) throws IllegalArgumentException {
        Map<Field, String> map = mappedProfiles.get(profile.getClass());
        if (map == null) {
            map = mapProfile(profile);
            mappedProfiles.put(profile.getClass(), map);
        }

        String clone = url;
        Set<String> usedPlaceholders = new HashSet<>();

        for (Map.Entry<Field, String> entry : map.entrySet()) {
            usedPlaceholders.add(entry.getValue());
            try {
                Object value = entry.getKey().get(profile);
                String strValue = value != null ? value.toString() : "";
                clone = replacePlaceholder(clone, entry.getValue().toLowerCase(), strValue);
            } catch (IllegalAccessException e) {
                throw new RuntimeException("Failed to access field: " + entry.getKey().getName() + " in profile: " + profile.getClass().getName(), e);
            }
        }

        for (Placeholder placeholder : placeholders) {
            usedPlaceholders.add(placeholder.getName().toLowerCase());
            clone = replacePlaceholder(clone, placeholder.getName().toLowerCase(), placeholder.getValue());
        }

        for (String placeholder : requiredPlaceholders) {
            if (!usedPlaceholders.contains(placeholder.toLowerCase())) {
                throw new IllegalArgumentException("Couldn't find required placeholder for name: " + placeholder.toLowerCase());
            }
        }
        return clone;
    }

    private Map<Field, String> mapProfile(Profile profile) {
        Map<Field, String> map = new HashMap<>();
        for (Field field : profile.getClass().getFields()) {
            if (field.isAnnotationPresent(Parameter.class)) {
                Parameter parameter = field.getAnnotation(Parameter.class);
                map.put(field, parameter.name());
            }
        }
        for (Field field : profile.getClass().getDeclaredFields()) {
            if (field.isAnnotationPresent(Parameter.class)) {
                field.setAccessible(true);
                Parameter parameter = field.getAnnotation(Parameter.class);
                map.put(field, parameter.name());
            }
        }
        mappedProfiles.put(profile.getClass(), map);
        return map;
    }

    private String replacePlaceholder(String clone, String name, String value) {
        return clone.replace("%" + name + "%", value);
    }
}