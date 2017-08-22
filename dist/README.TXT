Reinforcement learning (not search)
Find the the optimal path to the final state (State 10) from all other states
The path is coded in the R table, a -1 means no connection, 0 is a connection
100 is the reward i.e. final state because it always tries to maximize its
rewards.

Sample run:

  States (X is not passable)
    |----------------|
    | 2 | 5 | 7 | 10 |
    |----------------|
    | 1 | 4 | X | 9  |
    |----------------|
    | 0 | 3 | 6 | 8  |
    |----------------|
    Rewards
    |-----------------|
    | 0 | 0 | 0 | 100 |
    |-----------------|
    | 0 | 0 | X | -100|
    |-----------------|
    | 0 | 0 | 0 | 0   |
    |-----------------|
Solution: 0->1->2->5->7->10
Solution: 1->2->5->7->10
Solution: 2->5->7->10
Solution: 3->4->5->7->10
Solution: 4->5->7->10
Solution: 5->7->10
Solution: 6->3->4->5->7->10
Solution: 7->10
Solution: 8->6->3->4->5->7->10 (Avoids -100 rewards!)
Solution: 9->10
Solution: 10->10    