/*
 * Copyright (c) 2018.
 */

package commands;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import com.jagrosh.jdautilities.commons.waiter.EventWaiter;

public class Test extends Command {

    private final EventWaiter waiter;

    public Test(EventWaiter waiter) {
        this.name = "test";
        this.waiter = waiter;
    }

    @Override
    protected void execute(CommandEvent event) {


    }
}
