/*
 * Copyright (c) 2018.
 */

package events;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.core.events.guild.member.GuildMemberLeaveEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.awt.*;

public class GuildMember extends ListenerAdapter {

    public void onGuildMemberJoin(GuildMemberJoinEvent event) {
        TextChannel textChannel = event.getGuild().getTextChannelsByName("gate-in-out", true).get(0);
        User user = event.getUser();
        textChannel.sendMessage(
                new EmbedBuilder()
                        .setAuthor(user.getName(), null, user.getAvatarUrl())
                        .setDescription("User joined!")
                        .setColor(Color.GREEN)
                        .build()
        ).queue();
    }

    public void onGuildMemberLeave(GuildMemberLeaveEvent event) {
        TextChannel textChannel = event.getGuild().getTextChannelsByName("gate-in-out", true).get(0);
        User user = event.getUser();
        if (user.getJDA().getRolesByName("guest", true) != null) {
            textChannel.sendMessage(
                    new EmbedBuilder()
                            .setAuthor(user.getName(), null, user.getAvatarUrl())
                            .setDescription("Guest Left")
                            .setColor(Color.RED)
                            .build()
            ).queue();
        } else if (user.getJDA().getRoles() == null) {
            textChannel.sendMessage(
                    new EmbedBuilder()
                            .setAuthor(user.getName(), null, user.getAvatarUrl())
                            .setDescription("User Left")
                            .setColor(Color.RED)
                            .build()
            ).queue();
        } else {
            textChannel.sendMessage(
                    new EmbedBuilder()
                            .setAuthor(user.getName(), null, user.getAvatarUrl())
                            .setDescription("Member Left")
                            .setColor(Color.RED)
                            .build()
            ).queue();
        }

    }

}

