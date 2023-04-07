package com.samikb.assignment.ui.movie

import android.os.Bundle
import android.text.Spanned
import android.text.style.AbsoluteSizeSpan
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.bold
import androidx.core.text.buildSpannedString
import com.samikb.assignment.R
import com.samikb.assignment.databinding.ActivityMovieDetailsBinding
import com.samikb.assignment.model.MovieApiConfiguration
import com.samikb.assignment.model.MovieCompleteDetails
import com.samikb.assignment.util.Util
import com.google.android.flexbox.FlexboxLayout
import com.google.gson.Gson


class MovieDetailsActivity : AppCompatActivity() {
    companion object{
        const val MOVIE_COMPLETE_DETAILS_EXTRA = "movie_complete_details_extra"
        const val API_CONFIGURATION_EXTRA = "api_config_extra"
    }
    private lateinit var binding: ActivityMovieDetailsBinding
    private var movieCompleteDetails: MovieCompleteDetails? = null
    private var configuration: MovieApiConfiguration? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieDetailsBinding.inflate(layoutInflater)
        movieCompleteDetails = Gson().fromJson(intent.getStringExtra(MOVIE_COMPLETE_DETAILS_EXTRA), MovieCompleteDetails::class.java)
        configuration = intent.getParcelableExtra(API_CONFIGURATION_EXTRA)
        if(movieCompleteDetails != null){
            setContentView(binding.root)
            binding.backImageView.setOnClickListener {
                finish()
            }
            initViews()
        }
        else{
            finish()
        }

    }

    private fun initViews(){
        binding.title = movieCompleteDetails?.title
        binding.releaseDate = "${Util.getFormattedDate(movieCompleteDetails?.releaseDate)} - ${Util.getFormattedRuntime(movieCompleteDetails?.runtime ?: 0)}"
        binding.overview = buildSpannedString {
            bold { append(getString(R.string.overview) + "\n\n") }
            append(movieCompleteDetails?.overview)
            setSpan(AbsoluteSizeSpan(60), 0, getString(R.string.overview).length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            setSpan(AbsoluteSizeSpan(40), getString(R.string.overview).length, length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        }
        binding.imageUrl = "${configuration?.images?.secureBaseUrl}/w500${movieCompleteDetails?.posterPath}"
        movieCompleteDetails?.genres?.forEach {
            val textView = layoutInflater.inflate(R.layout.genre_text_view, null) as TextView
            val textViewLayoutParams: FlexboxLayout.LayoutParams =
                FlexboxLayout.LayoutParams(FlexboxLayout.LayoutParams.WRAP_CONTENT, FlexboxLayout.LayoutParams.WRAP_CONTENT)
            textViewLayoutParams.setMargins(5, 5, 5, 5)
            textView.layoutParams = textViewLayoutParams
            textView.text = it.name
            binding.genreLayout.addView(textView)
        }
    }

}