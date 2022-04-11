import processing.core.PImage;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.stream.Stream;

public abstract class EntityMovement extends EntityExecute {

    public EntityMovement(String id, Point position, List<PImage> images, int animationPeriod, int actionPeriod)
    {
        super(id, position, images, animationPeriod, actionPeriod);
    }

    public Point nextPosition(WorldModel world, Point destPos)
    {

        Predicate<Point> canPassThrough = (p1) -> (world.withinBounds(p1) && !_nextPositionHelper(world, p1));
        BiPredicate<Point, Point> withinReach = (p1, p2) -> adjacent(p1, p2);
        Function<Point, Stream<Point>> potentialNeighbors = PathingStrategy.CARDINAL_NEIGHBORS;

         //PathingStrategy strategy = new SingleStepPathingStrategy();
         PathingStrategy strategy = new AStarPathingStrategy();

        List<Point> points = strategy.computePath(getPosition(), destPos, canPassThrough, withinReach, potentialNeighbors);

        if (points == null || points.size() == 0) {
            return getPosition();
        }
        else {
            return points.get(0);
        }
    }

    protected abstract boolean _nextPositionHelper(WorldModel world, Point newPos);

    public void executeEntityActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
        scheduler.scheduleEvent(this,
                Factory.createActivityAction(this, world, imageStore),
                super.getActionPeriod());
    }

    public boolean move(WorldModel world, Entity target, EventScheduler scheduler) {
        if (adjacent(super.getPosition(), target.getPosition())) {
            _moveHelper(world, target, scheduler);
            return true;
        }
        else {
            Point nextPos = this.nextPosition(world, target.getPosition());

            if (!super.getPosition().equals(nextPos)) {
                Optional<Entity> occupant = world.getOccupant(nextPos);
                if (occupant.isPresent()) {
                    scheduler.unscheduleAllEvents(occupant.get());
                }

                world.moveEntity(this, nextPos);
            }
            return false;
        }
    }

    protected abstract void _moveHelper(WorldModel world, Entity target, EventScheduler scheduler);


    public static boolean adjacent(Point p1, Point p2) {
        return (p1.getX() == p2.getX() && Math.abs(p1.getY() - p2.getY()) == 1) || (p1.getY() == p2.getY()
                && Math.abs(p1.getX() - p2.getX()) == 1);
    }

}

