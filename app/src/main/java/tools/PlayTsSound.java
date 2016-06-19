package tools;

import android.annotation.SuppressLint;
import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

import java.util.HashMap;

import old.service.TsSoundService;


public class PlayTsSound {
	private SoundPool sp;
	private HashMap<Integer, Integer> spMap;

	@SuppressLint("UseSparseArrays")
	public PlayTsSound(Context context, int id) {
		sp = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
		spMap = new HashMap<Integer, Integer>();
		spMap.put(1, sp.load(context, id, 1));
		playSounds(1, 0, context);
	}

	public void playSounds(int sound, int number, Context context) {
		AudioManager am = (AudioManager) context
				.getSystemService(TsSoundService.AUDIO_SERVICE);
		float audioMaxVolumn = am.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
		float audioCurrentVolumn = am
				.getStreamVolume(AudioManager.STREAM_MUSIC);
		float volumnRatio = audioCurrentVolumn / audioMaxVolumn;
		sp.play(spMap.get(sound), volumnRatio, volumnRatio, 1, number, 1);
	}

}
