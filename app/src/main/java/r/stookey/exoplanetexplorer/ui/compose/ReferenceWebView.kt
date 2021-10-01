package r.stookey.exoplanetexplorer.ui.compose.theme

import android.webkit.WebView
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView


private val testRef = "https://ui.adsabs.harvard.edu/abs/2015ApJ...802...61L/abstract"

@Composable
fun ReferenceWebView(reference: String) {
    AndroidView(
        modifier = Modifier
            .padding(2.dp),
        factory = { context ->
            WebView(context)
        }
    ){ webView ->
        webView.loadUrl(reference)
    }
}


@Preview
@Composable
fun PreviewReferenceWebView(){
    ReferenceWebView(reference = testRef)
}