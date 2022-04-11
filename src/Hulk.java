import processing.core.PImage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class Hulk extends EntityMovement{

    public Hulk(String id, Point position, List<PImage> images, int animationPeriod, int actionPeriod)
    {
        super(id, position, images, animationPeriod, actionPeriod);
    }

    protected boolean _nextPositionHelper(WorldModel world, Point newPos) {
        return (world.isOccupied(newPos));
    }

    protected void _moveHelper(WorldModel world, Entity target, EventScheduler scheduler) {
        world.removeEntity(target);
        scheduler.unscheduleAllEvents(target);    }

    public void executeEntityActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler)
    {
        Optional<Entity> target =
                world.findNearest(super.getPosition(), new ArrayList<>(Arrays.asList(Tree.class)));
        try {
            this.move(world, target.get(), scheduler);
        }
        catch (Exception e)
        {
            System.out.println("Oh no! There are no more trees left.");
            System.exit(0);
        }
        super.executeEntityActivity(world, imageStore, scheduler);

    }


}
