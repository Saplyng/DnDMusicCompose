package com.example.dndmusiccompose.sampledata

import android.content.Context
import android.media.MediaMetadataRetriever
import android.net.Uri
import androidx.ui.input.KeyboardType
import com.example.dndmusiccompose.R
//import sun.invoke.util.VerifyAccess.getPackageName


val songs = listOf(
    "Arcade Overture",
    "Ascender",
    "Aurora Cove",
    "Avarice",
    "The Ballad o' Lil Lemon",
    "Balrog",
    "Boss Battle",
    "Boxcutter",
    "Bruno",
    "Call of the Road",
    "Cargo",
    "Cassini",
    "Castle View",
    "Clubs and Catacombs",
    "Coming Home"
)

val scenes = listOf(
    "WaterDeep Docks",
    "Castle in the Sky",
    "Cerberus Deck",
    "Town on Fire",
    "Generic Fight",
    "Call of the Wild",
    "Adorable Kitty",
    "Winter Wonderland",
    "Meow"
).sorted()

val testWhatever = mutableListOf<String>(
    "Cotton Club"
)
val test_songs: MutableList<String> = mutableListOf<String>()

//fun getSongTitleForFile(filePath: String?, context: Context) {
//    val mediaPath: Uri = KeyboardType.Uri.parse("android.resource://" + context!!.packageName + "/" + R.raw.cotton_club)
//    val mmr = MediaMetadataRetriever()
//    mmr.setDataSource(context, mediaPath)
//}
//var song_titles: List<String> = getSongTitleForFile(R.raw.cotton_club.toString())