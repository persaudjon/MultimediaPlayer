package application;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

public class Player extends BorderPane {
	Media media;
	MediaPlayer player;
	MediaView view;
	Pane apane;
	MediaBar bar;
	public Player(String file){
		media = new Media(file); // stores file location to a media 
		player = new MediaPlayer(media); // the media is then put inside the player
		view = new MediaView(player); // and the player is put in the mediaView or window
		apane = new Pane();
		
		apane.getChildren().add(view);//the pane gets the view so that it can be displayed
		
		setCenter(apane); //it is centered in th screen
		
		bar = new MediaBar(player);// this adds the media bar class to the player 
		setBottom(bar);
		setStyle("fx-background-color: #0099ff");
		player.play();
		
	}

}
