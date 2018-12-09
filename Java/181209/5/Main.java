package com.tutorial;

public class Main {
	/**
	 * Hello Everybody!
	 * <code>category</code> in the learning.
	 * @param args this is for the test
	 * @author lee3jjang@naver.com
	 */
	public static void main(String[] args) {
		MediaPlayer player = new MP3();
		player.play("file.mp3");
		
		player = new FormatAdapter(new MP4());
		player.play("file.mp4");
		
		player = new FormatAdapter(new MKV());
		player.play("file.mkv");
		
	}

}
