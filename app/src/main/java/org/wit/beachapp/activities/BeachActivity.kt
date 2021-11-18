package org.wit.beachapp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import org.wit.beachapp.main.MainApp
import org.wit.beachapp.models.BeachModel
import org.wit.beachapp.R
import android.view.Menu
import android.view.MenuItem
import org.wit.beachapp.databinding.ActivityBeachBinding


class BeachActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBeachBinding
    var beach = BeachModel()
    lateinit var app: MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBeachBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbarAdd.title = title
        setSupportActionBar(binding.toolbarAdd)
        app = application as MainApp

        if (intent.hasExtra("beach_edit")) {
            beach = intent.extras?.getParcelable("beach_edit")!!
            binding.beachTitle.setText(beach.title)
            binding.description.setText(beach.description)
        }

        binding.btnAdd.setOnClickListener() {
            beach.title = binding.beachTitle.text.toString()
            beach.description = binding.description.text.toString()
            if (beach.title.isNotEmpty()) {
                app.beaches.create(beach.copy())
                setResult(RESULT_OK)
                finish()
            }
            else {
                Snackbar.make(it,"Please Enter a title", Snackbar.LENGTH_LONG)
                        .show()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_beach, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_cancel -> { finish() }
        }
        return super.onOptionsItemSelected(item)
    }
}