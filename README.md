*NOTE: This is a small scale project for a mandatory course in Turku University aiming to teach students cooperation, project management and flow skills. Thus, documentation is mainly in Finnish.

# Topic

Our team decided to create a Discord bot that would serve the needs of Discord users looking to have a bite to eat at Turku University campus.

# Packages

## Botti

Package for UnicaBot's functionalities with following classes:

### BotEventListener 
  * settings for UnicaBot, f.ex. command prefix
### UnicaMenuEventListener
  * commands for printing Unica restaurants' menus
### Botti
  * creates new Botti instances together with above classes
### jarBootMain
  * checks if launch is from local or remote and creates GUI based on this 
  * unfinished

## JSONParse

Package for determining the output specs of a restaurant

### SetMenu
  * creates each menu element or lunch option as name(lunch/dessert/take away), price(student/staff/other) and food items
### MenusForDay
  * combines above menu elements into a full menu list
### Restaurant
  * creates a Restaurant object that holds info of a restaurant retrieved from Unica Json API
