package r.stookey.exoplanetexplorer.ui.test

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
                            Button(
                                modifier = Modifier.padding(16.dp),
                                onClick = { testViewModel.doSomeWork() },
                            ) {
                                Text("Do some work")
                            }
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
                                XAttributeSelection()
                            }
                        }
                    }
                )
            }
        }
    }


    @Composable
    fun DropDownList(
        requestToOpen: Boolean = false,
        list: List<String>,
        request: (Boolean) -> Unit,
        selectedString: (String) -> Unit
    ) {
        DropdownMenu(
            expanded = requestToOpen,
            onDismissRequest = { request(false) },
        ) {
            list.forEach {
                DropdownMenuItem(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        request(false)
                        selectedString(it)
                    }
                ) {
                    Text(it, modifier = Modifier.wrapContentWidth())
                }
            }
        }
    }


    @Composable
    fun XAttributeSelection() {
        val countryList = listOf(
            "Mass",
            "Radius",
            "Density",
            "Period",
        )
        val text = remember { mutableStateOf("") } // initial value
        val isOpen = remember { mutableStateOf(false) } // initial value
        val openCloseOfDropDownList: (Boolean) -> Unit = {
            isOpen.value = it
        }
        val userSelectedString: (String) -> Unit = {
            text.value = it
        }
        Box {
            Column {
                OutlinedTextField(
                    value = text.value,
                    onValueChange = { text.value = it },
                    label = { Text(text = "Choose X axis value") },
                    modifier = Modifier.fillMaxWidth()
                )
                DropDownList(
                    requestToOpen = isOpen.value,
                    list = countryList,
                    openCloseOfDropDownList,
                    userSelectedString
                )
            }
            Spacer(
                modifier = Modifier
                    .matchParentSize()
                    .background(Color.Transparent)
                    .padding(10.dp)
                    .clickable(
                        onClick = { isOpen.value = true }
                    )
            )
        }
    }




    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}