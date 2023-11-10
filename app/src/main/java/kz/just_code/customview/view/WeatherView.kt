package kz.just_code.customview.view

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import kz.just_code.customview.R
import kz.just_code.customview.TimeOfDay
import kz.just_code.customview.databinding.ViewWeatherBinding

class WeatherView @JvmOverloads constructor(
    private val context: Context, private val attrs: AttributeSet? = null
) : FrameLayout(context, attrs) {

    private val binding: ViewWeatherBinding =
        ViewWeatherBinding.inflate(LayoutInflater.from(context), this)

    var pauseClick: (() -> Unit)? = null
    var likeClick: (() -> Unit)? = null

    init {
        setAttrs(attrs, R.styleable.WeatherView) {
            binding.temp.text = it.getString(R.styleable.WeatherView_weather_temp)
            binding.cloud.setImageResource(it.getResourceId(R.styleable.WeatherView_weather_cloud, R.drawable.ic_rain))
            val timeOfDay = TimeOfDay.values().firstOrNull { time ->
                time.ordinal == it.getInt(R.styleable.WeatherView_weather_times_of_day, 0)
            }
            binding.time.text = timeOfDay?.name

            binding.imageView.setOnClickListener {
                pauseClick?.invoke()
            }
        }
    }
}

inline fun View.setAttrs(
    attrs: AttributeSet?,
    styleable: IntArray,
    crossinline body: (TypedArray) -> Unit
) {
    context.theme.obtainStyledAttributes(attrs, styleable, 0, 0)
        .apply {
            try {
                body.invoke(this)
            } finally {
                recycle()
            }
        }
}