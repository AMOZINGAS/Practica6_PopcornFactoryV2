package olguin.amos.popcornfactory

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.collection.emptyIntList
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class SeatSelection : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_seat_selection)

        val row1: RadioGroup = findViewById(R.id.row1)
        val row2: RadioGroup = findViewById(R.id.row2)
        val row3: RadioGroup = findViewById(R.id.row3)
        val row4: RadioGroup = findViewById(R.id.row4)

        val payMethod: Spinner = findViewById(R.id.payMethod)

        val metodosPago = listOf("Tarjeta de débito", "Efectivo", "Tarjeta de crédito")

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, metodosPago)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        payMethod.adapter = adapter



        val nombre: EditText = findViewById(R.id.etNombre)
        val title: TextView = findViewById(R.id.titleseats)

        var posMovie = -1

        val ocupados = intent.getIntegerArrayListExtra("ocupados")?: arrayListOf()
        val seat = intent.getIntExtra("id", 0)
        val previosPayMethod = intent.getStringExtra("payMethod")
        val previousName = intent.getStringExtra("clientName")
        val previousTitle = intent.getStringExtra("name")

        desactivarAsientos(listOf(row1, row2, row3, row4), ocupados)

        previousName?.let { nombre.setText(it) }
        previousTitle?.let { title.text = it }

        if(seat != 0){

            val selectedSeat = seat.toString()
            selectedSeat?.let { seat ->
                val marcado = seleccionarRadioButtonPorTexto(row1, seat) ||
                        seleccionarRadioButtonPorTexto(row2, seat) ||
                        seleccionarRadioButtonPorTexto(row3, seat) ||
                        seleccionarRadioButtonPorTexto(row4, seat)

                if (!marcado) {
                    Toast.makeText(this, "Asiento previamente seleccionado no disponible", Toast.LENGTH_SHORT).show()
                }
            }
            previosPayMethod?.let { metodo ->
                val index = metodosPago.indexOf(metodo)
                if (index != -1) {
                    payMethod.setSelection(index)
                }
            }

        }

        val bundle = intent.extras
        if (bundle != null) {
            title.text = bundle.getString("name", "")
            posMovie = bundle.getInt("id", -1)
        }

        val confirm: Button = findViewById(R.id.confirm)

        confirm.setOnClickListener {

            if (nombre.text.toString().isBlank()) {
                Toast.makeText(this, "Please insert the owner's name", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            val selectedSeatId = when {
                row1.checkedRadioButtonId != -1 -> row1.checkedRadioButtonId
                row2.checkedRadioButtonId != -1 -> row2.checkedRadioButtonId
                row3.checkedRadioButtonId != -1 -> row3.checkedRadioButtonId
                row4.checkedRadioButtonId != -1 -> row4.checkedRadioButtonId
                else -> -1
            }

            if (selectedSeatId == -1) {
                Toast.makeText(this, "Please select a seat", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            val selectedSeatButton = findViewById<android.widget.RadioButton>(selectedSeatId)
            val seatText = selectedSeatButton?.text.toString()

            val metodoSeleccionado = payMethod.selectedItem.toString()

            val intento = Intent(this, BuyTicket::class.java)
            intento.putExtra("payMethod", metodoSeleccionado)
            intento.putExtra("clientName", nombre.text.toString())
            intento.putExtra("name", title.text.toString())
            intento.putExtra("seat", seatText.toInt())

            startActivity(intento)
            Toast.makeText(this, "Enjoy the movie :D", Toast.LENGTH_LONG).show()
        }

        row1.setOnCheckedChangeListener { group, checkId ->
            if (checkId > -1) {
                row2.clearCheck()
                row3.clearCheck()
                row4.clearCheck()

                row1.check(checkId)


            }
        }

        row2.setOnCheckedChangeListener { group, checkId ->
            if (checkId > -1) {
                row1.clearCheck()
                row3.clearCheck()
                row4.clearCheck()

                row2.check(checkId)

            }
        }

        row3.setOnCheckedChangeListener { group, checkId ->
            if (checkId > -1) {
                row1.clearCheck()
                row2.clearCheck()
                row4.clearCheck()

                row3.check(checkId)

            }
        }

        row4.setOnCheckedChangeListener { group, checkId ->
            if (checkId > -1) {
                row1.clearCheck()
                row2.clearCheck()
                row3.clearCheck()
            }
        }
    }

    fun seleccionarRadioButtonPorTexto(grupo: RadioGroup, texto: String?): Boolean {
        for (i in 0 until grupo.childCount) {
            val radioButton = grupo.getChildAt(i) as? android.widget.RadioButton
            radioButton?.let {
                if (it.text.toString() == texto) {
                    it.isChecked = true
                    return true
                }
            }
        }
        return false
    }

    fun desactivarAsientos(grupos: List<RadioGroup>, asientosOcupados: List<Int>) {
        for (grupo in grupos) {
            for (i in 0 until grupo.childCount) {
                val radioButton = grupo.getChildAt(i) as? RadioButton
                radioButton?.let {
                    val textoAsiento = it.text.toString().toIntOrNull()
                    if (textoAsiento != null && textoAsiento in asientosOcupados) {
                        it.isEnabled = false
                    }
                }
            }
        }
    }



}
