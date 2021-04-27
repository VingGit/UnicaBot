package Botti;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import static Botti.UnicaMenuEventListener.*;


public class unicaForwardListener extends ListenerAdapter{


    @Override
    public void onGuildMessageReactionAdd(GuildMessageReactionAddEvent event) {
        if(viikonpaiva>6){
            viikonpaiva=6;
        }
        //super.onMessageReactionAdd(event);
        if(event.getReactionEmote().getName().equals("▶")&&
            !event.getMember().getUser().equals(event.getJDA().getSelfUser())){
            //if(event.getMember().getUser().equals(event.getChannel().retrieveMessageById(event.getMessageId()).complete().getAuthor())){

            UnicaMenuEventListener.viikonpaiva++;
            System.out.println(event.getMember().getJDA().getSelfUser());
            System.out.println(restaurant.getErrorMessage());
            if (restaurant.getErrorMessage() == null) {

                event.getChannel().retrieveMessageById(event.getMessageId()).complete().editMessage(embedRestaurant(restaurant).build()).queue(message -> {
                    event.getReaction().removeReaction(event.getUser()).queue();
                    if(viikonpaiva==restaurant.getRestaurantMenuArray().size()){

                        message.removeReaction("▶").queue();
                    }
                    else {

                        message.addReaction("◀").queue();
                        message.addReaction("▶").queue();
                    }
                });
                }
        }
    }


}
