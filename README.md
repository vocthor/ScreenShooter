## Introduction

This program was made by [@vocthor](https://github.com/vocthor/).
Its purpose is to take screen captures periodically.


## Disclaimer

This program is made 100% in Java. 
This program is still in development, and i'm not a genius. I am not responsible for a bad use. If you discover a new bug, problem, ... or have an idea for a new feature, please report it.
A lot of features are not implemented yet, and are still in development, but for now this program is functional for a basic use.


## Parameters

 - Monitor menu allows you to choose which part of your screen to capture :
    * `Primary monitor` captures only the screen of your primary monitor
    * `All Monitors` creates a big capture of all your monitors
    * `Selected Area` allows you to take captures of only a part of your screen (still in development)
 - `Path` : must contain the absolute path of where to store the captures.
 - `Name` : generic name of your captures. For now the file's name is in format "YY-MM-DD_Name_id.jpg".
 - `Period` : how much between each capture (in milliseconds).
 - `Save Changes` : saves the current parameters.
 - `Start Capture` : starts the capture session. You can't change the parameters while capturing.
 - `Pause Capture` : pauses the capture session. You can't change the parameters. Press `Start Capture` to resume the current capture session.
 - `Stop Capture` : stops the capture session. Reset the `Path`, `Name`, and `Period` parameters.
 - `Console` : just a tab where you can see the run of the program.
 
 
## How to use

 - Start the program, a new window will appear.
 - Enter a valid absolute path, a generic name (not mandatory), and set up the time period.
 - Save changes
 - Start the capture session
 - Pause / Stop the capture session


## Features not implemented yet

* `File` and `Monitor` menus
* `Capture` tab
* Better console

(and don't mind the App.java file)