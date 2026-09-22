package com.tp.taskflow

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.tp.taskflow.core.firebase.ClassroomViewModel
import com.tp.taskflow.core.ui.ThemeViewModel
import com.tp.taskflow.navigation.TaskFlowNavHost
import com.tp.taskflow.ui.theme.TaskFlowTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val themeViewModel: ThemeViewModel = hiltViewModel()
            val classroomViewModel: ClassroomViewModel = hiltViewModel()
            val darkMode by themeViewModel.darkMode.collectAsStateWithLifecycle()
            val flags by classroomViewModel.flags.collectAsStateWithLifecycle()
            TaskFlowTheme(darkTheme = darkMode) {
                TaskFlowNavHost(
                    darkMode = darkMode,
                    themeViewModel = themeViewModel,
                    flags = flags,
                    onTestCrash = classroomViewModel::testCrash
                )
            }
        }
    }
}
