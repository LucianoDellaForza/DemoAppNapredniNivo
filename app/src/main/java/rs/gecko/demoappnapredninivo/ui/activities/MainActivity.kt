package rs.gecko.demoappnapredninivo.ui.activities

import android.annotation.SuppressLint
import android.database.CursorWindow
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import rs.gecko.demoappnapredninivo.R
import rs.gecko.demoappnapredninivo.ui.fragments.CommentsFragment
import rs.gecko.demoappnapredninivo.ui.fragments.PhotosFragment
import rs.gecko.demoappnapredninivo.ui.fragments.PostsFragment
import rs.gecko.demoappnapredninivo.ui.fragments.UserPhotosFragment
import java.lang.reflect.Field

@AndroidEntryPoint
class MainActivity : AppCompatActivity(R.layout.activity_main) {

//    lateinit var postsFragment: PostsFragment
//    lateinit var commentsFragment: CommentsFragment
//    lateinit var photosFragment: PhotosFragment
//    lateinit var userPhotosFragment: UserPhotosFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        cursorWindowFix()
        initUi()
    }

    private fun initUi() {
//        postsFragment = PostsFragment()
//        commentsFragment = CommentsFragment()
//        photosFragment = PhotosFragment()
//        userPhotosFragment = UserPhotosFragment()
//        val myViewPagerAdapter = MyViewPagerAdapter(supportFragmentManager)
//        myViewPagerAdapter.apply {
//            addFragment(postsFragment, "Posts")
//            addFragment(commentsFragment, "Comments")
//            addFragment(photosFragment, "Photos")
//            addFragment(userPhotosFragment, "My photos")
//        }
//        view_pager.adapter = myViewPagerAdapter
//
//        tabLay.setupWithViewPager(view_pager)

        bottomNavigationView.setupWithNavController(myNavHostFragment.findNavController())
    }

//    @SuppressLint("WrongConstant")
//    inner class MyViewPagerAdapter(private val supportFragmentManager: FragmentManager) : FragmentPagerAdapter(supportFragmentManager, 0) {
//
//        val fragmentList: MutableList<Fragment> = mutableListOf()
//        val fragmentTitles: MutableList<String> = mutableListOf()
//
//        override fun getCount(): Int {
//            return fragmentList.size
//        }
//
//        override fun getItem(position: Int): Fragment {
//            return fragmentList[position]
//        }
//
//        override fun getPageTitle(position: Int): CharSequence? {
//            return fragmentTitles[position]
//        }
//
//        fun addFragment(fragment: Fragment, title: String) {
//            fragmentList.add(fragment)
//            fragmentTitles.add(title)
//        }
//    }

    private fun cursorWindowFix() {
        try {
            val field: Field = CursorWindow::class.java.getDeclaredField("sCursorWindowSize")
            field.isAccessible = true
            field.set(null, 100 * 1024 * 1024) //the 100MB is the new size
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}