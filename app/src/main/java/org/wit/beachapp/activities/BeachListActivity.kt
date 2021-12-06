package org.wit.beachapp.activities

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
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
    private lateinit var refreshIntentLauncher : ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBeachListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbar.title = title
        setSupportActionBar(binding.toolbar)
        app = application as MainApp

        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager
        //binding.recyclerView.adapter = BeachAdapter(app.beaches.findAll(),this)
        loadBeaches()

        registerRefreshCallback()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_add -> {
                val launcherIntent = Intent(this, BeachActivity::class.java)
                refreshIntentLauncher.launch(launcherIntent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBeachClick(beach: BeachModel) {
        val launcherIntent = Intent(this, BeachActivity::class.java)
        launcherIntent.putExtra("beach_edit", beach)
        refreshIntentLauncher.launch(launcherIntent)
    }

    private fun registerRefreshCallback() {
        refreshIntentLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { loadBeaches() }
    }

    private fun loadBeaches() {
        showBeaches(app.beaches.findAll())
    }

    fun showBeaches (beaches: List<BeachModel>) {
        binding.recyclerView.adapter = BeachAdapter(beaches, this)
        binding.recyclerView.adapter?.notifyDataSetChanged()
    }
}