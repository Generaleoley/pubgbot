/*
 * Copyright (c) 2018.
 */

package commands;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import models.Misc;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.exceptions.HierarchyException;
import net.dv8tion.jda.core.managers.GuildController;
import org.apache.commons.lang3.StringUtils;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

public class NewMember extends Command {

    public NewMember() {
        this.name = "newmember";
        this.aliases = new String[]{"nm"};
    }

    @Override
    protected void execute(CommandEvent event) {
        ArrayList<String> clanNames;
        try {
            Gson gson = new Gson();
            JsonReader reader = new JsonReader(new FileReader(System.getProperty("user.dir") + "/src/main/java/utils/Misc.json"));
            Misc misc = gson.fromJson(reader, Misc.class);
            clanNames = misc.getClanNames();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            event.reply("`There was an error please check your console log`");
            return;
        }
        Member member = null;
        String roleName = "";

        try {
            member = event.getMessage().getMentionedMembers().get(0);
            roleName = event.getArgs().split(" ")[1];
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (member == null || roleName.equalsIgnoreCase("")) {
            event.reply("Please make sure you typed this correctly!");
            return;
        }

        System.out.println(clanNames);
        System.out.println(roleName);

        if (!clanNames.contains(roleName.toLowerCase())) {
            event.replyWarning("Invalid clan name!");
            return;
        }

        Role role = event.getGuild().getRolesByName(roleName, true).get(0);

        event.getGuild().getController().addSingleRoleToMember(member, role).queue();

        for (String clanName : clanNames) {
            if (roleName.equalsIgnoreCase(clanName)) {
                String nickname = member.getUser().getName() + " | " + StringUtils.capitalize(clanName);
                if (nickname.length() > 32) {
                    event.replyWarning("Nickname length exceeds the allowed length for a nickname.");
                    return;
                }
                GuildController guildController = event.getGuild().getController();
                try {
                    guildController.setNickname(member, member.getNickname() + " | " + StringUtils.capitalize(clanName)).queue();
                    guildController.addSingleRoleToMember(member, role).queue();
                } catch (HierarchyException e) {
                    Consumer<Message> callback = (response) -> response.delete().queueAfter(3, TimeUnit.SECONDS);
                    event.getChannel().sendMessage(":no_entry: You do not have the powers to do this!").queue(callback);
                } finally {
                    TextChannel textChannel = event.getGuild().getTextChannelsByName("general", true).get(0);
                    textChannel.sendMessage("Everybody welcome + " + member.getAsMention() + "!").queue();
                }
            }
        }


    }

}
