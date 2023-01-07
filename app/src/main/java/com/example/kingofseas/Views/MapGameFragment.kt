package com.example.kingofseas.Views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.example.kingofseas.GameActivity
import com.example.kingofseas.R

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MapGameFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MapGameFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_map_game, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //The context of the activity above
        val context = context as GameActivity

        val vm = context.viewModel

        val tvKing = view.findViewById<TextView>(R.id.tv_king)

        vm.kingPlayerInd.observe(context, {
            if (vm.kingPlayerInd.value!! == -1)
                tvKing.text = "No king in the Sea"
            else {
                val king_name = vm.players.value!![vm.kingPlayerInd.value!!].name
                tvKing.text = king_name
                Toast.makeText(context, "$king_name is now king/queen", Toast.LENGTH_SHORT).show()
            }

        })
    }

}