import processing.core.PImage;

import java.security.CryptoPrimitive;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class Wizard extends EntityMovement {

    private static final String CRYSTAL_KEY = "crystal";
    private static final String CRYSTAL_ID = "crystal";
    private static final int CRYSTAL_ANIMATION_PERIOD = 786;
    private static final int CRYSTAL_ACTION_PERIOD = 100;

    public Wizard(String id, Point position, List<PImage> images, int animationPeriod, int actionPeriod)
    {
        super(id, position, images, animationPeriod, actionPeriod);
    }

    public boolean _nextPositionHelper(WorldModel world, Point newPos) {
        return (world.isOccupied(newPos));
    }

    protected void _moveHelper(WorldModel world, Entity target, EventScheduler scheduler) {
        world.removeEntity(target);
        scheduler.unscheduleAllEvents(target);
    }

    public void executeEntityActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler)
    {
        Optional<Entity> target =
                world.findNearest(super.getPosition(), new ArrayList<>(Arrays.asList(Tree.class)));

        if (target.isPresent())
        {
            Point tgtPos = target.get().getPosition();
            if (this.move(world, target.get(), scheduler)) {
                Crystal crystal = new Crystal(CRYSTAL_ID, tgtPos, imageStore.getImageList(CRYSTAL_KEY), CRYSTAL_ANIMATION_PERIOD, CRYSTAL_ACTION_PERIOD);
                world.addEntity(crystal);
                crystal.scheduleActions(scheduler, world, imageStore);
            }
        }
        super.executeEntityActivity(world, imageStore, scheduler);

    }

}
