import processing.core.PImage;

import java.util.List;

public class Crystal extends EntityExecute{


    public Crystal(String id, Point position, List<PImage> images, int actionPeriod,
                int animationPeriod)
    {
        super(id, position, images, animationPeriod, actionPeriod);
    }

    public void executeEntityActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
            scheduler.scheduleEvent(this,
                    Factory.createActivityAction(this, world, imageStore),
                    super.getActionPeriod());
    }
}
