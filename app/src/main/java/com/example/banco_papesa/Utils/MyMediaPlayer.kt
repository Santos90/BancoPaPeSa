package com.example.banco_papesa.Utils

import android.content.Context
import android.media.MediaPlayer

class MyMediaPlayer private constructor(var context: Context, var songResId : Int) {

	private var mediaPlayer: MediaPlayer? = null
	private var musicFilePath: String? = null
	private var playbackPosition = 0


	init {
		mediaPlayer = MediaPlayer.create(context, songResId)
	}

	companion object {
		private var instance: MyMediaPlayer? = null

		fun getInstance(context: Context, songResId: Int): MyMediaPlayer {
			if (instance == null) {
				instance = MyMediaPlayer(context, songResId)
			}
			return instance!!
		}
	}
	fun setMusicFilePath(musicFilePath: String) {

		this.musicFilePath = musicFilePath
	}

	fun playPause() {
		if (mediaPlayer!!.isPlaying) {
			pauseMusic()
		} else {
			playMusic()
		}
	}

	// Método para reproducir música
	fun playMusic() {

		try {

			mediaPlayer?.prepare()
			mediaPlayer?.start()
			mediaPlayer?.seekTo(playbackPosition)
		} catch (e: Exception) {
			e.printStackTrace()
		}
	}

	// Método para pausar la reproducción
	fun pauseMusic() {
		mediaPlayer?.let {
			if (it.isPlaying) {
				it.pause()
				playbackPosition = mediaPlayer!!.currentPosition
			}
		}
	}

	// Método para reiniciar la reproducción
	fun restartMusic() {
		mediaPlayer?.let {
			it.seekTo(0)
			it.start()
		}
	}
}

