import processing.core.PImage;
import java.util.List;

public abstract class EntityExecute extends EntityAnimations {

    private final int actionPeriod;

    public EntityExecute(String id, Point position, List<PImage> images, int animationPeriod, int actionPeriod)
    {
        super(id, position, images, animationPeriod);
        this.actionPeriod = actionPeriod;
    }

    public abstract void executeEntityActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler);

    public int getActionPeriod() { return actionPeriod; }

    public void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore) {
        scheduler.scheduleEvent(this,
                Factory.createActivityAction(this, world, imageStore),
                getActionPeriod());
        super.scheduleActions(scheduler, world, imageStore);
    }

}