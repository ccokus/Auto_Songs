import java.awt.EventQueue;
import java.awt.PopupMenu;
import javax.swing.JFrame;
import java.awt.Button;
import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Choice;
import java.awt.GridBagLayout;
import javax.swing.JCheckBox;
import java.awt.GridBagConstraints;
import javax.swing.JEditorPane;
import java.awt.Insets;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import java.awt.TextArea;
import javax.swing.JTextPane;
import javax.swing.text.Document;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.parser.Element;
import java.awt.Color;
import javax.swing.JScrollBar;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormSpecs;
import com.jgoodies.forms.layout.RowSpec;
import jm.audio.Instrument;
import jm.music.data.Note;
import jm.music.data.Part;
import jm.music.data.Phrase;
import jm.music.data.Score;
import jm.util.Play;
import jm.util.Write;
import javax.swing.SwingConstants;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;


public class SongWindow {

    private JFrame frame;
    private String[] send = new String[4];
    private HTMLDocument doc;
    private Phrase riff;
    private Phrase prog;
    private Part song;
    MidiTranslator composer;
    private Instrument instRiff = new SawCombInst(10000); //set instrument for riff
    private Instrument instProg = new SawCombInst(10000); //set instrument for progression
    private Instrument[] insts;
    private JTextField textNameEnter;
    private JTextField textNumReps;
    private JTextField textTempo;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    SongWindow window = new SongWindow();
                    window.frame.setVisible(true);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }


    /**
     * Create the application.
     * @throws ParserConfigurationException 
     */
    public SongWindow() {
        initialize();
        
        doc = new HTMLDocument();
        riff = new Phrase();
        prog = new Phrase();
        song = new Part();
        composer = new MidiTranslator(1);
        insts = new Instrument[2];
        insts[0] = instRiff;
        insts[1] = instProg;
    }


    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frame = new JFrame();
        frame.setBounds(100, 100, 450, 319);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[]{35, 60, 35, 28, 93, 0};
        gridBagLayout.rowHeights = new int[]{14, 23, 23, 156, 0};
        gridBagLayout.columnWeights = new double[]{1.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
        gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
        frame.getContentPane().setLayout(gridBagLayout);
        
        Choice density = new Choice();
        density.add("1/4");
        density.add("1/8");
        density.add("1/16");
        
        //JLABELS
        
        JLabel lblLength = new JLabel("Length");
        GridBagConstraints gbc_lblLength = new GridBagConstraints();
        gbc_lblLength.insets = new Insets(0, 0, 5, 5);
        gbc_lblLength.gridx = 0;
        gbc_lblLength.gridy = 0;
        frame.getContentPane().add(lblLength, gbc_lblLength);
        
        JLabel lblDensity = new JLabel("Note Density");
        GridBagConstraints gbc_lblDensity = new GridBagConstraints();
        gbc_lblDensity.insets = new Insets(0, 0, 5, 5);
        gbc_lblDensity.gridx = 1;
        gbc_lblDensity.gridy = 0;
        frame.getContentPane().add(lblDensity, gbc_lblDensity);
        
        JLabel lblMode = new JLabel("Mode");
        GridBagConstraints gbc_lblMode = new GridBagConstraints();
        gbc_lblMode.insets = new Insets(0, 0, 5, 5);
        gbc_lblMode.gridx = 2;
        gbc_lblMode.gridy = 0;
        frame.getContentPane().add(lblMode, gbc_lblMode);
        
        
        //CHOICES
        
        Choice length = new Choice();
        length.add("2 Measure Phrase");
        length.add("4 Measure Phrase");
        length.add("8 Measure Phrase");
        
        JLabel lblRepetition = new JLabel("Repetition");
        GridBagConstraints gbc_lblRepetition = new GridBagConstraints();
        gbc_lblRepetition.anchor = GridBagConstraints.EAST;
        gbc_lblRepetition.insets = new Insets(0, 0, 5, 5);
        gbc_lblRepetition.gridx = 3;
        gbc_lblRepetition.gridy = 0;
        frame.getContentPane().add(lblRepetition, gbc_lblRepetition);
        
        
        
        Choice mode = new Choice();
        mode.add("positive");
        mode.add("explorative");
        mode.add("interogative");
        mode.add("negative");
        GridBagConstraints gbc_mode = new GridBagConstraints();
        gbc_mode.insets = new Insets(0, 0, 5, 5);
        gbc_mode.gridx = 2;
        gbc_mode.gridy = 1;
        frame.getContentPane().add(mode, gbc_mode);
        
        Choice repeat = new Choice();
        repeat.add("very low");
        repeat.add("low");
        repeat.add("high");
        repeat.add("very high");
        GridBagConstraints gbc_repeat = new GridBagConstraints();
        gbc_repeat.insets = new Insets(0, 0, 5, 5);
        gbc_repeat.gridx = 3;
        gbc_repeat.gridy = 1;
        frame.getContentPane().add(repeat, gbc_repeat);
        
        JLabel lblSongName = new JLabel("Song Name:");
        GridBagConstraints gbc_lblSongName = new GridBagConstraints();
        gbc_lblSongName.insets = new Insets(0, 0, 5, 5);
        gbc_lblSongName.anchor = GridBagConstraints.EAST;
        gbc_lblSongName.gridx = 0;
        gbc_lblSongName.gridy = 2;
        frame.getContentPane().add(lblSongName, gbc_lblSongName);
        
        //TextFields for naming songs, setting tempo, setting number repetitions
        
        textNameEnter = new JTextField();
        GridBagConstraints gbc_textNameEnter = new GridBagConstraints();
        gbc_textNameEnter.gridwidth = 3;
        gbc_textNameEnter.insets = new Insets(0, 0, 5, 5);
        gbc_textNameEnter.fill = GridBagConstraints.HORIZONTAL;
        gbc_textNameEnter.gridx = 1;
        gbc_textNameEnter.gridy = 2;
        frame.getContentPane().add(textNameEnter, gbc_textNameEnter);
        textNameEnter.setColumns(10);
        textNameEnter.setText("(Song Name)");
        
        textTempo = new JTextField();
        GridBagConstraints gbc_textTempo = new GridBagConstraints();
        gbc_textTempo.insets = new Insets(0, 0, 5, 0);
        gbc_textTempo.fill = GridBagConstraints.HORIZONTAL;
        gbc_textTempo.gridx = 4;
        gbc_textTempo.gridy = 0;
        frame.getContentPane().add(textTempo, gbc_textTempo);
        textTempo.setColumns(10);
        GridBagConstraints gbc_length = new GridBagConstraints();
        gbc_length.insets = new Insets(0, 0, 5, 5);
        gbc_length.gridx = 0;
        gbc_length.gridy = 1;
        frame.getContentPane().add(length, gbc_length);
        GridBagConstraints gbc_density = new GridBagConstraints();
        gbc_density.insets = new Insets(0, 0, 5, 5);
        gbc_density.gridx = 1;
        gbc_density.gridy = 1;
        frame.getContentPane().add(density, gbc_density);
        textTempo.setText("(Tempo)");
        
        textNumReps = new JTextField();
        GridBagConstraints gbc_textNumReps = new GridBagConstraints();
        gbc_textNumReps.anchor = GridBagConstraints.NORTH;
        gbc_textNumReps.fill = GridBagConstraints.HORIZONTAL;
        gbc_textNumReps.gridx = 4;
        gbc_textNumReps.gridy = 3;
        frame.getContentPane().add(textNumReps, gbc_textNumReps);
        textNumReps.setColumns(10);
        textNumReps.setText("(Number of Reps)");
        
        JPanel panel = new JPanel();
        panel.setBorder(new TitledBorder(null, "JPanel title", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        GridBagConstraints gbc_panel = new GridBagConstraints();
        gbc_panel.fill = GridBagConstraints.BOTH;
        gbc_panel.gridwidth = 2;
        gbc_panel.insets = new Insets(0, 0, 0, 5);
        gbc_panel.gridx = 0;
        gbc_panel.gridy = 3;
        frame.getContentPane().add(panel, gbc_panel);
        GridBagLayout gbl_panel = new GridBagLayout();
        gbl_panel.columnWidths = new int[]{35, 60, 35, 28, 64, 0};
        gbl_panel.rowHeights = new int[]{156, 0};
        gbl_panel.columnWeights = new double[]{1.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
        gbl_panel.rowWeights = new double[]{0.0, Double.MIN_VALUE};
        panel.setLayout(gbl_panel);
        
        //TextArea for displaying songs visually
        
        JTextArea textSongDisplay = new JTextArea();
        GridBagConstraints gbc_textSongDisplay = new GridBagConstraints();
        gbc_textSongDisplay.fill = GridBagConstraints.BOTH;
        gbc_textSongDisplay.gridwidth = 5;
        gbc_textSongDisplay.insets = new Insets(0, 0, 0, 5);
        gbc_textSongDisplay.gridx = 0;
        gbc_textSongDisplay.gridy = 0;
        panel.add(textSongDisplay, gbc_textSongDisplay);
        
        
        //JButtons for generation and saving songs as audio
        
        JButton btnCreateSong = new JButton("Create Song");
        GridBagConstraints gbc_btnCreateSong = new GridBagConstraints();
        gbc_btnCreateSong.insets = new Insets(0, 0, 5, 0);
        gbc_btnCreateSong.gridx = 4;
        gbc_btnCreateSong.gridy = 1;
        frame.getContentPane().add(btnCreateSong, gbc_btnCreateSong);
        btnCreateSong.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                
                int densityVal = (density.getSelectedIndex());
                
                send[0] = Integer.toString(length.getSelectedIndex());
                send[1] = Integer.toString(densityVal);
                send[2] = Integer.toString(mode.getSelectedIndex());
                send[3] = Integer.toString(repeat.getSelectedIndex());
                
                composer.setDensity(densityVal);
                
                //songInfo: [0] has riff notation, [1] has bass notation
                String songInfo[] = SongMaker.songMake((send));
                
                
                textSongDisplay.setText("");
                textSongDisplay.append(songInfo[0]);
                textSongDisplay.append(songInfo[1]);
                
                riff = composer.findMidiRiff(songInfo[0]);
                prog = composer.findMidiProg(songInfo[1]);

            }
        });     
        
        JButton btnStoreSong = new JButton("Store Song");
        GridBagConstraints gbc_btnStoreSong = new GridBagConstraints();
        gbc_btnStoreSong.insets = new Insets(0, 0, 5, 0);
        gbc_btnStoreSong.gridx = 4;
        gbc_btnStoreSong.gridy = 2;
        frame.getContentPane().add(btnStoreSong, gbc_btnStoreSong);
        btnStoreSong.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                
                composer.setDensity(density.getSelectedIndex());
                
                Part riffPart = new Part();
                riffPart.setChannel(0);
                riffPart.setInstrument(0);
                
                Part progPart = new Part();
                progPart.setChannel(1);
                progPart.setInstrument(1);
                
                for (int i = 0; i < Integer.parseInt(textNumReps.getText()); i++) {
                    riffPart.appendPhrase(riff);
                    progPart.appendPhrase(prog);
                }
                
                Score song = new Score();
                song.add(riffPart);
                song.add(progPart);
                
                song.setTempo(Integer.parseInt(textTempo.getText()));
                
                Write.au(song, textNameEnter.getText() + ".au", insts);
            }
        });
        
        
        JButton btnPlay = new JButton("Play");
        GridBagConstraints gbc_btnPlay = new GridBagConstraints();
        gbc_btnPlay.insets = new Insets(0, 0, 0, 5);
        gbc_btnPlay.gridx = 3;
        gbc_btnPlay.gridy = 3;
        frame.getContentPane().add(btnPlay, gbc_btnPlay);
        btnPlay.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                
                Score score = new Score();
                
              //extend/shorten riff to appropriate number of repetitions
                int numReps = Integer.parseInt(textNumReps.getText());
                int riffLength = riff.getNoteArray().length;
                
                Note[] riffArray = new Note[riffLength * numReps];
                //outer loop for how many times we need to repeat array
                for (int i = 0; i < numReps; i++) {
                    //inner loop will append a copy of notes once
                    for (int j = 0; j < riffLength; j++) {
                        riffArray[j + (i * riffLength)] = riff.getNote(j);
                    }
                }
                
                Note[] progArray = new Note[riffLength * numReps];
                //outer loop for how many times we need to repeat array
                for (int i = 0; i < numReps; i++) {
                    //inner loop will append a copy of notes once
                    for (int j = 0; j < riffLength; j++) {
                        progArray[j + (i * riffLength)] = prog.getNote(j);
                    }
                }
                
                
                Phrase riffPlay = new Phrase(riffArray);
                Phrase progPlay = new Phrase(progArray);
                
                Double tempo = (Double.parseDouble(textTempo.getText()));
                //set tempos of parts and score
                riffPlay.setTempo(tempo);
                progPlay.setTempo(tempo);
                score.setTempo(tempo);
                
                
                
                //combine riff and progression into parts array for score
                Part[] parts = new Part[2];
                parts[0] = new Part(riffPlay);
                parts[0].setChannel(0);
                parts[0].setInstrument(0);
                parts[0].setTempo(tempo);
                parts[1] = new Part(progPlay);
                parts[1].setChannel(1);
                parts[1].setInstrument(1);
                parts[1].setTempo(tempo);
                
                
                score.addPartList(parts);
                
                System.out.println(score);
                Play.audio(score, insts);
            }
        });
    }

}
