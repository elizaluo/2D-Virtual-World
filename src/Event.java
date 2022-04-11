/**
 * An event is made up ofTE an Entity that is taking an
 * Action a specified time.
 */
public final class Event
{
    private final Action action;
    private final long time;
    private final EntityAnimations animatingEntity;

    public Action getAction() {
        return action;
    }

    public long getTime() {
        return time;
    }

    public EntityAnimations getEntity() {
        return animatingEntity;
    }

    public Event(Action action, long time, EntityAnimations animatingEntity) {
        this.action = action;
        this.time = time;
        this.animatingEntity = animatingEntity;
    }
}
