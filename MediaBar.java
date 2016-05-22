package application;

import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;

//HBox is a bar that lies across the media player for a file chooser
public class MediaBar extends HBox{
	Slider time = new Slider(); // creates a slider for manipulating the time and volume
	Slider vol = new Slider();
	Button playButton = new Button("||"); // creates button for pausing video
	Label volume = new Label("Volume: ");
	
	MediaPlayer player;
	
	public MediaBar(MediaPlayer play){
		player = play; // set player in this class to the one we choose
		
		
		setAlignment(Pos.CENTER);// centers HBox aka menu bar
		setPadding(new Insets(5,10,5,10));
		
		vol.setPrefWidth(70);// sets the size of volume bar
		vol.setMinWidth(30);
		vol.setValue(100);
		
		HBox.setHgrow(time, Priority.ALWAYS);
		
		playButton.setPrefWidth(30);
		
		getChildren().add(playButton);// adds all object to the media player, note how order matters
		getChildren().add(time);
		getChildren().add(vol);
		getChildren().add(volume);
		
		playButton.setOnAction(new EventHandler<ActionEvent>() {//when ever the button is pressed
			
			@Override
			public void handle(ActionEvent event) { //this handles the button event
				 Status status = player.getStatus();//this stores if button is either playing or paused
				 
				 if(status == Status.PLAYING){//if the button is playing then
					 if(player.getCurrentTime().greaterThanOrEqualTo(player.getTotalDuration())){ //check if we are at the end of the video
						 player.seek(player.getStartTime());//then it will play the video over again on a loop
						 player.play();
					 }
					 else{
						 player.pause();
						 playButton.setText(">");
					 }
				 }
				if(status == Status.PAUSED || status == Status.HALTED || status == Status.STOPPED){ //checks if vid is stopped for any reason
					player.play();
					playButton.setText("||");//this changes the symbol on button to pause
				}
			}
		});
		
		player.currentTimeProperty().addListener(new InvalidationListener() {
			
			@Override
			public void invalidated(Observable observable) {
				updateValues();
				
			}
		});
		time.valueProperty().addListener(new InvalidationListener() {
			
			public void invalidated(Observable observable) {//allows user to move video file timer
				if(time.isPressed()){
					player.seek(player.getMedia().getDuration().multiply(time.getValue()/100));
				}
				
			}
		});
		
		vol.valueProperty().addListener(new InvalidationListener() {
			
			@Override
			public void invalidated(Observable observable) {
				if(vol.isPressed()){
					player.setVolume(vol.getValue()/100);
				}
				
			}
		});
		
	}
	protected void updateValues(){//moves timer silder according to where the media is playing
		Platform.runLater(new Runnable(){
			public void run(){
				time.setValue(player.getCurrentTime().toMillis()/player.getTotalDuration().toMillis()*100);
			}
		});
	}
}
