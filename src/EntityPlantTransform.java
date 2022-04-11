import processing.core.PImage;

import java.util.List;

public abstract class EntityPlantTransform extends EntityExecute {

    private int health;

    public EntityPlantTransform(String id, Point position, List<PImage> images, int animationPeriod,
                                int actionPeriod, int health)
    {
        super(id, position, images, animationPeriod, actionPeriod);
        this.health = health;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public boolean transformPlant(WorldModel world, EventScheduler scheduler,ImageStore imageStore) {
        if (getHealth() <= 0) {
            Entity stump = Factory.createStump(super.getId(),
                    super.getPosition(),
                    imageStore.getImageList(Functions.STUMP_KEY));

            world.removeEntity(this);
            scheduler.unscheduleAllEvents(this);

            world.addEntity(stump);
            return true;
        }

        return false;
    }

    public void executeEntityActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
        if (!this.transformPlant(world, scheduler, imageStore)) {
            scheduler.scheduleEvent(this,
                    Factory.createActivityAction(this, world, imageStore),
                    super.getActionPeriod());
        }
    }

}
