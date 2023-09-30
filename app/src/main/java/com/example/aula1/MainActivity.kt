package com.example.aula1

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.aula1.databinding.ActivityMainBinding

import org.tensorflow.lite.DataType;
import org.tensorflow.lite.support.image.ImageProcessor
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.support.image.ops.ResizeOp


// Initialization code
// Create an ImageProcessor with all ops required. For more ops, please
// refer to the ImageProcessor Architecture section in this README.
var imageProcessor = ImageProcessor.Builder()
    .add(ResizeOp(224, 224, ResizeOp.ResizeMethod.BILINEAR))
    .build()

// Create a TensorImage object. This creates the tensor of the corresponding
// tensor type (uint8 in this case) that the TensorFlow Lite interpreter needs.
var tensorImage = TensorImage(DataType.UINT8)

// Analysis code for every frame
// Preprocess the image

private lateinit var binding: ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Ref: https://developer.android.com/topic/libraries/view-binding
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        var bitmap = assets
            .open("cat1.png")
            .use(BitmapFactory::decodeStream)

        tensorImage.load(bitmap)
        tensorImage = imageProcessor.process(tensorImage)
        var rescaledBitmap = tensorImage.bitmap

        binding.previewBitmap.setImageBitmap(bitmap)
        binding.resBtn.setOnClickListener {
            binding.previewBitmap.setImageBitmap(rescaledBitmap)
        }
    }
}