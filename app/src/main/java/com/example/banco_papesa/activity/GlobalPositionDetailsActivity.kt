package com.example.banco_papesa.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.banco_papesa.R
import com.example.banco_papesa.databinding.ActivityGlobalPositionBinding
import com.example.banco_papesa.databinding.ActivityGlobalPositionDetailsBinding
import com.example.banco_papesa.fragment.AccountsFragment
import com.example.banco_papesa.fragment.AccountsMovementsFragment
import com.example.bancoapiprofe.pojo.Cuenta


class GlobalPositionDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGlobalPositionDetailsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_global_position_details)

        binding = ActivityGlobalPositionDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val fragment = AccountsMovementsFragment.newInstance(intent.getSerializableExtra("cuenta") as Cuenta)
        //supportFragmentManager.findFragmentById(binding.fragmentMovement.id) as AccountsMovementsFragment

        supportFragmentManager
            .beginTransaction()
            .add(binding.fragmentMovement.id, fragment, AccountsMovementsFragment::class.java.name)
            .commit()
    }
}