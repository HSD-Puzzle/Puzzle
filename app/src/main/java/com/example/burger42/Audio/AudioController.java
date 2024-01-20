package com.example.burger42.Audio;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.MediaPlayer;

import com.example.burger42.R;

/**
 * controlls the audio
 */
public class AudioController {

    /**
     * name for the preference
     */
    private static final String PREFERENCE_NAME = "audioPref";
    /**
     * key for the progress for the audio
     */
    private static final String AUDIO_PROGRESS_KEY = "audioProgress";
    /**
     * variable for the shared preferences
     */
    private SharedPreferences preferences;
    /**
     * variable for the volume
     */
    private int volume;
    /**
     * id for the music
     */
    private int musicId;
    /**
     * variable for the media player
     */
    MediaPlayer mediaPlayer;
    /**
     * context of the activity
     */
    Context context;
    /**
     * boolean for checking if music is playing
     */
    boolean musicIsPlaying;
    /**
     * instance of the audio controller
     */
    private static AudioController INSTANCE;



    /**
     * Constructs an AudioController with the provided Android application context.
     *
     * This controller manages audio-related functionalities such as preferences, media player initialization,
     * and volume adjustment based on stored preferences.
     *
     * @param context The Android application context to be used for accessing preferences and creating the MediaPlayer.
     */
    public AudioController(Context context, int soundId){
        this.context = context;
        playThisTrack(soundId);
        preferences = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        volume = preferences.getInt(AUDIO_PROGRESS_KEY, 0);
        leftAndRightVolume(volume);
    }

    /**
     * Retrieves the singleton instance of the AudioController, creating it if it doesn't exist.
     *
     * This method follows the singleton pattern, ensuring that only one instance of the AudioController
     * is created and reused throughout the application's lifecycle.
     *
     * @param context The Android application context to be used for initializing the AudioController.
     * @return The singleton instance of the AudioController.
     */
    public static AudioController getInstance(Context context, int soundId){
        if (INSTANCE == null){
            INSTANCE = new AudioController(context, soundId);
        }else {
            INSTANCE.playThisTrack(soundId);
        }
        INSTANCE.musicId = soundId;
        System.out.println("SoundId: "+ soundId);
        return INSTANCE;
    }

    /**
     * Changes the Music to another track
     * @param musicId to switch to another track
     */
    public void playThisTrack(int musicId){
        if (mediaPlayer != null && this.musicId != musicId) {
            mediaPlayer.stop();
        }
        if (mediaPlayer == null) {
            switch (musicId){
                case 0:
                    mediaPlayer = MediaPlayer.create(context, R.raw.morningfunnybeat);
                    break;
                case 1:
                    mediaPlayer = MediaPlayer.create(context, R.raw.restaurantsound);
                    break;
                default:
                    mediaPlayer = MediaPlayer.create(context, R.raw.morningfunnybeat);
                    break;
            }
        }
    }

    /**
     * Checks if music is currently playing.
     *
     * @return {@code true} if music is playing, {@code false} otherwise.
     */
    public boolean musicIsPlaying(){
        return musicIsPlaying;
    }

    /**
     * Method to start the Music
     */
    public void startMusic(){
        mediaPlayer.start();
        musicIsPlaying = true;
        mediaPlayer.setLooping(true);
    }

    /**
     * Method to stop the Music if its playing. Releases die Mediaplayer and sets
     * the boolean musicIsPlaying to false
     */
    public void stopMusic() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            musicIsPlaying = false;
            mediaPlayer = null;
        }
    }

    /**
     * pauses the music
     */
    public void onPause() {
        mediaPlayer.pause();
    }
    /**
     * Adjusts the left and right volume of the MediaPlayer and updates the stored preferences.
     *
     * @param volume The volume level as a percentage (0 to 100), where 0 is muted and 100 is maximum volume.
     */
    public void leftAndRightVolume(int volume){
        this.volume = volume;
        mediaPlayer.setVolume(volume/100f,volume/100f);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(AUDIO_PROGRESS_KEY, volume);
        editor.apply();
    }

    /**
     * Method to return the volume
     *
     * @return volume The volume level as a percentage (0 to 100), where 0 is muted and 100 is maximum volume.
     */
    public int volume(){
        return volume;
    }

}
