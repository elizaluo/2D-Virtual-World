import java.util.*;
import processing.core.PImage;

/**
 * An entity that exists in the world. See EntityKind for the
 * different kinds of entities that exist.
 */
public final class Dude_Not_Full extends Dude_Helpers
{
    private final int resourceLimit;
    private  int resourceCount;

    public Dude_Not_Full(String id, Point position, List<PImage> images, int resourceLimit,
                         int resourceCount, int actionPeriod, int animationPeriod)
    {
        super(id, position, images, animationPeriod, actionPeriod);
        this.resourceLimit = resourceLimit;
        this.resourceCount = resourceCount;
    }

    public void executeEntityActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler)
    {
        Optional<Entity> target =
                world.findNearest(super.getPosition(), new ArrayList<>(Arrays.asList(Tree.class, Sapling.class)));

        if (!target.isPresent() || !this.move(world, target.get(), scheduler)
                || !this.transformNotFull(world, scheduler, imageStore))
        {
            super.executeEntityActivity(world, imageStore, scheduler);
        }
    }

    public boolean transformNotFull(WorldModel world, EventScheduler scheduler, ImageStore imageStore)
    {
        if (this.resourceCount >= this.resourceLimit) {
            Entity miner = Factory.createDudeFull(super.getId(),
                    super.getPosition(), super.getActionPeriod(),
                    super.getAnimationPeriod(),
                    this.resourceLimit,
                    super.getImages());

            world.removeEntity(this);
            scheduler.unscheduleAllEvents(this);

            world.addEntity(miner);

            ((Dude_Full)miner).scheduleActions(scheduler, world, imageStore);

            return true;
        }

        return false;
    }

    protected void _moveHelper(WorldModel world, Entity target, EventScheduler scheduler) {
        this.resourceCount += 1;
        ((EntityPlantTransform)target).setHealth(-1);
    }
}

