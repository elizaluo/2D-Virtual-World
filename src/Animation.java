public final class Animation implements Action{

    private final EntityAnimations animatingEntity;
    private final int repeatCount;

    public Animation(
            EntityAnimations animatingEntity,
            int repeatCount)
    {
        this.animatingEntity = animatingEntity;
        this.repeatCount = repeatCount;
    }

    public void executeAction(EventScheduler scheduler) {
        this.animatingEntity.nextImage();

        if (this.repeatCount != 1) {
            scheduler.scheduleEvent(this.animatingEntity,
                    Factory.createAnimationAction(this.animatingEntity, Math.max(this.repeatCount - 1, 0)),
                    this.animatingEntity.getAnimationPeriod());
        }
    }

}

