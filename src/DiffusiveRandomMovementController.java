/**
 * Moves molecules according to passive transport,
 * using a random walk
 */

import java.util.Random;

public class DiffusiveRandomMovementController extends MovementController {
    private static final Random random = new Random();

    public DiffusiveRandomMovementController(CollisionHandler collHandle, MolComSim sim, Molecule mol) {
        super(collHandle, sim, mol);
    }

    /** Randomly selects where to move molecule based on simulation step length parameters
     *  @param molecule The molecule to move
     *  @return the position to move to
     */
    protected Position decideNextPosition() {
        //Randomly decide the next position based on current position + some delta.
        Position currentPosition = getMolecule().getPosition();
        int currentX = currentPosition.getX();
        int currentY = currentPosition.getY();
        int currentZ = currentPosition.getZ();
        // The idea behind the getMolRandMove~ methods are that the molecule moves a random amount in one step
        // , and that will, for each dimension, be a random amount between -getMolRandMove~ and +getMolRandMove~
        int maxXDelta = getSimulation().getSimParams().getMolRandMoveX();
        int maxYDelta = getSimulation().getSimParams().getMolRandMoveY();
        int maxZDelta = getSimulation().getSimParams().getMolRandMoveZ();

        // jj - Deciding the next position based on whether the molecule the function is being called on is a noise molecule or a non-noise molecule and if it is a noise molecule, is it random movement or active movement based on MoleculeMovementType.
        if (getMolecule() instanceof NoiseMolecule && getMolecule().getMoleculeMovementType() == MoleculeMovementType.ACTIVE) {
            // jj - in ACTIVE movement type, we're defining directional movement along th x-axis from left to right.
            int nextX = currentX + random.nextInt(1 + (2 * maxXDelta)) - maxXDelta;
            // jj - keeping y and z positions the same.
            int nextY = currentY;
            int nextZ = currentZ;
            Position nextPosition = new Position(nextX, nextY, nextZ);
            return nextPosition;
        } else {
            int nextX = currentX + random.nextInt(1 + (2 * maxXDelta)) - maxXDelta;
            int nextY = currentY + random.nextInt(1 + (2 * maxYDelta)) - maxYDelta;
            int nextZ = currentZ + random.nextInt(1 + (2 * maxZDelta)) - maxZDelta;
            Position nextPosition = new Position(nextX, nextY, nextZ);
            //If the molecule has ACTIVE movement type, it looks for a nearby microtubule to reattach to
            if (getMolecule().getMoleculeMovementType() == MoleculeMovementType.ACTIVE) {
                //If a microtubule is found, change molecule's collision handler and movement controller
                Microtubule microtubule = simulation.getMedium().hasMicrotubule(getMolecule().getPosition());
                if (microtubule != null) {
                    CollisionHandler collH = simulation.isUsingCollisions() ?
                            (simulation.decomposing() ? new OnTubuleCollisionHandler(new DecomposingCollisionHandler(new SimpleCollisionHandler())) :
                                    new OnTubuleCollisionHandler(new SimpleCollisionHandler())) : new NullCollisionHandler(new SimpleCollisionHandler());
                    new OnMicrotubuleMovementController(collH, simulation, getMolecule(), microtubule);
                }
            }
            return nextPosition;
        }


    }
}

