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
            Ball ball = (Ball) context;
            for (PropertyChangeListener pcl : iWalls) {
                APaintObject po = (APaintObject) pcl;
                if (po.getType().equals("wall")) {
                    Wall wall = (Wall) po;
                    if (ball.detectCollisionInnerWall(wall)) {
                        int wallLen = wall.getLength();
                        int ballRadius = ball.getRadius();
                        if (wallLen > ballRadius) {
                            wall.setLength(ballRadius);
                        }
                        wall.setLocation(ball.getLocation());
                        wall.setVelocity(ball.getVelocity());
                        wall.setStrategy(ball.getStrategy());

                        wall.getStrategy().updateState(wall);
                    }
                }
            }
        } else if ( context.getType().equals("wall") ) {
            // do something else
            // System.out.println("wall");
        } else {
            // do something else
            // System.out.println("null object");
        }

        context.getStrategy().updateState(context);
    }

}
