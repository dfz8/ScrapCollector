# ScrapCollector

This project is an exercise in artificial intelligence based on the idea of genetic algorithms.

This program runs several generations of robots that traverse a map, picking up scraps.
Each robot has its own "DNA" that deterministically gives it an action based on input from its surroundings.
AFter each generation runs, we decide on a way to breed the robots so that the next generation has a higher chance of getting the
"good genes" that allow it to perform better on the map.

An actual run for the robot consists of it moving around on the map as long as it still has energy.
As the robot is moving around on the map, it automatically picks up any items in its current cell. If it happens to be an oil can,
then the robot gets an energy boost and can continue for a bit longer.

### Robot Fitness
Points are scored upon picking up scrap or oil. The emphasis is on scrap collection, so the score for pick up each are:

Scrap: +1000

Oil:   + 100

If the scores of two robots happen to be the same, the fitter one is the one that has traveled the furthest from its starting point.

## Run Instructions
At some point I plan on creating a .jar file.
