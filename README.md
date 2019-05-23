# Java Multithreaded TCP Chat

## The application

This application is made as an example of a TCP multithreaded chat application, where multiple clients can communicate with each other via text messages. The application consists of:

###### Main frame - the frame where you start and stop the server. If the server is stopped, no client can be created, otherwise, you can create as much clients as possible. The host I have used is the *localhost* with port *6666* assigned to it. 

###### Main GUI - created on the client side, where user can see the *online users list* and *user count number*. User can send messages via *send* button.

###### Server side - the part where server proceeds requests to every client connected on the server. The server works as a broadcaster.

## Screenshots
**Main frame with server start / stop button and client creation button**

![Screenshot_1](https://user-images.githubusercontent.com/33487958/58261566-d6837e00-7d78-11e9-9d49-8218845a43f0.png)

**Screenshot of 10 users online**

![Screenshot_3](https://user-images.githubusercontent.com/33487958/58261565-d6837e00-7d78-11e9-8cde-c5a931fa8f92.png)

## Implementation

Just download this project and load it in your Java IDE, make sure port *6666* is free and it will be working.
