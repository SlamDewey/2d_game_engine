package sound;

import java.io.IOException;
import java.io.InputStream;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Sound {

	// volume starts at 100%, it can shift between -80.0db -> +6.0db
	// Basically, -80 is mute and 0 is max.
	// the volume float represents the volume offset, set it to a value between
	// the specified range
	// using the 'setVolume(float f)' static method.
	public static float volume = -30.0f;

	String filepath;
	Clip clip;
	long currentTimeFrame = 0;
	AudioInputStream ais;
	FloatControl gainControl;

	public Sound(String filepath) {
		filepath = "/res/sounds/" + filepath;
		this.filepath = filepath;
		InputStream is = Class.class.getResourceAsStream(filepath);
		AudioInputStream audioIn;
		try {
			audioIn = AudioSystem.getAudioInputStream(is);
			clip = AudioSystem.getClip();
			clip.open(audioIn);
			gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		}
	}

	public void play() {
		gainControl.setValue(volume);
		Thread play = new Thread("Sound + " + filepath) {
			@Override
			public void run() {
				if (clip.isRunning())
					clip.stop();
				clip.setFramePosition(0);
				clip.start();
			}
		};
		play.start();
	}

	public void playFromStart() {
		reset();
		play();
	}

	public void pause() {
		if (!clip.isRunning())
			return;
		currentTimeFrame = clip.getMicrosecondLength();
		clip.stop();
	}

	public void resume() {
		gainControl.setValue(volume);
		if (clip.isRunning())
			return;
		if (currentTimeFrame == 0L)
			play();
		else
			clip.setMicrosecondPosition(currentTimeFrame);
		clip.start();
	}

	public void reset() {
		clip.stop();
	}

	public static void setVolume(float vol) {
		volume = vol;
	}
}
