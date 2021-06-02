package rs.gecko.demoappnapredninivo.compose


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltNavGraphViewModel
import rs.gecko.demoappnapredninivo.ui.models.UserPhoto
import rs.gecko.demoappnapredninivo.ui.viewmodels.UserPhotoViewModel

@Composable
fun UserPhotosScreen(
    viewModel: UserPhotoViewModel = hiltNavGraphViewModel()
) {
    val userPhotos by viewModel.userPhotos.observeAsState()

    val (showDialog, setShowDialog) =  remember { mutableStateOf(false) }

    LazyColumn(
        contentPadding = PaddingValues(16.dp)
    ) {
        userPhotos?.let {
                val itemCount = userPhotos!!.size
            items(count = itemCount) {
                UserPhotoEntry(userPhoto = userPhotos!![it])
            }
        }
    }
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = {

            }) {
                Text(text = "+")
            }
        },
        floatingActionButtonPosition = FabPosition.End
    ) {
    }
}

@Composable
fun UserPhotoEntry(
    userPhoto: UserPhoto
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        Row() {
            Image(
                bitmap = userPhoto.photo as ImageBitmap,
                contentDescription = ""
            )
        }
    }
}

@Composable
fun Dialog(
    showDialog: Boolean,
    setShowDialog: (Boolean) -> Unit) {
    if (showDialog) {
        AlertDialog(
            onDismissRequest = {
            },
            title = {
                Text("Title")
            },
            confirmButton = {
                Button(
                    onClick = {
                        // Change the state to close the dialog
                        setShowDialog(false)
                    },
                ) {
                    Text("Confirm")
                }
            },
            dismissButton = {
                Button(
                    onClick = {
                        // Change the state to close the dialog
                        setShowDialog(false)
                    },
                ) {
                    Text("Dismiss")
                }
            },
            text = {
                Text("This is a text on the dialog")
            },
        )
    }
}