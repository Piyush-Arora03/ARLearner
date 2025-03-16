package com.example.arlearner.ui.screens

import android.os.Build
import android.util.Log
import android.view.MotionEvent
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.NavController
import com.example.arlearner.utils.Utils
import com.google.ar.core.Config
import com.google.ar.core.Frame
import com.google.ar.core.TrackingFailureReason
import io.github.sceneview.ar.ARScene
import io.github.sceneview.ar.arcore.createAnchorOrNull
import io.github.sceneview.ar.arcore.isValid
import io.github.sceneview.ar.rememberARCameraNode
import io.github.sceneview.model.ModelInstance
import io.github.sceneview.node.Node
import io.github.sceneview.rememberCollisionSystem
import io.github.sceneview.rememberEngine
import io.github.sceneview.rememberMaterialLoader
import io.github.sceneview.rememberModelLoader
import io.github.sceneview.rememberNodes
import io.github.sceneview.rememberOnGestureListener
import io.github.sceneview.rememberView

@RequiresApi(Build.VERSION_CODES.VANILLA_ICE_CREAM)
@Composable
fun ARScreen(navController: NavController,model:String)
{
    Log.d("main","in ARScreen")
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

    ARScene(
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
        },
        sessionConfiguration = {session, config ->  
            config.depthMode=when (
                session.isDepthModeSupported(Config.DepthMode.AUTOMATIC)
            ){
                true-> Config.DepthMode.AUTOMATIC
                else-> Config.DepthMode.DISABLED
            }
            config.lightEstimationMode=Config.LightEstimationMode.ENVIRONMENTAL_HDR
        },
        onGestureListener = rememberOnGestureListener(
            onSingleTapConfirmed = {me: MotionEvent, node: Node? ->  
                if(node==null){
                    val hitTestResult=frame.value?.hitTest(me.x,me.y)
                    hitTestResult?.firstOrNull{
                        it.isValid(
                            depthPoint = false,
                            point = false
                        )
                    }?.createAnchorOrNull()?.let {
                        val nodeModel=Utils.CreateAnchorNode(
                            engine,
                            modelInstance,
                            modelLoader,
                            anchor = it,
                            model=Utils.GetModelsFromAlphabet(model),
                            materialLoader
                        )
                        childNode+=nodeModel
                    }
                }
            }
        )
    )
    DisposableEffect(Unit) {
        onDispose {
            Log.d("main","clearing nodes")
            childNode.clear()
            modelInstance.clear()
        }
    }


}