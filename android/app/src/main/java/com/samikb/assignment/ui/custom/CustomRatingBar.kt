package com.samikb.assignment.ui.custom

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Color
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.text.HtmlCompat
import com.samikb.assignment.R
import com.google.android.material.progressindicator.CircularProgressIndicator


class CustomRatingBar@JvmOverloads constructor(
    context: Context,
    attributes: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0): ConstraintLayout(context, attributes, defStyleAttr, defStyleRes) {
    var rating = 0
    set(value) {
        progressBar.progress = value
        textView.text = HtmlCompat.fromHtml("$value<sup><small> % </small></sup>", HtmlCompat.FROM_HTML_MODE_LEGACY)
        field = value
        if(value >= 50){
            progressBar.setIndicatorColor(Color.parseColor("#00cc44"))
        }
        else{
            progressBar.setIndicatorColor(Color.parseColor("#fcd052"))
        }
    }
    private lateinit var progressBar: CircularProgressIndicator
    private lateinit var textView: AppCompatTextView

    init {
        LayoutInflater.from(context).inflate(R.layout.custom_rating_bar, this)
        setupViewReference()
        if (attributes != null) {
            val attribs: TypedArray = context.obtainStyledAttributes(attributes, R.styleable.custom_rating_bar)
            rating = attribs.getInt(R.styleable.custom_rating_bar_rating, 0)
            attribs.recycle()
        }
    }

    private fun setupViewReference() {
        progressBar = findViewById(R.id.rating_progress_bar)
        textView = findViewById(R.id.rating_text_view)
    }

}