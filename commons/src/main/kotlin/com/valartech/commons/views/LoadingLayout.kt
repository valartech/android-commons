package com.valartech.commons.views

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import androidx.annotation.IntDef
import com.valartech.commons.R

/**
 * Layout with support for easily switching between a loading state, a final loaded state and an
 * optional zero ("empty") state.
 * <p />
 *
 * There are 2 ways to use this layout:
 * 1. Tags (preferred): tag your views with the strings [R.string.ll_loading], [R.string.ll_complete] and [R.string.ll_empty].
 * 2. Ordering:
 * Add in, top to bottom: loading view(like a progressbar), loaded view(actual layout) and a view for
 * the zero state.
 * <p />
 * Note that the order of the views as laid out in XML is significant if the second method is
 * used: this layout will misbehave if the order noted above isn't followed.
 */
@Suppress("MemberVisibilityCanBePrivate")
class LoadingLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private var loadingView: View? = null
    private var completeView: View? = null
    private var emptyView: View? = null
    private var overlayView: View? = null
    private val defaultState: Int
    private var currentState: Int? = null
    private val shortAnimDuration = resources.getInteger(android.R.integer.config_shortAnimTime).toLong()

    init {
        //get values from attrs
        val a = context.obtainStyledAttributes(
            attrs,
            R.styleable.LoadingLayout, 0, 0
        )

        defaultState = a.getInt(R.styleable.LoadingLayout_default_state, COMPLETE)
        a.recycle()
    }

    @kotlin.annotation.Retention(AnnotationRetention.SOURCE)
    @IntDef(LOADING, LOADING_OVERLAY, COMPLETE, EMPTY)
    annotation class ViewState

    override fun onFinishInflate() {
        super.onFinishInflate()
        //Use the default order to find views
        loadingView = getChildAt(0)
        completeView = getChildAt(1)
        emptyView = getChildAt(2)

        //If views are specified with tags, override
        findViewWithTag<View?>(context.getString(R.string.ll_loading))?.let { loadingView = it }
        findViewWithTag<View?>(context.getString(R.string.ll_complete))?.let { completeView = it }
        findViewWithTag<View?>(context.getString(R.string.ll_empty))?.let { emptyView = it }

        if (!isInEditMode) {
            if (loadingView == null) {
                throw IllegalStateException("No child views present in this layout. Loading and Loaded view are required.")
            }
            if (completeView == null) {
                throw IllegalStateException("Either loading or complete view is missing. Both are required")
            }
        }
        //default state
        currentState = defaultState
        setState(defaultState)

    }

    fun setState(@ViewState viewState: Int) {
        when (viewState) {
            EMPTY -> {
                loadingView?.visibility = View.GONE
                completeView?.visibility = View.GONE
                emptyView?.visibility = View.VISIBLE
            }
            LOADING -> {
                loadingView?.alpha = 1f
                loadingView?.visibility = View.VISIBLE
                completeView?.visibility = View.GONE
                emptyView?.visibility = View.GONE
            }
            LOADING_OVERLAY -> {
                loadingView?.alpha = 1f
                loadingView?.visibility = View.VISIBLE
                completeView?.visibility = View.VISIBLE
                emptyView?.visibility = View.GONE
            }
            COMPLETE -> {
                emptyView?.visibility = View.GONE
                if (isInEditMode) {
                    loadingView?.visibility = View.GONE
                    completeView?.visibility = View.VISIBLE
                } else if (currentState == LOADING) {
                    crossfadeCompleteView()
                } else {
                    loadingView?.visibility = View.GONE
                    completeView?.visibility = View.VISIBLE
                }
            }
        }
        currentState = viewState
    }

    /**
     * https://developer.android.com/training/animation/reveal-or-hide-view#kotlin
     */
    private fun crossfadeCompleteView() {
//        loadingView?.visibility = View.GONE
//        completeView?.visibility = View.VISIBLE
        completeView?.apply {
            // Set the content view to 0% opacity but visible, so that it is visible
            // (but fully transparent) during the animation.
            alpha = 0f
            visibility = View.VISIBLE

            // Animate the content view to 100% opacity, and clear any animation
            // listener set on the view.
            animate()
                .alpha(1f)
                .setDuration(shortAnimDuration)
                .setListener(null)
        }
        // Animate the loading view to 0% opacity. After the animation ends,
        // set its visibility to GONE as an optimization step (it won't
        // participate in layout passes, etc.)
        loadingView?.animate()
            ?.alpha(0f)
            ?.setDuration(shortAnimDuration)
            ?.setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    loadingView?.visibility = View.GONE
                }
            })
    }

    companion object {
        //the values of these constants is significant, they correspond to params in attrs.xml
        const val LOADING = 1
        const val LOADING_OVERLAY = 2
        const val COMPLETE = 3
        const val EMPTY = 4
    }
}
