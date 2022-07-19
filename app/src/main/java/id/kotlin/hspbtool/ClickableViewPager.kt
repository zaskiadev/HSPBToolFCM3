package id.kotlin.hspbtool

import android.content.Context
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.GestureDetector.SimpleOnGestureListener
import android.view.MotionEvent
import android.view.View
import androidx.viewpager.widget.ViewPager


class ClickableViewPager @JvmOverloads constructor(context: Context?, attrs: AttributeSet? = null) :
    ViewPager(context!!, attrs) {

    private var onClickListener: OnClickListener? = null
    override fun setOnClickListener(onClickListener: OnClickListener?) {
        this.onClickListener = onClickListener
    }

    private inner class OnSingleTapConfirmedGestureListener(private val view: View) :
        SimpleOnGestureListener() {
        override fun onSingleTapConfirmed(e: MotionEvent): Boolean {
            if (onClickListener != null) {
                onClickListener!!.onClick(view)
            }
            return true
        }
    }

    init {
        val onSingleTapConfirmedGestureDetector =
            GestureDetector(context, OnSingleTapConfirmedGestureListener(this))
        setOnTouchListener { v, event ->
            onSingleTapConfirmedGestureDetector.onTouchEvent(event)
            false
        }
    }
}