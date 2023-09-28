package edu.rice.comp504.model.cmd;

import edu.rice.comp504.model.paintobj.APaintObject;
import edu.rice.comp504.model.paintobj.Ball;
import edu.rice.comp504.model.paintobj.Wall;

import java.awt.*;
import java.beans.PropertyChangeListener;

/**
 * The UpdateStateCmd will possibly update either the paint object location or attribute (color).
 */
public class UpdateStateCmd implements IPaintObjCmd {
    private final PropertyChangeListener[] iWalls;

    /**
     * The constructor.
     * @param iWalls The canvas inner walls
     *
     */
    public UpdateStateCmd(PropertyChangeListener[] iWalls) {
        this.iWalls = iWalls;
    }

    /**
     * Update the state of the paint object
     * @param context  The paint object.
     */
    public void execute(APaintObject context) {
        context.detectCollisionBoundary();

        // TODO: check if there's a collision between a ball and an inner wall
        context.getStrategy().updateState(context);
    }

}
