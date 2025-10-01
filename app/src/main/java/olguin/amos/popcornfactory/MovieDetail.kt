package olguin.amos.popcornfactory

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MovieDetail : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_movie_detail)


        var id = 0

        val ocupados = intent.getIntegerArrayListExtra("ocupados")
        val titulo = intent.getStringExtra("titulo")
        val sinopsis = intent.getStringExtra("sinopsis")
        val header = intent.getIntExtra("header", 0)
        val setaNum = intent.getIntExtra("numeroSeats", 0)
        id = intent.getIntExtra("id", 0)

        val imageHeader: ImageView = findViewById(R.id.imageHeader)
        val tvSinopsis: TextView = findViewById(R.id.sinopsis)
        val tvTitle: TextView = findViewById(R.id.title)
        val seats: TextView = findViewById(R.id.seatsleft)
        val buttonBuy: Button = findViewById(R.id.bytTicket)



        imageHeader.setImageResource(header)
        tvSinopsis.setText(sinopsis)
        tvTitle.setText(titulo)
        seats.setText("$setaNum seats disponibles")

        if(setaNum==0){
            buttonBuy.isEnabled = false
        }else {

            buttonBuy.setOnClickListener{

                val intento: Intent = Intent(this, SeatSelection::class.java)

                intento.putExtra("name", tvTitle.text.toString())
                intento.putIntegerArrayListExtra("ocupados", ocupados)

                startActivity(intento)


            }

        }


    }
}