package com.example.kingofseas.Views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import android.widget.Toast.makeText
import androidx.recyclerview.widget.RecyclerView
import com.example.kingofseas.Adapter.CardAdapter
import com.example.kingofseas.Adapter.PlayerAdapter
import com.example.kingofseas.GameActivity
import com.example.kingofseas.R
import androidx.recyclerview.widget.LinearLayoutManager




// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [CardsGameFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CardsGameFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cards_game, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //The context of the activity above
        val context = context as GameActivity

        val vm = context.viewModel

        val adapter = CardAdapter(vm.cards.value!!) {
            if (!vm.canApplyCard(it))
                Toast.makeText(context, "Not enough energy", Toast.LENGTH_SHORT).show()
        }

        val cards_rv: RecyclerView = view.findViewById(R.id.rv_cards)
        //Allow to have an horizontal recycler_view to display the players
        val layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        cards_rv.layoutManager = layoutManager

        //Linking the adapter to the recycler_view
        cards_rv.adapter = adapter

        vm.cards.observe(context, {
            adapter.setData(vm.cards.value!!)
        })

    }
}