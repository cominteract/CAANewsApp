package com.ainsigne.ui.domain

/**
 * Dialog data for data population within a dialog
 * @param titleData [String] title for the dialog
 * @param captionData [String] caption/description for the dialog
 * @param confirmationType [ConfirmationType] type of confirmation for distinction between icon used
 * @param acceptBlock [Unit] optional block for the accept button
 * @param cancelBlock [Unit] optional block for the cancel button
 */
data class DialogData(
    val titleData: String,
    val captionData: String,
    val confirmationType: ConfirmationType,
    val acceptBlock: (() -> Unit)? = null,
    val cancelBlock: (() -> Unit)? = null
)

/**
 * Type of confirmation for distinction between icon used
 */
enum class ConfirmationType {
    ERROR,
    SUCCESS,
    INFO
}
