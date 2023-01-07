package com.example.kingofseas.Views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kingofseas.Adapter.DiceAdapter
import com.example.kingofseas.GameActivity
import com.example.kingofseas.R

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [DicesGameFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DicesGameFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dices_game, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //The context of the activity above
        val context = context as GameActivity

        val vm = context.viewModel

        val dices_rv: RecyclerView = view.findViewById(R.id.rv_dices)

        //Allow to have an horizontal recycler_view to display the players
        val layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        dices_rv.layoutManager = layoutManager

        //A simple change for test purpose, need to remove it
        val adapter = DiceAdapter(vm.dices.value!!) {
            vm.changeSelection(it)
        }

        //Linking the adapter to the recycler_view
        dices_rv.adapter = adapter

        //Allows to broadcast the change of the MutableList to the adapter
        vm.dices.observe(context, {
            adapter.setData(vm.dices.value!!)
        })

        val roll_bt: Button = view.findViewById(R.id.bt_roll)
        val current_player_tv: TextView = view.findViewById(R.id.tv_current_player)

        vm.remaining_rolls.observe(context, {
            roll_bt.text = "ROLL DICES " + vm.remaining_rolls.value!!.toString() + "/" + vm.max_number_of_rolls.value!!.toString()
            roll_bt.isEnabled = vm.remaining_rolls.value!! != 0
        })

        vm.currentPlayerInd.observe(context, {
            current_player_tv.text = vm.players.value!![vm.currentPlayerInd.value!!].name + " is playing"
        })

        roll_bt.setOnClickListener{
            vm.rollDices()
            vm.decrementRolls()
        }
    }
}