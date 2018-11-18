/*
 * Copyright (c) 2018.
 */

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandClientBuilder;
import com.jagrosh.jdautilities.commons.waiter.EventWaiter;
import com.sun.prism.paint.Color;
import commands.*;
import commands.Shutdown;
import events.GuildMember;
import net.dv8tion.jda.core.*;
import net.dv8tion.jda.core.entities.Game;
import utils.Constants;

import javax.security.auth.login.LoginException;

public class Main {

    public static void main(String[] args) throws LoginException, IllegalArgumentException {
        // the first is the bot token
        String token = Constants.TOKEN;

        // the second is the bot's owner's id
        String ownerId = Constants.OWNER_ID;

        // define an event waiter, don't forget to add this to the JDABuilder!
        EventWaiter waiter = new EventWaiter();

        // define a command client
        CommandClientBuilder client = new CommandClientBuilder();

        // The default is "Type !!help" (or whatever prefix you set)
        client.useDefaultGame();

        // sets the owner of the bot
        client.setOwnerId(ownerId);

        // sets emojis used throughout the bot on successes, warnings, and failures
        client.setEmojis("\uD83D\uDE03", "\uD83D\uDE2E", "\uD83D\uDE26");

        // sets the bot prefix
        client.setPrefix("!");

        //help
        client.setHelpConsumer(e -> {
            e.getMessage().addReaction("✅").queue();
            EmbedBuilder eb = new EmbedBuilder();
            eb.setColor(java.awt.Color.orange);
            eb.setTitle(":robot:   **Nitrogen Bot Commands**");
            for (Command command : e.getClient().getCommands()) {
                if (!command.isHidden() && (!command.isOwnerCommand() || e.isOwner())) {
                    if (command.getArguments() == null) {
                        eb.addField("**" + e.getClient().getPrefix() + command.getName() + "**", "`" + command.getHelp() + "`", false);
                    } else {
                        eb.addField("**" + e.getClient().getPrefix() + command.getName() + "**" + " " + command.getArguments(), "`" + command.getHelp() + "`", false);
                    }
                } else {
                    if (command.getArguments() == null) {
                        eb.addField("**" + e.getClient().getPrefix() + command.getName() + "**", "`" + command.getHelp() + "`", false);
                    } else {
                        eb.addField("**" + e.getClient().getPrefix() + command.getName() + "**" + " " + command.getArguments(), "`" + command.getHelp() + "`", false);
                    }
                }
            }
            eb.setFooter("Contact " + e.getGuild().getMemberById(e.getClient().getOwnerId()).getUser().getName() + "#" + e.getGuild().getMemberById(e.getClient().getOwnerId()).getUser().getDiscriminator(), e.getGuild().getMemberById(e.getClient().getOwnerId()).getUser().getAvatarUrl());
            e.replyInDm(eb.build());
        });


        // adds commands
        client.addCommands(
                // command to show information about the bot
                new About(Color.BLUE, "a bot made for PUBG",
                        new String[]{"Server Moderation", "Cool commands ", "Fun games!"},
                        new Permission[]{Permission.ADMINISTRATOR}),

                // command to approve a member
                new Approve(),

                // command to ban a member
                new Ban(),

                // command to show a random cat
                new Cat(),

                // command to change the nickname of a user
                new ChangeNick(),

                // command to make a random choice
                new Choose(),

                // command to post a new room
                new CustomRoom(),

                // command to give a user the Guest role
                new Guest(),

                // command to say hello
                new Hello(waiter),

                // command to kick a member
                new Kick(),

                // command to display about legend/clans
                new Legend(),

                // command to add new member
                new NewMember(),

                // command to check bot latency
                new Ping(),

                // command to purge messages
                new Purge(),

                // command to make the bot say something
                new Say(),

                // command to test latest feature
                new Test(waiter),

                // command to add roles to user
                new ToggleRole(),

                // command to shut off the bot
                new Shutdown());

        // start getting a bot account set up
        new JDABuilder(AccountType.BOT)
                // set the token
                .setToken(token)

                // set the game for when the bot is loading
                .setStatus(OnlineStatus.DO_NOT_DISTURB)
                .setGame(Game.playing("loading..."))

                // add the listeners
                .addEventListener(waiter)
                .addEventListener(client.build())
                .addEventListener(new GuildMember())

                // start it up!
                .buildAsync();
    }

}
