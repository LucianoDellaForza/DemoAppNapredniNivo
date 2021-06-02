package rs.gecko.demoappnapredninivo.compose

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltNavGraphViewModel
import coil.request.ImageRequest
import com.google.accompanist.coil.CoilImage
import rs.gecko.demoappnapredninivo.ui.models.Photo
import rs.gecko.demoappnapredninivo.ui.viewmodels.PhotoViewModel

@Composable
fun PhotosScreen(
    viewModel: PhotoViewModel = hiltNavGraphViewModel()
) {
    val albumsWithPhotos by viewModel.albumsWithPhotos.observeAsState()

    LazyColumn(
        contentPadding = PaddingValues(16.dp)
    ) {
        albumsWithPhotos?.data?.let {
            val itemCount = albumsWithPhotos!!.data!!.size
            items(count = itemCount) {
                val albumKeys = albumsWithPhotos!!.data!!.keys
                val albumKey = albumKeys.elementAt(it)
                AlbumHeader(
                    albumId = albumKey
                )
                Spacer(modifier = Modifier.height(8.dp))
                AlbumPhotos(
                    albumPhotos = albumsWithPhotos!!.data!!.get(albumKey)!!
                )
            }
        }
    }
}

@Composable
fun AlbumHeader(
    modifier: Modifier = Modifier,
    albumId: Int
) {
    Row(modifier = Modifier
        .fillMaxWidth()
        .background(Color.LightGray),
        horizontalArrangement = Arrangement.Center
    ){
        Text(text = albumId.toString())
    }

}

@Composable
fun AlbumPhotos(
    albumPhotos: MutableList<Photo>,
    columns: Int = 3
) {
    //val photosForRowsChunked = albumPhotos.chunked(columns)
    var photosIterator = 0
    var lessThanSpecifiedInLastRow = false

    var numberOfRows = 0
    if (albumPhotos.size % columns == 0) {
        numberOfRows = albumPhotos.size / columns
    } else {
        numberOfRows = albumPhotos.size / columns + 1
        lessThanSpecifiedInLastRow = true
    }

    Column() {
        for (row in 0..numberOfRows-1) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = if (lessThanSpecifiedInLastRow && row == numberOfRows-1) Arrangement.SpaceEvenly else Arrangement.Start
            ) {
              for (i in 0..columns-1) {
                  if(photosIterator < albumPhotos.size-1) {
                      val photo = albumPhotos.get(photosIterator)
//                      Text(
//                          text = "PhotoID: ${photo.id}"
//                      )
                      CoilImage(
                          request = ImageRequest.Builder(LocalContext.current)
                              .data(photo.thumbnailUrl)
                              .build(),
                          contentDescription = photo.title,
                          fadeIn = true,
                          modifier = Modifier
                              .size(120.dp)
                              .align(Alignment.CenterVertically)
                              .padding(5.dp)
                      ) {
                          CircularProgressIndicator(
                              color = MaterialTheme.colors.primary,
                              modifier = Modifier.scale(0.5f)
                          )
                      }
                      photosIterator++
                  }
              }
            }
        }
    }

//    LazyColumn() {
//        val itemCount = if (albumPhotos.size % columns != 0) {
//            albumPhotos.size / columns
//        } else {
//            albumPhotos.size / columns + 1
//        }
//        Log.d("PhotosScreen", "itemCountForLazyColumn: ${itemCount}" )
//        items(count = itemCount) {
////            val photos = photosForRows.get(photosForRowsIterator)
////            LazyRow() {
////                val itemCount = photos.size
////                items(count = itemCount) {
////                    Row() {
////                        Text(text = "Photo id: ${photos.get(it).id}")
////                    }
////                }
////            }
////            photosForRowsIterator++
//        }
//
//    }


//    val chunkedList = albumPhotos.chunked(columns)
//    LazyColumn() {
//        val itemCount = chunkedList.size
//        items(count = itemCount) {
//            for (photoRow in chunkedList) {
//                Row() {
//                    for (photo in photoRow)
//                    CoilImage(
//                        request = ImageRequest.Builder(LocalContext.current)
//                            .data(photo.thumbnailUrl)
////                            .target {
////                                viewModel.calcDominantColor(it) { color ->
////                                    dominantColor = color
////                                }
////                            }
//                            .build(),
//                        contentDescription = photo.title,
//                        fadeIn = true,
//                        modifier = Modifier
//                            .size(130.dp)
//                            .align(Alignment.CenterVertically)
//                    ) {
//                        CircularProgressIndicator(
//                            color = MaterialTheme.colors.primary,
//                            modifier = Modifier.scale(0.5f)
//                        )
//                    }
//                }
//            }
//
//        }
//    }
}