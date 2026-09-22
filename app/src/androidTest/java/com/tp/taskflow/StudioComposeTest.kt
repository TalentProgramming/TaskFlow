package com.tp.taskflow

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsOn
import androidx.compose.ui.test.isToggleable
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.tp.taskflow.feature.product.presentation.FavoriteIcon
import com.tp.taskflow.feature.settings.presentation.SettingToggleRow
import com.tp.taskflow.ui.theme.TaskFlowTheme
import org.junit.Rule
import org.junit.Test

class StudioComposeTest {

    @get:Rule
    val rule = createComposeRule()

    @Test
    fun toggle_row_click_turnsOn() {
        rule.setContent {
            var checked by remember { mutableStateOf(false) }
            TaskFlowTheme {
                SettingToggleRow(
                    title = "Dark theme",
                    subtitle = "Use OLED black",
                    checked = checked,
                    onCheckedChange = { checked = it },
                    icon = Icons.Default.DarkMode
                )
            }
        }
        rule.onNodeWithText("Dark theme").performClick()
        rule.onNode(isToggleable()).assertIsOn()
    }

    @Test
    fun favorite_announcesOnState() {
        rule.setContent {
            var favorite by remember { mutableStateOf(false) }
            TaskFlowTheme {
                FavoriteIcon(favorite = favorite, onClick = { favorite = !favorite })
            }
        }
        rule.onNodeWithContentDescription("Add favorite").performClick()
        rule.onNodeWithContentDescription("Remove favorite").assertIsDisplayed()
    }
}
