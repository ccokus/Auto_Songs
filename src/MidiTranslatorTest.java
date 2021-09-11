import java.util.LinkedList;
import inst.GranularInst;
import inst.PluckInst;
import inst.SabersawInst;
import jm.audio.Instrument;
import jm.music.data.CPhrase;
import jm.music.data.Part;
import jm.music.data.Phrase;
import jm.music.data.Score;
import jm.util.Play;
import jm.util.Write;

public class MidiTranslatorTest {

    public static void main(String args[]) {
        test1();
    }
    
    public static void test1() {
        String test1 = "Riff: \r\n" + 
            "- 10 - 7\r\n" + 
            "9 2 7 4\r\n" + 
            "\r\n" + 
            "Progression: \r\n" + 
            "10 - - -\r\n" + 
            "0 - - -\r\n";
        
        MidiTranslator mt = new MidiTranslator(0);
        
        Phrase pl = new Phrase();
        
        Phrase riff = mt.findMidiRiff(test1);
        //System.out.println("riff section: \n" + riff.toString());
        
        Phrase prog = mt.findMidiProg(test1);
        //System.out.println("prog section: \n" + prog.toString());
        
        Instrument inst1 = new SawCombInst(100000);
        Instrument inst2 = new SawCombInst(100000);
        
        Instrument[] insts = new Instrument[2];
        insts[0] = inst1;
        insts[1] = inst2;
        
        Part riffPart = new Part();
        riffPart.setChannel(0);
        riffPart.setInstrument(0);
        riffPart.appendPhrase(riff);
        riffPart.appendPhrase(riff);
        
        Part progPart = new Part();
        progPart.setChannel(1);
        progPart.setInstrument(1);
        progPart.appendPhrase(prog);
        progPart.appendPhrase(prog);
        
        Score song = new Score();
        song.add(riffPart);
        song.add(progPart);
        
        Write.au(song, "test.au", insts);
    }
    
    public static void test2() {
        String riff = "Riff: \r\n" + 
            "- 10 - 7\r\n" + 
            "9 2 7 4\r\n" + 
            "\r\n";
        String prog = "Progression: \r\n" + 
            "10 11 10 -\r\n" + 
            "0 1 0 -\r\n";
        
        MidiTranslator mt = new MidiTranslator(0);
        
        CPhrase hopesAndDreams = mt.findMidiBoth(riff, prog);
       
        Instrument[] insts = {new GranularInst(0), new GranularInst(3)};
        Part bs = new Part();
        bs.addCPhrase(hopesAndDreams);
        
        System.out.println(bs);
        
        Write.au(bs, insts);
    }
}
