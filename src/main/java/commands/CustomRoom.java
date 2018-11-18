/*
 * Copyright (c) 2018.
 */

package commands;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.entities.TextChannel;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;

public class CustomRoom extends Command {

    public CustomRoom() {
        this.name = "customroom";
        this.aliases = new String[]{"cr"};
        this.help = "posts info about a custom room.";
    }

    @Override
    protected void execute(CommandEvent event) {
        //!cc Room 1767 pass Asia duo erangle 5 minutes

        String[] items = event.getArgs().split(",");

        ArrayList<String> timeUnits = new ArrayList<>();
        timeUnits.add("minutes");
        timeUnits.add("mins");
        timeUnits.add("m");
        timeUnits.add("min");
        timeUnits.add("minute");
        timeUnits.add("hour");
        timeUnits.add("hours");
        timeUnits.add("h");

        if (items.length < 8) {

            return;
        }


        String name = StringUtils.capitalize(items[0]);
        String id = StringUtils.capitalize(items[1].replaceAll("\\s", ""));
        String password = StringUtils.capitalize(items[2].replaceAll("\\s", ""));
        String server = StringUtils.capitalize(items[3].replaceAll("\\s", ""));
        String gameMode = StringUtils.capitalize(items[4].replaceAll("\\s", ""));
        String map = StringUtils.capitalize(items[5].replaceAll("\\s", ""));

        String[] time = items[7].split(" ");

        String timeAmount = StringUtils.capitalize(time[0].replaceAll("\\s", ""));
        String unit = time[1].replaceAll(" ", "");

        if (!timeUnits.contains(unit)) {
            event.replyWarning("error");
            return;
        }

        EmbedBuilder builder = new EmbedBuilder();
        builder.setAuthor(event.getAuthor().getName(), null, event.getAuthor().getAvatarUrl());
        builder.setTitle("New Room created!");
        builder.addField("Room Name", name, true);
        builder.addField("Room ID", id, true);
        builder.addField("Room Password", password, true);
        builder.addField("Server", server, true);
        builder.addField("Game Mode", gameMode, true);
        builder.addField("Map", map, true);
        builder.addField("Time", timeAmount, true);
        builder.setTimestamp(event.getMessage().getCreationTime());

        TextChannel textChannel = event.getGuild().getTextChannelsByName("custom-rooms", true).get(0);

        Role role = event.getGuild().getRolesByName("custom", true).get(0);

        textChannel.sendMessage(role.getAsMention()).queue();
        textChannel.sendMessage(builder.build()).queue();

    }
}
