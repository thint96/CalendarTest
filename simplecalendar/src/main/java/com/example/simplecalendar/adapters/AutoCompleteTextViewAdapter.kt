package com.example.simplecalendar.adapters

import android.graphics.drawable.LayerDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Filter
import com.example.simplecalendar.R
import com.example.simplecalendar.activities.SimpleActivity
import com.example.simplecalendar.extensions.config
import com.example.simplecalendar.models.Attendee
import com.simplemobiletools.commons.extensions.applyColorFilter
import com.simplemobiletools.commons.extensions.normalizeString
import kotlinx.android.synthetic.main.item_autocomplete_email_name.view.*

class AutoCompleteTextViewAdapter(val activity: SimpleActivity, val contacts: ArrayList<Attendee>) : ArrayAdapter<Attendee>(activity, 0, contacts) {
    var resultList = ArrayList<Attendee>()
    private var placeholder = activity.resources.getDrawable(R.drawable.attendee_circular_background)

    init {
        (placeholder as LayerDrawable).findDrawableByLayerId(R.id.attendee_circular_background).applyColorFilter(activity.config.primaryColor)
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val contact = resultList[position]
        var listItem = convertView
        if (listItem == null || listItem.tag != contact.name.isNotEmpty()) {
            val layout = if (contact.name.isNotEmpty()) R.layout.item_autocomplete_email_name else R.layout.item_autocomplete_email
            listItem = LayoutInflater.from(activity).inflate(layout, parent, false)
        }

        listItem!!.apply {
            tag = contact.name.isNotEmpty()
            item_autocomplete_name?.text = contact.name
            item_autocomplete_email?.text = contact.email

            contact.updateImage(context, item_autocomplete_image, placeholder)
        }

        return listItem
    }

    override fun getFilter() = object : Filter() {
        override fun performFiltering(constraint: CharSequence?): FilterResults {
            val filterResults = Filter.FilterResults()
            if (constraint != null) {
                resultList.clear()
                val searchString = constraint.toString().normalizeString()
                contacts.forEach {
                    if (it.email.contains(searchString, true) || it.name.contains(searchString, true)) {
                        resultList.add(it)
                    }
                }

                resultList.sortWith(compareBy<Attendee>
                { it.name.startsWith(searchString, true) }.thenBy
                { it.email.startsWith(searchString, true) }.thenBy
                { it.name.contains(searchString, true) }.thenBy
                { it.email.contains(searchString, true) })
                resultList.reverse()

                filterResults.values = resultList
                filterResults.count = resultList.size
            }
            return filterResults
        }

        override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
            if (results?.count ?: -1 > 0) {
                notifyDataSetChanged()
            } else {
                notifyDataSetInvalidated()
            }
        }

        override fun convertResultToString(resultValue: Any?) = (resultValue as? Attendee)?.getPublicName()
    }

    override fun getItem(index: Int) = resultList[index]

    override fun getCount() = resultList.size
}
