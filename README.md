# Rock-Paper-Scissors
Rock Paper Scissors made in LibGdx

# P.S 
the desktop launcher wouldn't transfer to git, so I'll just paste the class here:

package com.mygdx.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;

import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import com.mygdx.game.RPS;

public class DesktopLauncher {

  // runs the game and sets some default settings such as the size of the window and not making it resizable
  
	public static void main (String[] arg) {
  
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
    
		config.title = "RockPaperScissors";
    
		config.resizable = false;
    
		config.width = 800;
    
		config.height = 800;
    
		new LwjglApplication(new RPS(), config);
    
	}
  
}
