package Botti;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import static Botti.UnicaMenuEventListener.*;


public class unicaForwardListener extends ListenerAdapter{


    @Override
    public void onGuildMessageReactionAdd(GuildMessageReactionAddEvent event) {

        if(viikonpaiva>restaurant.getMenusForDays().size()){
            viikonpaiva--;
        }
        //super.onMessageReactionAdd(event);
        if(event.getReactionEmote().getName().equals("▶")&&
            !event.getMember().getUser().equals(event.getJDA().getSelfUser())){
            //if(event.getMember().getUser().equals(event.getChannel().retrieveMessageById(event.getMessageId()).complete().getAuthor())){

            UnicaMenuEventListener.viikonpaiva++;
            System.out.println(restaurant.getErrorMessage());
            if (restaurant.getErrorMessage() == null) {

                event.getChannel().retrieveMessageById(event.getMessageId()).complete().editMessage(embedRestaurant(restaurant).build()).queue(message -> {
                    event.getReaction().removeReaction(event.getUser()).queue();
                    System.out.println(restaurant.getMenusForDays().size()+" menulistan koko");
                    System.out.println(viikonpaiva+" viikonpäivän numero");
                    if(viikonpaiva==restaurant.getMenusForDays().size()-1){

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
