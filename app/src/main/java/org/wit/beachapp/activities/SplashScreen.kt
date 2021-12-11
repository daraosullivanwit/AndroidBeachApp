package org.wit.beachapp.activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import org.wit.beachapp.R

@Suppress("DEPRECATION")
class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

<<<<<<< HEAD
        // Hide the status bar and make splash screen as a full screen activity.
=======
        // This is used to hide the status bar and make
        // the splash screen as a full screen activity.
>>>>>>> 4d18cdd096e7f1090301f0536af4e72413ced121
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

<<<<<<< HEAD
        // Send a message with a delayed time.
=======
        // we used the postDelayed(Runnable, time) method
        // to send a message with a delayed time.
>>>>>>> 4d18cdd096e7f1090301f0536af4e72413ced121
        Handler().postDelayed({
            val intent = Intent(this, BeachListActivity::class.java)
            startActivity(intent)
            finish()
<<<<<<< HEAD
        }, 2000) // 3000 is the delayed time in milliseconds.
=======
        }, 3000) // 3000 is the delayed time in milliseconds.
>>>>>>> 4d18cdd096e7f1090301f0536af4e72413ced121
    }
}