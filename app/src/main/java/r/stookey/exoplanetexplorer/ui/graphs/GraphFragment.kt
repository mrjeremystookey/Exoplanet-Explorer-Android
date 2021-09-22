package r.stookey.exoplanetexplorer.ui.graphs

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Row
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import r.stookey.exoplanetexplorer.ui.compose.ScatterPlot
import timber.log.Timber


@AndroidEntryPoint
class GraphFragment : Fragment() {

    companion object {
        fun newInstance() = GraphFragment()
    }

    private val graphViewModel: GraphViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return ComposeView(requireContext()).apply {
            setContent {
                Row{
                    if(graphViewModel.planetsList.value != null){
                        Timber.d("planetsList size: ${graphViewModel.planetsList.value.size}")
                        ScatterPlot(graphViewModel.planetsList.value, context)
                    }
                }
            }
        }
    }

}