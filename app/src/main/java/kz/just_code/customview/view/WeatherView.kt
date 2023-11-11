package kz.just_code.customview.view

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import kz.just_code.customview.PrecipitationType
import kz.just_code.customview.R
import kz.just_code.customview.TimeOfDay
import kz.just_code.customview.databinding.ViewWeatherBinding

class WeatherView @JvmOverloads constructor(
    private val context: Context, private val attrs: AttributeSet? = null
) : FrameLayout(context, attrs) {

    private val binding: ViewWeatherBinding =
        ViewWeatherBinding.inflate(LayoutInflater.from(context), this)

//    var pauseClick: (() -> Unit)? = null
//    var likeClick: (() -> Unit)? = null

    init {
        setAttrs(attrs, R.styleable.WeatherView) {
            binding.description.text = it.getString((R.styleable.WeatherView_weather_description))
            binding.temp.text = it.getString(R.styleable.WeatherView_weather_temp)
            binding.city.text = it.getString(R.styleable.WeatherView_weather_city)
            binding.country.text = it.getString(R.styleable.WeatherView_weather_country)
            // Extracting the drawable resource ID based on the weather_times_of_day attribute
            val timeOfDayDrawableId =
                when (it.getInt(R.styleable.WeatherView_weather_times_of_day, 0)) {
                    TimeOfDay.DAY.ordinal -> R.drawable.ic_sun
                    TimeOfDay.NIGHT.ordinal -> R.drawable.ic_moon
                    else -> R.drawable.ic_sun // Default for unknown time of day
                }
            binding.time.setImageResource(timeOfDayDrawableId)

            val precipitationTypeDrawableId =
                when (it.getInt(R.styleable.WeatherView_weather_precipitation_type, 0)) {
                    PrecipitationType.LIGHT_RAIN.ordinal -> R.drawable.ic_light_rain
                    PrecipitationType.HEAVY_RAIN.ordinal -> R.drawable.ic_heavy_rain
                    PrecipitationType.LIGHT_SNOW.ordinal -> R.drawable.ic_light_snow
                    PrecipitationType.HEAVY_SNOW.ordinal -> R.drawable.ic_heavy_snow
                    else -> R.drawable.ic_light_rain
                }
            binding.precipitationType.setImageResource(precipitationTypeDrawableId)

            val hasWind = it.getBoolean(R.styleable.WeatherView_weather_has_wind, false)
            if (hasWind){
                binding.windyOrNot.text = "No wind"
            } else {
                binding.windyOrNot.text = "Wind"
            }
//            val timeOfDay = TimeOfDay.values().firstOrNull { time ->
//                time.ordinal == it.getInt(R.styleable.WeatherView_weather_times_of_day, 0)
//            }
//            val timeOfDayDrawableId = it.getResourceId(
//                R.styleable.WeatherView_weather_times_of_day,
//                R.drawable.ic_sun // Default drawable for daytime
//            )
//            val timeOfDayDrawableId = it.getResourceId(
//                R.styleable.WeatherView_weather_times_of_day,
//                R.drawable.ic_sun // Default drawable for daytime
//            )
//
//            binding.time.setImageResource(timeOfDayDrawableId)
//
////
//            binding.imageView.setOnClickListener {
//                pauseClick?.invoke()
//            }
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