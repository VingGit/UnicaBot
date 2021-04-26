package Botti;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.lang.reflect.Member;

import static Botti.UnicaMenuEventListener.*;


public class unicaBackwardsListener extends ListenerAdapter{


    @Override
    public void onGuildMessageReactionAdd(GuildMessageReactionAddEvent event) {
        if(viikonpaiva<0){
            viikonpaiva=0;
        }
        //super.onMessageReactionAdd(event);
        if(event.getReactionEmote().getName().equals("◀")&&
                !event.getMember().getUser().equals(event.getJDA().getSelfUser())){
            //if(event.getMember().getUser().equals(event.getChannel().retrieveMessageById(event.getMessageId()).complete().getAuthor())){

            UnicaMenuEventListener.viikonpaiva--;
            System.out.println(event.getMember().getJDA().getSelfUser());
            System.out.println(restaurant.getErrorMessage());
            if (restaurant.getErrorMessage() == null) {

                event.getChannel().retrieveMessageById(event.getMessageId()).complete().editMessage(embedRestaurant(restaurant).build()).queue(message -> {
                    event.getReaction().removeReaction(event.getUser()).queue();
                    if(viikonpaiva==0){

                        message.removeReaction("◀").queue();
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
