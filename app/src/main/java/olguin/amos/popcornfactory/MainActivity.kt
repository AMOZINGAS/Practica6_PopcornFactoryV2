package olguin.amos.popcornfactory

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)


        var button: Button = findViewById(R.id.buttonInit)

        button.setOnClickListener{

            var intent: Intent = Intent(this, CatalogoActivity::class.java)
            this.startActivity(intent)

        }



    }
}