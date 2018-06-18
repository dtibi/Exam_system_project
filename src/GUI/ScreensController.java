package GUI;
/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2013 Oracle and/or its affiliates. All rights reserved.
 *
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common Development
 * and Distribution License("CDDL") (collectively, the "License"). You
 * may not use this file except in compliance with the License. You can
 * obtain a copy of the License at
 * https://glassfish.dev.java.net/public/CDDL+GPL_1_1.html
 * or packager/legal/LICENSE.txt.  See the License for the specific
 * language governing permissions and limitations under the License.
 *
 * When distributing the software, include this License Header Notice in each
 * file and include the License file at packager/legal/LICENSE.txt.
 *
 * GPL Classpath Exception:
 * Oracle designates this particular file as subject to the "Classpath"
 * exception as provided by Oracle in the GPL Version 2 section of the License
 * file that accompanied this code.
 *
 * Modifications:
 * If applicable, add the following below the License Header, with the fields
 * enclosed by brackets [] replaced by your own identifying information:
 * "Portions Copyright [year] [name of copyright owner]"
 *
 * Contributor(s):
 * If you wish your version of this file to be governed by only the CDDL or
 * only the GPL Version 2, indicate your decision by adding "[Contributor]
 * elects to include this software in this distribution under the [CDDL or GPL
 * Version 2] license."  If you don't indicate a single choice of license, a
 * recipient has the option to distribute your version of this file under
 * either the CDDL, the GPL Version 2 or to extend the choice of license to
 * its licensees as provided above.  However, if you add GPL Version 2 code
 * and therefore, elected the GPL Version 2 license, then the option applies
 * only if the new code is made subject to such option by the copyright
 * holder.
 */ 
import java.util.HashMap;

import Controllers.ControlledScreen;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import logic.Globals;

/**
 *
 * @author Angie & Group-12
 * Modified by Group 12 to fit Project Needs
 */
public class ScreensController  extends StackPane {
    //Holds the screens to be displayed
	private String currentScreen =null;
	
    private HashMap<String, Node> screens = new HashMap<>();
    /**
     * This will hold the hashmap of controlleres by ID to be able to call functions from other controllers and pass informations between screens if needed.
     */
    private HashMap<String, ControlledScreen> controllers = new HashMap<>();
    
    public ScreensController() {
        super();
        Globals.mainContainer=this;
        ProgressIndicator bar = new ProgressIndicator(-1);
        bar.setPrefSize(200, 24);

    }

    //Add the screen to the collection
    public void addScreen(String name, Node screen) {
        screens.put(name, screen);
    }

    //Returns the Node with the appropriate name
    public Node getScreen(String name) {
        return screens.get(name);
    }
    
    public ControlledScreen getController(String name) {
    	return controllers.get(name);
    }

    //Loads the fxml file, add the screen to the screens collection and
    //finally injects the screenPane to the controller.
    public boolean loadScreen(String name, String resource) {
        try {
            FXMLLoader myLoader = new FXMLLoader(getClass().getResource(resource));
            Parent loadScreen = (Parent) myLoader.load();
            loadScreen.setId("pane");
            loadScreen.getStylesheets().add(getClass().getResource("/resources/styleSheet.css").toExternalForm());
            ControlledScreen myScreenControler = ((ControlledScreen) myLoader.getController());
            addScreen(name, loadScreen);
            addController(name, myScreenControler);
            return true;
        } catch (Exception e) {
        	e.printStackTrace();
            System.out.println(e.getMessage());
            System.exit(1);
            return false;
        }
    }

    private void addController(String name, ControlledScreen myScreenControler) {
    	controllers.put(name, myScreenControler);
	}

	//This method tries to displayed the screen with a predefined name.
    //First it makes sure the screen has been already loaded.  Then if there is more than
    //one screen the new screen is been added second, and then the current screen is removed.
    // If there isn't any screen being displayed, the new screen is just added to the root.
    public boolean setScreen(final String name) {       
    	if (screens.get(name) != null) {   //screen loaded
            final DoubleProperty opacity = opacityProperty();
            if (!getChildren().isEmpty()) {    //if there is more than one screen
            	showProgressIndicator();
                controllers.get(name).runOnScreenChange();
                hideProgressIndicator();
                Timeline fade = new Timeline(
                        new KeyFrame(Duration.ZERO, new KeyValue(opacity, 1.0)),
                        new KeyFrame(new Duration(400), new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent t) {
                        getChildren().remove(0);                    //remove the displayed screen
                        getChildren().add(0, screens.get(name));     //add the screen
                        if(screens.get(name) instanceof javafx.scene.layout.Region) {
                        	double width = ((javafx.scene.layout.Region)screens.get(name)).getPrefWidth();
                        	double height = ((javafx.scene.layout.Region)screens.get(name)).getPrefHeight();
                        	Globals.primaryStage.setHeight(height+30);
                    		Globals.primaryStage.setWidth(width+20);
                        }
                        Timeline fadeIn = new Timeline(
                                new KeyFrame(Duration.ZERO, new KeyValue(opacity, 0.0)),
                                new KeyFrame(new Duration(400), new KeyValue(opacity, 1.0)));
                        fadeIn.play();
                    }
                }, new KeyValue(opacity, 0.0)));
                fade.play();
            } else {
            	controllers.get(name).runOnScreenChange();
                setOpacity(0.0);
                getChildren().add(screens.get(name));       //no one else been displayed, then just show
                Timeline fadeIn = new Timeline(
                        new KeyFrame(Duration.ZERO, new KeyValue(opacity, 0.0)),
                        new KeyFrame(new Duration(600), new KeyValue(opacity, 1.0)));
                fadeIn.play();
            }
            currentScreen=name;
            Globals.primaryStage.setTitle(name);
            return true;
        } else {
            System.out.println("screen hasn't been loaded!!! \n");
            return false;
        }
    }
    

	//This method will remove the screen with the given name from the collection of screens
    public boolean unloadScreen(String name) {
        if (screens.remove(name) == null) {
            System.out.println("Screen didn't exist");
            return false;
        } else {
            return true;
        }
    }

	public String getCurrentScreen() {
		return currentScreen;
	}

	public void showProgressIndicator() {
		final DoubleProperty opacity = opacityProperty();
        Timeline fade = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(opacity, 1.0)),
                new KeyFrame(new Duration(250), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                getChildren().remove(0);                    //remove the displayed screen
                getChildren().add(0, screens.get(Globals.ProgressIndicatorID));     //add the screen
                controllers.get(Globals.ProgressIndicatorID).runOnScreenChange();
                Timeline fadeIn = new Timeline(
                        new KeyFrame(Duration.ZERO, new KeyValue(opacity, 0.0)),
                        new KeyFrame(new Duration(250), new KeyValue(opacity, 1.0)));
                fadeIn.play();
            }
        }, new KeyValue(opacity, 0.0)));
        fade.play();
	}
    
	public void hideProgressIndicator() {
		final DoubleProperty opacity = opacityProperty();
        Timeline fade = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(opacity, 1.0)),
                new KeyFrame(new Duration(400), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                getChildren().remove(0);                    //remove the displayed screen
                getChildren().add(0, screens.get(currentScreen));     //add the screen
                Timeline fadeIn = new Timeline(
                        new KeyFrame(Duration.ZERO, new KeyValue(opacity, 0.0)),
                        new KeyFrame(new Duration(400), new KeyValue(opacity, 1.0)));
                fadeIn.play();
            }
        }, new KeyValue(opacity, 0.0)));
        fade.play();
	}
	
}