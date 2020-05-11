# FakeBook README 
## CS4084 Mobile Application development

Fakebook is a social media application that was written using java and is compatible with android versions 5 and above. The app offers an integrated messaging system where users can communicate with their friends and anybody that they have connected with on the app. The app also has a modern and slick orange colour scheme and allows for a variety of customization options of your personal profile.
The app uses Firebase in order to store user details along with messages by that user.

## Getting Started:

A prerequisite for running the app successfully is a pre-established internet connection as all user details are stored on the FireBase cloud server. 
In order to register for the app you are only _required_ to have an email address, the next step is to input personal information such as date of birth, name and in-app username. After registering, you will be able to log-in and out as you please. 

Once you open the app, there are 4 tabs, The latest tab (2nd from the left) was added towards the end of propject completion, however, it became apparent that it was serving the same purpose as the first tab, and that was a messaging option, where you could select users and send them a message, this message is then referenced in the database and a permanent copy of it remains attached to your profile.

The first tab is redundant and has no unique functionality. __(The second tab replaces it)__

At some point late in the repository, we decided to attempt to simplify the structure of the profile features, changing the information from being stored in a hashmap, to being stored in a separate class with checker statements to ensure details are legitimate. This became problematic, unfortunately and the change profile picture no longer worked but was fine  [at this point](https://github.com/JamesBrosnan1903/FakebookOne/commit/ac46b06d180fac45a3262f9352da17f566b33667) 

The posting feature was not implemented on time, posts were supposed to come up on your home screen that your connections had made.

Otherwise all other aspects of the app that we mentioned in our design document are functioning correctly, there have been some changes in UI, so the current visual look of the app does not correspond with what was initially drafted in our document.


### More Information:
[Design] (https://github.com/JamesBrosnan1903/FakebookOne/blob/master/design.md)
[Structure] (https://github.com/JamesBrosnan1903/FakebookOne/blob/master/structure.md)
