package snake.som;

import java.util.ArrayList;

import javax.sound.sampled.Clip;

public class Ma�aAudio extends RandomAudio {
	
	private static ArrayList<Clip> listaAudio = new ArrayList<Clip>();

	public Ma�aAudio(String fileName) {
		super(fileName, listaAudio);
	}
}
