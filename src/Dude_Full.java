import java.util.*;
import processing.core.PImage;

/**
 * An entity that exists in the world. See EntityKind for the
 * different kinds of entities that exist.
 */
public final class Dude_Full extends Dude_Helpers
{
    private final int resourceLimit;
    public Dude_Full(String id, Point position, List<PImage> images, int resourceLimit, int resourceCount,
                     int actionPeriod, int animationPeriod)
    {
        super(id, position, images, animationPeriod, actionPeriod);
        this.resourceLimit = resourceLimit;
    }

    public void executeEntityActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler)
    {
        Optional<Entity> fullTarget =
                world.findNearest(super.getPosition(), new ArrayList<>(Arrays.asList(House.class)));

        if (fullTarget.isPresent() && this.move(world,
                fullTarget.get(), scheduler))
        {
            this.transformFull(world, scheduler, imageStore);
        }
        else {
            super.executeEntityActivity(world, imageStore, scheduler);
        }
    }

    public void transformFull(WorldModel world, EventScheduler scheduler, ImageStore imageStore)
    {
        Entity miner = Factory.createDudeNotFull(super.getId(),
                super.getPosition(), super.getActionPeriod(),
                super.getAnimationPeriod(),
                this.resourceLimit,
                super.getImages());

        world.removeEntity(this);
        scheduler.unscheduleAllEvents(this);
        world.addEntity(miner);

        ((Dude_Not_Full)miner).scheduleActions(scheduler, world, imageStore);

    }

    protected void _moveHelper(WorldModel world, Entity target, EventScheduler scheduler) {
    }

}
