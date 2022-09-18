package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class GameStage {
	private Scene scene;
	private Stage stage;
	private StackPane pane;

	/*Group layout/container is a component which applies no special
	layout to its children. All child components (nodes) are positioned at 0,0*/
	private Group root;
	private Canvas canvas;
	private GraphicsContext gc;

	private GridPane map;
	private int[][] gameBoard;

	private ArrayList<Element> tiles;


	private int[][] puzzleNumbers = {{1,8,2},{0,4,3},{7,6,5}};
	private int[][] goalBoard = {{1,2,3},{4,5,6},{7,8,9}};

	public final static int MAX_CELLS = 81;
	public final static int MAP_NUM_ROWS = 3;
	public final static int MAP_NUM_COLS = 3;
	public final static int MAP_WIDTH = 500;
	public final static int MAP_HEIGHT = 500;
	public final static int CELL_WIDTH = 160;
	public final static int CELL_HEIGHT = 160;
	public final static int WINDOW_WIDTH = 510;
	public final static int WINDOW_HEIGHT = 560;

	public final Image bg = new Image("images/background.jpg",610,610,false,false);
	private boolean status;

	public GameStage() throws FileNotFoundException {
		this.root = new Group();
		this.scene = new Scene(root, GameStage.WINDOW_WIDTH,GameStage.WINDOW_HEIGHT,Color.WHITE);
		this.canvas = new Canvas(GameStage.WINDOW_WIDTH,GameStage.WINDOW_HEIGHT);
		this.gc = canvas.getGraphicsContext2D();

		this.map = new GridPane();
		this.tiles = new ArrayList<Element>();
		this.gameBoard = new int[GameStage.MAP_NUM_ROWS][GameStage.MAP_NUM_COLS];

		File puzzleFile = new File("puzzle.in");
		if (puzzleFile.exists()){
			Scanner scan = new Scanner(puzzleFile);

			System.out.println(scan.nextLine());
		}else {
		   System.out.println("Input file does not exist.");
		}

		if (isSolvable()){
			System.out.println("Solvable");
			this.status = true;
		}else{
			System.out.println("Not Solvable");
			this.status = false;
		}


	}

	private boolean isSolvable(){

		int oneLiner[];
		oneLiner = new int[9];
		int count = 0;

		for (int i=0; i<3; i++){
			for (int j=0; j<3; j++){
				oneLiner[count++] = puzzleNumbers[i][j];
			}
		}

		int inversionCount = countInversion(oneLiner);
		System.out.println("Inversion Count: ");
		System.out.println(inversionCount);
		if (inversionCount % 2 == 0){
			return true;
		}

		return false;
	}

	private int countInversion(int[] puzzle){
		int count = 0;
		for (int i = 0; i<9; i++){
			for (int j = i; j<9; j++){
				if (puzzle[j] != 0 && puzzle[i] != 0 && puzzle[i] > puzzle[j]){
					count++;
				}
			}
		}

		return count;
	}



	//Method to add the stage elements
	public void setStage(Stage stage){
		this.stage = stage;
		//draw the background to the canvas at location x=0, y=70
		this.gc.drawImage( this.bg, 0, 0);

		Font theFont = Font.font("Roboto",FontWeight.BOLD,20);//set font type, style and size
		this.gc.setFont(theFont);

		this.initGameBoard();
		this.createMap();

		this.root.getChildren().add(canvas);
		this.root.getChildren().add(map);


		this.stage.setTitle("8-Puzzle Game");
		this.stage.setScene(this.scene);
		this.stage.show();

		if (this.status){
			this.gc.fillText("The Puzzle is Solvable, Good Luck!", GameStage.WINDOW_WIDTH*0.20, GameStage.WINDOW_HEIGHT*0.08);
		} else {
			this.gc.fillText("The Puzzle is Not Solvable!", GameStage.WINDOW_WIDTH*0.25, GameStage.WINDOW_HEIGHT*0.08);
		}
	}

	private void createMap(){
		for(int i=0;i<GameStage.MAP_NUM_ROWS;i++){
			for(int j=0;j<GameStage.MAP_NUM_COLS;j++){
				// Instantiate tile elements
				Element tile = new Element(puzzleNumbers[i][j],this);
				tile.initRowCol(i,j);

				this.tiles.add(tile);
			}
		}
		this.setGridPaneProperties();
		this.setGridPaneContents();
	}

	//method to set size and location of the grid pane
	private void setGridPaneProperties(){
		this.map.setPrefSize(GameStage.MAP_WIDTH, GameStage.MAP_HEIGHT);
		//set the map to x and y location; add border color to see the size of the gridpane/map
		this.map.setLayoutX(GameStage.WINDOW_WIDTH*0.02);
	    this.map.setLayoutY(GameStage.WINDOW_WIDTH*0.12);
	    this.map.setVgap(5);
	    this.map.setHgap(5);
	}

	private void initGameBoard(){
		for(int i=0;i<GameStage.MAP_NUM_ROWS;i++){
			for(int j=0; j<GameStage.MAP_NUM_COLS; j++){
				this.gameBoard[i][j] = puzzleNumbers[i][j];
			}
		}

		for(int i=0;i<GameStage.MAP_NUM_ROWS;i++){
			System.out.println(Arrays.toString(this.gameBoard[i]));//print final board content
		}
	}

	//method to add row and column constraints of the grid pane
	private void setGridPaneContents(){
		 //loop that will set the constraints of the elements in the grid pane
	     int counter=0;
	     for(int row=0;row<GameStage.MAP_NUM_ROWS;row++){
	    	 for(int col=0;col<GameStage.MAP_NUM_COLS;col++){
	    		 // map each land's constraints
	    		 GridPane.setConstraints(tiles.get(counter).getImageView(),col,row);
	    		 counter++;
	    	 }
	     }
	   //loop to add each land element to the gridpane/map
	     for(Element tile: tiles){
	    	 this.map.getChildren().add(tile.getImageView());
	     }
	}

	String movement (Element element){
		int i = element.getRow();
		int j = element.getCol();

		//check up
		if (isZero(i-1,j)== true){
			return "up";
		}
		//check down
		if (isZero(i+1,j)== true){
			return "down";
		}
		//check right
		if (isZero(i,j+1)== true){
			return "right";
		}
		//check left
		if (isZero(i,j-1)== true){
			return "left";
		}
		return "none";
	}

	boolean isZero(int i, int j){
		if (i < 0 || j > 2 || j < 0 || i > 2 ){
			return false;
		}
		if(this.gameBoard[i][j] == 0){
			return true;
		}

		return false;
	}

	int getTileLocation( Element element){

		int i = element.getRow();
		int j = element.getCol();

		return (gameBoard[i][j]);
	}

	protected void tileSwitch (Element element, Element zero){
		int tileRow = element.getRow();
		int tileCol = element.getCol();
		int ZeroRow = zero.getRow();
		int ZeroCol = zero.getCol();

		System.out.println("Zero Type:" + zero.getType());

		int temp = gameBoard[tileRow][tileCol];
		gameBoard[tileRow][tileCol] = gameBoard[ZeroRow][ZeroCol];
		gameBoard[ZeroRow][ZeroCol] = temp;

		element.initRowCol(ZeroRow,ZeroCol);
		zero.initRowCol(tileRow, tileCol);

//		int typeTemp = element.getType();
//		zero.setType(typeTemp);
//		element.setType(0);

		System.out.println("====================");
		printBoard();
	}

	protected Element findZero(){
		for (int i =0; i < tiles.size(); i++){
			if (tiles.get(i).getType() == 0){
				return(tiles.get(i));
			}
		}
		return null;
	}

	protected void printBoard (){
		for(int i=0;i<GameStage.MAP_NUM_ROWS;i++){
			System.out.println(Arrays.toString(this.gameBoard[i]));//print final board content
		}
	}
}