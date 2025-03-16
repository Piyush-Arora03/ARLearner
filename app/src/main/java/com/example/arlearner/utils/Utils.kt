package com.example.arlearner.utils

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.ui.graphics.Color
import com.google.android.filament.Engine
import com.google.android.filament.MaterialInstance
import com.google.ar.core.Anchor
import io.github.sceneview.ar.node.AnchorNode
import io.github.sceneview.loaders.MaterialLoader
import io.github.sceneview.loaders.ModelLoader
import io.github.sceneview.model.ModelInstance
import io.github.sceneview.node.CubeNode
import io.github.sceneview.node.ModelNode

object Utils {
    val alphabet= mapOf(
        "A" to "apple.glb",
        "B" to "banana.glb",
        "C" to "cow.glb",
        "D" to "dog.glb",
        "E" to "elephant.glb",
        "F" to "fish.glb",
        "G" to "goat.glb",
        "H" to "hen.glb",
        "I" to "ice_cream.glb",
        "J" to "joker.glb",
        "K" to "kite.glb",
        "L" to "lamp",
        "M" to "mango.glb",
        "N" to "necklace.glb",
        "O" to "orange",
        "P" to "parrot.glb",
        "Q" to "queen.glb",
        "R" to "rat.glb",
        "S" to "snake.glb",
        "T" to "tree.glb",
        "U" to "umbrella.glb",
        "V" to "van.glb",
        "W" to "wolf.glb",
        "X" to "xmas_tree.glb",
        "Y" to "yacht.glb",
        "Z" to "zebra"
    )

    fun GetModelsFromAlphabet(alphabets:String) : String{
        val modelName=alphabet[alphabets]?: error("no model found")
        return "models/$modelName"
    }


    fun CreateAnchorNode(
        engine: Engine,
        modelInstance: MutableList<ModelInstance>,
        modelLoader: ModelLoader,
        anchor: Anchor,
        model: String,
        materialLoader: MaterialLoader
    ): AnchorNode {
        val anchorNode=AnchorNode(anchor=anchor, engine = engine)
        val modelNode=ModelNode(
            modelInstance = modelInstance.apply {
                    if(isEmpty()) {
                        this += modelLoader.createInstancedModel(model, 10)
                    }
            }.removeAt(modelInstance.lastIndex), scaleToUnits = 0.2f
        ).apply {
            isEditable=true
        }

        val boundingBox=CubeNode(
            engine = engine,
            size = modelNode.extents,
            center = modelNode.center,
            materialInstance = materialLoader.createColorInstance(Color.White)
        ).apply {
            isVisible=false
        }
        modelNode.addChildNode(boundingBox)
        anchorNode.addChildNode(modelNode)
        listOf(modelNode,anchorNode).forEach {
            it.onEditingChanged={ editingTransforms ->
                boundingBox.isVisible=editingTransforms.isNotEmpty()
            }
        }

        anchorNode.onRemovedFromScene={
            modelNode.destroy()
            anchorNode.destroy()
        }
        return anchorNode
    }
}