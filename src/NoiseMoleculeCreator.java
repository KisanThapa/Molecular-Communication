/**
 * NoiseMoleculeCreator is a MoleculeCreator that creates
 * sedentary noise molecules randomly throughout the medium
 */

import java.util.ArrayList;

public class NoiseMoleculeCreator extends MoleculeCreator {

    public NoiseMoleculeCreator(ArrayList<MoleculeParams> noiseMoleculeParams, MolComSim sim) {
        super(noiseMoleculeParams, sim);
    }

    /**
     * Create molecules and populate them through the medium
     */
    public void createMolecules() {
        ArrayList<Molecule> noiseMolecules = new ArrayList<Molecule>();
        //TODO: check these values to make sure they're not occupied
        for (MoleculeParams nmp : molParams) {
            for (int i = 0; i < nmp.getNumMolecules(); i++) {
                int mh = simulation.getSimParams().getMediumHeight();
                int ml = simulation.getSimParams().getMediumLength();
                int mw = simulation.getSimParams().getMediumWidth();
                int x = (int) ((Math.random() * ml) - (ml / 2));
                int y = (int) ((Math.random() * mh) - (mh / 2));
                int z = (int) ((Math.random() * mw) - (mw / 2));
                Position randomPos = new Position(x, y, z);
                NoiseMolecule tempmol = new NoiseMolecule(randomPos, simulation, nmp.getMoleculeMovementType());

                //jj - Printing the initial position of each noise molecule.
                System.out.println("Initial position of Noise molecule " + (i + 1) + ": " + randomPos);

                //jj - assigning the corresponding MovementController based on MoleculeMovementType assigned in input.dat
                if (nmp.getMoleculeMovementType() != MoleculeMovementType.NONE)
                    new DiffusiveRandomMovementController(new SimpleCollisionHandler(), simulation, tempmol);
                else
                    new NullMovementController(new SimpleCollisionHandler(), simulation, tempmol);

                noiseMolecules.add(tempmol);
            }
            //create molecules using noise molecule params with positions randomly distributed throughout the medium (simulation.getMedium().getlength()…)
            simulation.addMolecules(noiseMolecules);
        }
    }

}
