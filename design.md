# Technical design choices
The main storage facility for user details and messages was Firebase, this is a cloud storage option that offers substantial storage for most things, but for things like sending picture messages in chat, there was far too little storage available to mitigate the errors that come with picture image spam in chat. 

We used the Glide, Pretty Text and Picasso libraries in order to facilitate the transfer of certain file types like images to and from the Database as well as direct incorporation of the data into our Application's user interface.

# Visual design
The colour scheme of the app was an important conversation as we needed to make something that sets us apart from Silicon Valley's prized "Blue Apps" , so we went with an orange tone that can be seen across the app and a subtle Grey logo with hints of the aforementioned Orange shade. One of our biggest setbacks was regulating UI display in accordance with screen-size, as we all have different phones and different emulators are being run, we found that the difference in display can sometimes be quite huge, this can be seen on the connections tab (tab 2) where on some of our phones, we could not see the text field for sending the message, but others could so it went unnoticed until it was noticed in the final test.

## What we would do differently next time

We have learned that it is more important to focus on functionality rather than the styling of the UI, this was a hole we ended up getting caught in quite regularly because with every complex change in UI with something even as simple as a button, the reference to that UI item's functionality could get affected (depending on what view it was located) in which would result in a time-consuming reverting of UI changes to return functionality back.

We have gained a lot more experience using GitHub now, initially, we would make a lot of changes and only commit once, but towards the end of the project, we would try to commit every change we made, so as to ensure that all of us were working with the same application.
