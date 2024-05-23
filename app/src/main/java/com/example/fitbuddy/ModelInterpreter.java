package com.example.fitbuddy;

import android.content.Context;
import org.tensorflow.lite.DataType;
import org.tensorflow.lite.Interpreter;
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

import android.content.Context;
import android.util.Log;

import org.tensorflow.lite.Interpreter;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

public class ModelInterpreter {
    private Interpreter tflite;

    public ModelInterpreter(Context context) throws IOException {
        tflite = new Interpreter(loadModelFile(context));
    }

    private MappedByteBuffer loadModelFile(Context context) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(context.getAssets().openFd("model.tflite").getFileDescriptor());
        FileChannel fileChannel = fileInputStream.getChannel();
        long startOffset = context.getAssets().openFd("model.tflite").getStartOffset();
        long declaredLength = context.getAssets().openFd("model.tflite").getDeclaredLength();
        return fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength);
    }

    public int predict(float[] inputData) {
        float[][] output = new float[1][1]; // Çıkış boyutunu modele göre ayarlayın
        tflite.run(inputData, output);
        Log.d("ModelInterpreter", "Model çıktısı: " + output[0][0]);
        return Math.round(output[0][0]);
    }
}