package olguin.amos.popcornfactory

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import org.w3c.dom.Text

class BuyTicket : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_buy_ticket)

        val clientName: TextView = findViewById(R.id.clientName)
        val movieName: TextView = findViewById(R.id.movieName)
        val seatNumber: TextView = findViewById(R.id.seatNumber)
        val payMethod: TextView = findViewById(R.id.payMethod)

        val buttonConfirm: Button = findViewById(R.id.buttonConfirm)
        val buttonEdit: Button = findViewById(R.id.buttonEdit)

        payMethod.setText(intent.getStringExtra("payMethod"))
        clientName.setText(intent.getStringExtra("clientName"))
        movieName.setText(intent.getStringExtra("name"))
        seatNumber.setText(intent.getIntExtra("seat", 0).toString())

        buttonConfirm.setOnClickListener{

            val intento: Intent = Intent(this, CatalogoActivity::class.java)
            intento.putExtra("payMethod", payMethod.text.toString())
            intento.putExtra("movieName", movieName.text.toString())
            intento.putExtra("clientName", clientName.text.toString())
            intento.putExtra("seat", seatNumber.text.toString().toInt())
            startActivity(intento)

        }

        buttonEdit.setOnClickListener{

            val intento: Intent = Intent(this, SeatSelection::class.java)
            intento.putExtra("payMethod", payMethod.text.toString())
            intento.putExtra("clientName",clientName.text.toString())
            intento.putExtra("name", movieName.text.toString())
            intento.putExtra("id", seatNumber.text.toString().toInt())
            startActivity(intento)

        }

    }
}