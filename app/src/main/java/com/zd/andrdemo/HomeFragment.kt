package com.zd.andrdemo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView

class HomeFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        val viewAdapter = ZdListAdapter(homeListItems)

        view.findViewById<RecyclerView>(R.id.zd_list).run {
            // use this setting to improve performance if you know that changes
            // in content do not change the layout size of the RecyclerView
            setHasFixedSize(true)

            // specify an viewAdapter (see also next example)
            adapter = viewAdapter

        }
        return view
    }

}

class ZdListAdapter(private val list: List<ZdListItem>) :
    RecyclerView.Adapter<ZdListAdapter.ViewHolder>() {

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder.
    // Each data item is just a string in this case that is shown in a TextView.
    class ViewHolder(val item: View) : RecyclerView.ViewHolder(item)


    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        // create a new view
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_view_item, parent, false)


        return ViewHolder(itemView)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.item.findViewById<TextView>(R.id.name_text).text = list[position].name
        holder.item.findViewById<TextView>(R.id.des_text).text = list[position].des

        holder.item.setOnClickListener(list[position].onClick)
        /*{
              holder.item.findNavController().navigate(
                  R.id.action_title_to_about
              )
        }*/
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = list.size

}

class ZdListItem(val name: String, val onClick: View.OnClickListener, val des: String = "")

