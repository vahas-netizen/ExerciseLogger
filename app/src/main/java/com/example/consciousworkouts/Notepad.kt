package com.example.consciousworkouts



/*

PROBLEM:

**GETTING THE PAST SCREEN TO SHOW MINIPROGRESS SQUARES OF POINTS THAT ARE CORRECT**

Ok so what am I trying to do here?
I want the Past screen to show individual MiniProgressSquares that have their information fed
in from the data that feeds into the TodayScreen.

What data do I need though?
I need only two pieces of data, I need the date, and the points earned on that date.

Where are the points saved?
They aren't saved anywhere in the DB, they are derived from the workouts completed.

For my past screen, should I derive the data also, or have it saved somewhere in the DB?

I would rather derive, rather than creating extra sources of data. Deriving implies a single source
of data, but does that come at the cost of more computation?

Honestly, I have no idea. I don't know what trade off is worth it or not.

If I can first understand how the TodayScreen derives its points, maybe I can feed that somehow
to the PastScreen

Ok I made it work.


**WHEN I PRESS A MINIPROGRESS SQUARE IT DOESN"T TAKE ME TO THE DATE, IT JUST TAKES ME HOME**
I expected it to work, but it's not.

Which part of it isn't working? Is it the funciton itself, or the function call?

Let me try to isolate it.

Maybe the call isn't actually running? My log didn't show up. Not sure if I'm just stupid with filtering or it didn't actually run...

might be too layered in brackets.

Im an idiot..put the log in the wrong function. It definitely calls.

It doesn't seem to be picking up on any of the selectedDate updates. Is it because they are using different versions of the viewModel maybe?

Let me test that by adding the variable to the PastScreen to see if it reflects the update.



REVIEW:
Areas to learn from:
1. Learning proper navigation and backstacks etc (done)
2. Proper Room migrations and fallbacks (done)
3. Testing
4. Error handling



Room Migrations. Things to practice.
1. Adding a new table DONE
2. Removing a table DONE
3. Updating the entity on a table
    a. Removing a column?
    b. Changing a type?

Let me just practice a lot of that. Continously migrating my table. Doing it from my head, without
referring to previous code as well.


Read the latest Ai response and work on getting a better understanding of where to put viewModel vs domain logic.


















 */