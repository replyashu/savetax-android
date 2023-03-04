package com.ashu.savemytax.ui.dialogs

import android.app.Dialog
import android.content.Context
import android.text.SpannableStringBuilder
import android.view.MotionEvent
import android.view.Window
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatTextView
import com.ashu.savemytax.R
import com.ashu.savemytax.data.SalaryResponse
import javax.inject.Inject


class HRADialog @Inject constructor(
    context: Context, private val hra: SalaryResponse?, private val base: Double?,
        private val currency: String?):
    Dialog(context, R.style.AppTheme_FullScreenDialog) {

   init {
       this.window?.setBackgroundDrawableResource(android.R.color.transparent)
       window?.requestFeature(Window.FEATURE_NO_TITLE)
       setContentView(R.layout.dialog_hra)
       val hraGiven = findViewById<AppCompatTextView>(R.id.text_hra_given)
       val hra10Less = findViewById<AppCompatTextView>(R.id.text_hra_10_less)
       val hraMaxClaim = findViewById<AppCompatTextView>(R.id.text_hra_max_claim)
       val hra50Less = findViewById<AppCompatTextView>(R.id.text_hra_50_less)
       val editHraDetails = findViewById<AppCompatEditText>(R.id.edit_hra_details)

       hraGiven.text = "HRA Given: \t " + currency + hra?.componentAmount.toString()
       currency + (hra?.componentAmount!! + (base!! * 10 / 100)).toString().also { hra10Less.text = "HRA Ideally to be claimed: \t" +it }
       hra50Less.text = "50% of basic \t" + currency + (base!! / 2.0).toString()
       hraMaxClaim.text = "Max HRA that can be claimed: \t" + currency + hra.componentAmount.toString()

       var initial = "Paid amount "
       val creditor = "as rent to Mr"
       val monthName = "for the month of"
       var address = "towards property rent situated at"
       var tenure = "for the month of"
       var dated = "Dated"
       var sign = "Singnature"
       var pan = "Pan"

       val spannableStringBuilder = SpannableStringBuilder()

       var  initialStringBuilder: SpannableStringBuilder =  SpannableStringBuilder(initial)
       var  creditorStringBuilder = SpannableStringBuilder(creditor)
       var  monthStringBuilder = SpannableStringBuilder(monthName)
       var  addressrStringBuilder = SpannableStringBuilder(address)
       var  tenureStringBuilder = SpannableStringBuilder(tenure)
       var  datedStringBuilder = SpannableStringBuilder(dated)
       var  signStringBuilder = SpannableStringBuilder(sign)
       var  panStringBuilder = SpannableStringBuilder(pan)







       // Fill in the blanks for hra

       show()
    }


    override fun onTouchEvent(event: MotionEvent): Boolean {
        if(event.action == MotionEvent.ACTION_OUTSIDE){
            dismiss();
        }
        return false;
    }

}