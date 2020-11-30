package ro.ubbcluj.cs.ilazar.mycarapplication196.todo.item

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_item_edit.*
import kotlinx.android.synthetic.main.fragment_item_list.fab
import kotlinx.android.synthetic.main.fragment_item_list.progress
import ro.ubbcluj.cs.ilazar.mycarapplication196.R
import ro.ubbcluj.cs.ilazar.mycarapplication196.core.TAG


class CarEditFragment : Fragment() {
    companion object {
        const val ITEM_ID = "ITEM_ID"
    }

    private lateinit var viewModel: CarEditViewModel
    private var itemId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.v(TAG, "onCreate")
        arguments?.let {
            if (it.containsKey(ITEM_ID)) {
                itemId = it.getString(ITEM_ID).toString()
            }
        }

    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        Log.v(TAG, "onCreateView")
        return inflater.inflate(R.layout.fragment_item_edit, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.v(TAG, "onViewCreated")
        item_title.setText(itemId)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Log.v(TAG, "onActivityCreated")
        setupViewModel()
        fab.setOnClickListener {
            Log.v(TAG, "save item")
            viewModel.saveOrUpdateItem(item_title.text.toString(), item_content.text.toString())
        }

    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(this).get(CarEditViewModel::class.java)
        viewModel.item.observe(viewLifecycleOwner, { item ->
            Log.v(TAG, "update items")
            item_title.setText(item.title)
            item_content.setText(item.text)
        })
        viewModel.fetching.observe(viewLifecycleOwner, { fetching ->
            Log.v(TAG, "update fetching")
            progress.visibility = if (fetching) View.VISIBLE else View.GONE
        })
        viewModel.fetchingError.observe(viewLifecycleOwner, { exception ->
            if (exception != null) {
                Log.v(TAG, "update fetching error")
                val message = "Fetching exception ${exception.message}"
                val parentActivity = activity?.parent
                if (parentActivity != null) {
                    Toast.makeText(parentActivity, message, Toast.LENGTH_SHORT).show()
                }
            }
        })
        viewModel.completed.observe(viewLifecycleOwner, Observer { completed ->
            if (completed) {
                Log.v(TAG, "completed, navigate back")
                findNavController().navigateUp()
            }
        })
        val id = itemId
        if (id != null) {
            viewModel.loadItem(id)
        }
    }
}