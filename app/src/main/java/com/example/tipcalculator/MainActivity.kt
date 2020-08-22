package com.example.tipcalculator

import android.app.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.InputFilter
import android.text.Spanned
import android.text.TextWatcher
import android.util.Log
import android.view.Gravity

import android.widget.SeekBar
import kotlinx.android.synthetic.main.activity_main.*

import android.view.View
import android.view.inputmethod.InputMethodManager
import android.view.ViewGroup

import android.widget.EditText
import android.widget.Toast
import java.util.regex.Pattern


class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //appliying hide function to the activity
        hideSetUp(findViewById(R.id.reset))
        //Reset function functionalities are defined below which resets all the values
        button.setOnClickListener {
            seekBar.progress = 15
            seekBar2.progress = 1
            per.text = "15%"
            num.text = "1"
            tip.text = "0.0"
            total.text = "0.0"
            per_person.text = "0.0"
            bill_entry.setText("")
        }
        // Default values of Tip, party size, Bill is set below
        seekBar.progress = 15
        seekBar2.progress = 1
        per.text = "15%"
        num.text = "1"
        bill_entry.setText("0")
        //function which listens the changes in the tip seekbar
        Log.i("name","message")
        seekBar.setOnSeekBarChangeListener(object:SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                per.text = "$progress%"
                calculate()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {

            }
        })
        //function which listens the changes in the part size seekbar
        seekBar2.setOnSeekBarChangeListener(object:SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                num.text ="$progress"
                calculate()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }
        })
        //function which listens the changes in the number(decimal) text box

        bill_entry.addTextChangedListener(object :TextWatcher{
            override fun afterTextChanged(s: Editable?) {
                calculate()
                //Condition which allows users to enter upto two decimals
                if("$s".contains(".") && "$s".substring("$s".indexOf(".")+1).length>2){
                    val myToast = Toast.makeText(applicationContext,"Maximum two decimal points only",Toast.LENGTH_SHORT)
                    bill_entry.setText("$s".substring(0,"$s".length-1))
                    bill_entry.setSelection(bill_entry.getText().length)
                    myToast.setGravity(Gravity.LEFT,200,200)
                    myToast.show()
                }
                if(bill_entry.text.toString().equals("")){
                    bill_entry.setError("Enter any positive number")
                }


            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {


            }
        })

    }
    //Function which calculates the values of tip, total bill, bill per person
    fun calculate(){
        if(bill_entry.text.toString().isEmpty()){
            tip.text = "0.0"
            per_person.text = "0.0"
            total.text = "0.0"
            return
        }
        val amoun = bill_entry.text.toString().toDouble()
        val perc = seekBar.progress
        val pers = seekBar2.progress
        val tip_amoun = amoun * perc /100
        val total_amoun = amoun + tip_amoun
        val total_per = total_amoun/ pers
        tip.text = "%.2f".format(tip_amoun)
        total.text = "%.2f".format(total_amoun)
        per_person.text = "%.2f".format(total_per)
    }
    //Function which hides the keyboard
    private fun hideKeyboard(activity:Activity){
        val inputMethodManager:InputMethodManager = activity.getSystemService(
            Activity.INPUT_METHOD_SERVICE
        ) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(
            activity.currentFocus!!.windowToken,0
        )
    }
    //Function to hides keyboard outside EditText
    private fun hideSetUp(view: View){
        if (view !is EditText) {
            view.setOnTouchListener { _:View, _ ->
                hideKeyboard(this)
                false
            }
        }

        //It iterates over the childs in the activity.
        if (view is ViewGroup) {
            for (i in 0 until view.childCount) {
                val innerView = view.getChildAt(i)
                hideSetUp(innerView)
            }
        }
    }



}

