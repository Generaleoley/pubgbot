/*
 * Copyright (c) 2018.
 */

package commands;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import net.dv8tion.jda.core.entities.Member;


public class Guest extends Command {

    public Guest() {
        this.name = "guest";
        this.help = "gives guest role to a user.";
        this.hidden = true;
    }

    @Override
    protected void execute(CommandEvent event) {

        Member member = event.getMessage().getMentionedMembers().get(0);
        member.getGuild().getController().addSingleRoleToMember(member, event.getJDA().getRolesByName("Guest", true).get(0)).queue();
        String name = member.getNickname();

        try {
            event.getGuild().getController().setNickname(member, name + " | Guest").queue();
            System.out.println("I was here");
        } catch (Exception e) {
            event.getGuild().getTextChannelsByName("mod-log", true);
        }
    }
}
