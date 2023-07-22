import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.json.JSONArray;
import org.json.JSONObject;
import net.dv8tion.jda.api.EmbedBuilder;

import java.util.Objects;

public class BotCommands extends ListenerAdapter {

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event)
    {
        DatabaseCommands app = new DatabaseCommands();
        switch (event.getName()){
            case "item":
                String itemName = Objects.requireNonNull(event.getOption("name")).getAsString(); // Replace this with the item name you have
                try {
                    // Send a GET request to Wowhead's search results page
                    String searchUrl = "https://www.wowhead.com/search?q=" + itemName;
                    Document doc = Jsoup.connect(searchUrl).get();

                    // Extract the JavaScript object from the page's HTML
                    String scriptData = doc.selectFirst("script:containsData(Listview)").data();

                    // Find the data JSON array within the JavaScript object
                    int startIndex = scriptData.indexOf("[{");
                    int endIndex = scriptData.lastIndexOf("}]");
                    String jsonData = scriptData.substring(startIndex, endIndex + 2);

                    // Parse the JSON array
                    JSONArray jsonArray = new JSONArray(jsonData);

                    if (jsonArray.length() > 0) {
                        // Get the first item from the JSON array
                        JSONObject itemData = jsonArray.getJSONObject(0);
    
                        // Extract the item ID and name from the item data
                        int itemId = itemData.getInt("id");
                        String itemNameFromData = itemData.getString("name");
    
                        // Ensure the itemName is the same as the searched name without the "-" characters
                        if (itemNameFromData.replace("-", " ").equalsIgnoreCase(itemName)) {
                            // Format the item name for the Wowhead link
                            String formattedItemName = itemName.replace(" ", "+");
    
                            // Build the Wowhead link
                            String wowheadLink = "https://www.wowhead.com/wotlk/item=" + itemId + "/" + formattedItemName;
    
                            // Build the embed
                            EmbedBuilder embedBuilder = new EmbedBuilder()
                                    .setTitle(itemNameFromData)
                                    .setDescription("Item ID: " + itemId + "\n" + wowheadLink)
                                    .setColor(0x00ff00); // Green color (you can use decimal or hexadecimal values)
    
                            // Send the embed as a message to the text channel
                            event.replyEmbeds(embedBuilder.build()).setEphemeral(true).queue();
                        } else {
                            event.reply("Item not found.").setEphemeral(true).queue();
                        }
                    } else {
                        event.reply("Item not found.").setEphemeral(true).queue();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            break;
            
/*REFERENCE FROM OLD BOT
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
 */
            case "setup":
                String channelID = Objects.requireNonNull(event.getOption("channelid")).getAsString();
                PropertiesHandler.setPropertie("channelid", channelID);
                event.reply("Set Channel to ID:" + channelID);
            break;
        }
    }
}
