import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
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
/*REFERENCE FROM OLD BOT
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
*/
        jda.upsertCommand("item", "get info of an item")
                .addOptions(
                        new OptionData(OptionType.STRING, "name", "Name of the item", true)
                ).queue();
               
        jda.updateCommands().queue();
   }
}
