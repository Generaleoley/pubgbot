/*
 * Copyright (c) 2018.
 */

package commands;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;

public class Legend extends Command {

    public Legend() {
        this.name = "legend";
        this.hidden = true;
    }

    @Override
    protected void execute(CommandEvent event) {
        event.reply("```\uD83D\uDD25 Want a clan that offers more than other clans? **Legend Family is unique!** \uD83D\uDD25\n" +
                "\n" +
                "We have all the usual that everyone else has: high activity and well organized leadership.\n" +
                "\n" +
                "\n" +
                "\n" +
                "What makes us **unique**?\n" +
                "\n" +
                "\uD83D\uDCB0 GRAND PRIZES for weekly events!\n" +
                "⚔ EXPERTS in Customs Rooms, We create a room and members from our 2 clans join it.\n" +
                "\uD83C\uDFC6 DAILY Custom rooms\n" +
                "\uD83D\uDD25 2 clan family!\n" +
                "\uD83C\uDF0E 24/7 active discord server. Discord invite code: https://discord.gg/v4XqSzZ\n" +
                "\uD83D\uDCAA Competitive eSport team: To parcipate in Events, Tournaments, Clan wars and more.\n" +
                "\n" +
                "**Join a clan. Become a Legend.**\n" +
                "\n" +
                "Here are our clans:\n" +
                "\n" +
                "*(Clan name - Clan level - Required Tier - Required level - Approximate clan activity at end of every week)*\n" +
                "\n" +
                "\uD83D\uDD25 LeGeND Phoenix – 6 – Platinum I+ – Level 10 - 25k\n" +
                "\uD83D\uDD25 LeGeND Flame – 5 - Platinum V+ – level 20 – 33k```");
    }

}
