Definitions:
n = number of events
w = total time = Wm + Wa
Wm = amount of time in the morning = 180 mins
Wa = amount of time in the afternoon = 480 mins

I did 3 solutions:

First solution, DumbSolution:

Loop through the list of events and take the event if it fits in to the current time slot. Takes O(n) time since we
just loop through all the events. O(n) space to hold all the events. This solution prioritizes time over anything else
and gives a valid schedule.

Second solution, GreedySolution:

Sort the events by time. This takes O(n log(n)) time. Then proceed to do the same thing you do in the dumb solution,
which is an additional O(n) time. Total time is O(n log(n)) time. O(n) space to hold all the events. This solution
prioritizes getting more events into the schedule and does not consider votes.

Third solution, Dynamic Programming:

Sort the events by time. This takes O(n log(n)) time. Fill the "m" array, which takes O(n*w) time, and O(n*w) space.
m[i][j] describes the optimal solution with i events considered and j minutes available. Thus the optimal solution for
the problem lies at m[n-1][w-1]. Optimal solution in this case is maximizing the number of votes that you can fit
in the amount of time given. To get the actual schedule, we must backtrack the matrix m. This takes no more than O(n)
time. Thus this whole solution takes O(n*w) time and space. In this particular problem however, we have w as a constant.
Thus this solution takes O(n) time and space.