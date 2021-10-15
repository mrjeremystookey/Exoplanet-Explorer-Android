package r.stookey.exoplanetexplorer.ui.test

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import r.stookey.exoplanetexplorer.databinding.FragmentTestBinding

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
                            Divider(thickness = dividerThickness, modifier = dividerModifier)
                            Button(
                                modifier = Modifier.padding(16.dp),
                                onClick = { testViewModel.crash() },
                            ) {
                                Text("crash (and record event in Crashlytics)")
                            }
                            Divider(thickness = dividerThickness, modifier = dividerModifier)
                            Button(
                                modifier = Modifier.padding(16.dp),
                                onClick = { testViewModel.logInFireBase() },
                            ) {
                                Text("Add event to Firebase")
                            }
                            Row {

                            }
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