/*
 * This file is part of Breezy Weather.
 *
 * Breezy Weather is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by the
 * Free Software Foundation, version 3 of the License.
 */

package org.breezyweather.ui.main.adapters.main.holder

import android.graphics.Color
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.widget.PopupMenu
import androidx.core.content.ContextCompat
import org.breezyweather.R
import org.breezyweather.common.extensions.getThemeColor

internal fun LinearLayout.bindTrendTabs(
    names: List<String>,
    selectedIndex: Int,
    onSelected: (Int) -> Unit,
) {
    removeAllViews()
    val accentColor = context.getThemeColor(androidx.appcompat.R.attr.colorPrimary)
    val normalColor = context.getThemeColor(com.google.android.material.R.attr.colorOnSurfaceVariant)
    val density = context.resources.displayMetrics.density
    val minWidth = (88 * density).toInt()
    val tabHeight = (44 * context.resources.displayMetrics.density).toInt()
    val indicatorHeight = (2 * context.resources.displayMetrics.density).toInt()
    val visibleIndices = if (selectedIndex >= 3) {
        listOf(0, 1, selectedIndex)
    } else {
        names.indices.take(3)
    }

    visibleIndices.forEach { index ->
        val name = names[index]
        val selected = index == selectedIndex
        val tab = LinearLayout(context).apply {
            orientation = LinearLayout.VERTICAL
            gravity = Gravity.CENTER
            minimumWidth = minWidth
            isClickable = true
            isFocusable = true
            setOnClickListener { onSelected(index) }
        }
        val label = TextView(context).apply {
            text = name
            gravity = Gravity.CENTER
            textSize = 12f
            setTextColor(if (selected) accentColor else normalColor)
            setPadding((4 * density).toInt(), 0, (4 * density).toInt(), 0)
            maxLines = 1
        }
        tab.addView(label, LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, tabHeight))
        tab.addView(
            View(context).apply {
                setBackgroundColor(if (selected) accentColor else Color.TRANSPARENT)
            },
            LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, indicatorHeight)
        )
        // Give each tab enough room for its label. Equal weights clip longer
        // localized names such as "Luchtkwaliteit" on narrow screens.
        val tabWidth = maxOf(minWidth, (name.length * 7.5f * density).toInt() + (16 * density).toInt())
        addView(tab, LinearLayout.LayoutParams(tabWidth, ViewGroup.LayoutParams.WRAP_CONTENT))
    }

    if (names.size > 3) {
        val more = TextView(context).apply {
            text = "⋮"
            textSize = 24f
            gravity = Gravity.CENTER
            contentDescription = context.getString(R.string.action_more)
            setTextColor(normalColor)
            background = ContextCompat.getDrawable(context, android.R.drawable.list_selector_background)
            isClickable = true
            isFocusable = true
            setOnClickListener { anchor ->
                PopupMenu(context, anchor).apply {
                    names.forEachIndexed { index, name ->
                        if (index !in visibleIndices) {
                            menu.add(name).setOnMenuItemClickListener {
                                onSelected(index)
                                true
                            }
                        }
                    }
                }.show()
            }
        }
        addView(more, LinearLayout.LayoutParams((40 * density).toInt(), tabHeight + indicatorHeight))
    }
}
