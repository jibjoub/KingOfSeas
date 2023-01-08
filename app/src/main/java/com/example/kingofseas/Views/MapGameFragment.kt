package com.example.kingofseas.Views

import android.content.Intent
import android.graphics.drawable.AnimationDrawable
import android.media.Image
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.kingofseas.GameActivity
import com.example.kingofseas.GameOverActivity
import com.example.kingofseas.R
import android.view.animation.Animation

import android.view.animation.LinearInterpolator

import android.view.animation.RotateAnimation




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
        val view = inflater.inflate(R.layout.fragment_map_game, container, false)

        // Inflate the layout for this fragment
        return view
    }

    private lateinit var rocketAnimation: AnimationDrawable

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //The context of the activity above
        val context = context as GameActivity

        val vm = context.viewModel

        val ivIcon = view.findViewById<ImageView>(R.id.iv_current_in_sea)

        vm.kingPlayerInd.observe(context, {
            if (vm.kingPlayerInd.value!! == -1) {
                ivIcon.setImageResource(0)
            }
            else {
                val king = vm.players.value!![vm.kingPlayerInd.value!!]
                ivIcon.setImageResource(king.Icon)
                Toast.makeText(context, king.name + " is now in the sea", Toast.LENGTH_SHORT).show()
            }

        })



        vm.is_game_finished.observe(context, {
            if (vm.is_game_finished.value!!.first) {
                val intent = Intent(requireContext(), GameOverActivity::class.java)
                intent.putExtra("winner", vm.is_game_finished.value!!.second)
                startActivity(intent)
            }
        })
    }

}