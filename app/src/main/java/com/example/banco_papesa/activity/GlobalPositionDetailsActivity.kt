package com.example.banco_papesa.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.banco_papesa.R
import com.example.banco_papesa.databinding.ActivityGlobalPositionDetailsBinding
import com.example.banco_papesa.fragment.AccountsMovementsFragment
import com.example.banco_papesa.fragment.FilterMovementsFragment
import com.example.bancoapiprofe.pojo.Cuenta


class GlobalPositionDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGlobalPositionDetailsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityGlobalPositionDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val cuenta = intent.getSerializableExtra("cuenta") as Cuenta

        var fragment = AccountsMovementsFragment.newInstance(cuenta)
        fragment.setFilter(-1)
        supportFragmentManager
            .beginTransaction()
            .add(binding.fragmentMovement.id, fragment, FilterMovementsFragment::class.java.name)
            .commit()
        binding.bottomNavigation?.visibility = View.INVISIBLE

        binding.bottomNavigation?.setOnNavigationItemSelectedListener {
            it.isChecked = true
            fragment = AccountsMovementsFragment.newInstance(cuenta)
            when (it.itemId) {
                R.id.filter_all -> {
                    fragment.setFilter(-1)
                }

                R.id.filter_1 -> {
                    fragment.setFilter(0)
                }

                R.id.filter_2 -> {
                    fragment.setFilter(1)
                }

                R.id.filter_3 -> {
                    fragment.setFilter(2)
                }
            }

            supportFragmentManager
                .beginTransaction()
                .replace(binding.fragmentMovement.id, fragment, FilterMovementsFragment::class.java.name)
                .commitNow()
            true
        }
    }



}