package puzzles.jam.gui;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import puzzles.common.Observer;
import puzzles.hoppers.model.HoppersModel;
import puzzles.jam.model.Car;
import puzzles.jam.model.JamConfig;
import puzzles.jam.model.JamModel;

import java.io.File;
import java.util.HashMap;
import java.util.Random;

/**
 * A class of the GUI of the Jam game
 * @author Lucas Romero
 * @author Aniket Sonika
 */

public class JamGUI extends Application  implements Observer<JamModel, String>  {
    /** The resources directory is located directly underneath the gui package */
    private final static String RESOURCES_DIR = "resources/";

    // for demonstration purposes
    private final static String X_CAR_COLOR = "#DF0101";
    private static final String EMPTY_COLOR = "#FFFFFF";
    private static final String BLANK = "#EBECF0";
    private final static int BUTTON_FONT_SIZE = 20;
    private final static int ICON_SIZE = 75;
    private JamModel model;
    private boolean initialize;
    private String file1;
    private int row;
    private int col;
    private HashMap<Character, Car> map;
    private Label update = new Label();
    private Stage stage;
    private BorderPane borderPane;
    private GridPane gridPane;
    public static final char EMPTY = '.';


    /**
     * Initializes the GUI
     */
    public void init() {
        String filename = getParameters().getRaw().get(0);
        file1 = filename;
        initialize =  false;
        this.model= new JamModel(filename);
        model.addObserver(this);
        map = this.model.getCurrentConfig().getCars();
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
     * Prints the map of the game
     */
    public void printBoard() {
        char[][] config = model.getCurrentConfig().getMap();
        int row = model.getCurrentConfig().getRow();
        int col = model.getCurrentConfig().getCol();

        char carName = '-';


        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                int r = i;
                int c = j;
                carName = config[r][c];


                Button b1 = new Button(String.valueOf(carName));
                b1.setMaxSize(ICON_SIZE, ICON_SIZE);
                b1.setMinSize(ICON_SIZE, ICON_SIZE);
                String rowS = String.valueOf(i);
                String colS = String.valueOf(j);
                b1.setOnAction(actionEvent -> this.model.select(Integer.parseInt(rowS), Integer.parseInt(colS)));
                gridPane.add(b1, c, r);
                if (carName != EMPTY && carName != 'X' )
                {
                    b1.setStyle(
                            "-fx-font-size: " + BUTTON_FONT_SIZE + ";" +
                                    "-fx-background-color: " + EMPTY_COLOR + ";" +
                                    "-fx-font-weight: bold;");

                }
                else if (carName == 'X')
                {
                    b1.setStyle(
                            "-fx-font-size: " + BUTTON_FONT_SIZE + ";" +
                                    "-fx-background-color: " + X_CAR_COLOR + ";" +
                                    "-fx-font-weight: bold;");
                }
                else
                {
                    b1.setStyle(
                            "-fx-font-size: " + BUTTON_FONT_SIZE + ";" +
                                    "-fx-background-color: " + BLANK + ";" +
                                    "-fx-font-weight: bold;");
                }




            }
        }
    }

    /**
     * Updates the GUI
     * @param jamModel
     * @param msg
     */
    @Override
    public void update(JamModel jamModel, String msg) {

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
