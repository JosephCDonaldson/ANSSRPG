package disconsented.anssrpg.player;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

import scala.util.parsing.input.StreamReader;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import disconsented.anssrpg.data.DataSave;
import net.minecraft.world.WorldSavedData;

public class PlayerFile {
	static String PERK_LIST = "Perk List";
	static String SKILL_LIST = "Skill List";
	static File dataFolder = new File("data");
	
	
	public static void loadPlayer(String playerID) {
		File dataLocation = new File(dataFolder, playerID);
		try{
			FileReader reader = new FileReader(dataLocation);
            Gson gson = new GsonBuilder().create();
            PlayerData p = gson.fromJson(reader, PlayerData.class);
            System.out.println(p);
            }
		catch(FileNotFoundException e){
			DataSave.createPlayer(playerID);
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	/**
	 * Write all of the player data to a file
	 * 
	 */

	public static void writePlayer(PlayerData player){
		File dataLocation = new File(dataFolder, player.playerID);
		dataFolder.mkdirs();
		Gson gson = new Gson();  
		String json = gson.toJson(player);
		try {
			//write converted json data to a file named "file.json"
			FileWriter writer = new FileWriter(dataLocation);
			writer.write(json);
			writer.close();
	 
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}