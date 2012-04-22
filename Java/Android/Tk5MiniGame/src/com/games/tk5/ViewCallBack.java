package com.games.tk5;

public interface ViewCallBack {
	public void changeWelcome();
	public void changeIndex(boolean lastView);
	public void changeGames(ViewBase view);
	public void onEndVideo();
	public void endGames();
}
