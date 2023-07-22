import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.interactions.commands.DefaultMemberPermissions;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

import java.util.Collections;

public class Bot
{
    public static void main(String[] args)
    {
        String token = PropertiesHandler.getPropertie("bot.token.master");
        JDA jda = JDABuilder.createLight(token, Collections.emptyList())
                .addEventListeners(new BotCommands())
                .setActivity(Activity.listening("/"))
                .setStatus(OnlineStatus.ONLINE)
                .build();
        jda.upsertCommand("add", "add new entry")
                .addOptions(
                        new OptionData(OptionType.STRING, "name", "Name of the player", true),
                        new OptionData(OptionType.STRING, "start", "Start of vacation | Format: DD.MM.YYYY [Devider: {. , - , /}]", true),
                        new OptionData(OptionType.STRING, "end", "End of vacation | Format: DD.MM.YYYY [Devider: {. , - , /}]", true)
                ).queue();

        jda.upsertCommand("rm", "remove a entry")
                .addOptions(
                        new OptionData(OptionType.STRING, "id", "Key of the Data", true)
                ).queue();

        jda.upsertCommand("query", "query the bot db, by name")
                .addOption(OptionType.STRING, "name", "Name of the member", true)
                .setDefaultPermissions(DefaultMemberPermissions.enabledFor(Permission.ADMINISTRATOR))
                .queue();

        jda.upsertCommand("all", "shows all items")
                .setDefaultPermissions(DefaultMemberPermissions.enabledFor(Permission.ADMINISTRATOR))
                .queue();

        jda.upsertCommand("setup", "setup")
                .addOptions(
                        new OptionData(OptionType.STRING, "channelid", "what channel the messages get posted in: Example: 1089967425040167063", true)
                ).queue();
   }
}
