package com.example.burger42.Audio;

import android.content.Context;
import android.content.SharedPreferences;

public class AudioValueText {

    /**
     * context of the activity
     */
    Context context;
    /**
     *
     */
    private static final String PREFERENCE_NAME = "audioPref";
    /**
     *
     */
    private static final String AUDIO_PROGRESS_KEY = "audioValueProgress";
    /**
     * variable for
     */
    private SharedPreferences preferences;
    /**
     * variable for the volume
     */
    private int audioValue;

    /**
     *
     * @param context
     */
    public AudioValueText(Context context){
        this.context = context;
        preferences = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        audioValue = preferences.getInt(AUDIO_PROGRESS_KEY, 0);
    }

    /**
     *
     * @param value
     */
    public void saveData(int value){
        this.audioValue = value;
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(AUDIO_PROGRESS_KEY, audioValue);
        editor.apply();
    }

    /**
     *
     * @return
     */
    public int getAudioValue(){
        return audioValue;
    }

}
