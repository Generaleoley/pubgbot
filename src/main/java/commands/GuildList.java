/*
 * Copyright (c) 2018.
 */

package commands;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import com.jagrosh.jdautilities.commons.waiter.EventWaiter;
import com.jagrosh.jdautilities.doc.standard.CommandInfo;
import com.jagrosh.jdautilities.doc.standard.Error;
import com.jagrosh.jdautilities.doc.standard.RequiredPermissions;
import com.jagrosh.jdautilities.menu.Paginator;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.ChannelType;
import net.dv8tion.jda.core.exceptions.PermissionException;

import java.awt.*;
import java.util.concurrent.TimeUnit;

@CommandInfo(
        name = "GuildList",
        description = "Gets a paginated list of the guilds the bot is on.",
        requirements = {
                "The bot has all necessary permissions.",
                "The user is the bot's owner."
        }
)
@Error(
        value = "If arguments are provided, but they are not an integer.",
        response = "[PageNumber] is not a valid integer!"
)
@RequiredPermissions({Permission.MESSAGE_EMBED_LINKS, Permission.MESSAGE_ADD_REACTION})


public class GuildList extends Command {

    private final Paginator.Builder pbuilder;

    public GuildList(EventWaiter waiter) {
        this.name = "guildlist";
        this.help = "shows the list of guilds the bot is on";
        this.arguments = "[pagenum]";
        this.botPermissions = new Permission[]{Permission.MESSAGE_EMBED_LINKS, Permission.MESSAGE_ADD_REACTION};
        this.guildOnly = false;
        this.ownerCommand = true;
        pbuilder = new Paginator.Builder().setColumns(1)
                .setItemsPerPage(10)
                .showPageNumbers(true)
                .waitOnSinglePage(false)
                .useNumberedItems(false)
                .setFinalAction(m -> {
                    try {
                        m.clearReactions().queue();
                    } catch (PermissionException ex) {
                        m.delete().queue();
                    }
                })
                .setEventWaiter(waiter)
                .setTimeout(1, TimeUnit.MINUTES);
    }

    @Override
    protected void execute(CommandEvent event) {
        int page = 1;
        if (!event.getArgs().isEmpty()) {
            try {
                page = Integer.parseInt(event.getArgs());
            } catch (NumberFormatException e) {
                event.reply(event.getClient().getError() + " `" + event.getArgs() + "` is not a valid integer!");
                return;
            }
        }
        pbuilder.clearItems();
        event.getJDA().getGuilds().stream()
                .map(g -> "**" + g.getName() + "** (ID:" + g.getId() + ") ~ " + g.getMembers().size() + " Members")
                .forEach(pbuilder::addItems);
        Paginator p = pbuilder.setColor(event.isFromType(ChannelType.TEXT) ? event.getSelfMember().getColor() : Color.black)
                .setText(event.getClient().getSuccess() + " Guilds that **" + event.getSelfUser().getName() + "** is connected to"
                        + (event.getJDA().getShardInfo() == null ? ":" : "(Shard ID " + event.getJDA().getShardInfo().getShardId() + "):"))
                .setUsers(event.getAuthor())
                .build();
        p.paginate(event.getChannel(), page);
    }

}
