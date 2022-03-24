/**
 * Molecule class represents a generic molecule
 * in the molecular communication simulation
 * Contains information necessary for moving
 * the molecule through the medium
 */

public abstract class Molecule {

    protected MolComSim simulation;
    //Id of the message a molecule carries - null for noise molecules
    protected Integer msgId;
    private Position position;
    private MovementController movementController;
    private final MoleculeMovementType moleculeMovementType;

    protected Molecule(MovementController mc, Position psn, MolComSim sim, MoleculeMovementType molMvType) {
        this.movementController = mc;
        this.position = psn;
        this.simulation = sim;
        this.moleculeMovementType = molMvType;
    }

    protected Molecule(Position psn, MolComSim sim, MoleculeMovementType molMvType) {
        this.movementController = null;
        this.position = psn;
        this.simulation = sim;
        this.moleculeMovementType = molMvType;
    }

    //Moves the molecule as defined by its movementController
    public abstract void move();

    public Position getPosition() {
        return position;
    }

    protected void setPosition(Position p) {
        this.position = p;
    }

    public MolComSim getSimulation() {
        return simulation;
    }

    public MovementController getMovementController() {
        return movementController;
    }

    public void setMovementController(MovementController mc) {
        this.movementController = mc;
    }

    public MoleculeMovementType getMoleculeMovementType() {
        return moleculeMovementType;
    }

    public Integer getMsgId() {
        return this.msgId;
    }

}
