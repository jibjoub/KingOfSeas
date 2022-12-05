package com.example.kingofseas.Views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListAdapter
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.example.kingofseas.Adapter.PlayerAdapter
import com.example.kingofseas.GameActivity
import com.example.kingofseas.Model.Player
import com.example.kingofseas.R
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kingofseas.ViewModel.GameViewModel
import org.w3c.dom.Text


/**
 * A simple [Fragment] subclass.
 * Use the [TopGameFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class TopGameFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_top_game, container, false)
    }

    private val viewModel: GameViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val context = context as GameActivity

        val players_rv: RecyclerView = view.findViewById(R.id.rv_players)
        val layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        players_rv.layoutManager = layoutManager

        //A simple change for test purpose, need to remove it
        val adapter = PlayerAdapter(viewModel.players.value!!) {
            viewModel.changeName(it)
        }

        players_rv.adapter = adapter

        //Allows to broadcast the change of the MutableList to the adapter
        viewModel.players.observe(context, {
            adapter.setData(viewModel.players.value!!)
        })
    }
}