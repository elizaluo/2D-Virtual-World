import processing.core.PImage;
import java.util.List;

public abstract class EntityAnimations extends Entity {

    private final int animationPeriod;

    public EntityAnimations (String id, Point position, List<PImage> images, int animationPeriod)
    {
        super(id, position, images);
        this.animationPeriod = animationPeriod;
    }

    public void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore)
    {
        scheduler.scheduleEvent(this,
                Factory.createAnimationAction(this, 0),
                this.getAnimationPeriod());
    }

    public void nextImage() { super.setImageIndex((super.getImageIndex() + 1) % super.getImages().size());}
    public int getAnimationPeriod() { return this.animationPeriod; }

}

