package com.bignerdranch.android.beatbox

import android.content.res.AssetManager
import android.media.SoundPool
import android.util.Log
import java.io.IOException
import java.lang.Exception

private const val TAG = "BeatBox"
private const val MAX_SOUNDS = 5

private const val SOUND_FOLDER = "sample_sounds"

class BeatBox(private val assetManager: AssetManager) {

    val sounds: List<Sound>

    private val soundPool =
        SoundPool.Builder()
            .setMaxStreams(MAX_SOUNDS)
            .build()

    //=====

    init {
        sounds = loadSounds()
    }

    //=====

    fun play(sound: Sound) {
        sound.soundId?.let { soundId ->
            soundPool.play(
                soundId,
                1.0f, 1.0f,
                1,
                0,
                1.0f
            )
        }
    }

    fun release() {
        soundPool.release()
    }

    //=====

    private fun loadSounds(): List<Sound>{
        val soundName: Array<String>

        try {
            soundName = assetManager.list(SOUND_FOLDER)!!
        } catch(e: Exception) {
            Log.e(TAG, "couldn't load songs from $SOUND_FOLDER", e)
            return emptyList()
        }

        val sounds = mutableListOf<Sound>()

        soundName.forEach { filename ->
            val assetPath = "$SOUND_FOLDER/$filename"
            val sound = Sound(assetPath)

            try {
                loadSound(sound)
                sounds.add(sound)
            } catch(ioe: IOException) {
                Log.e(TAG, "Could not load sound ${sound.name}", ioe)
            }
        }

        return sounds
    }

    private fun loadSound(sound: Sound) {
        val soundFileDescriptor = assetManager.openFd(sound.assetPath)
        val soundId = soundPool.load(soundFileDescriptor, 1)

        sound.soundId = soundId
    }
}