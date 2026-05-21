package com.example.components

import android.webkit.WebView
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView

@Composable
fun MathTexView(
    latex: String,
    modifier: Modifier = Modifier
) {
    val htmlData = """
        <!DOCTYPE html>
        <html>
        <head>
            <meta charset="UTF-8">
            <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=no">
            <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/katex@0.16.8/dist/katex.min.css" integrity="sha384-GvrOXuhMATgEsSwCs4smul74iXGOixntILdUW9XmUC6+HX0sLNAK3q71bZlhw5v6" crossorigin="anonymous">
            <script defer src="https://cdn.jsdelivr.net/npm/katex@0.16.8/dist/katex.min.js" integrity="sha384-cpW21T6RZO/O04KPZhbvcwyBjy/q8F/Z4Jq4/8DNDZ+Nn52r2b8G4/iU1M3E2L/K" crossorigin="anonymous"></script>
            <script defer src="https://cdn.jsdelivr.net/npm/katex@0.16.8/dist/contrib/auto-render.min.js" integrity="sha384-+VBxd3r6XgURycqtZ117nYw44OcwXp6/QO3+JRVe9t7UaPehFq7RzFvLzT3I/JcT" crossorigin="anonymous" onload="renderMathInElement(document.body);"></script>
            <style>
                body {
                    background-color: transparent;
                    color: #0F172A;
                    font-size: 13px;
                    font-family: sans-serif;
                    padding: 4px;
                    margin: 0;
                }
            </style>
        </head>
        <body>
            $latex
        </body>
        </html>
    """.trimIndent()

    AndroidView(
        factory = { context ->
            WebView(context).apply {
                settings.javaScriptEnabled = true
                setBackgroundColor(android.graphics.Color.TRANSPARENT)
                loadDataWithBaseURL(null, htmlData, "text/html", "UTF-8", null)
            }
        },
        update = { webView ->
            webView.loadDataWithBaseURL(null, htmlData, "text/html", "UTF-8", null)
        },
        modifier = modifier.fillMaxWidth()
    )
}
