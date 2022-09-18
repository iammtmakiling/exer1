package main;

import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class Element {
	private int type;
	protected Image img;
	protected GameStage gameStage;
	protected int row, col;
	protected ImageView imgView;

	public final static Image ZERO = new Image ("images/0.png",GameStage.CELL_WIDTH,GameStage.CELL_WIDTH,false,false);
	public final static Image ONE = new Image ("images/1.png",GameStage.CELL_WIDTH,GameStage.CELL_WIDTH,false,false);
	public final static Image TWO = new Image ("images/2.png",GameStage.CELL_WIDTH,GameStage.CELL_WIDTH,false,false);
	public final static Image THREE = new Image ("images/3.png",GameStage.CELL_WIDTH,GameStage.CELL_WIDTH,false,false);
	public final static Image FOUR = new Image ("images/4.png",GameStage.CELL_WIDTH,GameStage.CELL_WIDTH,false,false);
	public final static Image FIVE = new Image ("images/5.png",GameStage.CELL_WIDTH,GameStage.CELL_WIDTH,false,false);
	public final static Image SIX = new Image ("images/6.png",GameStage.CELL_WIDTH,GameStage.CELL_WIDTH,false,false);
	public final static Image SEVEN = new Image ("images/7.png",GameStage.CELL_WIDTH,GameStage.CELL_WIDTH,false,false);
	public final static Image EIGHT = new Image ("images/8.png",GameStage.CELL_WIDTH,GameStage.CELL_WIDTH,false,false);

	public final static int IMAGE_SIZE = 70;

	public Element(int type, GameStage gameStage) {
		this.type = type;
		this.gameStage = gameStage;

		switch(this.type) {
			case 1: this.img = Element.ONE; break;
			case 2: this.img = Element.TWO; break;
			case 3: this.img = Element.THREE; break;
			case 4: this.img = Element.FOUR; break;
			case 5: this.img = Element.FIVE; break;
			case 6: this.img = Element.SIX; break;
			case 7: this.img = Element.SEVEN; break;
			case 8: this.img = Element.EIGHT; break;
			case 0: this.img = Element.ZERO; break;
		}

		// call the functions that sets the image and handles the events
		this.setImageView();
		this.setMouseHandler();
	}

	protected ImageView getImageView(){
		return this.imgView;
	}

	// implement the functions that initializes the image view of this element and the event handlers you need
	void initRowCol(int i, int j) {
		this.row = i;
		this.col = j;
	}

	private void setImageView() {
		// initialize the image view of this element
		this.imgView = new ImageView();
		this.imgView.setImage(this.img);
		this.imgView.setLayoutX(0);
		this.imgView.setLayoutY(0);
		this.imgView.setPreserveRatio(true);
	}


	private void setMouseHandler(){
		Element element = this;
		this.imgView.setOnMouseClicked(new EventHandler<MouseEvent>(){
			public void handle(MouseEvent e) {
				String move = element.gameStage.movement(element);
				if (move != "none"){
					Element zeroTile = element.gameStage.findZero();
					int tileNumber = element.gameStage.getTileLocation(element);

					element.gameStage.tileSwitch(element, zeroTile);
					switchImage(zeroTile, tileNumber);
					switchImage(element, 0);

//					System.out.println(zeroTile.getRow());
//					System.out.println(zeroTile.getCol());



				} else {
					return;
				}
			}
		});
	}

	private void switchImage(Element element, int toSwitch) {
		switch (toSwitch){
			case 1:
				element.setType(1);
				element.setImg(Element.ONE);
				changeImage(element,Element.ONE);
				break;
			case 2:
				element.setType(2);
				element.setImg(Element.TWO);
				changeImage(element,Element.TWO);
				break;
			case 3:
				element.setType(3);
				element.setImg(Element.THREE);
				changeImage(element,Element.THREE);
				break;
			case 4:
				element.setType(4);
				element.setImg(Element.FOUR);
				changeImage(element,Element.FOUR);
				break;
			case 5:
				element.setType(5);
				element.setImg(Element.FIVE);
				changeImage(element,Element.FIVE);
				break;
			case 6:
				element.setType(6);
				element.setImg(Element.SIX);
				changeImage(element,Element.SIX);
				break;
			case 7:
				element.setType(7);
				element.setImg(Element.SEVEN);
				changeImage(element,Element.SEVEN);
				break;
			case 8:
				element.setType(8);
				element.setImg(Element.EIGHT);
				changeImage(element,Element.EIGHT);
				break;
			case 0:
				element.setType(0);
				changeImage(element,Element.ZERO);
				element.setImg(Element.ZERO);
				break;
		}
	}

	private void changeImage(Element element, Image image) {
		element.imgView.setImage(image);
	}


	int getType(){
		return this.type;
	}

	int getRow() {
		return this.row;
	}

	int getCol() {
		return this.col;
	}
	void setType(int type){
		this.type = type;
	}

	void setImg(Image img){
		this.img = img;
	}
}