*NOTE: This is a small scale project for a mandatory course in Turku University aiming to teach students cooperation, project management and flow skills. Thus, documentation is mainly in Finnish.

# Topic

Our team decided to create a Discord bot that would serve the needs of Discord users looking to have a bite to eat at Turku University campus.

# Packages

## Botti

Package contains classes for UnicaBot's functionalities:

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

Package contains classes for determining the output specs of a restaurant:

### SetMenu
  * creates each menu element or lunch option as name(lunch/dessert/take away), price(student/staff/other) and food items
### MenusForDay
  * combines above menu elements into a full menu list
### Restaurant
  * creates a Restaurant object that holds json parameters of a restaurant retrieved from Unica Json API

## GUI

Package contains classes for creating the GUI and handling it:

###ControllerForLogin
* controller class for Login window
###ControllerForScene2
* controller class for second window that is lauched after successful login
###Locations
* controller for using and manipulating locations.json and the Restaurant listing
###Main
* launches the GUI application

##Config

Package contains classes for UnicaBot configuration:

###Configuration
* creates a Configuration object that holds the current settings
###EditConfig
* handles editions of the config, such as prefix changes




