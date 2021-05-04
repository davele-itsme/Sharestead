<!-- INTRODUCTION -->
<br />
<p align="center">
  <a href ="https://www.youtube.com/watch?v=Pb1qB4GzyJk">
    <img src="https://img.youtube.com/vi/Pb1qB4GzyJk/0.jpg"/>
  </a>
  <h2 align="center">SHARESTEAD</h2>
  <p align="center">
  Android application built for Android development assignment
    <br />
    <a href="https://youtu.be/Pb1qB4GzyJk"><strong>Check out the application by watching this video »</strong></a>
    <br />
  </p>
</p>

<!-- TABLE OF CONTENTS -->
<details open="open">
  <summary>Table of Contents</summary>
  <ol>
    <li>
      <a href="#about-the-project">About The Project</a>
      <ul>
        <li><a href="#built-with">Built With</a></li>
      </ul>
    </li>
    <li>
      <a href="#getting-started">Getting Started</a>
      <ul>
        <li><a href="#prerequisites">Prerequisites</a></li>
        <li><a href="#installation">Installation</a></li>
      </ul>
    </li>
    <li>
       <a href="#inspiration">Inspiration</a>
    </li>
  </ol>
</details>

<!-- ABOUT THE PROJECT -->
## About The Project

Android application, that would create a game community.

<strong>Features:</strong>
* Fetching game information and displaying them
* Note management for games
* Profile customization in the application

### Built With

* [Android Studio](https://developer.android.com/studio)
* [Firebase](https://firebase.google.com/)

<!-- GETTING STARTED -->
## Getting Started

The application is not published, therefore you need to build the game in Android Studio.

Application runs on Android version 6.0 Marshmallow - 9.0 Pie

### Prerequisites

* [Android Studio](https://developer.android.com/studio)
* [Google Account](https://console.firebase.google.com/u/0/) - To access Google Firebase console

### Installation

1. Clone the repository:
  ```
 git clone https://github.com/davele-itsme/Sharestead.git
  ```
2. Open the project in Android Studio
3. The application uses Google Firebase, therefore follow this tutorial [google-services.json tutorial](https://www.youtube.com/watch?v=kts-yg-2vkg)
4. Once you have it, replace google-services.json in the `app` folder
5. EDIT 4.5.2021: RAWG newly requires API key, therefore you need to have an account there. Follow this site to create API key [RAWG](https://rawg.io/apidocs). There will be needed a small tweak in code to adjust API calls.
![image](https://user-images.githubusercontent.com/42817904/117043931-1d70a100-ad0e-11eb-8029-63168151715b.png)
7. Run the application using either mobile phone/tablet or virtual device provided by AVD manager

## Inspiration
I got some inspiration from these source codes/guides: <br />
https://www.youtube.com/watch?v=iA9iqygq11Q <br />
https://www.youtube.com/watch?v=dYbbTGiZ2sA <br />
https://medium.com/@oluwabukunmi.aluko/bottom-navigation-view-with-fragments-a074bfd08711
https://stackoverflow.com/questions/41347150/checking-internet-connection-with-broadcast-receiver-not-working
