/*
 * Copyright (c) 2018.
 */

package commands;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import net.dv8tion.jda.core.entities.Member;

public class Kick extends Command {

    public Kick() {
        this.name = "kick";
        this.help = "kicks a user";
        this.ownerCommand = true;
    }

    @Override
    protected void execute(CommandEvent event) {
        Member member = event.getMessage().getMentionedMembers().get(0);

        if (event.getArgs().length() > 23) {
            String reason = event.getArgs().substring(23);
            event.getGuild().getController().kick(member, reason).queue();
        } else {
            event.getGuild().getController().kick(member).queue();
        }

    }
}
