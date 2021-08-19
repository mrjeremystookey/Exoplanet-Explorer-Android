package r.stookey.exoplanetexplorer.ui.tester

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.room.Room
import androidx.room.RoomDatabase
import r.stookey.exoplanetexplorer.cache.PlanetDatabase
import r.stookey.exoplanetexplorer.databinding.FragmentNotificationsBinding
import r.stookey.exoplanetexplorer.repository.RepositoryImpl
import timber.log.Timber
import javax.inject.Inject

class TestFragment : Fragment() {




    private lateinit var testerViewModel: TestViewModel
    private var _binding: FragmentNotificationsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        testerViewModel = ViewModelProvider(this).get(TestViewModel::class.java)
        _binding = FragmentNotificationsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textNotifications
        testerViewModel.text.observe(viewLifecycleOwner, Observer {})


        val button: Button = binding.bTest
        button.setOnClickListener {}

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        
    }




}