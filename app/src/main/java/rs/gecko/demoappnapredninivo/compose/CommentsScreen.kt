package rs.gecko.demoappnapredninivo.compose

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Divider
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltNavGraphViewModel
import rs.gecko.demoappnapredninivo.ui.models.Comment
import rs.gecko.demoappnapredninivo.ui.models.Post
import rs.gecko.demoappnapredninivo.ui.viewmodels.CommentViewModel
import rs.gecko.demoappnapredninivo.ui.viewmodels.PostViewModel

@Composable
fun CommentsScreen (
//    viewModel: PostViewModel = hiltNavGraphViewModel()
) {
    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        CommentList()
    }

}

@Composable
fun CommentList(
    viewModel: CommentViewModel = hiltNavGraphViewModel()
) {
    val comments by viewModel.comments.observeAsState()

    LazyColumn(
        contentPadding = PaddingValues(16.dp)
    ) {
        comments?.data?.let {
            val itemCount = comments!!.data!!.size
            items(count = itemCount) {
                CommentEntry(
                    modifier = Modifier,
                    comment = comments!!.data!!.get(it))
                Spacer(modifier = Modifier
                    .height(8.dp))
            }
        }

    }
}

@Composable
fun CommentEntry(
    modifier: Modifier = Modifier,
    comment: Comment
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        Row {
            Text(text = "Post title:")
            Spacer(modifier = Modifier
                .width(10.dp))
            Text(text = comment.postId.toString())
        }
        Spacer(modifier = Modifier
            .height(5.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(text = "Comment:")
            Spacer(modifier = Modifier
                .width(10.dp))
            Text(text = comment.body)
        }
        Spacer(modifier = Modifier
            .height(8.dp))
        Divider(color = Color.Gray, thickness = 1.dp)
    }
}