/*
 * Copyright (c) 2018.
 */

package commands;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.Role;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

public class Purge extends Command {
    public Purge() {
        this.name = "purge";
        this.aliases = new String[]{"p"};
        this.help = "deletes messages";
        this.hidden = true;
    }

    @Override
    protected void execute(CommandEvent event) {
        int iter = 0;
        int amount = Integer.parseInt(event.getArgs());
        String myMsgID = event.getMessage().getId();
        Member member = event.getMember();
        Role clanDeputy = event.getGuild().getRolesByName("clan deputy", true).get(0);
        Role admin = event.getGuild().getRolesByName("clan deputy", true).get(0);
        List<Role> roleList = member.getRoles();

        if (!roleList.contains(clanDeputy) || !roleList.contains(admin)) {
            return;
        }

        event.getMessage().delete().queue();

        event.getChannel().getHistoryBefore(myMsgID, amount).complete();
        List<Message> msg = event.getChannel().getHistory().retrievePast(amount).complete();
        ArrayList<String> msgIDS = new ArrayList<>();

        while (iter < amount) {
            msgIDS.add(msg.get(iter).getId());
            iter++;
        }

        // using try catch because the if the amount is greater than 100 there is an error (IndexOutOfBoundsException)

        try {
            event.getChannel().purgeMessagesById(msgIDS);
            Consumer<Message> callback = (response) -> response.delete().queueAfter(3, TimeUnit.SECONDS);
            event.getChannel().sendMessage(":white_check_mark: Purged " + amount + " messages!").queue(callback);
        } catch (IndexOutOfBoundsException e) {
            Consumer<Message> callback = (response) -> response.delete().queueAfter(3, TimeUnit.SECONDS);
            event.getChannel().sendMessage(":white_check_mark: Purged " + amount + " messages!").queue(callback);
        }

    }
}
