package com.example.hangman.hangman;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.example.hangman.domain.LetterStatistics;
import com.example.hangman.domain.Response;
import com.example.hangman.domain.Word;

public class Game {
	CallServer callServer = new CallServer();
	private final String START_NEW_GAME = "startNewGame";
	private final String GUESS = "guess?";
	private Map<Character, List<Integer>> result = new HashMap<Character, List<Integer>>();
	private String exceptCharacters = "";
	
	public Response startGame() {
		
		return callServer.call(START_NEW_GAME);
	}
	
	public void keepPlaying(Repository repo, Response response) {
		int wordSize = response.getHangman().length();
		Map<Integer, List<Word>> words = repo.getWords();
		// filtering all the words that does not match the size of word we want to guess
		List<Word> listOfWords = words.get(wordSize);
		
		//looking for the most common character
		String mostCommonChar = findMostCommonChar(listOfWords);
		
		//calling the guess for the first time
		response = callServer.call(GUESS+"token="+response.getToken()+"&guess="+mostCommonChar);
		if (response != null) {
			//looping until we get response with ending indicator
			while (!response.isGameEnded()) {
				mostCommonChar = findMostCommonChar(listOfWords, response.getHangman(), response.isCorrect()? "": mostCommonChar);
				response = callServer.call(GUESS+"token="+response.getToken()+"&guess="+mostCommonChar);
			}
		}
		
	}

	private String findMostCommonChar(List<Word> listOfWords, String hangman, String prevChar) {
		// if character found putting all the characters in a list else 
		// putting the wrong character in other list
		if (prevChar.equals("")) {
			for(int i = 0; i < hangman.charAt(i); i++) {
				if (hangman.charAt(i) != ' ') {
					List<Integer> res =  result.get(hangman.charAt(i));
					if(res == null) {
						res = new ArrayList<Integer>();
					}
					res.add(i);
				}
			}	
		}
		else {
			exceptCharacters = prevChar;
		}
		//filtering words with new results
		filterWords(listOfWords);
		
		//if we have 1 word  - sending it to the server
		if (listOfWords.size() == 1) {
			return listOfWords.get(0).getText();
		}
		//If we have more than 1 word in list of words looking for the most common character again 
		return findMostCommonChar(listOfWords);
		
	}

	private void filterWords(List<Word> listOfWords) {
		for (Word word: listOfWords) {
			for (Map.Entry<Character , LetterStatistics> item : word.getWordStatistics().entrySet()) {
				//filter all words that which chars not matching the result's position or have an except character  
				if(!filterCorrectResults(item) || item.getKey() == exceptCharacters.charAt(0)) {
					listOfWords.remove(word);
				}
			}
		}	
	}

	//finding the most common char amongst all words
	private String findMostCommonChar(List<Word> listOfWords) {
		
		Map<Character , Integer> mapOfCounts = new HashMap<Character , Integer>();
		for (Word word: listOfWords) {
			for (Map.Entry<Character , LetterStatistics> item : word.getWordStatistics().entrySet()) {
				mapOfCounts.put(item.getKey(), mapOfCounts.getOrDefault(item.getKey(), 0)+item.getValue().getCount());	
			}
		}
		
		char maxChar = ' ';
		int maxCount = 0;
		for (Map.Entry<Character , Integer> item : mapOfCounts.entrySet()) {
			if (item.getValue() > maxCount) {
				maxCount = item.getValue();
				maxChar = item.getKey();
			}
		}
		return String.valueOf(maxChar);
	}

	// filtering words that does not have chars in the correct place or does not have them at all 
	private boolean filterCorrectResults(Entry<Character, LetterStatistics> item) {
		for(Map.Entry<Character , List<Integer>> c : result.entrySet()) {
			if (item.getKey() == c.getKey() && positionsMatch(item.getValue().getPositions(), c.getValue())) {
				return true;
			}
		}
		return false;
	}
	
    // Checking if the list of positions in result matches to the list of position 
	// of each word in list with same char.
	private boolean positionsMatch(List<Integer> positions, List<Integer> correctPositions) {
		if (positions.size() == correctPositions.size()) {
			for(int i = 0; i< correctPositions.size(); i++) {
				if (positions.get(i) != correctPositions.get(i)) {
					return false;
				}
			}
			return true;
		}
		return false;
	}
}
