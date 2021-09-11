import java.util.LinkedList;
import java.util.Random;

public class HarmonizerTest extends student.TestCase {

    private Harmonizer harmon;
    private LinkedList<Integer> riff;
    private LinkedList<Integer> prog;
    private Scales sc;
    private int[] scale;
    
    public void setUp() {
        sc = new Scales();
        
        riff = new LinkedList<Integer>();
        prog = new LinkedList<Integer>();
    }
    
    public void testFindProgression0000() {
        
        scale = sc.getScale(0);
        
        riff.add(0);
        riff.add(4);
        riff.add(7);
        riff.add(4);
        riff.add(5);
        riff.add(9);
        riff.add(0);
        riff.add(9);
        for (int i = 0; i < 8; i++) {
            riff.add(scale[i % 7]);
        }
        
        //riff looks like: [0, 4, 7, 4, 5, 9, 0, 9
                          //0, 2, 4, 5, 7, 9, 11, 0
        
        harmon = new Harmonizer(scale, riff, 1);
        
        LinkedList<Integer> p1 = new LinkedList<Integer>();
        for (int i = 0; i < 4; i++) {
            p1.add(riff.get(i));
        }

        
    }
}
