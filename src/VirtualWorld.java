import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.Optional;

import processing.core.*;

public final class VirtualWorld extends PApplet
{
    private static final int TIMER_ACTION_PERIOD = 100;

    private static final int VIEW_WIDTH = 640;
    private static final int VIEW_HEIGHT = 480;
    private static final int TILE_WIDTH = 32;
    private static final int TILE_HEIGHT = 32;
    private static final int WORLD_WIDTH_SCALE = 2;
    private static final int WORLD_HEIGHT_SCALE = 2;

    private static final int VIEW_COLS = VIEW_WIDTH / TILE_WIDTH;
    private static final int VIEW_ROWS = VIEW_HEIGHT / TILE_HEIGHT;
    private static final int WORLD_COLS = VIEW_COLS * WORLD_WIDTH_SCALE;
    private static final int WORLD_ROWS = VIEW_ROWS * WORLD_HEIGHT_SCALE;

    private static final String IMAGE_LIST_FILE_NAME = "imagelist";
    private static final String DEFAULT_IMAGE_NAME = "background_default";
    private static final int DEFAULT_IMAGE_COLOR = 0x808080;

    private static String LOAD_FILE_NAME = "world.sav";

    private static final String FAST_FLAG = "-fast";
    private static final String FASTER_FLAG = "-faster";
    private static final String FASTEST_FLAG = "-fastest";
    private static final double FAST_SCALE = 0.5;
    private static final double FASTER_SCALE = 0.25;
    private static final double FASTEST_SCALE = 0.10;

    private static final String WIZARD_KEY = "wizard";
    private static final String WIZARD_ID = "wizard";
    private static final int WIZARD_COL = 10;
    private static final int WIZARD_ROW = 2;
    private static final int WIZARD_ACTION_PERIOD = 1200;
    private static final int WIZARD_ANIMATION_PERIOD = 90;

    private static final String HULK_KEY = "hulk";
    private static final String HULK_ID = "hulk";
    private static final int HULK_ACTION_PERIOD = 500;
    private static final int HULK_ANIMATION_PERIOD = 90;

    private static double timeScale = 1.0;

    private ImageStore imageStore;
    private WorldModel world;
    private WorldView view;
    private EventScheduler scheduler;

    private long nextTime;

    private boolean userPressed = false;

    public void settings() {
        size(VIEW_WIDTH, VIEW_HEIGHT);
    }

    /*
       Processing entry point for "sketch" setup.
    */
    public void setup() {
        this.imageStore = new ImageStore(
                createImageColored(TILE_WIDTH, TILE_HEIGHT,
                                   DEFAULT_IMAGE_COLOR));
        this.world = new WorldModel(WORLD_ROWS, WORLD_COLS,
                                    createDefaultBackground(imageStore));
        this.view = new WorldView(VIEW_ROWS, VIEW_COLS, this, world, TILE_WIDTH,
                                  TILE_HEIGHT);
        this.scheduler = new EventScheduler(timeScale);

        loadImages(IMAGE_LIST_FILE_NAME, imageStore, this);
        loadWorld(world, LOAD_FILE_NAME, imageStore);

        scheduleActions(world, scheduler, imageStore);

        nextTime = System.currentTimeMillis() + TIMER_ACTION_PERIOD;
    }

    public void draw() {
        long time = System.currentTimeMillis();
        if (time >= nextTime) {
            this.scheduler.updateOnTime(time);
            nextTime = time + TIMER_ACTION_PERIOD;
        }

        view.drawViewport();

        if (userPressed) {
            // spawn new wizard at set x and y
            Wizard wizard = new Wizard(WIZARD_ID, mouseToPoint(mouseX, mouseY), imageStore.getImageList(WIZARD_KEY),WIZARD_ANIMATION_PERIOD, WIZARD_ACTION_PERIOD);
            world.addEntity(wizard);
            wizard.scheduleActions(scheduler, world, imageStore);


            // transformed dude_not_full into hulk
            Point pressed = mouseToPoint(mouseX, mouseY);
            List<EntityMovement> listDudesNF = findNearestDudeNotFull(pressed);
            for (EntityMovement dudeNF: listDudesNF) {
                Hulk hulk = new Hulk(HULK_ID, dudeNF.getPosition(), imageStore.getImageList(HULK_KEY), HULK_ANIMATION_PERIOD, HULK_ACTION_PERIOD);
                world.removeEntity(dudeNF);
                scheduler.unscheduleAllEvents(dudeNF);
                world.addEntity(hulk);
                hulk.scheduleActions(scheduler, world, imageStore);
            }

            // background
            Point shift = new Point(pressed.x-1, pressed.y);
            Point shift2 = new Point(pressed.x, pressed.y-1);
            Point shift3 = new Point(pressed.x-1, pressed.y-1);
            Point shift4 = new Point(pressed.x+1, pressed.y);
            Point shift5 = new Point(pressed.x, pressed.y+1);
            Point shift6 = new Point(pressed.x+1, pressed.y+1);
            Point shift7 = new Point(pressed.x-1, pressed.y+1);
            Point shift8 = new Point(pressed.x+1, pressed.y-1);
            Point shift9 = new Point(pressed.x-2, pressed.y);
            Point shift10 = new Point(pressed.x, pressed.y-2);
            Point shift11 = new Point(pressed.x-2, pressed.y-2);
            Point shift12 = new Point(pressed.x+2, pressed.y);
            Point shift13 = new Point(pressed.x, pressed.y+2);
            Point shift14 = new Point(pressed.x+2, pressed.y+2);
            Point shift15 = new Point(pressed.x-2, pressed.y+2);
            Point shift16 = new Point(pressed.x+2, pressed.y-2);
            Point shift17 = new Point(pressed.x-2, pressed.y+1);
            Point shift18 = new Point(pressed.x+1, pressed.y-2);
            Point shift19 = new Point(pressed.x-1, pressed.y-2);
            Point shift20 = new Point(pressed.x-1, pressed.y+2);
            Point shift21 = new Point(pressed.x+1, pressed.y+2);
            Point shift22 = new Point(pressed.x+2, pressed.y+1);
            Point shift23 = new Point(pressed.x-2, pressed.y-1);
            Point shift24 = new Point(pressed.x+2, pressed.y-1);
            world.setBackground(mouseToPoint(mouseX,mouseY), new Background("ice",imageStore.getImageList("ice")));
            world.setBackground(shift, new Background("ice",imageStore.getImageList("ice")));
            world.setBackground(shift2, new Background("ice",imageStore.getImageList("ice")));
            world.setBackground(shift3, new Background("ice",imageStore.getImageList("ice")));
            world.setBackground(shift4, new Background("ice",imageStore.getImageList("ice")));
            world.setBackground(shift5, new Background("ice",imageStore.getImageList("ice")));
            world.setBackground(shift6, new Background("ice",imageStore.getImageList("ice")));
            world.setBackground(shift7, new Background("ice",imageStore.getImageList("ice")));
            world.setBackground(shift8, new Background("ice",imageStore.getImageList("ice")));
            world.setBackground(shift9, new Background("ice",imageStore.getImageList("ice")));
            world.setBackground(shift10, new Background("ice",imageStore.getImageList("ice")));
            world.setBackground(shift11, new Background("ice",imageStore.getImageList("ice")));
            world.setBackground(shift12, new Background("ice",imageStore.getImageList("ice")));
            world.setBackground(shift13, new Background("ice",imageStore.getImageList("ice")));
            world.setBackground(shift14, new Background("ice",imageStore.getImageList("ice")));
            world.setBackground(shift15, new Background("ice",imageStore.getImageList("ice")));
            world.setBackground(shift16, new Background("ice",imageStore.getImageList("ice")));
            world.setBackground(shift17, new Background("ice",imageStore.getImageList("ice")));
            world.setBackground(shift18, new Background("ice",imageStore.getImageList("ice")));
            world.setBackground(shift19, new Background("ice",imageStore.getImageList("ice")));
            world.setBackground(shift20, new Background("ice",imageStore.getImageList("ice")));
            world.setBackground(shift21, new Background("ice",imageStore.getImageList("ice")));
            world.setBackground(shift22, new Background("ice",imageStore.getImageList("ice")));
            world.setBackground(shift23, new Background("ice",imageStore.getImageList("ice")));
            world.setBackground(shift24, new Background("ice",imageStore.getImageList("ice")));
            userPressed = false;
        }

    }

    // Just for debugging and for P5
    public void mousePressed() {
        Point pressed = mouseToPoint(mouseX, mouseY);
        System.out.println("BAM! Wizard spawned at " + pressed.getX() + ", " + pressed.getY());

        userPressed = true;
    }

    public List<EntityMovement> findNearestDudeNotFull(Point pos)
    {
        List<EntityMovement> inRange = new LinkedList<>();

        for (Entity entity : world.getEntities()) {
            if (entity.getClass() == Dude_Not_Full.class) {
                if (heuristic(pos, entity.getPosition()) <= 5) {
                    inRange.add((EntityMovement)entity);
                }
            }
        }

        return inRange;
    }

    private int heuristic(Point current, Point dest) {
        return (int)Math.sqrt(Math.pow((dest.x-current.x), 2) + Math.pow((dest.y-current.y), 2));
    }


    private Point mouseToPoint(int x, int y)
    {
        return view.getViewport().viewportToWorld(mouseX/TILE_WIDTH, mouseY/TILE_HEIGHT);
    }

    public void keyPressed() {
        if (key == CODED) {
            int dx = 0;
            int dy = 0;

            switch (keyCode) {
                case UP:
                    dy = -1;
                    break;
                case DOWN:
                    dy = 1;
                    break;
                case LEFT:
                    dx = -1;
                    break;
                case RIGHT:
                    dx = 1;
                    break;
            }
            view.shiftView(dx, dy);
        }
    }

    public static Background createDefaultBackground(ImageStore imageStore) {
        return new Background(DEFAULT_IMAGE_NAME,
                              imageStore.getImageList(DEFAULT_IMAGE_NAME));
    }

    public static PImage createImageColored(int width, int height, int color) {
        PImage img = new PImage(width, height, RGB);
        img.loadPixels();
        for (int i = 0; i < img.pixels.length; i++) {
            img.pixels[i] = color;
        }
        img.updatePixels();
        return img;
    }

    static void loadImages(
            String filename, ImageStore imageStore, PApplet screen)
    {
        try {
            Scanner in = new Scanner(new File(filename));
            imageStore.loadImages(in, screen);
        }
        catch (FileNotFoundException e) {
            System.err.println(e.getMessage());
        }
    }

    public static void loadWorld(
            WorldModel world, String filename, ImageStore imageStore)
    {
        try {
            Scanner in = new Scanner(new File(filename));
            Functions.load(in, world, imageStore);
        }
        catch (FileNotFoundException e) {
            System.err.println(e.getMessage());
        }
    }

    public static void scheduleActions(
            WorldModel world, EventScheduler scheduler, ImageStore imageStore)
    {
        for (Entity entity : world.getEntities()) {
            if (entity instanceof EntityAnimations) {
                ((EntityAnimations)entity).scheduleActions(scheduler, world, imageStore);
            }
        }
    }

    public static void parseCommandLine(String[] args) {
        if (args.length > 1)
        {
            if (args[0].equals("file"))
            {

            }
        }
        for (String arg : args) {
            switch (arg) {
                case FAST_FLAG:
                    timeScale = Math.min(FAST_SCALE, timeScale);
                    break;
                case FASTER_FLAG:
                    timeScale = Math.min(FASTER_SCALE, timeScale);
                    break;
                case FASTEST_FLAG:
                    timeScale = Math.min(FASTEST_SCALE, timeScale);
                    break;
            }
        }
    }

    public static void main(String[] args) {
        parseCommandLine(args);
        PApplet.main(VirtualWorld.class);
    }
}
