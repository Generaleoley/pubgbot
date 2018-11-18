/*
 * Copyright (c) 2018.
 */

package commands;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.Role;
import org.apache.commons.lang3.StringUtils;

import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;


public class ToggleRole extends Command {

    public ToggleRole() {
        this.name = "togglerole";
        this.aliases = new String[]{"tr"};
    }

    @Override
    protected void execute(CommandEvent event) {
        String roleName = event.getArgs();
        Member member = event.getMember();

        if (roleName == null || StringUtils.isEmpty(roleName)) {
            event.reply("```Toggleable Roles : Custom```");
            return;
        }

        if (roleName.equalsIgnoreCase("custom")) {
            Role role = event.getGuild().getRolesByName("custom", true).get(0);
            event.getGuild().getController().addSingleRoleToMember(member, role).queue();
            Consumer<Message> callback = (response) -> response.delete().queueAfter(3, TimeUnit.SECONDS);
            event.getChannel().sendMessage(":white_check_mark: Toggled Custom Role!").queue(callback); // ^ calls that
        }
    }
}
