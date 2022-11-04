package com.example.kingofseas.Views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import com.example.kingofseas.Model.GameManager
import com.example.kingofseas.R
import com.example.kingofseas.ViewModel.GameViewModel

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [BoardFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class BoardFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private val gameViewModel: GameViewModel = GameViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_board, container, false)
        val dice1: TextView = view.findViewById(R.id.dice_1)
        val dice2: TextView = view.findViewById(R.id.dice_2)
        val dice3: TextView = view.findViewById(R.id.dice_3)
        val dice4: TextView = view.findViewById(R.id.dice_4)
        val dice5: TextView = view.findViewById(R.id.dice_5)
        val dice6: TextView = view.findViewById(R.id.dice_6)
        gameViewModel.rollAllDices();
        dice1.text = GameManager.dices[0].face.toString()
        dice2.text = GameManager.dices[1].face.toString()
        dice3.text = GameManager.dices[2].face.toString()
        dice4.text = GameManager.dices[3].face.toString()
        dice5.text = GameManager.dices[4].face.toString()
        dice6.text = GameManager.dices[5].face.toString()

        view.findViewById<Button>(R.id.btn_roll_dices).setOnClickListener {
            gameViewModel.rollAllDices();
            dice1.text = GameManager.dices[0].face.toString()
            dice2.text = GameManager.dices[1].face.toString()
            dice3.text = GameManager.dices[2].face.toString()
            dice4.text = GameManager.dices[3].face.toString()
            dice5.text = GameManager.dices[4].face.toString()
            dice6.text = GameManager.dices[5].face.toString()
        }
        return view
    }

}