import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.text.ParseException;
import java.util.Objects;

public class BotCommands extends ListenerAdapter {

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event)
    {
        DatabaseCommands app = new DatabaseCommands();
        switch (event.getName()){
            case "add":
                String name = Objects.requireNonNull(event.getOption("name")).getAsString();
                String start = Objects.requireNonNull(event.getOption("start")).getAsString();
                String end = Objects.requireNonNull(event.getOption("end")).getAsString();
                if(!app.insert(name,start,end)) {
                    event.reply(String.format("> **Format dd.MM.YY**\n\n You typed: \n> **Start Date**: %s \n> **End Date**: %s", start,end)).setEphemeral(true).queue();
                }
                else{
                    event.reply("**Done**").setEphemeral(true).queue();
                }
            break;

            case "rm":
                String id = Objects.requireNonNull(event.getOption("id")).getAsString();
                if(!app.delete(id)) {
                    event.reply("Something went wrong!").setEphemeral(true).queue();
                }
                else{
                    event.reply("**Done**").setEphemeral(true).queue();
                }
            break;

            case "query":
                String replytext = ":crown: **" + Objects.requireNonNull(event.getOption("name")).getAsString() + "**\n > Format: id [ StartDate - EndDate ]\n\n";

                if(app.selectName(Objects.requireNonNull(event.getOption("name")).getAsString()) == null){
                    event.reply("**ERR**\n > Check the name again, name not found.").setEphemeral(true).queue();
                    break;
                }
                for (Member member :
                        app.selectName(Objects.requireNonNull(event.getOption("name")).getAsString())) {
                    replytext = replytext.concat(
                            String.format(
                                    "**%s** [ %s - %s ]\n",
                                    member.getId().toString(),
                                    member.getStartDate().toString(),
                                    member.getEndDate().toString()
                            )
                    );
                }
                event.reply(replytext).setEphemeral(true).queue();
            break;

            case "all":
                String replytextII = "";
                try {
                    for (Member member :
                            app.selectAll()) {
                        replytextII = replytextII.concat(
                                String.format(
                                        "**Name: %s ID: %s** [ %s - %s ]\n",
                                        member.getName(),
                                        member.getId().toString(),
                                        member.getStartDate().toString(),
                                        member.getEndDate().toString()
                                )
                        );
                    }
                } catch (ParseException e) {
                    event.reply("**ERR**\n > ParseException.").setEphemeral(true).queue();
                    break;
                }
                event.reply(replytextII).setEphemeral(true).queue();
            break;

            case "setup":
                String channelID = Objects.requireNonNull(event.getOption("channelid")).getAsString();
                PropertiesHandler.setPropertie("channelid", channelID);
                event.reply("Set Channel to ID:" + channelID);
            break;
        }
    }
}
