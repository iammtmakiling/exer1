package main;

import java.io.FileNotFoundException;

/********************************************
*
* Chapter 08: GUI
*
* Make a Modified Minesweeper Game
*
* @author Michael Jay Makiling
* @date 9-18-2022 07:00 PM
*/


import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	public void start(Stage stage) throws FileNotFoundException{
		GameStage theGameStage = new GameStage();
		theGameStage.setStage(stage);
	}

}
