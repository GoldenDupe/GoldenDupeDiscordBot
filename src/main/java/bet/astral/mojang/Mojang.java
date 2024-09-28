package bet.astral.mojang;

import bet.astral.mojang.network.Link;
import bet.astral.mojang.network.Placeholder;
import bet.astral.mojang.network.Profile;
import bet.astral.mojang.profile.Name;
import bet.astral.mojang.profile.NameProfile;
import bet.astral.mojang.profile.UUIDProfile;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.concurrent.CompletableFuture;

public class Mojang {
    private final Gson gson = new Gson();
    public <T extends Profile> CompletableFuture<T> getAsync(Class<T> clazz, Placeholder... placeholders){
        return getAsync(clazz, null, placeholders);
    }
    public <T extends Profile> CompletableFuture<T> getAsync(Class<T> clazz, Profile profile, Placeholder... placeholders){
        return CompletableFuture.supplyAsync(()->request(clazz, profile, placeholders));
    }
    public <T extends Profile> T get(Class<T> clazz, Placeholder... placeholders){
        return get(clazz, null, placeholders);
    }
    public <T extends Profile> T get(Class<T> clazz, Profile profile, Placeholder... placeholders){
        return request(clazz, profile, placeholders);
    }
    private <T> T request(Class<T> clazz, Profile profile, Placeholder... placeholders) {
        try {
            Link link = (Link) clazz.getField("LINK").get(null);
            String url;
            if (profile == null){
                url = link.create(placeholders);
            } else{
                url = link.create(profile, placeholders);
            }
            URL urlR = URI.create(url).toURL();
            HttpURLConnection connection = (HttpURLConnection) urlR.openConnection();
            connection.setConnectTimeout(10000);
            connection.setReadTimeout(10000);

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            return gson.fromJson(reader, clazz);
        } catch (NoSuchFieldException | IllegalAccessException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public CompletableFuture<NameProfile> getNameProfileASync(String name) {
        return getAsync(NameProfile.class, Placeholder.of("name", name));
    }
    public NameProfile getNameProfile(String name) {
        return get(NameProfile.class, Placeholder.of("name", name));
    }
    public CompletableFuture<NameProfile> getNameProfileASync(Name name) {
        return getAsync(NameProfile.class, Placeholder.of("name", name));
    }
    public NameProfile getNameProfile(Name name) {
        return get(NameProfile.class, Placeholder.of("name", name));
    }
    public CompletableFuture<NameProfile> getNameProfileASync(Profile profile) throws IllegalArgumentException{
        return getAsync(NameProfile.class, profile);
    }
    public NameProfile getNameProfile(Profile profile) throws IllegalArgumentException{
        return get(NameProfile.class, profile);
    }
    public CompletableFuture<UUIDProfile> getUUIDProfileASync(String name) {
        return getAsync(UUIDProfile.class, Placeholder.of("name", name));
    }
    public UUIDProfile getUUIDProfile(String name) {
        return get(UUIDProfile.class, Placeholder.of("name", name));
    }
    public CompletableFuture<UUIDProfile> getUUIDProfileASync(Name name) {
        return getAsync(UUIDProfile.class, Placeholder.of("name", name));
    }
    public UUIDProfile getUUIDProfile(Name name) {
        return get(UUIDProfile.class, Placeholder.of("name", name));
    }
    public CompletableFuture<UUIDProfile> getUUIDProfileASync(Profile profile) throws IllegalArgumentException{
        return getAsync(UUIDProfile.class, profile);
    }
    public UUIDProfile getUUIDProfile(Profile profile) throws IllegalArgumentException{
        return get(UUIDProfile.class, profile);
    }
    public static void main(String[] args){
        runTest("ycu");
        runTest("antritus");
        runTest("aaa");
        runTest("bbb");
        runTest("ccc");
        runTest("dcd");
    }

    private static void runTest(String name){
        Mojang api = new Mojang();
        NameProfile profile = api.getNameProfile(name);
        System.out.println(profile.getName());
        System.out.println(profile.getUniqueId());
        UUIDProfile uuidProfile = api.getUUIDProfile((Profile) profile);
        System.out.println(uuidProfile.getName());
        System.out.println(uuidProfile.getUniqueId());

        System.out.println(uuidProfile.getProperties());

        System.out.println();
    }
}
