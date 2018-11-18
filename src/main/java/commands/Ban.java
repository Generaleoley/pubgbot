/*
 * Copyright (c) 2018.
 */

package commands;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import net.dv8tion.jda.core.entities.Member;

public class Ban extends Command {

    public Ban() {
        this.name = "ban";
        this.help = "bans a user";
        this.ownerCommand = true;
    }

    @Override
    protected void execute(CommandEvent event) {
        Member member = event.getMessage().getMentionedMembers().get(0);

        if (event.getArgs().length() > 23) {
            String reason = event.getArgs().substring(23);
            event.getGuild().getController().ban(member, 7, reason).queue();
        } else {
            event.getGuild().getController().ban(member, 7).queue();
        }

    }
}
