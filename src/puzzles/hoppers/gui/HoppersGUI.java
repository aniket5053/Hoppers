package puzzles.hoppers.gui;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import puzzles.common.Observer;
import puzzles.hoppers.model.HoppersConfig;
import puzzles.hoppers.model.HoppersModel;

import javafx.application.Application;
import javafx.stage.Stage;

import java.io.File;
/**
 * A class of the GUI of the Hoopers game
 * @author Lucas Romero
 * @author Aniket Sonika
 */


public class HoppersGUI extends Application implements Observer<HoppersModel, String> {
    /** The resources directory is located directly underneath the gui package */
    private final static String RESOURCES_DIR = "resources/";
    private HoppersModel model;
    private HoppersConfig config;
    private boolean initialize;
    private char[][] map;
    private String file1;
    private int selRow;
    private int selCol;
    private Label update = new Label();
    private Stage stage;

    public final static char EMPTY = '.';
    public final static char INVALID = '*';
    public final static char GREEN = 'G';
    public final static char RED = 'R';

    // for demonstration purposes
    private Image redFrog = new Image(getClass().getResourceAsStream(RESOURCES_DIR+"red_frog.png"));
    private Image greenFrog = new Image(getClass().getResourceAsStream(RESOURCES_DIR+"green_frog.png"));
    private Image lilyPad = new Image(getClass().getResourceAsStream(RESOURCES_DIR+"lily_pad.png"));
    private Image water = new Image(getClass().getResourceAsStream(RESOURCES_DIR+"water.png"));
    private GridPane gridPane;
    private BorderPane borderPane;

    /**
     * Initializes the GUI
     */
    public void init() {
        String filename = getParameters().getRaw().get(0);
        file1 = filename;
        initialize =  false;
        this.model= new HoppersModel(filename);
        model.addObserver(this);
        map = this.model.getCurrentConfig().getFrogMap();
        selCol = this.model.getSelectCol();
        selRow = this.model.getSelectRow();
        this.update.setText("Loaded: " + file1);
    }

    /**
     * Creates the stage for the GUI
     * @param stage
     * @throws Exception
     */
    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;
        Scene scene = new Scene(newBoard());
        stage.setScene(scene);
        stage.show();


    }

    /**
     * Creates the border pane for the GUI
     * @return borderPane
     */
    public BorderPane newBoard(){
        borderPane = new BorderPane();
        HBox hBox = new HBox();
        borderPane.setBottom(hBox);
        borderPane.setCenter(board());
        borderPane.setTop(update);

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");

        Button load = new Button("Load");
        load.setOnAction(actionEvent -> {

            File selectedFile = fileChooser.showOpenDialog(stage);
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Text Files", "*.txt"));
            model.load(String.valueOf(selectedFile));
        });

        Button reset = new Button("Reset");
        reset.setOnAction(actionEvent -> model.reset());

        Button hint = new Button("Hint");
        hint.setOnAction(actionEvent -> model.hint());


        BorderPane.setAlignment(hBox, Pos.CENTER);
        hBox.getChildren().addAll(load, reset, hint);
        borderPane.setBottom(hBox);

        return borderPane;



    }
    /**
     * Creates the GridPane for the GUI
     * @return gridPane
     */
    public GridPane board(){
         gridPane = new GridPane();
        this.printBoard();
         borderPane.setCenter(gridPane);
        return gridPane;
    }

    /**
     *  Prints the map of the game
     */
    public void printBoard() {
        char[][] config = model.getCurrentConfig().getFrogMap();
        int row = model.getCurrentConfig().getMapRow();
        int col = model.getCurrentConfig().getMapCol();



        for (int i = 0; i < row ; i++) {
            for (int j = 0; j < col ; j++) {
                if (config[i][j] == EMPTY)
                {
                    Button lily_pad = new Button();
                    lily_pad.setGraphic(new ImageView(lilyPad));
                    String rowS = String.valueOf(i);
                    String colS = String.valueOf(j);
                    lily_pad.setOnAction(actionEvent -> this.model.select(Integer.parseInt(rowS), Integer.parseInt(colS)));
                    gridPane.add(lily_pad, j, i);

                }
                if (config[i][j] == INVALID)
                {
                    Button invalid = new Button();
                    invalid.setGraphic(new ImageView(water));

                    gridPane.add(invalid, j, i);

                }

                if (config[i][j] == GREEN)
                {
                    Button green_frog = new Button();
                    green_frog.setGraphic(new ImageView(greenFrog));
                    String rowS = String.valueOf(i);
                    String colS = String.valueOf(j);
                    green_frog.setOnAction(actionEvent -> this.model.select(Integer.parseInt(rowS), Integer.parseInt(colS)));
                    gridPane.add(green_frog, j, i);

                }

                if (config[i][j] == RED)
                {
                    Button red_frog = new Button();
                    red_frog.setGraphic(new ImageView(redFrog));
                    String rowS = String.valueOf(i);
                    String colS = String.valueOf(j);
                    red_frog.setOnAction(actionEvent -> this.model.select(Integer.parseInt(rowS), Integer.parseInt(colS)));
                    gridPane.add(red_frog, j, i);

                }


            }

        }
    }

    /**
     * Updates the GUI
     * @param hoppersModel
     * @param msg
     */
    @Override
    public void update(HoppersModel hoppersModel, String msg) {

        update.setText(msg);
        this.board();

    }

    /**
     * Main method of the GUI
     * @param args
     */
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java HoppersPTUI filename");
        } else {
            Application.launch(args);
        }
    }
}
