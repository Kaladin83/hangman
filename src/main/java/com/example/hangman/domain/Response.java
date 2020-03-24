package com.example.hangman.domain;

public class Response {
	private String token;
	private String hangman;
	private boolean correct;
	private int failedAttempts;
    private boolean gameEnded;
    
    
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getHangman() {
		return hangman;
	}
	public void setHangman(String hangman) {
		this.hangman = hangman;
	}
	public boolean isCorrect() {
		return correct;
	}
	public void setCorrect(boolean correct) {
		this.correct = correct;
	}
	public int getFailedAttempts() {
		return failedAttempts;
	}
	public void setFailedAttempts(int failedAttempts) {
		this.failedAttempts = failedAttempts;
	}
	public boolean isGameEnded() {
		return gameEnded;
	}
	public void setGameEnded(boolean gameEnded) {
		this.gameEnded = gameEnded;
	}
	@Override
	public String toString() {
		return "Response [token=" + token + ", hangman=" + hangman + ", correct=" + correct + ", failedAttempts="
				+ failedAttempts + ", gameEnded=" + gameEnded + "]";
	}
}
