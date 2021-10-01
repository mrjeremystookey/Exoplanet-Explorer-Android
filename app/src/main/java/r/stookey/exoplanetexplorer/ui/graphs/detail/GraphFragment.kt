package r.stookey.exoplanetexplorer.ui.graphs.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import r.stookey.exoplanetexplorer.ui.graphs.GraphListViewModel

@AndroidEntryPoint
class GraphFragment : Fragment() {

    companion object {
        fun newInstance() = GraphFragment()
    }

    private val graphListViewModel: GraphListViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return ComposeView(requireContext()).apply {

        }
    }


}