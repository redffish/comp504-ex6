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
        if ( context.getType().equals("ball") ) {
            for (PropertyChangeListener iWall : iWalls) {
                if (((APaintObject) iWall).getType().equals("wall") && ((Ball) context).detectCollisionInnerWall((Wall) iWall)) {
                    int wallLen = ((Wall) iWall).getLength();
                    int ballRadius = ((Ball) context).getRadius();
                    if (wallLen > ballRadius) {
                        ((Wall) iWall).setLength(ballRadius);
                    }
                    ((Wall) iWall).setLocation(context.getLocation());
                    ((Wall) iWall).setVelocity(context.getVelocity());
                    ((Wall) iWall).setStrategy(context.getStrategy());
                }
            }
        } else if ( context.getType().equals("wall") ) {
            System.out.println("This is a wall");
        } else {  // null object
            System.out.println("This is a null object");
        }

        context.getStrategy().updateState(context);
    }

}
