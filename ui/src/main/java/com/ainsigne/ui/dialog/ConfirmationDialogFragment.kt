package com.ainsigne.ui.dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.ainsigne.ui.R
import com.ainsigne.ui.domain.ConfirmationType
import com.ainsigne.ui.domain.DialogData

class ConfirmationDialogFragment(private var confirmationData: DialogData) : DialogFragment() {

    fun updateDialogData(confirmationData: DialogData) {
        this.confirmationData = confirmationData
    }

    fun getDialogData() = confirmationData

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val rootView: View = inflater.inflate(R.layout.fragment_confirmation_dialog, null)

        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        rootView.findViewById<TextView>(R.id.text_confirmation_title).text = confirmationData.titleData
        rootView.findViewById<TextView>(R.id.text_confirmation_caption).text = confirmationData.captionData
        val image = rootView.findViewById<ImageView>(R.id.image_confirmation)
        when (confirmationData.confirmationType) {
            ConfirmationType.SUCCESS -> {
                image.setImageResource(com.google.android.material.R.drawable.ic_mtrl_chip_checked_circle)
            }
            ConfirmationType.INFO -> {
                image.setImageResource(com.google.android.material.R.drawable.mtrl_ic_error)
            }
            ConfirmationType.ERROR -> {
                image.setImageResource(com.google.android.material.R.drawable.ic_mtrl_chip_close_circle)
            }
        }

        rootView.findViewById<Button>(R.id.button_dismiss).setOnClickListener {
            getDialogData().acceptBlock?.invoke()
            dismiss()
        }
        return rootView
    }

    override fun onResume() {
        super.onResume()
        dialog?.window?.setLayout(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
    }
}
