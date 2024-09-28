package xyz.goldendupe.discord;

import bet.astral.mojang.Mojang;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.exceptions.ErrorHandler;
import net.dv8tion.jda.api.requests.ErrorResponse;
import net.dv8tion.jda.api.requests.GatewayIntent;

public class DiscordBot {
    public static final Mojang MOJANG = new Mojang();
    private static final String ID = System.getenv("GOLDENDUPE_DISCORD_ID");
    private final JDA jda;
    private final long communityDiscord;
    private final long developerDiscord;

    public static void main(String[] args){
        DiscordBot bot = new DiscordBot(JDABuilder.create(ID,
                GatewayIntent.GUILD_MESSAGE_POLLS, GatewayIntent.MESSAGE_CONTENT, GatewayIntent.GUILD_MESSAGES, GatewayIntent.GUILD_MESSAGE_REACTIONS)
                .build(), 1206234598758027304L);

        try {
            bot.jda.awaitReady();
            TextChannel channel = bot.jda.getGuildById(bot.communityDiscord).getTextChannelById(1206234599525580832L);

            channel.retrieveMessageById(1287901789173059657L).queue((message) -> {
                message.reply("Stop pinging me!!!!").queue();
                System.out.println("Message Content: " + message.getContentDisplay());
            }, new ErrorHandler().handle(ErrorResponse.UNKNOWN_MESSAGE, (e) -> {
                System.out.println("ERROR: " + e.getMeaning());
            }));
            bot.jda.shutdown();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public DiscordBot(JDA jda, long communityDiscord){
        this.jda = jda;
        this.communityDiscord = communityDiscord;
        this.developerDiscord = communityDiscord;
    }
}
