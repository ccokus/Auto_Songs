import java.util.LinkedList;
import java.util.Random;

public class Piece {
    private int len;
    private int[] scale;
    private int den;
    private LinkedList<Integer> notes;
    
    public Piece(int length, int[] mode, int density) {
        len = length;
        scale = mode;
        den = density;
        notes = new LinkedList<Integer>();
        makePiece();
    }
    
    /**
     * sets note values or rests (-1) consistent with the mode for each 
     * possible note entry (in "notes" list)
     */
    public void makePiece() {
        Random random = new Random();
        int note;
        for (int i = 0; i < len; i++) {
            note = random.nextInt(8 + (den * 3));
            if (note < 7) { //the note is not a rest; has scale value
                note = scale[note];
                notes.add(note);
            }
            else {
                notes.add(-1);
            }
        }
    }
    
    /**
     * @return the note information of this piece
     */
    public LinkedList<Integer> getNotes() {
        LinkedList<Integer> copy = new LinkedList<Integer>();
        for (int i = 0; i < len; i++) {
            copy.add(notes.get(i));
        }
        return copy;
    }
}
