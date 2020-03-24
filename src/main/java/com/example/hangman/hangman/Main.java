package com.example.hangman.hangman;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import com.example.hangman.domain.Response;

public class Main {

	
	public static void main(String[] args) {
		Repository repo = new Repository();
		loadWords(repo);
		Game g = new Game();
		//Starting the game - getting the empty word - we know only the size
		Response response = g.startGame();
		
		//starting the game 
		if (response != null) {
			g.keepPlaying(repo, response);	
		}
	}

	private static void loadWords(Repository repo) {
		File file = new File("C:\\Users\\maratbe\\OneDrive - AMDOCS\\Backup Folders\\Desktop\\dictionary.txt");

		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader(file));
			String st;
			try {
				
				while ((st = br.readLine()) != null) {
					repo.analizeWord(st);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

}
