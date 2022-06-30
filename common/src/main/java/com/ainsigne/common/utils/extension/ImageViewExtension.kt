package com.ainsigne.common.utils.extension

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.renderscript.Allocation
import android.renderscript.Element.U8_4
import android.renderscript.RenderScript
import android.renderscript.ScriptIntrinsicBlur
import android.widget.ImageView
import androidx.annotation.ColorRes
import androidx.annotation.DimenRes
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import com.ainsigne.common.R
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import kotlin.math.roundToInt

/**
 * Rounded image extension
 */
fun ImageView.toRoundImage(
    url: String?,
    @DrawableRes placeholder: Int = com.ainsigne.ui.R.drawable.image_empty,
    @DrawableRes error: Int = com.ainsigne.ui.R.drawable.image_empty,
) {
    val requestOptions = RequestOptions()
        .circleCrop()
        .placeholder(ContextCompat.getDrawable(this.context, placeholder))
        .error(ContextCompat.getDrawable(this.context, error))

    Glide.with(this.context)
        .load(url)
        .apply(requestOptions)
        .into(this)
}

/**
 * Extension to Enable/Disable control with color fade toggle
 * @param disabled [Int] input disabled color resource
 * @param enabled [Int] input enabled color resource
 * @param isEnabled [Boolean] input toggle for enabling/disabling imageview
 */
fun ImageView.enableControl(@ColorRes disabled: Int, @ColorRes enabled: Int, isEnabled: Boolean) {
    this.setColorFilter(
        ContextCompat.getColor(this.context, if (isEnabled) enabled else disabled),
        android.graphics.PorterDuff.Mode.SRC_IN
    )
    this.setEnable(isEnabled)
}

/**
 * Extension for Image view to load hex color
 * @param hex [Int] parsed color for background to be set
 */
fun ImageView.loadHex(
    hex: Int,
) {
    this.setBackgroundColor(hex)
}

/**
 * Extension to load the url with default
 * but extesible placeholder and error resource
 * and optional radius for rounded imageview
 * @param url [String] image url
 * @param error [Int] error resource when url fails to load
 * @param placeholder [Int] placeholder resource when url is still loading
 * @param radius [Int] radius for how rounded the imageview will be
 */
fun ImageView.loadUrl(
    url: String?,
    @DrawableRes placeholder: Int = com.ainsigne.ui.R.drawable.image_empty,
    @DrawableRes error: Int = com.ainsigne.ui.R.drawable.image_empty,
    @DimenRes radius: Int? = null,
) {
    val requestOptions = RequestOptions()
        .placeholder(ContextCompat.getDrawable(this.context, placeholder))
        .error(ContextCompat.getDrawable(this.context, error))
    radius?.let {
        requestOptions.apply(
            RequestOptions.bitmapTransform(
                RoundedCorners(
                    this.context.resources.getDimension(radius).toInt()
                )
            )
        )
    }
    Glide.with(this.context)
        .load(url)
        .dontAnimate()
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .apply(requestOptions)
        .into(this)
}

/**
 * Extension to load the url with default
 * but extesible placeholder and error resource
 * and optional radius for rounded imageview
 * @param url [String] image url
 * @param error [Int] error resource when url fails to load
 * @param placeholder [Int] placeholder resource when url is still loading
 * @param radius [Int] radius for how rounded the imageview will be
 */
fun ImageView.loadUrlListener(
    url: String?,
    @DrawableRes placeholder: Int = com.ainsigne.ui.R.drawable.image_empty,
    @DrawableRes error: Int = com.ainsigne.ui.R.drawable.image_empty,
    @DimenRes radius: Int? = null,
    block: (Drawable?) -> Unit,
) {
    val requestOptions = RequestOptions()
        .placeholder(ContextCompat.getDrawable(this.context, placeholder))
        .error(ContextCompat.getDrawable(this.context, error))
    radius?.let {
        requestOptions.apply(
            RequestOptions.bitmapTransform(
                RoundedCorners(
                    this.context.resources.getDimension(radius).toInt()
                )
            )
        )
    }
    Glide.with(this.context)
        .load(url)
        .apply(requestOptions)
        .listener(object : RequestListener<Drawable> {
            override fun onLoadFailed(
                e: GlideException?,
                model: Any?,
                target: Target<Drawable>?,
                isFirstResource: Boolean,
            ): Boolean {
                return false
            }

            override fun onResourceReady(
                resource: Drawable?,
                model: Any?,
                target: Target<Drawable>?,
                dataSource: DataSource?,
                isFirstResource: Boolean,
            ): Boolean {
                block.invoke(resource)
                return false
            }
        })
        .into(this)
}

/**
 * Extension to load the url with default
 * but extesible placeholder and error resource
 * and optional radius for rounded imageview
 * @param image [Int] image drawable
 * @param error [Int] error resource when url fails to load
 * @param placeholder [Int] placeholder resource when url is still loading
 * @param radius [Int] radius for how rounded the imageview will be
 */
fun ImageView.loadImage(
    image: Int = com.ainsigne.ui.R.drawable.image_empty,
    @DrawableRes placeholder: Int = com.ainsigne.ui.R.drawable.image_empty,
    @DrawableRes error: Int = com.ainsigne.ui.R.drawable.image_empty,
    @DimenRes radius: Int? = null,
) {
    val requestOptions = RequestOptions()
        .placeholder(ContextCompat.getDrawable(this.context, placeholder))
        .error(ContextCompat.getDrawable(this.context, error))
    radius?.let {
        requestOptions.apply(
            RequestOptions.bitmapTransform(
                RoundedCorners(
                    this.context.resources.getDimension(radius).toInt()
                )
            )
        )
    }
    Glide.with(this.context)
        .load(image)
        .apply(requestOptions)
        .into(this)
}

/**
 * Extension to load the url with default
 * but extesible placeholder and error resource
 * and optional radius for rounded imageview
 * @param image [Int] image drawable
 * @param error [Int] error resource when url fails to load
 * @param placeholder [Int] placeholder resource when url is still loading
 * @param radius [Int] radius for how rounded the imageview will be
 */
fun ImageView.loadBitmap(
    image: Bitmap?,
    @DrawableRes placeholder: Int = com.ainsigne.ui.R.drawable.image_empty,
    @DrawableRes error: Int = com.ainsigne.ui.R.drawable.image_empty,
    @DimenRes radius: Int? = null,
) {
    val requestOptions = RequestOptions()
        .placeholder(ContextCompat.getDrawable(this.context, placeholder))
        .error(ContextCompat.getDrawable(this.context, error))
    radius?.let {
        requestOptions.apply(
            RequestOptions.bitmapTransform(
                RoundedCorners(
                    this.context.resources.getDimension(radius).toInt()
                )
            )
        )
    }
    image?.let {
        Glide.with(this.context)
            .load(it)
            .apply(requestOptions)
            .into(this)
    } ?: kotlin.run {
        Glide.with(this.context)
            .load(placeholder)
            .apply(requestOptions)
            .into(this)
    }
}

/**
 * Blur an imageview using a given bitmap to be blurred and displayed on the imageview
 * @param blurRadius [Float] blur radius setting for the blurring to be done
 * @param bitmap [Bitmap] original bitmap to add a blur with
 */
fun ImageView.blur(blurRadius: Float, bitmap: Bitmap? = null) {
    this.post {
        val currentBitmap = bitmap ?: bitmap()
        val bitmapScale = 0.4f
        val bitmapWidth = width
        val bitmapHeight = height
        val width = (bitmapWidth * bitmapScale).roundToInt()
        val height = (bitmapHeight * bitmapScale).roundToInt()
        currentBitmap?.let { image ->
            val inputBitmap = Bitmap.createScaledBitmap(image, width, height, false)
            val outputBitmap = Bitmap.createBitmap(inputBitmap)
            val rs = RenderScript.create(this.context)
            val theIntrinsic = ScriptIntrinsicBlur.create(rs, U8_4(rs))
            val tmpIn = Allocation.createFromBitmap(rs, inputBitmap)
            val tmpOut = Allocation.createFromBitmap(rs, outputBitmap)
            theIntrinsic.setRadius(blurRadius)
            theIntrinsic.setInput(tmpIn)
            theIntrinsic.forEach(tmpOut)
            tmpOut.copyTo(outputBitmap)
            this.background = BitmapDrawable(outputBitmap)
        }
    }
}

fun ImageView.bitmap(): Bitmap? {
    return (this.drawable as BitmapDrawable?)?.bitmap
}
