import java.util.LinkedList;
import java.util.Random;

public class Harmonizer {
    
    private int[] s;
    private LinkedList<Integer> r;
    private LinkedList<Integer> history;
    private int d;
    private LinkedList<Integer> prog;
    private Random random;

    public Harmonizer(int[] scale, LinkedList<Integer> riff, int density) {
        s = scale;
        r = riff;
        history = new LinkedList<Integer>();
        d = density;
        random = new Random();
        
    }
    
    /**
     * Finds a suitable progression to use for the riff
     * @return A list of the notes chosen for the progression
     */
    public LinkedList<Integer> findProgression() {
        LinkedList<Integer> prog = new LinkedList<Integer>();
        
        int count = 0; //position in progression reached so far; necessary to start for-loop on next measure (instead of repeating)
        LinkedList<Integer> section = new LinkedList<Integer>();
        
        
        boolean useTonic;
        int decider = random.nextInt(10); // 9/10ths of time uses tonic as first note
        if (decider > 1) {
            useTonic = true;
        }
        else {
            useTonic = false;
        }
        
        int changesInRiff = getChangesInRiff(); //times a new note should happenprint
        int initChanges = changesInRiff;
        
        if (random.nextInt(3) == 0) { //gives 1/3 chance to start with tonic
            prog.add(0);
            history.add(0);
            count += r.size() / changesInRiff; //count move forward to where the next note starts (0 starts as first note)
            
            changesInRiff -= 1; //first choice is 0; 3 remaining
        }
        
        for (int i = 0; i < changesInRiff; i++) {
            for (int j = 0; j < (r.size() / initChanges); j++) {
                section.add(r.get(count + j)); 
            }
            if ((i % 4 == 0) || i == changesInRiff - 1) {
                prog.add(getBestNote(section, useTonic));
                history.add(prog.getLast());
            }
            else {
                prog.add(getBestNote(section, !useTonic));
                history.add(prog.getLast());
            }
            count += r.size() / initChanges;
            section.clear();
        }
        
        //check for phrase ending earlier than riff; must be doubled in length (repeated) if so
//        if (initChanges > r.size()) {
//            prog.addAll(prog);
//        }
        
        return prog;
    }
    
    private int getChangesInRiff() {
        int progPerMeas = random.nextInt(2) + 1; //choice of 1 or 2 key change per measure 
        double numerator = r.size() * progPerMeas;
        double denominator = Math.pow(2, d + 2);
        double changesFix = numerator / denominator;
        int changesInRiff = (int)Math.round(changesFix); //times a new note should happen
        
        return changesInRiff;
    }
    
    /**
     * Determines the point value of a given note for a section
     * @param bnote The base note being evaluated for the given section
     * @param section The section of the riff being evaluated
     * @return The point value the base note has when applied to the section
     */
    private int findPoints(int bnote, LinkedList<Integer> section) {
        int points = 0;
        int count = 0;
        for (int i = 0; i < history.size(); i++) {
            if (history.get(i) == bnote) {
                points -= 15;
                count++;
                if (count > 2) {
                    return 0;
                }
            }
        }
        
        if (bnote == 2) {
            points -= 5;
        }
        
        for (int i = 0; i < section.size() - 1; i++) { //compare basenote to each note in section
            if (section.get(i) != -1) {
                int calc = Math.floorMod(section.get(i) - bnote, 12);
                switch (calc) {
                    
                    case 0:
                        points += 5;
                        break;
                    case 1:
                        points += 3;
                        break;
                    case 2:
                        points += 7;
                        break;
                    case 3:
                    case 4:
                        points += 9;
                        break;
                    case 5:
                        points += 2;
                        break;
                    case 6:
                        points += 5;
                        break;
                    case 7:
                        points += 8;
                        break;
                    case 8:
                        points += 5;
                        break;
                    case 9:
                        points += 3;
                        break;
                    case 10:
                        points += 6;
                        break;
                    case 11:
                        points += 7;
                        break;
                }
            }
        }

        return points;
    }
    
    /**
     * Finds an optimal root note for the given chords
     * @param section The section of the riff being evaluated
     * @return
     */
    public int getBestNote(LinkedList<Integer> section, boolean useTonic) {
        int noteChoice = 0;
        int notePoints = 0;
        int newChoice;
        int newPoints;
        
        for (int i = 0; i < 3; i++) {
            newChoice = getNum(useTonic);
            //Checks in loop: 
                //1. don't use 0 major third (these don't generally sound good)
                //2. don't use major 7 (these don't generally sound good)
            while (s[newChoice] == 4 || s[newChoice] == 11) {
                newChoice = getNum(useTonic);
            }
            newPoints = findPoints(s[newChoice], section);
            if (newPoints > notePoints) {
                notePoints = newPoints;
                noteChoice = newChoice;
            }
        }

        return s[noteChoice];
    }
    
    /**
     * Returns a number 0-6 
     * @param useTonic If true, excludes choice of 0
     * @return The number 0-6
     */
    private int getNum(boolean useTonic) {
        if (useTonic) {
            return random.nextInt(7);
        }
        else {
            return random.nextInt(6) + 1;
        }
    }
    
    public LinkedList<Integer> findProgRhythm(LinkedList<Integer> prog) {
        int len = (int)Math.pow(2, d + 2); //length of measure
        int note;
        
        LinkedList<Integer> rhythm = new LinkedList<Integer>();
        rhythm.add(0);
        for (int i = 0; i < len - 1; i++) { //create random rhythm for each measure (downbeat always hit)
            note = (int)random.nextInt(2);
            rhythm.add(note);
        }
        LinkedList<Integer> finale = new LinkedList<Integer>();
        
        for (int j = 0; j < prog.size(); j++) {
            note = prog.get(j); //causes issues (no such element)
            for (int i = 0; i < len; i++) {
                if (rhythm.get(i) == 0) {
                    finale.add(note);
                }
                else {
                    finale.add(-1);
                }
            }
        }
        
        return finale;
    }
    
    public void setProg(LinkedList<Integer> progression) {
        prog = progression;
    }
    
    public LinkedList<Integer> getProg() {
        return prog;
    }
    
    /**
     * Concatenates the baseline to a given string
     * @param str - the given string
     */
    public String concat() {
        String str = "";
        int note;
        int measure = (int)Math.pow(2, (d + 2));
        str = str.concat("Progression: " + "\n");
        for (int i = 0; i < prog.size(); i++) {
            note = prog.get(i);
            if (note >= 0) {
                str = str.concat("" + note);
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
        return str;
    }
    
    public void print() {
        int note;
        int measure = (int)Math.pow(2, (d + 2));
        System.out.println("Progression:");
        for (int i = 0; i < prog.size(); i++) {
            note = prog.get(i);
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
    }
}
