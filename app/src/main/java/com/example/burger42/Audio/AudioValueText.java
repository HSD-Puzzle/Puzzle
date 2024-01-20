package com.example.burger42.Audio;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * value to set for the text
 */
public class AudioValueText {

    /**
     * context of the activity
     */
    Context context;
    /**
     * name for the preference
     */
    private static final String PREFERENCE_NAME = "audioPref";
    /**
     * key for the progress of the audio
     */
    private static final String AUDIO_PROGRESS_KEY = "audioValueProgress";
    /**
     * variable for the preferences
     */
    private SharedPreferences preferences;
    /**
     * variable for the volume
     */
    private int audioValue;

    /**
     * constructor for the text
     * load the shared preferences
     * @param context
     */
    public AudioValueText(Context context){
        this.context = context;
        preferences = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        audioValue = preferences.getInt(AUDIO_PROGRESS_KEY, 0);
    }

    /**
     * saves the data
     * @param value
     */
    public void saveData(int value){
        this.audioValue = value;
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(AUDIO_PROGRESS_KEY, audioValue);
        editor.apply();
    }

    /**
     * returns the value for the text
     * @return int value for text
     */
    public int getAudioValue(){
        return audioValue;
    }

}
