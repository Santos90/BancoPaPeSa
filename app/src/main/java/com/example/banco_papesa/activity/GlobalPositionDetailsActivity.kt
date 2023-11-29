package com.example.banco_papesa.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.banco_papesa.R
import com.example.banco_papesa.adapter.OnClickListener
import com.example.banco_papesa.databinding.ActivityGlobalPositionBinding
import com.example.banco_papesa.databinding.ActivityGlobalPositionDetailsBinding
import com.example.banco_papesa.fragment.AccountsFragment
import com.example.banco_papesa.fragment.AccountsMovementsFragment
import com.example.bancoapiprofe.pojo.Cuenta


class GlobalPositionDetailsActivity : AppCompatActivity(), OnClickListener {

    private lateinit var binding: ActivityGlobalPositionDetailsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityGlobalPositionDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val cuenta = intent.getSerializableExtra("cuenta") as Cuenta
        val fragment = AccountsMovementsFragment.newInstance(cuenta)
        //supportFragmentManager.findFragmentById(binding.fragmentMovement.id) as AccountsMovementsFragment

        supportFragmentManager
            .beginTransaction()
            .add(binding.fragmentMovement.id, fragment, AccountsMovementsFragment::class.java.name)
            .commit()

        fragment.setListener(this)


        binding.bottomNavigation?.setOnNavigationItemSelectedListener {
            it.isChecked = true
            when (it.itemId) {
                R.id.filter_all -> {
                    fragment.setFilter(-1)
                    supportFragmentManager
                        .beginTransaction()
                        .replace(binding.fragmentMovement.id, fragment)
                        .commit()
                }
                R.id.filter_1 -> {
                    fragment.setFilter(0)
                    supportFragmentManager
                        .beginTransaction()
                        .replace(binding.fragmentMovement.id, fragment)
                        .commit()
                }
                R.id.filter_2 -> {
                    fragment.setFilter(1)
                    supportFragmentManager
                        .beginTransaction()
                        .replace(binding.fragmentMovement.id, fragment)
                        .commit()
                }
                R.id.filter_3 -> {
                    fragment.setFilter(2)
                    supportFragmentManager
                        .beginTransaction()
                        .replace(binding.fragmentMovement.id, fragment)
                        .commit()
                }
            }
            false
        }
    }

    override fun onClick(obj: Any?) {
        TODO("Not yet implemented")
    }


}