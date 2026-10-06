package com.vanyookk.unipocket.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vanyookk.unipocket.domain.model.Lesson

@Composable
fun LessonCardList(
    lessons: List<Lesson>,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        lessons.forEach { lesson ->
            LessonCard(lesson = lesson)
        }
    }
}