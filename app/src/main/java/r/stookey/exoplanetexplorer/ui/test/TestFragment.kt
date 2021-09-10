package r.stookey.exoplanetexplorer.ui.test

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import r.stookey.exoplanetexplorer.databinding.FragmentTestBinding
import r.stookey.exoplanetexplorer.ui.compose.PlanetLoadedSnackBar
import timber.log.Timber

@AndroidEntryPoint
class TestFragment : Fragment() {


    private val testViewModel: TestViewModel by viewModels()
    private var _binding: FragmentTestBinding? = null


    @ExperimentalComposeUiApi
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTestBinding.inflate(inflater, container, false)
        return ComposeView(requireContext()).apply {
            setContent {

                val dividerThickness = 16.dp
                val dividerModifier = Modifier.padding(4.dp)

                Scaffold(
                    backgroundColor = MaterialTheme.colors.background,
                    content = {
                        Column {
                            Row(){
                                Button(
                                    modifier = Modifier.padding(16.dp),
                                    onClick = { testViewModel.doSomeWork() },
                                ) {
                                    Text("Do some work")
                                }
                                Button(
                                    modifier = Modifier.padding(16.dp),
                                    onClick = { testViewModel.logInFireBase() },
                                ) {
                                    Text("Add event to Firebase")
                                }
                            }
                            Divider(thickness = dividerThickness, modifier = dividerModifier)
                            Divider(thickness = dividerThickness, modifier = dividerModifier)


                        }
                    }
                )


            }
        }
    }




    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}