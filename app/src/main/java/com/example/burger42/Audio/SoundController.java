package com.example.burger42.Audio;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;

import com.example.burger42.R;

public class SoundController {

    /**
     *
     */
    private SoundPool soundPool;
    /**
     *
     */
    private Context context;
    /**
     *
     */
    private int soundId_0;
    /**
     *
     */
    private int soundId_1;

    /**
     * Constructor for SoundController.
     *
     * @param context The application context.
     */
    public SoundController(Context context) {
        this.context = context;

        // Check if the version is supported
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_GAME)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .build();

            soundPool = new SoundPool.Builder()
                    .setMaxStreams(1)
                    .setAudioAttributes(audioAttributes)
                    .build();
        } else {
            soundPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
        }
       soundId_0 = soundPool.load(context,R.raw.tick,1);
        soundId_1 = soundPool.load(context,R.raw.ping,1);
    }

    /**
     * Play the loaded sound with id 0.
     */
    public void playSound_0() {
        soundPool.play(soundId_0, 1.0f, 1, 1, 0, 1.0f);
    }

    /**
     * Play the loaded sound with id 1
     */
    public void playSound_1() {
        soundPool.play(soundId_1, 1.0f, 1, 1, 0, 1.0f);
    }

    /**
     * Release resources associated with the SoundPool.
     */
    public void releaseSound() {
        soundPool.release();
    }
}
