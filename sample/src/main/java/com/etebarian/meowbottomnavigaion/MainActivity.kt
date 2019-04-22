package com.etebarian.meowbottomnavigaion

import android.graphics.Typeface
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.etebarian.navigation.Model
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    companion object {
        private const val ID_HOME = 1
        private const val ID_EXPLORE = 2
        private const val ID_MESSAGE = 3
        private const val ID_NOTIFICATION = 4
        private const val ID_ACCOUNT = 5
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tv_selected.setTypeface(Typeface.createFromAsset(assets, "fonts/SourceSansPro-Regular.ttf"));

        bottomNavigation.add(Model(ID_HOME, R.drawable.ic_home))
        bottomNavigation.add(Model(ID_EXPLORE, R.drawable.ic_explore))
        bottomNavigation.add(Model(ID_MESSAGE, R.drawable.ic_message))
        bottomNavigation.add(Model(ID_NOTIFICATION, R.drawable.ic_notification))
        bottomNavigation.add(Model(ID_ACCOUNT, R.drawable.ic_account))

        /*设置下角标*/
        bottomNavigation.setCount(ID_NOTIFICATION, "3")

        bottomNavigation.setOnShowListener {
            val name = when (it.id) {
                ID_HOME -> "HOME"
                ID_EXPLORE -> "EXPLORE"
                ID_MESSAGE -> "MESSAGE"
                ID_NOTIFICATION -> "NOTIFICATION"
                ID_ACCOUNT -> "ACCOUNT"
                else -> ""
            }
            tv_selected.text = "$name page is selected"
        }

        bottomNavigation.setOnClickMenuListener {
            val name = when (it.id) {
                ID_HOME -> "HOME"
                ID_EXPLORE -> "EXPLORE"
                ID_MESSAGE -> "MESSAGE"
                ID_NOTIFICATION -> "NOTIFICATION"
                ID_ACCOUNT -> "ACCOUNT"
                else -> ""
            }
            Toast.makeText(this, "$name is clicked", Toast.LENGTH_SHORT).show()
        }
    }
}
