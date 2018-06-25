### Solutions description

There are two branches now:
* master (with naive solution)
* remove_stat (with multithreading approach)


### Naive solution

It uses a fact that we don't need to keep transactions
to calculate statistics. It updates `min`, `max`, `sum` and `count` every transaction.
Then, when a statistic requested, counts `avg` as `sum / count`. 
There is also a scheduled job, which resets statistics every minute. 
That's why we have `O(1)` time and memory.

### Multithreading approach
The scheduled job, which was described above, removes all statistics, that's why it's not a proper solution.
Instead of job, I create a thread for every request, which updates statistic, then waits a counted time (see below)
and the updates the statistics again. 
 
We have `timestamp` it's possible to find how long have a transaction been living. `now - timestamp`.
There is no need to keep the transaction more than minute. That's why the thread have to wait `minute - (now - timestamp)`.

It's easy to update `sum` and `count`, we add value to `sum` and increment `count`. Then the thread will 
substract `sum` and decrement `count`. For `min` and `max` we need to now the previous values, that's why
`MinMaxPriorityQueue` from Google Guava was used. By this approach the statistics are always up to date,
we still count `avg` as `sum / count`. 
This approach give as `O(1)` time but not memory because queues were used. But technically, we can predefine queues size
and it give as `O(1)` in average.

### Used technologies

#### Spring boot
as a general framework to build the web application.

#### Spring Cloud Contract 
to generate stubs and API unit tests. See `test/resources/contracts`
  
#### Spring Rest Docs 
to generate up to date documentation form unit-tests.
All generated documentation is packed to the jar and can be found on `/docs/index.html` url.

#### Maven dependency plugin
to avoid transitive dependencies.

#### Maven sort-pom plugin
to keep a standard pom structure.
