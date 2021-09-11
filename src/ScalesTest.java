
public class ScalesTest extends student.TestCase {

    private Scales scaleRetriever;
    private int[] currScale;


    public void setUp() {
        scaleRetriever = new Scales();
        currScale = new int[7];
    }


    public void testGetScaleIonian() {
        currScale = scaleRetriever.getScale(0);
        
        System.out.println("First in Ionian is: " + currScale[0]);
        System.out.println("Second in Ionian is: " + currScale[1]);
        System.out.println("Third in Ionian is: " + currScale[2]);
        System.out.println("Fourth in Ionian is: " + currScale[3]);
        System.out.println("Fifth in Ionian is: " + currScale[4]);
        System.out.println("Sixth in Ionian is: " + currScale[5]);
        System.out.println("Seventh in Ionian is: " + currScale[6]);
        
        assertEquals(0, currScale[0]);
        assertEquals(2, currScale[1]);
        assertEquals(4, currScale[2]);
        assertEquals(5, currScale[3]);
        assertEquals(7, currScale[4]);
        assertEquals(9, currScale[5]);
        assertEquals(11, currScale[6]);
    }


    public void testGetScaleDorian() {
        currScale = scaleRetriever.getScale(1);
        
        assertEquals(0, currScale[0]);
        assertEquals(2, currScale[1]);
        assertEquals(3, currScale[2]);
        assertEquals(5, currScale[3]);
        assertEquals(7, currScale[4]);
        assertEquals(9, currScale[5]);
        assertEquals(10, currScale[6]);
    }


    public void testGetScalePhrygian() {
        currScale = scaleRetriever.getScale(2);
        
        assertEquals(0, currScale[0]);
        assertEquals(1, currScale[1]);
        assertEquals(3, currScale[2]);
        assertEquals(5, currScale[3]);
        assertEquals(7, currScale[4]);
        assertEquals(8, currScale[5]);
        assertEquals(10, currScale[6]);
    }


    public void testGetScaleLydian() {
        currScale = scaleRetriever.getScale(3);
        
        assertEquals(0, currScale[0]);
        assertEquals(2, currScale[1]);
        assertEquals(4, currScale[2]);
        assertEquals(6, currScale[3]);
        assertEquals(7, currScale[4]);
        assertEquals(9, currScale[5]);
        assertEquals(11, currScale[6]);
    }


    public void testGetScaleMixolydian() {
        currScale = scaleRetriever.getScale(4);
        
        assertEquals(0, currScale[0]);
        assertEquals(2, currScale[1]);
        assertEquals(4, currScale[2]);
        assertEquals(5, currScale[3]);
        assertEquals(7, currScale[4]);
        assertEquals(9, currScale[5]);
        assertEquals(10, currScale[6]);
    }


    public void testGetScaleAeolian() {
        currScale = scaleRetriever.getScale(5);
        
        assertEquals(0, currScale[0]);
        assertEquals(2, currScale[1]);
        assertEquals(3, currScale[2]);
        assertEquals(5, currScale[3]);
        assertEquals(7, currScale[4]);
        assertEquals(8, currScale[5]);
        assertEquals(10, currScale[6]);
    }


    public void testGetScaleLocrian() {
        currScale = scaleRetriever.getScale(6);
        
        assertEquals(currScale[0], 0);
        assertEquals(currScale[1], 1);
        assertEquals(currScale[2], 3);
        assertEquals(currScale[3], 5);
        assertEquals(currScale[4], 6);
        assertEquals(currScale[5], 8);
        assertEquals(currScale[6], 10);
        
        assertEquals(0, currScale[0]);
        assertEquals(1, currScale[1]);
        assertEquals(3, currScale[2]);
        assertEquals(5, currScale[3]);
        assertEquals(6, currScale[4]);
        assertEquals(8, currScale[5]);
        assertEquals(10, currScale[6]);
    }
}
