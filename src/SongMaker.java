import java.util.LinkedList;
import java.util.Random;

public class SongMaker {

    
    public static void main(String[] args) {
        SongMaker.songMake(args);
        //main isn't designed to be used here, for testing this is implemented
    }
    
    /**
     * currently 0 0 0 0 in run config
     * @param args
     *            args[0] = length from 0-2
     *            0 = 2 measure phrase
     *            1 = 4 measure phrase
     *            2 = 8 measure phrase
     * 
     *            args[1] = note density from 0-2 (1/4 to 1/16)
     * 
     *            args[2] = mode from 0-3
     *            0 = positive (ionian, mixolydian)
     *            1 = explorative (lydian, locrian)
     *            2 = interrogative (phrygian, locrian)
     *            3 = negative (aeolian, dorian)
     * 
     *            args[3] = repetitiveness from 0-3
     *            0 (eval as 1) = least repetition
     *            1 (eval as 2) = less repetition
     *            2 (eval as 3) = more repetition
     *            3 (eval as 0) = most repetition (mod function to straighten out)
     */
    public static String[] songMake(String[] args) {
        
        String[] song = new String[2];
        String riff = new String();
        String prog = new String();
        
        Random random = new Random();
        //read arguments
        int length = Integer.parseInt(args[0]);
        int density = Integer.parseInt(args[1]);
        int mode = Integer.parseInt(args[2]);
        int repeat = Integer.parseInt(args[3]);
        
        repeat = Math.floorMod((repeat + 1), 4); //mod used to make 4 == 0; 0 is technically most repetitive
        
        int[] modes = new int[2];
        //give mode options
        switch (mode) {
            case 0:            
                modes[0] = 0;
                modes[1] = 4;
                break;
            case 1:
                modes[0] = 3;
                modes[1] = 6;
                break;
            case 2:
                modes[0] = 2;
                modes[1] = 6;
                break;
            case 3:
                modes[0] = 5;
                modes[1] = 1;
                break;
        }
        //select mode option
        mode = modes[random.nextInt(2)];
        Scales scales = new Scales();
        //instantiate scale
        int[] scale = scales.getScale(mode);
        RiffArranger ra = new RiffArranger(length, density, repeat, scale);  //create RiffArranger
        
        //ra.print();
        
        Harmonizer progress = new Harmonizer(scale, ra.getRiff(), density);
        progress.setProg(progress.findProgression());
        progress.setProg(progress.findProgRhythm(progress.getProg()));
        
        //ra.print();
        //prog.print(); 
        //System.out.println(song);
        
        if (ra.getRiff().size() < progress.getProg().size()) {
            riff = riff.concat(ra.concat(2));
        }
        else {
            riff = riff.concat(ra.concat(1));//add riff to its respective string
        }
        prog = prog.concat(progress.concat());//add prog to its respective string
        
        song[0] = riff;
        song[1] = prog;
        return song;
    }
}

// ionian = major, lydian = major, mixolydian = major, phrygian = minor, locrian
// = minor, dorian = minor, aeolian = minor
