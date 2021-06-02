package rs.gecko.demoappnapredninivo.compose

import androidx.annotation.StringRes
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import rs.gecko.demoappnapredninivo.R

sealed class BottomNavigationScreens(val route: String, @StringRes val resourceId: Int, val iconDrawableId: Int) {
    object PostsNavItem : BottomNavigationScreens("Posts", R.string.posts_route, R.drawable.ic_baseline_assignment_24)
    object CommentsNavItem : BottomNavigationScreens("Comments", R.string.comments_route, R.drawable.ic_baseline_comment_24)
    object PhotosNavItem : BottomNavigationScreens("Photos", R.string.photos_route, R.drawable.ic_baseline_image_24)
    object UserPhotosNavItem : BottomNavigationScreens("UserPhotos", R.string.user_photos_route, R.drawable.ic_baseline_monochrome_photos_24)
}

@Composable
fun MainScreen() {

    val navController = rememberNavController()

    val bottomNavigationItems = listOf(
        BottomNavigationScreens.PostsNavItem,
        BottomNavigationScreens.CommentsNavItem,
        BottomNavigationScreens.PhotosNavItem,
        BottomNavigationScreens.UserPhotosNavItem
    )
    
    Scaffold(
        bottomBar = {
            MyBottomNavigation(navController = navController, items = bottomNavigationItems)
        }
    ) {
        MainScreenNavigationConfigurations(navController = navController)
    }
}

@Composable
private fun MainScreenNavigationConfigurations(
    navController: NavHostController
) {
    NavHost(navController, startDestination = BottomNavigationScreens.PostsNavItem.route) {
        composable(BottomNavigationScreens.PostsNavItem.route) {
            PostsScreen()
        }
        composable(BottomNavigationScreens.CommentsNavItem.route) {
            CommentsScreen()
        }
        composable(BottomNavigationScreens.PhotosNavItem.route) {
            PhotosScreen()
        }
        composable(BottomNavigationScreens.UserPhotosNavItem.route) {
            UserPhotosScreen()
        }
    }
}

@Composable
private fun MyBottomNavigation(
    navController: NavHostController,
    items: List<BottomNavigationScreens>
) {
    BottomNavigation() {
        val currentRoute = CurrentRoute(navController = navController)
        items.forEach { screen ->
            BottomNavigationItem(
                icon = { Icon(painter = painterResource(id = screen.iconDrawableId), contentDescription = "")},
                label = { Text(stringResource(id = screen.resourceId))},
                selected = currentRoute == screen.route,
                //alwaysShowLabel = false, // This hides the title for the unselected items
                onClick = {
                    // This if check gives us a "singleTop" behavior where we do not create a
                    // second instance of the composable if we are already on that destination
                    if (currentRoute != screen.route) {
                        navController.navigate(screen.route)
                    }
                }
            )
        }
    }
}

@Composable
private fun CurrentRoute(navController: NavHostController): String? {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    return navBackStackEntry?.arguments?.getString(KEY_ROUTE)
}