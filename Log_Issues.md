# ISSUES

## Exception in thread "JavaFX Application Thread" java.lang.IndexOutOfBoundsException: Index -1 out of bounds for length 3

* Symptoms : 
    - spam during execution
    - no parameters or button lock
    - captures ARE created ans saved w/out any pb

* 1° resolution :
    - remove the "displayTextArea.append(...)"
    - remove ONLY the "displayTextArea.append(...)" in the timer loop