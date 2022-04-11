import processing.core.PImage;
import java.util.List;

public abstract class Dude_Helpers extends EntityMovement{

    public Dude_Helpers(String id, Point position, List<PImage> images, int animationPeriod, int actionPeriod)
    {
        super(id, position, images, animationPeriod, actionPeriod);
    }

    public boolean _nextPositionHelper(WorldModel world, Point newPos) {
        return (world.isOccupied(newPos) && world.getOccupancyCell(newPos).getClass() != Stump.class);
    }

}
