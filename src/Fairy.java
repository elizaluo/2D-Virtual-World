import java.util.*;

import processing.core.PImage;

/**
 * An entity that exists in the world. See EntityKind for the
 * different kinds of entities that exist.
 */
public final class Fairy extends EntityMovement
{

    public Fairy(String id, Point position, List<PImage> images, int actionPeriod, int animationPeriod)
    {
        super(id, position, images, animationPeriod, actionPeriod);
    }

    public boolean _nextPositionHelper(WorldModel world, Point newPos)
    {
        return (world.isOccupied(newPos));
    }

    public void executeEntityActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler)
    {
        Optional<Entity> fairyTarget =
                world.findNearest(super.getPosition(), new ArrayList<>(Arrays.asList(Stump.class)));

        if (fairyTarget.isPresent()) {
            Point tgtPos = fairyTarget.get().getPosition();

            if (this.move(world, fairyTarget.get(), scheduler)) {
                Entity sapling = Factory.createSapling("sapling_" + super.getId(), tgtPos,
                        imageStore.getImageList(Functions.SAPLING_KEY));

                world.addEntity(sapling);
                ((Sapling)sapling).scheduleActions(scheduler, world, imageStore);
            }
        }
        super.executeEntityActivity(world, imageStore, scheduler);
    }

    protected void _moveHelper(WorldModel world, Entity target, EventScheduler scheduler) {
        world.removeEntity(target);
        scheduler.unscheduleAllEvents(target);
    }


}
