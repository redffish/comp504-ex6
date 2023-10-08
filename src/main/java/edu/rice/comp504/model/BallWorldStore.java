package edu.rice.comp504.model;

import edu.rice.comp504.model.cmd.UpdateStateCmd;
import edu.rice.comp504.model.paintobj.APaintObject;
import edu.rice.comp504.model.paintobj.Ball;
import edu.rice.comp504.model.paintobj.NullObject;
import edu.rice.comp504.model.paintobj.Wall;
import edu.rice.comp504.model.strategy.NullStrategy;
import edu.rice.comp504.model.strategy.StraightStrategy;

import java.util.List;
import java.util.ArrayList;
import java.awt.*;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Arrays;
import java.util.stream.Stream;

/**
 * Store for canvas dimensions, balls, and inner walls.
 */
public class BallWorldStore {
    private static Point dims;
    private PropertyChangeSupport pcs;

    /**
     * Constructor.
     */
    public BallWorldStore() {
        pcs = new PropertyChangeSupport(this);
    }

    /**
     * Get the canvas dimensions.
     * @return The canvas dimensions
     */
    public static Point getCanvasDims() {
        return dims;
    }

    /**
     * Set the canvas dimensions.
     * @param cHeight The canvas height (y).
     * @param cWidth  The canvas width (x).
     */
    public static void setCanvasDims(String cHeight, String cWidth) {
        dims = new Point(Integer.parseInt(cHeight), Integer.parseInt(cWidth));
    }

    /**
     * Call the update method on all the paint object observers to update their position in the paint object world.
     */
    public PropertyChangeListener[] updateBallWorld() {
        // TODO: updateBallWorld
        PropertyChangeListener[] pclArr = pcs.getPropertyChangeListeners("theClock");
        UpdateStateCmd updateStateCmd = new UpdateStateCmd(pclArr);
        pcs.firePropertyChange("theClock", null, updateStateCmd);
        return pclArr;
    }

    /**
     * Load a paint object into the paint object world.
     * @param type  The paint object type (inner wall, ball).
     * @return A new paint object in the ball world.
     */
    public APaintObject loadPaintObj(String type) {
        // TODO: add paint object to property change listener list
        // APaintObject po = NullObject.make();
        APaintObject po;
        switch (type.toLowerCase()) {
            case "ball":
                po = Ball.makeBall(StraightStrategy.makeStrategy(), getCanvasDims());
                break;
            case "wall":
                po = Wall.makeWall(NullStrategy.makeStrategy(), getCanvasDims());
                break;
            default:
                po = NullObject.make();
                break;
        }

        addListener(po);
        return po;
    }

    /**
     * Add a ball or inner wall to the property change listener list.
     * @param pcl  The ball or inner wall
     */
    private void addListener(PropertyChangeListener pcl) {
        this.pcs.addPropertyChangeListener("theClock", pcl);
    }

    /**
     * Remove all property change listeners.
     */
    public void removeListeners() {
        PropertyChangeListener[] pclArr = pcs.getPropertyChangeListeners();
        for (PropertyChangeListener pcl: pclArr) {
            pcs.removePropertyChangeListener(pcl);
        }
    }
}
