import java.util.Scanner;
import jm.music.data.CPhrase;
import jm.music.data.Note;
import jm.music.data.Phrase;
import jm.constants.*;

/**
 * Create Midi messages based off of output strings from song writer
 * @author cmcok
 *
 */
public class MidiTranslator {
    
    private int d;

    public MidiTranslator(int density) {
        d = density;
    }
    
    public void setDensity(int density) {
        d = density;
    }
    
    public Phrase findMidiRiff(String prevOut) {
        Phrase midiRiff = new Phrase();
        @SuppressWarnings("resource")
        Scanner scan = new Scanner(prevOut);
        
        String check = "";
        //while loop to push scanner past "Riff:"
        while (!check.contains("Riff:") && scan.hasNext()) {
            check = scan.next();
        }
        
        String noteVal = "";
        double rhythmVal = (1 / (Math.pow(2, d)));
        //while loop that reads in all integers before "Progression:"
        while (scan.hasNext()) {
            noteVal = scan.next();
            if (noteVal.contains("Progression:")) {
                break;
            }
            if (noteVal.equals("-")) {
                Note newRest = new Note(0, rhythmVal, 0);
                newRest.setDynamic(0);
                midiRiff.add(newRest);
            }
            else {
                Note newNote = new Note((Integer.parseInt(noteVal) + 72), rhythmVal);
                midiRiff.add(newNote);
            }
        }
        
        return midiRiff;
    }
    
    
    public Phrase findMidiProg(String prevOut) {
        Phrase midiProg = new Phrase();
        @SuppressWarnings("resource")
        Scanner scan = new Scanner(prevOut);
        
        String check = "";
        //while loop to push scanner past "Progression:"
        while (!check.contains("Progression:") && scan.hasNext()) {
            check = scan.next();
        }  
        
        String noteVal = "";
        System.out.println(d);
        double rhythmVal = (1 / (Math.pow(2, d)));
        
        System.out.println(rhythmVal);
        
        //while loop that read in all integers until no more are present
        while (scan.hasNext()) {
            noteVal = scan.next();
            if (noteVal.equals("-")) {
                Note newRest = new Note(0, rhythmVal, 0);
                newRest.setDynamic(0);
                midiProg.add(newRest);
            }
            else {
                Note newNote = new Note((Integer.parseInt(noteVal) + 48), rhythmVal);
                midiProg.add(newNote);
            }
        }
        
        return midiProg;
    }
}
