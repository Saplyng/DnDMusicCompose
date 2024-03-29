package com.example.dndmusiccompose

import android.app.Activity
import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import android.media.MediaMetadataRetriever
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.*
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.ui.core.*
import androidx.ui.foundation.Clickable
import androidx.ui.foundation.DrawImage
import androidx.ui.foundation.VerticalScroller
import androidx.ui.foundation.shape.corner.RoundedCornerShape
import androidx.ui.graphics.Image
import androidx.ui.layout.*
import androidx.ui.material.*
import androidx.ui.material.ripple.Ripple
import androidx.ui.res.imageResource
import androidx.ui.res.vectorResource
import androidx.ui.tooling.preview.Preview
import com.example.dndmusiccompose.sampledata.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                TabScreen()
            }
        }
    }
}



private enum class Sections(val title: String){
    Home("Home"),
    Songs("Songs"),
    Mixer("Mixer"),
    Genres("Genres"),
    Scenarios("Scenes")
}

@Composable
fun TabScreen() {

    var section by +state{ Sections.Home}
    val sectionTitles = Sections.values().map{it.title}

    FlexColumn{
        inflexible{
            TabRow(items = sectionTitles, selectedIndex = section.ordinal) { index, text ->
                Tab(text = text, selected = section.ordinal == index){
                    section = Sections.values()[index]
                }

            }
        }
        expanded(1f){
            when(section){
                Sections.Home -> HomeTab()
                Sections.Songs -> SongsTab()
                Sections.Mixer -> MixerTab()
                Sections.Genres -> GenresTab()
                Sections.Scenarios -> ScenariosTab()
            }
        }
    }
}

@Composable
private fun HomeTab(){
    val context = +ambient(ContextAmbient)
    Column(
        ExpandedHeight,
        mainAxisAlignment = MainAxisAlignment.End,
        crossAxisAlignment = CrossAxisAlignment.Stretch
    ){
            FloatingActionButton(icon = +imageResource(R.drawable.ic_add_black_48dp),onClick = { getSongs(context = context) },elevation = 12.dp)
    }


}

@Composable
private fun SongsTab(){
    TabWithTopics(Sections.Songs.title, songs)

}

@Composable
private fun MixerTab(){
    MixerList(Sections.Songs.title, testWhatever)
}

@Composable
private fun GenresTab(){
    
}

@Composable
private fun ScenariosTab(){
TabWithTopics(Sections.Scenarios.title,
    scenes)

}


@Preview
@Composable
fun DefaultPreview() {
    MaterialTheme {
        TabScreen()
    }
}
@Composable
private fun MixerList(songname: String, playlist: List<String>){
    VerticalScroller{
        Column{
            HeightSpacer(height = 16.dp)
            playlist.forEach{song ->
                MixerItem(
                    getTopicKey(songname,
                        "- ",
                        song
                    ), song)
            }
        }
    }
}

@Composable
private fun MixerItem(topicKey: String, itemTitle: String){
    val image = +imageResource(android.R.drawable.ic_media_play)
    val context = +ambient(ContextAmbient)
//    val image = MediaMetadataRetriever().primaryImage
    Padding(left = 16.dp, right = 16.dp) {
        FlexRow(
            crossAxisAlignment = CrossAxisAlignment.Center
        ){
            inflexible{
                Ripple(bounded = false){
                    Clickable(onClick = {
                        MediaPlayer.create(context, R.raw.cotton_club)?.start()
                    }) {
                        Container(width = 56.dp, height = 56.dp) {
                            Clip(
                                RoundedCornerShape(4.dp)
                            ) {

                                DrawImage(image)


                            }
                        }
                    }
                }

            }
            expanded(1f){
                Text(
                    text = itemTitle,
                    modifier = Spacing(16.dp),
                    style = +themeTextStyle { subtitle1 }
                )
            }
            inflexible{

            }
        }
    }
}

@Composable
private fun TabWithTopics(tabname: String, topics: List<String>){
    VerticalScroller{
        Column{
            HeightSpacer(16.dp)
            topics.forEach{ topic ->
                TopicItem(
                    getTopicKey(
                        tabname,
                        "- ",
                        topic
                    ), topic
                )
                TopicDivider()
            }
        }
    }
}

@Composable
private fun TabWithSections(
    tabname: String,
    sections: Map<String, List<String>>
){
    VerticalScroller{
        Column{
            sections.forEach { (section, topics) ->
                Text(
                    text = section,
                    modifier = Spacing(16.dp),
                    style = +themeTextStyle { subtitle1 }
                )
                topics.forEach{ topic ->
                    TopicItem(
                        getTopicKey(
                            tabname,
                            section,
                            topic
                        ), topic
                    )
                    TopicDivider()
                }
            }
        }
    }
}

@Composable
private fun TopicItem(topicKey: String, itemTitle: String){
    val image = +imageResource(R.drawable.placeholder_1_1)
//    val image = MediaMetadataRetriever().primaryImage
    Padding(left = 16.dp, right = 16.dp) {
        FlexRow(
            crossAxisAlignment = CrossAxisAlignment.Center
        ){
            inflexible{
                Container(width = 56.dp, height = 56.dp){
                    Clip(
                        RoundedCornerShape(4.dp)){
                        DrawImage(image)
                    }
                }
            }
            expanded(1f){
                Text(
                    text = itemTitle,
                    modifier = Spacing(16.dp),
                    style = +themeTextStyle { subtitle1 }
                )
            }
            inflexible{
                val selected = isTopicSelected(topicKey)
                SelectTopicButton(
                    onSelected = {
                        selectedTopic(topicKey, !selected)
                    },
                    selected = selected,
                    itemTitle = itemTitle
                )
            }
        }
    }
}

@Preview
@Composable
private fun TopicDivider(){
    Opacity(opacity = 0.08f) {
        Divider(Spacing(top = 8.dp, bottom = 8.dp, left = 72.dp))
    }
}

private fun getTopicKey(tab:String, group:String, topic:String)= "$tab=$group-$topic"

private fun isTopicSelected(key: String) = AppStatus.selectedTopics.contains(key)

private fun selectedTopic(key: String, select: Boolean){
    if (select){
        AppStatus.selectedTopics.add(key)
    } else {
        AppStatus.selectedTopics.remove(key)
    }
}

