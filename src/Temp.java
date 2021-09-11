import jm.JMC;
import jm.audio.Instrument;
import jm.music.data.*;
import jm.util.Write;


public class Temp implements JMC{
    public static void main(String args[]) {
        Instrument sinwav = new SimpleSineInst(44100);
        Phrase phrase = new Phrase();
        phrase.add(new Note(60, 1.0));
        
        Write.au(phrase, sinwav);
    }
}
