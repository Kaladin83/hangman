package com.example.hangman.domain;

import java.util.HashMap;
import java.util.Map;

public class Word {
	private String text;
	private int length;
	private Map<Character, LetterStatistics> wordStatistics = new HashMap<Character, LetterStatistics>();
	
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public int getLength() {
		return length;
	}
	public void setLength(int length) {
		this.length = length;
	}
	public Map<Character, LetterStatistics> getWordStatistics() {
		return wordStatistics;
	}
	public void setWordStatistics(Map<Character, LetterStatistics> wordStatistics) {
		this.wordStatistics = wordStatistics;
	}
	
}
