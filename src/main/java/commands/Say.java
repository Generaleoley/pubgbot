/*
 * Copyright (c) 2018.
 */

package commands;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;

public class Say extends Command {

    public Say() {
        this.name = "say";
        this.hidden = true;
        this.ownerCommand = true;
    }

    @Override
    protected void execute(CommandEvent event) {
        String args = event.getArgs();
        event.getMessage().delete().complete();
        event.reply(args);
    }
}
