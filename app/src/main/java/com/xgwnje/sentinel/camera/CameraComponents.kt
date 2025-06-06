@file:OptIn(androidx.camera.camera2.interop.ExperimentalCamera2Interop::class)

package com.xgwnje.sentinel.camera

import android.content.Context
import android.hardware.camera2.CaptureRequest
import android.media.Ringtone
import android.media.RingtoneManager
import android.util.Log
import androidx.camera.camera2.interop.Camera2Interop
import androidx.camera.core.AspectRatio
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview as CameraXPreview
import androidx.camera.core.UseCase
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import com.xgwnje.sentinel.ui.preview.PreviewViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.asExecutor
import java.nio.ByteBuffer
import kotlin.math.abs

private object AlarmSoundManager {
    private var ringtone: Ringtone? = null
    fun play(context: Context) {
        try {
            if (ringtone == null) {
                val alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)
                ringtone = RingtoneManager.getRingtone(context.applicationContext, alarmUri)
            }
            if (ringtone?.isPlaying == false) ringtone?.play()
        } catch (e: Exception) { e.printStackTrace() }
    }
    fun stop() {
        try {
            if (ringtone?.isPlaying == true) ringtone?.stop()
        } catch (e: Exception) { e.printStackTrace() }
    }
}

@Composable
internal fun CameraPreview(
    viewModel: PreviewViewModel,
    isPreviewing: Boolean
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val isMonitoring by viewModel.isMonitoring.collectAsState()
    val isAlarmActive by viewModel.isAlarmActive.collectAsState()

    val pixelThreshold by viewModel.pixelChangeThreshold.collectAsState()
    val analysisInterval by viewModel.analysisInterval.collectAsState()

    val imageAnalyzer = remember {
        val builder = ImageAnalysis.Builder()
            .setTargetAspectRatio(AspectRatio.RATIO_4_3)
            .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)

        val extender = Camera2Interop.Extender(builder)
        extender.setCaptureRequestOption(CaptureRequest.CONTROL_AF_MODE, CaptureRequest.CONTROL_AF_MODE_OFF)
        extender.setCaptureRequestOption(CaptureRequest.LENS_FOCUS_DISTANCE, 0.0f)

        builder.build()
    }

    LaunchedEffect(isAlarmActive) {
        if (isAlarmActive) AlarmSoundManager.play(context) else AlarmSoundManager.stop()
    }

    LaunchedEffect(isMonitoring, pixelThreshold, analysisInterval) {
        if (isMonitoring) {
            imageAnalyzer.setAnalyzer(
                Dispatchers.Default.asExecutor(),
                SimplifiedGridAnalyzer(
                    pixelChangeThreshold = pixelThreshold,
                    analysisInterval = analysisInterval.toInt(),
                    onDifferenceDetected = { viewModel.triggerAlarm() }
                )
            )
        } else {
            imageAnalyzer.clearAnalyzer()
        }
    }

    AndroidView(
        factory = { ctx -> PreviewView(ctx).apply { this.scaleType = PreviewView.ScaleType.FILL_CENTER } },
        update = { previewView ->
            val cameraProviderFuture = ProcessCameraProvider.getInstance(context)
            cameraProviderFuture.addListener({
                val cameraProvider = cameraProviderFuture.get()
                cameraProvider.unbindAll()

                val useCasesToBind = mutableListOf<UseCase>()

                if (isPreviewing) {
                    val previewBuilder = CameraXPreview.Builder().setTargetAspectRatio(AspectRatio.RATIO_4_3)

                    val extender = Camera2Interop.Extender(previewBuilder)
                    extender.setCaptureRequestOption(CaptureRequest.CONTROL_AF_MODE, CaptureRequest.CONTROL_AF_MODE_OFF)
                    extender.setCaptureRequestOption(CaptureRequest.LENS_FOCUS_DISTANCE, 0.0f)

                    val preview = previewBuilder.build().also { it.setSurfaceProvider(previewView.surfaceProvider) }
                    useCasesToBind.add(preview)
                }

                if (isMonitoring) {
                    useCasesToBind.add(imageAnalyzer)
                }

                if (useCasesToBind.isNotEmpty()) {
                    val cameraSelector = CameraSelector.Builder().requireLensFacing(CameraSelector.LENS_FACING_BACK).build()
                    try {
                        cameraProvider.bindToLifecycle(lifecycleOwner, cameraSelector, *useCasesToBind.toTypedArray())
                    } catch (e: Exception) {
                        Log.e("CameraPreview", "Use case binding failed", e)
                    }
                }
            }, ContextCompat.getMainExecutor(context))
        }
    )
}

private class SimplifiedGridAnalyzer(
    private val pixelChangeThreshold: Float,
    private val analysisInterval: Int,
    private val onDifferenceDetected: () -> Unit
) : ImageAnalysis.Analyzer {
    private var lastYBuffer: ByteArray? = null
    private var frameCounter = 0
    private val gridWidth = 16
    private val gridHeight = 12

    @androidx.annotation.OptIn(androidx.camera.core.ExperimentalGetImage::class)
    override fun analyze(imageProxy: androidx.camera.core.ImageProxy) {
        frameCounter++
        if (frameCounter % analysisInterval != 0) {
            imageProxy.close()
            return
        }

        val yPlane = imageProxy.planes[0]
        val currentYBuffer = yPlane.buffer
        val currentYByteArray = ByteArray(currentYBuffer.remaining())
        currentYBuffer.get(currentYByteArray)

        lastYBuffer?.let { lastFrame ->
            val imageWidth = imageProxy.width
            val imageHeight = imageProxy.height
            val cellWidth = imageWidth / gridWidth
            val cellHeight = imageHeight / gridHeight

            for (gridY in 0 until gridHeight) {
                for (gridX in 0 until gridWidth) {
                    var cellDiff: Long = 0
                    var pixelsInCell = 0

                    val startX = gridX * cellWidth
                    val startY = gridY * cellHeight
                    for (y in startY until startY + cellHeight step 2) {
                        for (x in startX until startX + cellWidth step 2) {
                            val index = y * imageWidth + x
                            if (index < lastFrame.size && index < currentYByteArray.size) {
                                val diff = abs(currentYByteArray[index] - lastFrame[index])
                                cellDiff += diff
                                pixelsInCell++
                            }
                        }
                    }

                    if (pixelsInCell > 0) {
                        val averageCellDiff = (cellDiff.toDouble() / pixelsInCell) / 255.0 * 100
                        if (averageCellDiff > pixelChangeThreshold) {
                            onDifferenceDetected()
                            lastYBuffer = currentYByteArray
                            imageProxy.close()
                            return
                        }
                    }
                }
            }
        }

        lastYBuffer = currentYByteArray
        imageProxy.close()
    }
}
