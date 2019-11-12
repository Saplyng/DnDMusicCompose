package com.example.dndmusiccompose

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.Composable
import androidx.compose.state
import androidx.compose.unaryPlus
import androidx.ui.core.*
import androidx.ui.foundation.DrawImage
import androidx.ui.foundation.VerticalScroller
import androidx.ui.foundation.shape.corner.RoundedCornerShape
import androidx.ui.layout.*
import androidx.ui.material.*
import androidx.ui.res.imageResource
import androidx.ui.tooling.preview.Preview
import com.example.dndmusiccompose.sampledata.scenes
import com.example.dndmusiccompose.sampledata.songs

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
//            TopAppBar(
//                    title = { Text(text = "Interests")},
//                    navigationIcon = {
//                        VectorImageButton(R.drawable.ic_launcher_foreground){
//                            openDrawer()
//                        }
//                    }
//            )
        }
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

}

@Composable
private fun SongsTab(){
    TabWithTopics(
        Sections.Songs.title,
        songs
    )
}

@Composable
private fun MixerTab(){

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
                    selected = selected
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