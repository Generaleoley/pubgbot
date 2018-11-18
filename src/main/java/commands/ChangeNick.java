/*
 * Copyright (c) 2018.
 */

package commands;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.managers.GuildController;

public class ChangeNick extends Command {

    public ChangeNick() {
        this.name = "changenick";
    }

    @Override
    protected void execute(CommandEvent event) {
        Member member = event.getMessage().getMentionedMembers().get(0);
        GuildController guildController = event.getGuild().getController();

        guildController.setNickname(member, event.getArgs().substring(23)).queue();


    }
}
