## Android-App-for-Solving-Stray-Cattle-Problem

# What is Stray Cattle Problem?
It is the common problem which is experienced by all of us in day to day life.For example while driving we see a bunch of cattles in group they lie on road or incidently they come in front of us, which can lead to accident.
This sought of situation is not good for humans and also for the cattle. So this Mobile App is to solve the Problem a bit.

# Approach
<b>Type of User's of the App are:-</b>
1) Normal User(Public) :- Their role for this application is to make a complaint at their respective interface if they found any stray cattle.

2) Professional User :- Their role is to visit the location of the cattle, send by the normal user and take the cattle to the organizations or the agencies which will look after them.

3) Admin :- He is the one who has the right to create the professionals at different places. And he also looks after the quality of work performed by the professionals.

# Flow of Complete Process:-
<b>Step 1:-</b>
As the user opens the app he/she has login into his/her profile. If he is not the registered user then
he needs to sign up first. So first step is to sign up
Yourself into the system.

<b>Step 2:-</b>
As the cattle spotted by the user he will sign in.
As he signs in he will see a new page in which first he needs to click a picture of the cattle. And
yet there is no complaint made, the Last Complaint button will show the status of the current
complaint.
Now as the user clicks the picture and clicks on the SEND button the complaint will go to
respective professional i.e., the nearest professional(Which is calculated using the ManHattan
distance) by the location API.
The below image will show how the users screen changes after clicking the TAKE A PICTURE
AND SEND button.
The below image will show how the Professionals screen before and after after clicking SEND
button by the user.

<b>Step 3:-</b>
Now as the Complaint received by the Professional he has the address, location and image of the
cattle. He will visit the Location solve the complaint made by the user. As he completes his
process he will click on the DONE button and his screen gets cleared and the notification is sent
to the user and his Last Complaint section is updated.
The below image shows screen of Professional and Last Complaint section of the user after
solving the Problem.

In this way the complete process takes place.

<b>For clear explanation of the project you can refer the project report, it includes the images which will be easier to understand.</b>

# Technologies Used:-
1) Java
2) Android Studio
3) Firebase(Authentication and Database)
4) Google Location API

<b> This mobile application can further be improved with additional features like by adding image processing concept to it.</b>
<b> This concept will help us to recognise the image whether it is an image of cattle or not as it is not necessary that all the users are trusted.</b>
