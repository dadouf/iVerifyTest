package com.davidferrand.iverifytest.ui.device

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.davidferrand.iverifytest.ui.theme.DefaultSpacing
import com.davidferrand.iverifytest.ui.theme.IVerifyTestTheme

@Composable
fun FilterButton(
    isFiltering: Boolean,
    filter: String?,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    onFilterChanged: (String) -> Unit = {},
) {
    FloatingActionButton(
        onClick = onClick,
        modifier = modifier,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(DefaultSpacing),
            modifier = Modifier
                .padding(horizontal = DefaultSpacing)
                .animateContentSize()
        ) {
            Icon(
                if (!isFiltering) Icons.Default.Search else Icons.Default.Close,
                contentDescription = "Search"
            )

            if (isFiltering) {
                TextField(
                    value = filter ?: "",
                    onValueChange = onFilterChanged,
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = Color.Transparent,
                        focusedContainerColor = Color.Transparent,
                    ),
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun FilterButtonPreview_Closed() {
    IVerifyTestTheme {
        FilterButton(
            isFiltering = false,
            filter = null,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun FilterButtonPreview_Open() {
    IVerifyTestTheme {
        FilterButton(
            isFiltering = true,
            filter = "abc"
        )
    }
}