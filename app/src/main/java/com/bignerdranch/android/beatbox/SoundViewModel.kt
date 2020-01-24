package com.bignerdranch.android.beatbox

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable

class SoundViewModel: BaseObservable() {

    var sound: Sound? = null
        set(sound) {
            field = sound
        }

    @get:Bindable
    val title: String?
        get() = sound?.name
}