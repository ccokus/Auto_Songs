
public class Scales {

    private int[] currScale;


    /**
     * intialize arrays for scales (constructor)
     */
    public Scales() {
        currScale = new int[7];
        currScale[0] = 2;
        currScale[1] = 2;
        currScale[2] = 1;
        currScale[3] = 2;
        currScale[4] = 2;
        currScale[5] = 2;
        currScale[6] = 1;
    }

    /**
     * find desired scale degree in array of number of halfsteps
     * from tonic to note in scale
     * @param deg - the starting note (0=c, 1=d...)
     * @return scale degree desired:
     *      0 = ionian
     *      1 = dorian
     *      2 = phrygian
     *      3 = lydian
     *      4 = mixolydian
     *      5 = aeolian
     *      6 = locrian
     */
    public int[] getScale(int deg) {
        int[] scale = new int[7];
        scale[0] = 0;
        int count = 1;
        int val = 0;
        while (count < 7) {
            scale[count] = currScale[deg % 7] + val;
            val = scale[count];
            deg++;
            count++;
        }
        
        return scale;
    }
}
