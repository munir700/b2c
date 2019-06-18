package co.yap.widgets

import android.content.Context
import android.util.AttributeSet
import android.R
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog



class CustomWidgetEditText(context: Context?, attrs: AttributeSet?) : EditText(context, attrs) {


    init {

        // Inflate the custom widget layout xml file.
        //val layoutInflater = LayoutInflater.from(context)
        val layoutInflater:LayoutInflater= LayoutInflater.from(getContext())
           //   val view: View =layoutInflater.inflate(R.layout.custok, null, false);

        //will define button
     //   val nextButton2=findViewById<Button>(R.id.etEmail)
      /*  nextButton2.setOnClickListener {
            val alertDialog = AlertDialog.Builder(getContext()).create()
            alertDialog.setMessage("You click Next button")
            alertDialog.show()
        }*/
    }

}