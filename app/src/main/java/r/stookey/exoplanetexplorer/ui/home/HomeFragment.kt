package r.stookey.exoplanetexplorer.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import r.stookey.exoplanetexplorer.databinding.FragmentHomeBinding
import r.stookey.exoplanetexplorer.network.ExoplanetApiService
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private var _binding: FragmentHomeBinding? = null


    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Timber.i("onCreateView called")
        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        Timber.d("homeViewModel.toString(): $homeViewModel")
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        Timber.d("_binding.toString(): ${_binding.toString()}")
        val root: View = binding.root

        val textView: TextView = binding.textHome
        homeViewModel.allPlanets.observe(viewLifecycleOwner, {
            textView.text = it.toString()
        })

        val button: Button = binding.button
        button.setOnClickListener {
            homeViewModel.buttonPressed()
        }


        return root
    }


    override fun onStart() {
        super.onStart()
        Timber.i("onStart called")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Timber.i("onDestroyView called")
        _binding = null
    }


}