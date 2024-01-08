package com.example.banco_papesa.Utils


import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.Binder
import android.os.IBinder
import com.example.banco_papesa.R


class MusicService : Service() {
	private var mediaPlayer: MediaPlayer? = null
	private val binder: IBinder = LocalBinder()
	private var playbackPosition = 0

	inner class LocalBinder : Binder() {
		val service: MusicService
			get() = this@MusicService
	}

	override fun onCreate() {
		super.onCreate()
		mediaPlayer = MediaPlayer.create(this, R.raw.bossa_nova)
		mediaPlayer!!.isLooping = true
	}

	fun playPause() {
		if (mediaPlayer!!.isPlaying) {
			pauseMusic()
		} else {
			playMusic()
		}
	}

	private fun playMusic() {
		if (!mediaPlayer!!.isPlaying) {
			mediaPlayer!!.start()
		}
	}

	private fun pauseMusic() {
		if (mediaPlayer!!.isPlaying) {
			mediaPlayer!!.pause()
			playbackPosition = mediaPlayer!!.currentPosition;
		}
	}

	fun restartMusic() {
		mediaPlayer!!.seekTo(0)
		playMusic()
	}

	fun setLooping(looping: Boolean) {
		mediaPlayer!!.isLooping = looping
	}

	override fun onBind(intent: Intent): IBinder? {
		return binder
	}

	override fun onDestroy() {
		super.onDestroy()
		if (mediaPlayer != null) {
			mediaPlayer!!.release()
		}
	}
}
