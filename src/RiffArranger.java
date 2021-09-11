import java.util.LinkedList;
import java.util.Random;

public class RiffArranger {
    
    private int l;
    private int d;
    private int[] s;
    private int r;
    private Random random;
    private LinkedList<Integer> riff;
    
    /**
     * Constructor for a riff arranger
     * @param length The length specifier (0-2)
     * @param density Specifier for density of notes (0-2)
     * @param mode Gives indexes of possible modes to use
     * @param repeat 0-3; larger = more repition of key phrases
     */
    public RiffArranger(int length, int density, int repeat, int[] scale) {
        l = length;
        d = density;
        s = scale;
        r = repeat;
        random = new Random();
        riff = new LinkedList<Integer>();
        
        Piece[] pieces = this.makePieces();
        this.arrangePieces(pieces);
    }
    
    /**
     * Makes random musical pieces to be used in the riff
     * @param modes - 2 possible modes for use
     * @return array of pieces
     */
    private Piece[] makePieces() {
        int numNotes = (int)(Math.pow(2, (d + 2)) * Math.pow(2, (l + 1))); //d = notes per measure; l = # meausures
        //^determines how many individual notes per phrase^
        int maxPiece = 2;
        if (r != 0) {
            //makes max number of musical pieces to include in the riff
            maxPiece = (4 * numNotes) / (3 * r); 
        }
        
        //^retrieves the scale the riff will use^
        Piece[] pieces = new Piece[maxPiece];
        int pieceLength;
        for (int i = 0; i < maxPiece; i++) {
            pieceLength = random.nextInt(3) + 1; 
            //^finds a random length for the piece^
            Piece newPiece = new Piece(pieceLength, s, d); //creates new piece 
            pieces[i] = newPiece; // adds to the piece array
        }
        return pieces;
    }
    
    /**
     * uses random musical pieces to construct the desired riff
     * @param pieces
     */
    private void arrangePieces(Piece[] pieces) {
        int count = 0;
        int pieceChoice;
        int note;
        LinkedList<Integer> notes;
       
        int numNotes = (int)(Math.pow(2, (d + 2)) * Math.pow(2, (l + 1))); //number of possible notes in the riff
        
        while (count < numNotes) {
            pieceChoice = random.nextInt(pieces.length); //had - 1 init
            //^choose a random piece index^
            notes = pieces[pieceChoice].getNotes();
            
            while (count < numNotes && !notes.isEmpty()) {
                note = notes.pop();
                riff.add(note);
                count++;
            }
        }
    }
    
    /**
     * @return the riff made by the riff arranger
     */
    public LinkedList<Integer> getRiff() {
        return riff;
    }
    
    /**
     * Concatenates the riff to a given string
     * @param str - string being used
     */
    public String concat(int reps) {
        String str = "";
        int note;
        int measure = (int)Math.pow(2, (d + 2));
        str = str.concat("Riff: " + "\n");
        
        for (int k = 0; k < reps; k++) {
            for (int i = 0; i < riff.size(); i++) {
                note = riff.get(i);
                if (note >= 0) {
                    str = str.concat(Integer.toString(note));
                }
                else {
                    str = str.concat("-");
                }
                
                if ((i % measure) == (measure - 1)) { //each measure starts on new line
                    str = str.concat("\n");
                }
                else{
                    str = str.concat(" ");
                }
            }
        }
        
        str = str.concat("\n");
        return str;
    }
    
    public void print() {
        int note;
        int measure = (int)Math.pow(2, (d + 2));
        System.out.println("Riff:");
        for (int i = 0; i < riff.size(); i++) {
            note = riff.get(i);
            if (note >= 0) {
                System.out.print(note);
            }
            else {
                System.out.print("-");
            }
            
            if ((i % measure) == (measure - 1)) { //each measure starts on new line
                System.out.println("");
            }
            else{
                System.out.print(" ");
            }
        }
        System.out.println("");
    }
    
}
