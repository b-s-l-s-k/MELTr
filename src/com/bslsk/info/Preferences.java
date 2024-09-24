package com.bslsk.info;

import java.awt.Color;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Preferences 
{
	public static Color bgColor;
	public static Color fontColor;
	
	public static Color headerColor;
	public static Color headerFontColor;
	
	//CONSTRAINTS ----- LIMITS TO VARIABLES SET VIA A FILE
	//DEFAULT
	public static void initPrefs()
	{
		bgColor = Color.gray;
		fontColor = Color.black;
		headerColor = Color.darkGray;
		headerFontColor = Color.white;
	}
	//DEFAULT
	public static void initPrefs(String pFile)
	{
		
	}
	public static void saveState() 
	{
		int x = 0;
		String f0 = "prefs";
		String f1 = ".mpf";
		File file = new File(f0+x+f1);
		try {
			while(!file.createNewFile())
			{
				x++;
				file = new File(f0+x+f1);
			}
			System.out.println("Saves to: " + file.getAbsolutePath());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.err.println("Could not create file: prefs");
		}
		finally {
			try {
			      FileWriter myWriter = new FileWriter(file);
			      //myWriter.write("Files in Java might be tricky, but it is fun enough!");
			      //Assets
			      //Keys
			      //Contraints
			      String total = Assets.export();
			      myWriter.write(total);
			      myWriter.close();
			      System.out.println("Successfully wrote to the file.");
			    } catch (IOException e) {
			      System.out.println("An error occurred.");
			      e.printStackTrace();
			    }
		}
		
	}
}
