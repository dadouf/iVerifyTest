package com.davidferrand.iverifytest.ui

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.davidferrand.iverifytest.ui.theme.DefaultElevation
import com.davidferrand.iverifytest.ui.theme.IVerifyTestTheme
import com.davidferrand.iverifytest.ui.theme.RoundedCornersSize

@Composable
fun ErrorIndicator(error: Throwable, onRetryClick: () -> Unit = {}) {
    Surface(
        shadowElevation = DefaultElevation,
        modifier = Modifier.padding(32.dp),
        shape = RoundedCornerShape(RoundedCornersSize),
        color = MaterialTheme.colorScheme.secondaryContainer,
        contentColor = MaterialTheme.colorScheme.onSecondaryContainer,
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "Error: $error"
            )

            Button(onClick = onRetryClick) {
                Text(text = "Retry")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ErrorIndicatorPreview() {
    IVerifyTestTheme {
        ErrorIndicator(Throwable())
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun ErrorIndicatorPreview_Dark() {
    IVerifyTestTheme {
        ErrorIndicator(Throwable())
    }
}