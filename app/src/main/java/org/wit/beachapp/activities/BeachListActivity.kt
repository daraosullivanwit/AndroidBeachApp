package org.wit.beachapp.activities

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import org.wit.beachapp.R
import org.wit.beachapp.adapters.BeachAdapter
import org.wit.beachapp.adapters.BeachListener
import org.wit.beachapp.databinding.ActivityBeachListBinding
import org.wit.beachapp.main.MainApp
import org.wit.beachapp.models.BeachModel

class BeachListActivity : AppCompatActivity(), BeachListener {

    lateinit var app: MainApp
    private lateinit var binding: ActivityBeachListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBeachListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbar.title = title
        setSupportActionBar(binding.toolbar)
        app = application as MainApp

        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = BeachAdapter(app.beaches.findAll(),this)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_add -> {
                val launcherIntent = Intent(this, BeachActivity::class.java)
                startActivityForResult(launcherIntent,0)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onPlacemarkClick(beach: BeachModel) {
        val launcherIntent = Intent(this, BeachActivity::class.java)
        launcherIntent.putExtra("beach_edit", beach)
        startActivityForResult(launcherIntent,0)
    }
}