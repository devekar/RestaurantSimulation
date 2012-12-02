Vaibhav Devekar
devekar.1@osu.edu
===============================================================
Restaurant Simulation using multi-threading and synchronization
===============================================================


How to run program
------------------
To run the program, navigate to the directory containing the java files and execute the following shell commands:

>javac Main.java
>java Main <FILEPATH>

where <FILEPATH> is a command-line argument specifying the filepath for input file. 
The output file will be produced in the same directory as the input file.


The data int the output file is formatted as:
Arrival time : TableNo CookId   SeatedTime   BurgerTime FriesTime CokeTime   ServedTime LeftTime

where BurgerTime,FriesTime and CokeTime specify the time when cook started using the machines.


Description
-----------
The Main thread simulates time via Clock object as well as activates Diner threads at their arrival time.

The Diner threads contend to acquire tables while the Cook threads contend to acquire tables to serve as well as the machines.
If the corresponding resources are not available, they wait on NoWork object.

The notion of time in the simulation is enforced as follows.
The active Diner threads as well as Cook threads are required to log their presence with the Clock object.
Only when all the active threads have signed their presence, does the Main thread increment the clock.
The Clock object maintains an array for diners as well as for cooks to log their presence.


Files
-----
1) Main.java
2) Restaurant.java
3) Diner.java
4) DinerInfo.java
5) Cook.java
6) Table.java
7) Burger.java
8) Fries.java
9) Coke.java
10)NoWork.java
11)Clock.java

