package com.xgwnje.sentinel.ui.preview

import android.app.Application
import android.media.Ringtone
import android.media.RingtoneManager
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

// 核心修复 1: 继承自 AndroidViewModel 以安全地访问 Application Context
class PreviewViewModel(application: Application) : AndroidViewModel(application) {

    private val _isMonitoring = MutableStateFlow(false)
    val isMonitoring = _isMonitoring.asStateFlow()

    private val _isPreviewing = MutableStateFlow(true)
    val isPreviewing = _isPreviewing.asStateFlow()

    private val _isAlarmActive = MutableStateFlow(false)
    val isAlarmActive = _isAlarmActive.asStateFlow()

    private val _currentEvent = MutableStateFlow("无")
    val currentEvent = _currentEvent.asStateFlow()

    private val _pixelChangeThreshold = MutableStateFlow(10f)
    val pixelChangeThreshold = _pixelChangeThreshold.asStateFlow()

    private val _analysisInterval = MutableStateFlow(30f)
    val analysisInterval = _analysisInterval.asStateFlow()

    // 核心修复 2: 创建一个 SharedFlow 用于向 UI 发送一次性事件（如 Snackbar 消息）
    private val _uiEvent = MutableSharedFlow<String>()
    val uiEvent = _uiEvent.asSharedFlow()

    // 核心修复 3: 在 ViewModel 中管理 Ringtone 实例
    private var ringtone: Ringtone? = null

    init {
        // 在 ViewModel 的生命周期内监听警报状态
        viewModelScope.launch {
            isAlarmActive.collect { isActive ->
                if (isActive) {
                    playAlarm()
                } else {
                    stopAlarm()
                }
            }
        }
    }

    private fun playAlarm() {
        try {
            if (ringtone == null) {
                val alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)
                ringtone = RingtoneManager.getRingtone(getApplication(), alarmUri)
            }
            if (ringtone?.isPlaying == false) {
                ringtone?.play()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun stopAlarm() {
        try {
            if (ringtone?.isPlaying == true) {
                ringtone?.stop()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    // 核心修复 4: 为所有操作添加前置检查
    fun onPreviewToggle() {
        if (checkAlarmAndNotify()) return
        _isPreviewing.value = !_isPreviewing.value
    }

    fun onMonitoringToggle() {
        if (checkAlarmAndNotify()) return
        _isMonitoring.value = !_isMonitoring.value
        if (!_isMonitoring.value) {
            resetAlarm()
        }
    }

    fun onPixelThresholdChange(newThreshold: Float) {
        if (checkAlarmAndNotify()) return
        _pixelChangeThreshold.value = newThreshold
    }

    fun onAnalysisIntervalChange(newInterval: Float) {
        if (checkAlarmAndNotify()) return
        _analysisInterval.value = newInterval
    }

    // 复位操作不需要检查
    fun onResetAlarm() {
        resetAlarm()
    }

    fun triggerAlarm() {
        if (_isMonitoring.value && !_isAlarmActive.value) {
            _isAlarmActive.value = true
            _currentEvent.value = "检测到画面差异"
        }
    }

    private fun resetAlarm() {
        _isAlarmActive.value = false
        _currentEvent.value = "无"
    }

    // 检查警报状态并发送通知的辅助函数
    private fun checkAlarmAndNotify(): Boolean {
        if (_isAlarmActive.value) {
            viewModelScope.launch {
                _uiEvent.emit("请先复位当前警报再执行其他操作")
            }
            return true
        }
        return false
    }

    override fun onCleared() {
        // 当 ViewModel 销毁时，确保停止铃声
        stopAlarm()
        super.onCleared()
    }
}
