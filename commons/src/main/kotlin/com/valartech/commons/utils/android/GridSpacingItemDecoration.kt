package com.valartech.commons.utils.android

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

/**
 * From: https://stackoverflow.com/a/30701422/3460025
 */
class GridSpacingItemDecoration(
    private val spanCount: Int,
    private val spacingPx: Int,
    private val includeEdge: Boolean
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        val position = parent.getChildAdapterPosition(view) // item position
        val column = position % spanCount // item column

        if (includeEdge) {
            outRect.left =
                spacingPx - column * spacingPx / spanCount // spacingPx - column * ((1f / spanCount) * spacingPx)
            outRect.right = (column + 1) * spacingPx / spanCount // (column + 1) * ((1f / spanCount) * spacingPx)

            if (position < spanCount) { // top edge
                outRect.top = spacingPx
            }
            outRect.bottom = spacingPx // item bottom
        } else {
            outRect.left = column * spacingPx / spanCount // column * ((1f / spanCount) * spacingPx)
            outRect.right =
                spacingPx - (column + 1) * spacingPx / spanCount // spacingPx - (column + 1) * ((1f/spanCount) * spacingPx)
            if (position >= spanCount) {
                outRect.top = spacingPx // item top
            }
        }
    }
}
