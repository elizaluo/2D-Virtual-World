public final class Activity implements Action {
    private final EntityExecute executingEntity;
    private final WorldModel world;
    private final ImageStore imageStore;

    public Activity(
            EntityExecute executingEntity,
            WorldModel world,
            ImageStore imageStore) {
        this.executingEntity = executingEntity;
        this.world = world;
        this.imageStore = imageStore;
    }

    public void executeAction(EventScheduler scheduler) {
        this.executingEntity.executeEntityActivity(this.world, this.imageStore, scheduler);
    }
}
