package com.tty.desh.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.tty.desh.R
import com.tty.desh.adapter.SimpleFragmentPagerAdapter
import com.tty.desh.ui.main.MainFragment
import kotlinx.android.synthetic.main.main_activity.*

/**
 * @author cht-ZJUT
 * 主活动
 */
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        val pages:ArrayList<Fragment> = arrayListOf(LeftFragment(), MainFragment())
        val adapter = SimpleFragmentPagerAdapter(supportFragmentManager,pages)
        main_content.adapter = adapter
        main_content.currentItem = 1

        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }
}
