/*
 * This file is part of Breezy Weather.
 *
 * Breezy Weather is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by the
 * Free Software Foundation, version 3 of the License.
 *
 * Breezy Weather is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public
 * License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Breezy Weather. If not, see <https://www.gnu.org/licenses/>.
 */

package org.breezyweather.ui.main.adapters.main.holder

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.content.Context
import android.view.View
import android.view.ViewGroup.MarginLayoutParams
import android.widget.ImageView
import androidx.annotation.CallSuper
import androidx.core.widget.ImageViewCompat
import breezyweather.domain.location.model.Location
import com.google.android.material.card.MaterialCardView
import org.breezyweather.R
import org.breezyweather.common.activities.BreezyActivity
import org.breezyweather.common.extensions.dpToPx
import org.breezyweather.domain.settings.SettingsManager
import org.breezyweather.ui.theme.resource.providers.ResourceProvider

@SuppressLint("ObjectAnimatorBinding")
abstract class AbstractMainCardViewHolder(
    view: View,
) : AbstractMainViewHolder(view) {
    protected var mLocation: Location? = null

    @CallSuper
    open fun onBindView(
        activity: BreezyActivity,
        location: Location,
        provider: ResourceProvider,
        listAnimationEnabled: Boolean,
        itemAnimationEnabled: Boolean,
    ) {
        super.onBindView(activity, location, provider, listAnimationEnabled, itemAnimationEnabled)
        mLocation = location
        val iconTint = ColorStateList.valueOf(SettingsManager.getInstance(context).iconColor)
        listOf(
            R.id.title_icon,
            R.id.daily_block_title_icon,
            R.id.hourly_block_title_icon,
            R.id.nowcasting_block_title_icon
        ).forEach { iconId ->
            itemView.findViewById<ImageView>(iconId)?.let { icon ->
                ImageViewCompat.setImageTintList(icon, iconTint)
            }
        }
        if (itemView is MaterialCardView) {
            (itemView as MaterialCardView).apply {
                elevation = context.dpToPx(2f)
            }
        }
        val params = itemView.layoutParams as MarginLayoutParams
        params.setMargins(
            context.resources.getDimensionPixelSize(R.dimen.main_card_spacing),
            context.resources.getDimensionPixelSize(R.dimen.main_card_spacing),
            context.resources.getDimensionPixelSize(R.dimen.main_card_spacing),
            context.resources.getDimensionPixelSize(R.dimen.main_card_spacing)
        )
        itemView.layoutParams = params
    }

    @CallSuper
    open fun onBindView(
        activity: BreezyActivity,
        location: Location,
        provider: ResourceProvider,
        listAnimationEnabled: Boolean,
        itemAnimationEnabled: Boolean,
        selectedTab: String?,
        setSelectedTab: (String?) -> Unit,
    ) {
        onBindView(activity, location, provider, listAnimationEnabled, itemAnimationEnabled)
    }

    @SuppressLint("MissingSuperCall")
    override fun onBindView(
        context: Context,
        location: Location,
        provider: ResourceProvider,
        listAnimationEnabled: Boolean,
        itemAnimationEnabled: Boolean,
    ) {
        throw RuntimeException("Deprecated method.")
    }
}
