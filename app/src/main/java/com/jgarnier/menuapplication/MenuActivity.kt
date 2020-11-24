package com.jgarnier.menuapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MenuActivity : AppCompatActivity() {

    private var lastNavController: NavController? = null

    private var currentNavController: LiveData<NavController>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.menu_activity)

        if (savedInstanceState == null) {
            setupBottomNavigationBar()
        }
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        setupBottomNavigationBar()
    }

    /**
     * Set up the bottom navigation bar with a multitude of navigation graphs. For more explanation,
     * see https://github.com/android/architecture-components-samples/blob/master/NavigationAdvancedSample/app/src/main/java/com/example/android/navigationadvancedsample/MainActivity.kt
     */
    private fun setupBottomNavigationBar() {
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)

        val navGraphIds = listOf(R.navigation.planning, R.navigation.menu, R.navigation.shopping_list)

        // Setup the bottom navigation view with a list of navigation graphs
        val controller = bottomNavigationView.setupWithNavController(
                navGraphIds = navGraphIds,
                fragmentManager = supportFragmentManager,
                containerId = R.id.nav_host_fragment,
                intent = intent
        )

        val listener = bottomNavigationAnimationOnDestinationChanged()
        controller.observe(this, Observer {
            it.addOnDestinationChangedListener(listener)
            lastNavController?.apply {
                removeOnDestinationChangedListener(listener)
            }
            lastNavController = it
        })

        currentNavController = controller
    }

    override fun onSupportNavigateUp(): Boolean {
        return currentNavController?.value?.navigateUp() ?: false
    }

    private fun bottomNavigationAnimationOnDestinationChanged() =
            NavController.OnDestinationChangedListener { _, destination, _ ->
                run {
                    val motionLayout = findViewById<MotionLayout>(R.id.main_container)
                    when (destination.id) {
                        R.id.planningFragment,
                        R.id.mealDialogFragment,
                        R.id.menuFragment,
                        R.id.shoppingListFragment -> motionLayout.transitionToStart()
                        else -> motionLayout.transitionToEnd()
                    }
                }
            }
}