/*
 * Copyright (C) 2021 The Android Open Source Project.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.lemonade

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.StringRes
import com.google.android.material.snackbar.Snackbar
import org.w3c.dom.Text

class MainActivity : AppCompatActivity() {


    private val LEMONADE_STATE = "LEMONADE_STATE"
    private val LEMON_SIZE = "LEMON_SIZE"
    private val SQUEEZE_COUNT = "SQUEEZE_COUNT"
    // SELECT represents the "pick lemon" state
    private val SELECT = "select"
    // SQUEEZE represents the "squeeze lemon" state
    private val SQUEEZE = "squeeze"
    // DRINK represents the "drink lemonade" state
    private val DRINK = "drink"
    // RESTART represents the state where the lemonade has be drunk and the glass is empty
    private val RESTART = "restart"
    // Default the state to select
    private var lemonadeState = "select"
    // Default lemonSize to -1
    private var lemonSize = -1
    // Default the squeezeCount to -1
    private var squeezeCount = -1

    private var lemonTree = LemonTree()
    private var lemonImage: ImageView? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState != null) {
            lemonadeState = savedInstanceState.getString(LEMONADE_STATE, "select")
            lemonSize = savedInstanceState.getInt(LEMON_SIZE, -1)
            squeezeCount = savedInstanceState.getInt(SQUEEZE_COUNT, -1)
        }

        lemonImage = findViewById(R.id.image_lemon_state)

        lemonImage!!.setOnClickListener {

            clickLemonImage()
        }
        lemonImage!!.setOnLongClickListener {

            showSnackbar()
        }
    }


    override fun onSaveInstanceState(outState: Bundle) {
        outState.putString(LEMONADE_STATE, lemonadeState)
        outState.putInt(LEMON_SIZE, lemonSize)
        outState.putInt(SQUEEZE_COUNT, squeezeCount)
        super.onSaveInstanceState(outState)
    }


    private fun clickLemonImage() {


        val textAction: TextView = findViewById(R.id.text_action)
        when(lemonadeState){
            SELECT -> {
                    lemonImage?.setImageResource(R.drawable.lemon_squeeze)
                    textAction.text = getString(R.string.lemon_squeeze)
                    squeezeCount = lemonTree.pick()
                    lemonadeState = SQUEEZE
            }
            SQUEEZE -> {
                when(squeezeCount){
                    1 -> {
                        lemonImage?.setImageResource(R.drawable.lemon_drink)
                        textAction.text = getString(R.string.lemon_drink)
                        lemonadeState = DRINK
                        squeezeCount = -1
                        lemonSize = squeezeCount
                        lemonImage?.layoutParams?.width = 560
                        lemonImage?.layoutParams?.height = 600
                        lemonImage?.requestLayout()
                    }
                    2 -> {
                        squeezeCount = 2 - 1
                        lemonSize = squeezeCount
                        lemonImage?.layoutParams?.width = 520
                        lemonImage?.layoutParams?.height = 560
                        lemonImage?.requestLayout()
                    }
                    3 -> {
                        squeezeCount = 3 - 1
                        lemonSize = squeezeCount
                        lemonImage?.layoutParams?.width = 540
                        lemonImage?.layoutParams?.height = 580
                        lemonImage?.requestLayout()
                    }
                    4 -> {
                        squeezeCount = 4 - 1
                        lemonSize = squeezeCount
                        lemonImage?.layoutParams?.width = 560
                        lemonImage?.layoutParams?.height = 600
                        lemonImage?.requestLayout()
                    }
                }
            }
            DRINK -> {
                lemonImage?.setImageResource(R.drawable.lemon_restart)
                textAction.text = getString(R.string.lemon_empty_glass)
                lemonadeState = RESTART
            }
            RESTART -> {
                lemonImage?.setImageResource(R.drawable.lemon_tree)
                textAction.text = getString(R.string.lemon_select)
                lemonadeState = SELECT
            }

        }


    }

    private fun showSnackbar(): Boolean {
        if (lemonadeState != SQUEEZE) {
            return false
        }
        val squeezeText = getString(R.string.squeeze_count, squeezeCount)
        Snackbar.make(
            findViewById(R.id.constraint_Layout),
            squeezeText,
            Snackbar.LENGTH_SHORT
        ).show()
        return true
    }
}


class LemonTree {
    fun pick(): Int {
        return (2..4).random()
    }
}
