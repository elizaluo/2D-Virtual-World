import java.util.*;

import processing.core.PImage;

/**
 * An entity that exists in the world. See EntityKind for the
 * different kinds of entities that exist.
 */
public final class Sapling extends EntityPlantTransform
{

    private final int healthLimit;

    public Sapling(String id, Point position, List<PImage> images, int actionPeriod,
                   int animationPeriod, int health, int healthLimit)
    {
        super(id, position, images, animationPeriod, actionPeriod, health);
        this.healthLimit = healthLimit;
    }

    public void executeEntityActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler)
    {
        setHealth(getHealth()+1);
        super.executeEntityActivity(world,imageStore,scheduler);
    }


    public boolean transformPlant(WorldModel world, EventScheduler scheduler, ImageStore imageStore) {
        if (super.transformPlant(world, scheduler, imageStore)) { return true; }
        else if (super.getHealth() >= this.healthLimit)
        {
            Entity tree = Factory.createTree("tree_" + super.getId(),
                    super.getPosition(),
                    this.getNumFromRange(Functions.TREE_ACTION_MAX, Functions.TREE_ACTION_MIN),
                    this.getNumFromRange(Functions.TREE_ANIMATION_MAX, Functions.TREE_ANIMATION_MIN),
                    this.getNumFromRange(Functions.TREE_HEALTH_MAX, Functions.TREE_HEALTH_MIN),
                    imageStore.getImageList(Functions.TREE_KEY));

            world.removeEntity(this);
            scheduler.unscheduleAllEvents(this);

            world.addEntity(tree);
            ((Tree)tree).scheduleActions(scheduler, world, imageStore);

            return true;
        }

        return false;
    }


    private static int getNumFromRange(int max, int min)
    {
        Random rand = new Random();
        return min + rand.nextInt(
                max
                        - min);
    }
}
