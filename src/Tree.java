import java.util.*;

import processing.core.PImage;

/**
 * An entity that exists in the world. See EntityKind for the
 * different kinds of entities that exist.
 */
public final class Tree extends EntityPlantTransform
{

    public Tree(String id, Point position, List<PImage> images, int actionPeriod,
                int animationPeriod, int health)
    {
        super(id, position, images, animationPeriod, actionPeriod, health);
    }
}

