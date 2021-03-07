package com.example.composebottomsheetbugs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ListItem
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import com.example.composebottomsheetbugs.ui.theme.AppTheme
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    @OptIn(ExperimentalMaterialApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                val sheetState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)
                val scope = rememberCoroutineScope()
                ModalBottomSheetLayout(
                    modifier = Modifier.fillMaxSize(),
                    sheetState = sheetState,
                    sheetContent = { BottomSheetContent() }
                ) {
                    Surface(Modifier.padding(16.dp), color = MaterialTheme.colors.background) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Button(
                                onClick = { scope.launch { sheetState.show() } }
                            ) {
                                Text("Show Compose BottomSheet")
                            }

                            Button(
                                modifier = Modifier.padding(top = 16.dp),
                                onClick = { showFragmentBottomSheet() }
                            ) {
                                Text("Show Fragment BottomSheet")
                            }
                        }
                    }
                }
            }
        }
    }

    private fun showFragmentBottomSheet() {
        TheBottomSheetDialogFragment().show(supportFragmentManager, "TAG")
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BottomSheetContent() {
    Column { // It works with LazyColumn
        for (i in 1..10) {
            ListItem(Modifier.clickable { }) {
                Text("Column item #$i")
            }
        }
    }
}


class TheBottomSheetDialogFragment : BottomSheetDialogFragment() {

    @OptIn(ExperimentalMaterialApi::class)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return ComposeView(requireContext()).apply {
            setContent {
                AppTheme {
                    for (i in 1..10) {
                        ListItem(Modifier.clickable { }) {
                            Text("Column item #$i")
                        }
                    }
                }
            }
        }
    }
}