#ToDoApp TestScript
//------------------------------
open ToDoApp.jar file
input: import sampledata.xml
to load the initial test tasks
//------------------------------

input: add lunch outing v/buona vista
expected output: (lunch outing added at the bottom)

input: add CS2103 lect1 s/20 May n/print notes v/NUS
expected output: (new task added with specified attributes)

input: add CS2103 assignment1 d/25 May n/must do p/3 v/NUS
expected output: (new task added with specified attributes)

input: mark 55
expected output: task at index55 will be mark completion as true

input: unmark 55
expected output: task at index55 will be mark completion as false

input: edit 55 d/26 May
expected output: edit the deadline of task at index55 to be 26May

input: edit 54 p/ 4 n/ going through mid term paper
expected output: edit task at index 54 with priority change to 4 and notes change to going through mid term paper

input: edit 55 d/
expected output: edit task at index55, deadline set to null 

input: find p/ 5
expected output: list all task with priority equal to 5

input: find c/ false
expected output: list all task with completion equals false(undone tasks)

input: find n/ CS1010
expected output: list all task containing "CS1010" in its name

input: list
expected output: list all task

input: edit 55 n/ try undo
expected output: task at index 55 will have its notes update to "try undo"

input: undo
expected output: (undo previous command)

input: redo
expected output: (redo previous undone command)

input: clear
expected output: (clear the list)

input: undo
expected output: (undo previous command)

input: delete 55
expected output: task at index55 deleted

input: undo
expected output: (undo previous command)

input: redo
expected output: (redo previous undone command)

input: add dinner outing
expected output: new task "dinner outing" added to the end of the task list

input: undo
expected output: (undo previous command)

input: select 53
expected output: (A google map search will be done base on the venue given for the task at index53)

input: select 54
expected output: (A google map search will be done base on the venue given for the task at index54)

input: add daily exercise s/10 Apr v/garden
expected output: new task daily exercise will be added to the end of the list

input: edit 55 t/daily
expected output: tag of task at index55 updated to daily(recurring task)

input: list
expected output: the start date of task at index55 will be updated to tomorrow's date

input: add new year countdown s/31 dec 2016 t/yearly
expected output: new recurring task(yearly) "new year countdown" added to the end of the list

input: list
expected output: startdate of recurring task at index56 will be updated to this year's date 

input: cd newFolder/newLocation.xml
expected output: (new save file directory)

input: exit
expected output: exit the program