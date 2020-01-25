package com.example.tipcalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.*
import kotlinx.android.synthetic.main.activity_main.*

const val HUNDRED_PERCENT = 100.00

class MainActivity : AppCompatActivity() {
    lateinit var slider : SeekBar
    lateinit var value : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        slider = findViewById(R.id.peopleSlider) as SeekBar
        value = findViewById(R.id.textPeopleValue) as TextView

        val tipSpinner = findViewById<Spinner>(R.id.tipDropDown)
        val tipList = resources.getStringArray(R.array.tip_selection)
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, tipList)
        var tipSelected = ""

        fun calculateExpense() {
            val totalAmountDue : Double = if(editBillInput.text.isNotEmpty()) editBillInput.text.toString().toDouble() else 24.98
            val percentToTip = if(tipSelected.isNotEmpty()) tipSelected.toInt() else 5
            val peopleCount = if(value.text.isNotEmpty()) value.text.toString().toInt() else 1

            val amtToTip : Double = totalAmountDue * (percentToTip / HUNDRED_PERCENT)
            val totalExpense  = amtToTip + totalAmountDue
            val perPersonTotal = totalExpense / peopleCount

            textTipValue.text = String.format("$%.2f", amtToTip)
            textBillValue.text = String.format("$%.2f", totalExpense)
            textPerPersonValue.text = String.format("$%.2f", perPersonTotal)
        }

        editBillInput.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                calculateExpense()
            }

        })

        slider.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                value.text = (progress + 1).toString()
                calculateExpense()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }
        })

        tipSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {
            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
                tipSelected = tipList[position]
                calculateExpense()
            }

        }

        tipSpinner.adapter = adapter
    }
}
