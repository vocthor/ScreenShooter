# SCREENSHOOTER


## Introduction

This program was made by [@vocthor](https://github.com/vocthor/).
Its purpose is to take screen captures periodically.


## Disclaimer

This program is made 100% in Java. 
This program is still in development, and i'm not a genius. I am not responsible for a bad use (even if there should'nt be one). If you discover a new bug, problem, ... or have an idea for a new feature, please report it.
A lot of features are not implemented yet, and are still in development, but for now this program is functional for a basic use.


## Parameters

- File menu allows you to clear the console tab.
- Monitor menu allows you to choose which part of your screen to capture :
   * `Primary monitor` captures only the screen of your primary monitor
   * `All Monitors` creates a big capture of all your monitors
   * `Selected Area` allows you to take captures of only a part of your screen
- `Help` : display this file into the console tab.
- `Path` : **MUST** contain the absolute path of where to store the captures.
- `Browse` : helps you chossing the Path parameter.
- `Name` : generic name of your captures (not mandatory). For now the file's name is in format "YY-MM-DD_Name_id.jpg".
- `Period` : how much time between each capture (in milliseconds).
- `Save Changes` : saves the current parameters.
- `Start Capture` : starts the capture session. You can't change the parameters while capturing.
- `Pause Capture` : pauses the capture session. You can't change the parameters. Press `Start Capture` to resume the current capture session.
- `Stop Capture` : stops the capture session. Reset the `Path`, `Name`, and `Period` parameters, as well as the selected monitor.
- `Console` : just a tab where you can see the run of the program.

 
## How to use

1. Start the program, the main window will appear.
2. Enter a valid absolute path, a generic name (not mandatory), and set up the time period.
3. Select the area to capture
>If you choose `Selected Area` a new window with low opacity will appear.
>Just superpose this window on the area you want to select (you can resize and move it).
>Then draw a rectangle on the new window by pressing your mouse on the top-left corner and releasing it on the down-right corner.
>This rectangle must match the area you want to capture.
>The red rectangle IS NOT lined with the area you selected. It just have the same surface area.
>You can now close this window
4. **SAVE CHANGES**
5. Start the capture session
6. Pause / Stop the capture session


## Features not implemented yet

* `New Window` button
* `Capture` tab
* Better console

(and don't mind the App.java file)
