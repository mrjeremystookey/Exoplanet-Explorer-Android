package r.stookey.exoplanetexplorer.ui.test

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkRequest
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import r.stookey.exoplanetexplorer.databinding.FragmentTestBinding
import r.stookey.exoplanetexplorer.network.ExoplanetApiWorker
import r.stookey.exoplanetexplorer.ui.compose.PlanetLoadedSnackBar
import timber.log.Timber
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class TestFragment : Fragment() {


    private val testViewModel: TestViewModel by viewModels()
    private var _binding: FragmentTestBinding? = null
    private lateinit var scaffoldState: ScaffoldState
    private lateinit var hostState: SnackbarHostState

    @ExperimentalComposeUiApi
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTestBinding.inflate(inflater, container, false)
        return ComposeView(requireContext()).apply {
            setContent {
                hostState = remember{ SnackbarHostState() }
                scaffoldState = rememberScaffoldState()

                val dividerThickness = 16.dp
                val dividerModifier = Modifier.padding(4.dp)


                Scaffold(
                    backgroundColor = MaterialTheme.colors.background,
                    content = {
                        Column {
                            Button(
                                modifier = Modifier.padding(16.dp),
                                onClick = { doSomeWork() },
                            ) {
                                Text("Do some work")
                            }
                            Divider(thickness = dividerThickness, modifier = dividerModifier)
                            LaunchedEffect(key1 = "snackbar", block = {
                                scaffoldState.snackbarHostState.showSnackbar(
                                    message = "Done adding planets",
                                    actionLabel = "Hide",
                                    duration = SnackbarDuration.Indefinite)
                            })
                            AndroidView(
                                modifier = Modifier
                                    .padding(32.dp)
                                    .background(Color.Blue)
                                    .clickable {
                                        Timber.d("Webview clicked")
                                    },
                                factory = { context ->
                                    WebView(context)
                                }
                            ){ webView ->
                                webView.loadUrl("https://www.google.com")
                            }
                            Divider(thickness = dividerThickness, modifier = dividerModifier)
                            Box(Modifier
                                .fillMaxSize()
                                .background(Color.Gray)){
                                Row(modifier = Modifier
                                    .wrapContentSize()
                                    .align(Alignment.BottomCenter)) {
                                    PlanetLoadedSnackBar(snackbarHostState = scaffoldState.snackbarHostState) {}
                                }
                            }
                        }
                    },
                    scaffoldState = scaffoldState,
                    snackbarHost = {scaffoldState.snackbarHostState}
               )
            }
        }
    }


    private fun doSomeWork(){
        Timber.d("Doing some work")
        val workManager = WorkManager.getInstance()
    }




    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}