package r.stookey.exoplanetexplorer.ui.tester

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.material.Text
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import dagger.hilt.android.AndroidEntryPoint
import r.stookey.exoplanetexplorer.databinding.FragmentTestBinding
import r.stookey.exoplanetexplorer.ui.compose.theme.ExoplanetExplorerTheme

@AndroidEntryPoint
class TestFragment : Fragment() {




    private lateinit var testerViewModel: TestViewModel
    private var _binding: FragmentTestBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        testerViewModel = ViewModelProvider(this).get(TestViewModel::class.java)
        _binding = FragmentTestBinding.inflate(inflater, container, false)
        return ComposeView(requireContext()).apply {
            setContent {
                ExoplanetExplorerTheme {
                    Text("test")
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        
    }




}