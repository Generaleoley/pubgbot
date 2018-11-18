/*
 * Copyright (c) 2018.
 */

package commands;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import models.Misc;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.TextChannel;
import org.apache.commons.lang3.StringUtils;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Random;


public class Approve extends Command {

    public Approve() {
        this.name = "approve";
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

        Member member;
        String clanName;

        try {
            member = event.getMessage().getMentionedMembers().get(0);
            clanName = event.getArgs().split(" ")[1];
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        Random random = new Random();

        StringBuilder stringBuilder = new StringBuilder();

        int code = 0;
        // generating code
        while (code < 6) {
            stringBuilder.append(random.nextInt(9));
            code++;
        }

        if (!clanNames.contains(clanName)) {
            event.replyWarning(":no_entry: Invalid clan name!");
            return;
        }


        EmbedBuilder builder = new EmbedBuilder();
        builder.setAuthor(event.getGuild().getName(), null, event.getGuild().getIconUrl());

        for (String name : clanNames) {
            if (name.equalsIgnoreCase(clanName)) {
                clanName = StringUtils.capitalize(name);
                builder.setTitle("Congratulations, You have been approved to join LeGeND " + clanName);
            }
        }

        builder.addField("**Recruit Code**", stringBuilder.toString() + " (Send this code in the join request message)", true);

        builder.addField("**That's it!**", "Now wait for your clan leadership to accept you. If you do not see a 'request to join' button, make sure you leave your current clan and check the trophy requirements.", true);

        builder.addField("**Important**", "Once your clan leadership has accepted your request, let a staff member in discord know that you have been accepted. They will then unlock all the member channels for you.", false);

        member.getUser().openPrivateChannel().queue((channel) ->
                channel.sendMessage(builder.build()).queue());

        TextChannel textChannel = event.getGuild().getTextChannelsByName("newly-joined", true).get(0);

        textChannel.sendMessage(new EmbedBuilder()
                .setTitle("New Recruit")
                .addField("Name", member.getNickname(), true)
                .addField("Code", Integer.toString(code), true)
                .addField("Clan", clanName, true)
                .setFooter("Bot by " + event.getGuild().getMemberById(event.getClient().getOwnerId()).getUser().getName(), event.getGuild().getMemberById(event.getClient().getOwnerId()).getUser().getAvatarUrl())
                .build()
        ).queue();
    }
}
