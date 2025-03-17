package com.example.arlearner.ui.screens

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavController
import com.example.arlearner.utils.Utils
import com.google.ar.core.Config
import com.google.ar.core.Frame
import com.google.ar.core.Plane
import com.google.ar.core.TrackingFailureReason
import io.github.sceneview.ar.ARScene
import io.github.sceneview.ar.arcore.createAnchorOrNull
import io.github.sceneview.ar.arcore.getUpdatedPlanes
import io.github.sceneview.ar.rememberARCameraNode
import io.github.sceneview.ar.scene.destroy
import io.github.sceneview.model.ModelInstance
import io.github.sceneview.rememberCollisionSystem
import io.github.sceneview.rememberEngine
import io.github.sceneview.rememberMaterialLoader
import io.github.sceneview.rememberModelLoader
import io.github.sceneview.rememberNodes
import io.github.sceneview.rememberView

@Composable
fun QuizScreen(navController: NavController) {
    var score= remember {
        mutableStateOf(0)
    }
    val model= remember {
        mutableStateOf(Utils.RandomModelGenerator())
    }
    val engine= rememberEngine() // create a engine to manage 3d model
    val modelLoader= rememberModelLoader(engine=engine) // loads the model
    val materialLoader= rememberMaterialLoader(engine=engine) // loads the objects texture color
    val cameraNode= rememberARCameraNode(engine = engine) // associate camera with ar environment
    val childNode= rememberNodes() // keeps track of objects in ar scene
    val view= rememberView(engine = engine) // crates a view for model rendering
    val collisionSystem= rememberCollisionSystem(view=view) // keeps track of object collision like in ar games
    val planeRenderer= remember { // render a plane like that dot dot
        mutableStateOf(true)
    }
    val modelInstance= remember { // stores a list of 3d model to be places in ar scene
        mutableListOf<ModelInstance>()
    }
    val trackingFailureReason= remember { // keep track of failure reason like no detected surface , low light
        mutableStateOf<TrackingFailureReason?>(null)
    }
    val frame= remember { // keeps current camera frame
        mutableStateOf<Frame?>(null)
    }
    Box(modifier = Modifier.
        fillMaxSize()){
        ARScene(
            modifier = Modifier.fillMaxSize(),
            engine = engine,
            modelLoader = modelLoader,
            materialLoader = materialLoader,
            cameraNode = cameraNode,
            childNodes = childNode,
            view = view,
            collisionSystem = collisionSystem,
            planeRenderer = planeRenderer.value,
            onTrackingFailureChanged = {
                trackingFailureReason.value=it
            },
            onSessionUpdated = {session, updatedframe ->
                frame.value=updatedframe
                if(childNode.isEmpty()){
                    updatedframe.getUpdatedPlanes()
                        .firstOrNull(){
                            it.type==Plane.Type.HORIZONTAL_UPWARD_FACING
                        }?.let {
                            Log.d("main","creating anchor node ${model.value.second}")
                            it.createAnchorOrNull(it.centerPose)?.let {
                                childNode+=Utils.CreateAnchorNode(
                                    engine,
                                    modelInstance,
                                    modelLoader,
                                    anchor = it,
                                    model=model.value.second,
                                    materialLoader
                                )
                            }
                        }
                }
            },
            sessionConfiguration = {session, config ->
                config.depthMode=when (
                    session.isDepthModeSupported(Config.DepthMode.AUTOMATIC)
                ){
                    true-> Config.DepthMode.AUTOMATIC
                    else-> Config.DepthMode.DISABLED
                }
                config.lightEstimationMode= Config.LightEstimationMode.ENVIRONMENTAL_HDR
            }
        )

        Box(modifier = Modifier.fillMaxWidth()
            .align(Alignment.TopCenter)
        ){
            Text("Quiz Screen", textAlign = TextAlign.Center, color = Color.Black, modifier = Modifier
                .align(Alignment.Center))
            Text("Score : ${score.value}", textAlign = TextAlign.Center, color = Color.Black, modifier = Modifier
                .align(Alignment.CenterEnd))
        }

        val listOfAnswers= remember {
            mutableStateOf(
                listOf(
                Utils.alphabet.keys.random(),
                Utils.alphabet.keys.random(),
                model.value.first
            ).shuffled())
        }

        Row(modifier = Modifier.fillMaxWidth()
            .align(Alignment.BottomCenter),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.Bottom) {
            listOfAnswers.value.forEach(){
                AlphabetScreenItems(alphabet = it) {
                    if(model.value.first==it){
                        score.value+=1
                        model.value=Utils.RandomModelGenerator()
                        Log.d("main","creating anchor node ${model.value.second}")
                        listOfAnswers.value= listOf(
                            Utils.alphabet.keys.random(),
                            Utils.alphabet.keys.random(),
                            model.value.first
                        ).shuffled()
                        childNode.forEach {
                            it.destroy()
                        }
                        childNode.clear()
                        modelInstance.clear()
                        frame.value=null
                    }
                }
            }
        }
    }
}