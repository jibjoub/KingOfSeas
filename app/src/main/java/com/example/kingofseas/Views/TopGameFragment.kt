package com.example.kingofseas.Views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.example.kingofseas.Adapter.PlayerAdapter
import com.example.kingofseas.GameActivity
import com.example.kingofseas.R
import androidx.recyclerview.widget.LinearLayoutManager


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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //The context of the activity above
        val context = context as GameActivity

        val vm = context.viewModel

        val players_rv: RecyclerView = view.findViewById(R.id.rv_players)

        //Allow to have an horizontal recycler_view to display the players
        val layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        players_rv.layoutManager = layoutManager

        //A simple change for test purpose, need to remove it
        val adapter = PlayerAdapter(vm.players.value!!) {
            //vm.changeLiveliness(it)
        }

        //Linking the adapter to the recycler_view
        players_rv.adapter = adapter

        //Allows to broadcast the change of the MutableList to the adapter
        vm.players.observe(context, {
            adapter.setData(vm.players.value!!)
        })

        val eot_bt: Button = view.findViewById(R.id.bt_end_of_turn)

        vm.remaining_rolls.observe(context, {
            eot_bt.isEnabled = vm.remaining_rolls.value!! != vm.max_number_of_rolls.value!!
        })

        //Clicking on that button allows the end of the turn
        eot_bt.setOnClickListener{
            vm.applyChangeEndOfRolls()
            vm.in_out_king()
            vm.nextPlayer()
        }

        //Notify the screen whenever the current player is changed
        vm.currentPlayerInd.observe(context, {
            val player = vm.players.value!![vm.currentPlayerInd.value!!]
            Toast.makeText(context,player.name + "'s turn", Toast.LENGTH_SHORT).show()
        })
    }
}