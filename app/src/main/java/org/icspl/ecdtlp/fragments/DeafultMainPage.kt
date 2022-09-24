package org.icspl.ecdtlp.fragments


import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.method.ScrollingMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.daimajia.slider.library.SliderLayout
import kotlinx.android.synthetic.main.fragment_deafult_main_page.view.*

import org.icspl.ecdtlp.R
import java.util.HashMap
import android.widget.Toast
import android.widget.TextView
import org.icspl.ecdtlp.MainActivity
import android.widget.AdapterView
import com.daimajia.slider.library.Animations.DescriptionAnimation
import com.daimajia.slider.library.SliderTypes.BaseSliderView
import com.daimajia.slider.library.SliderTypes.TextSliderView
import android.R.attr.keySet
import android.widget.ListView
import com.daimajia.slider.library.Tricks.ViewPagerEx


class DeafultMainPage : android.app.Fragment(), ViewPagerEx.OnPageChangeListener, BaseSliderView.OnSliderClickListener {


    companion object {
        private val TAG = "DeafultMainPage"
    }

    private lateinit var mSliderLayout: SliderLayout
    private val map = HashMap<String, String>()
    private var mContext: Context? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_deafult_main_page, container, false)
        initViews(v)
        return v
    }

    private fun initViews(v: View) {
        mContext = v.context
        mSliderLayout = v.slider
        v.marquee.setMovementMethod(ScrollingMovementMethod())

        val file_maps = HashMap<String, Int>()
        file_maps["ECD 1"] = R.drawable.img_first
        file_maps["ECD 2"] = R.drawable.imag_sec
        file_maps["ECD 3"] = R.drawable.imag_third
        file_maps["ECD 4"] = R.drawable.imag_four


        for (name in file_maps.keys) {
            val textSliderView = TextSliderView(mContext)
            // initialize a SliderLayout
            file_maps[name]?.let {
                textSliderView
                        .description(name)
                        .image(it)
                        .setScaleType(BaseSliderView.ScaleType.Fit)
                        .setOnSliderClickListener(this)
            }

            //add your extra information
            textSliderView.bundle(Bundle())
            textSliderView.bundle
                    .putString("extra", name)

            mSliderLayout.addSlider(textSliderView)
        }
        mSliderLayout.setPresetTransformer(SliderLayout.Transformer.Accordion)
        mSliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom)
        mSliderLayout.setCustomAnimation(DescriptionAnimation())
        mSliderLayout.setDuration(4000)
        mSliderLayout.addOnPageChangeListener(this)


    }

    override fun onSliderClick(slider: BaseSliderView?) {

    }

    override fun onPageScrollStateChanged(state: Int) {

    }

    override fun onPageSelected(position: Int) {
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

    }
}
