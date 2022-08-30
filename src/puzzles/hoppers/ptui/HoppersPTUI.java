package puzzles.hoppers.ptui;

import puzzles.common.Observer;
import puzzles.hoppers.model.HoppersModel;
import puzzles.jam.model.JamModel;

import java.io.PrintWriter;
import java.util.List;

/**
 * A class of the PTUI of the Hoppper game
 * @author Lucas Romero
 * @author Aniket Sonika
 */

public class HoppersPTUI extends ConsoleApplication implements Observer<HoppersModel, String> {
    private HoppersModel model;
    private boolean initialized;
    private PrintWriter out;


    /**
     * Inititalizes the PTUI
     * @throws Exception
     */
    @Override
    public void init() throws Exception{
        this.initialized = false;
        List< String > paramStrings = super.getArguments();
        this.model = new HoppersModel(paramStrings.get( 0 ));
        this.model.addObserver(this);

    }

    /**
     * Checks the userinput and calls the specific method from the model depending on the input
     * @param console Where the UI should print output. It is recommended to save
     *                this object in a field in the subclass.
     * @throws Exception
     */

    @Override
    public void start(PrintWriter console) throws Exception {
        this.out = console;
        this.initialized = true;
        super.setOnCommand("h", 0, "hint next move", args -> this.model.hint());
        super.setOnCommand("hint", 0, "hint next move", args -> this.model.hint());
        super.setOnCommand("l", 1, "load new puzzle file", args -> this.model.load(args[0]));
        super.setOnCommand("load", 1, "load new puzzle file", args -> this.model.load(args[0]));
        super.setOnCommand("s", 2, "select cell at r, c", args -> this.model.select(Integer.parseInt(args[0]), Integer.parseInt(args[1])));
        super.setOnCommand("select", 2, "select cell at r, c", args -> this.model.select(Integer.parseInt(args[0]), Integer.parseInt(args[1])));
        super.setOnCommand("q", 0, "quit the game", args -> this.model.quit());
        super.setOnCommand("quit", 0, "quit the game", args -> this.model.quit());
        super.setOnCommand("r", 0, "reset the current game", args -> this.model.reset());
        super.setOnCommand("reset", 0, "reset the current game", args -> this.model.reset());

    }

    /**
     * Updates the PTUI
     * @param model
     * @param msg
     */
    @Override
    public void update(HoppersModel model, String msg) {
        System.out.println(msg);
        System.out.println(model.getCurrentConfig().toString());


    }

    /**
     * Main method of the PTUI
     * @param args
     */
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java HoppersPTUI filename");
        }
        else {
            ConsoleApplication.launch( HoppersPTUI.class, args );
        }

    }
}
