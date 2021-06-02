package rs.gecko.demoappnapredninivo.compose

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Divider
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltNavGraphViewModel
import rs.gecko.demoappnapredninivo.ui.models.Post
import rs.gecko.demoappnapredninivo.ui.viewmodels.PostViewModel
import java.lang.reflect.Modifier

@Composable
fun PostsScreen(
//    viewModel: PostViewModel = hiltNavGraphViewModel()
) {
    Surface(
        modifier = androidx.compose.ui.Modifier.fillMaxSize()
    ) {
        PostList()
    }

}

@Composable
fun PostList(
    viewModel: PostViewModel = hiltNavGraphViewModel()
) {
    val posts by viewModel.posts.observeAsState()
    
    LazyColumn(
        contentPadding = PaddingValues(16.dp)
    ) {
        posts?.data?.let {
            val itemCount = posts!!.data!!.size
            items(count = itemCount) {
                PostEntry(
                    modifier = androidx.compose.ui.Modifier,
                    post = posts!!.data!!.get(it))
                Spacer(modifier = androidx.compose.ui.Modifier
                    .height(8.dp))
            }
        }
    }
}

@Composable
fun PostEntry(
    modifier: androidx.compose.ui.Modifier = androidx.compose.ui.Modifier,
    post: Post
) {
    Column(
        modifier = androidx.compose.ui.Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        Row {
            Text(text = "Title:")
            Spacer(modifier = androidx.compose.ui.Modifier
                .width(10.dp))
            Text(text = post.title)
        }
        Spacer(modifier = androidx.compose.ui.Modifier
            .height(5.dp))
        Row(
            modifier = androidx.compose.ui.Modifier
                .fillMaxWidth()
        ) {
            Text(text = "Post:")
            Spacer(modifier = androidx.compose.ui.Modifier
                .width(10.dp))
            Text(text = post.body)
        }
        Spacer(modifier = androidx.compose.ui.Modifier
            .height(8.dp))
        Divider(color = Color.Gray, thickness = 1.dp)
    }
}