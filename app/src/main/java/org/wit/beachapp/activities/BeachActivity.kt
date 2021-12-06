package org.wit.beachapp.activities

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import org.wit.beachapp.main.MainApp
import org.wit.beachapp.models.BeachModel
import org.wit.beachapp.R
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.squareup.picasso.Picasso
import com.squareup.picasso.MemoryPolicy
import org.wit.beachapp.databinding.ActivityBeachBinding
import org.wit.beachapp.helpers.showImagePicker
import org.wit.beachapp.models.Location
import timber.log.Timber
import timber.log.Timber.i
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.Marker



class BeachActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBeachBinding
    var beach = BeachModel()
    lateinit var app: MainApp
    private lateinit var imageIntentLauncher : ActivityResultLauncher<Intent>
    private lateinit var mapIntentLauncher : ActivityResultLauncher<Intent>
    //var location = Location(52.245696, -7.139102, 15f)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var edit = false
        binding = ActivityBeachBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbarAdd.title = title
        setSupportActionBar(binding.toolbarAdd)
        app = application as MainApp

        if (intent.hasExtra("beach_edit")) {
            edit = true
            beach = intent.extras?.getParcelable("beach_edit")!!
            binding.beachTitle.setText(beach.title)
            binding.description.setText(beach.description)
            binding.btnAdd.setText(R.string.save_beach)
            Picasso.get()
                .load(beach.image)
                .into(binding.beachImage)
            if (beach.image != Uri.EMPTY) {
                binding.chooseImage.setText(R.string.change_beach_image)
            }
        }


        binding.btnAdd.setOnClickListener() {
            beach.title = binding.beachTitle.text.toString()
            beach.description = binding.description.text.toString()
            if (beach.title.isEmpty()) {
                Snackbar.make(it,R.string.enter_beach_title, Snackbar.LENGTH_LONG)
                    .show()
            } else {
                if (edit) {
                    app.beaches.update(beach.copy())
                } else {
                    app.beaches.create(beach.copy())
                }
            }
            setResult(RESULT_OK)
            finish()
        }

        binding.chooseImage.setOnClickListener {
            showImagePicker(imageIntentLauncher)
        }
        binding.beachLocation.setOnClickListener {
            val location = Location(52.245696, -7.139102, 15f)
            if (beach.zoom != 0f) {
                location.lat =  beach.lat
                location.lng = beach.lng
                location.zoom = beach.zoom
            }
            val launcherIntent = Intent(this, MapActivity::class.java)
                .putExtra("location", location)
            mapIntentLauncher.launch(launcherIntent)
        }

        registerImagePickerCallback()
        registerMapCallback()
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

    private fun registerImagePickerCallback() {
        imageIntentLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { result ->
                when(result.resultCode){
                    RESULT_OK -> {
                        if (result.data != null) {
                            i("Got Result ${result.data!!.data}")
                            beach.image = result.data!!.data!!
                            Picasso.get()
                                .load(beach.image)
                                .into(binding.beachImage)
                            binding.chooseImage.setText(R.string.change_beach_image)
                        } // end of if
                    }
                    RESULT_CANCELED -> { } else -> { }
                }
            }
    }
    private fun registerMapCallback() {
        mapIntentLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { result ->
                when (result.resultCode) {
                    RESULT_OK -> {
                        if (result.data != null) {
                            i("Got Location ${result.data.toString()}")
                            val location = result.data!!.extras?.getParcelable<Location>("location")!!
                            i("Location == $location")
                            beach.lat = location.lat
                            beach.lng = location.lng
                            beach.zoom = location.zoom
                        } // end of if
                    }
                    RESULT_CANCELED -> { } else -> { }
                }
            }
    }
}