package de.janik.util.resource_loader;
// <- Import ->

// <- Static_Import ->

import java.util.ArrayList;
import java.util.List;

/**
 * @author Jan.Marcel.Janik [©2016]
 */
public final class Launcher {
    // <- Public ->
    // <- Protected ->

    // <- Private->
    private final LaunchComponent component;

    private final List<LaunchEvent> events;

    private double progress;

    // <- Static ->

    // <- Constructor ->
    public Launcher(final LaunchComponent component) {
        this.component = component;

        events = new ArrayList<>();

        progress = 0d;
    }

    // <- Abstract ->

    // <- Object ->
    public void add(final int weight, final LaunchAction action){
        addEvent(new LaunchEvent(action, weight));
    }

    public void addEvent(final LaunchEvent event) {
        event.setParent(component);

        events.add(event);
    }

    public void launch() {
        final Thread thread = new Thread(()->{

            long totalWeight = 0;

            for (final LaunchEvent e : events)
                totalWeight += e.getWeight();

            for (final LaunchEvent e : events) {
                e.getLaunchAction().perform(e);

                synchronized (this) {
                    progress += 100.0 / totalWeight * e.getWeight();
                }

                component.progress((int) Math.round(progress));
            }

            component.launch();
        });

        thread.setDaemon(true);
        thread.start();
    }

    // <- Getter & Setter ->
    public double getProgress() {
        synchronized (this) {
            return progress;
        }
    }

    public int getProgressRounded() {
        synchronized (this) {
            return (int) Math.round(progress);
        }
    }

    // <- Static ->
}
