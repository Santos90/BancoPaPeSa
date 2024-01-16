package com.example.banco_papesa.activity


import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.banco_papesa.databinding.ActivityMainBinding
import com.example.bancoapiprofe.pojo.Cliente


import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.example.banco_papesa.R
import com.example.banco_papesa.adapter.OnClickListener
import com.example.banco_papesa.fragment.AccountsFragment
import com.example.banco_papesa.fragment.AccountsMovementsFragment
import com.example.banco_papesa.fragment.CajerosListFragment
import com.example.banco_papesa.fragment.MainFragment
import com.example.banco_papesa.fragment.MovementsFragment
import com.example.banco_papesa.fragment.PasswordChangeFragment
import com.example.banco_papesa.fragment.TransferFragment
import com.example.bancoapiprofe.pojo.Cuenta
import com.example.bancoapiprofe.pojo.Movimiento
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() , NavigationView.OnNavigationItemSelectedListener, OnClickListener{

	private lateinit var mainBinding: ActivityMainBinding
	private lateinit var cliente: Cliente

	private lateinit var frgMain: MainFragment
	private lateinit var frgGlobal: AccountsFragment
	private lateinit var frgTrasfer: TransferFragment
	private lateinit var frgPassword: PasswordChangeFragment
	private lateinit var frgMovments: MovementsFragment
	private lateinit var frgSettings: SettingsActivity.SettingsFragment
	private lateinit var frgCajeros: CajerosListFragment


	private lateinit var drawerLayout: DrawerLayout
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		mainBinding = ActivityMainBinding.inflate(layoutInflater)
		setContentView(mainBinding.root)

		cliente = intent.
		getSerializableExtra("cliente") as Cliente


		drawerLayout = findViewById<DrawerLayout>(mainBinding.drawerLayout.id)

		val toolbar = findViewById<Toolbar>(R.id.toolbar)
		setSupportActionBar(toolbar)

		val navigationView = findViewById<NavigationView>(R.id.nav_view)
		navigationView.setNavigationItemSelectedListener(this)

		val toggle = ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_nav, R.string.close_nav)
		drawerLayout.addDrawerListener(toggle)
		toggle.syncState()
		frgGlobal = AccountsFragment.newInstance(cliente)
		frgGlobal.setListener(this)

		frgMain = MainFragment.newInstance(cliente)
		frgTrasfer = TransferFragment.newInstance(cliente)
		frgPassword = PasswordChangeFragment.newInstance(cliente)
		frgMovments = MovementsFragment.newInstance(cliente)
		frgCajeros = CajerosListFragment()

		frgSettings = SettingsActivity.SettingsFragment()

		if (savedInstanceState == null) {
			supportFragmentManager.beginTransaction()
				.replace(R.id.fragment_container_big, frgMain)
				.commit()
			navigationView.setCheckedItem(R.id.nav_home)
		}
	}
	override fun onNavigationItemSelected(item: MenuItem): Boolean {
		var horizontal = resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
		var fragmentContainerAdecuado = R.id.fragment_container_big;
		if (horizontal){
			Log.i("Orientacion", "Horizontal")
			fragmentContainerAdecuado = R.id.fragment_container
		} else Log.i("Orientacion", "Vertical")

		when (item.itemId) {
			R.id.nav_home -> 			launchFragment(frgMain)
			R.id.nav_global_position -> launchFragment(frgGlobal)
			R.id.nav_transfer -> 		launchFragment(frgTrasfer)
			R.id.nav_password_change -> launchFragment(frgPassword)
			R.id.nav_movements -> 		launchFragment(frgMovments)
			R.id.nav_atm -> 			launchFragment(frgCajeros)
			R.id.nav_settings -> 		launchFragment(frgSettings)
			R.id.nav_exit -> {
				Toast.makeText(this, "Logout!", Toast.LENGTH_SHORT).show()
				finish()
			}
		}

		drawerLayout.closeDrawer(GravityCompat.START)
		return true

	}

	private fun launchFragment(fragment : Fragment) {
		supportFragmentManager.beginTransaction()
			.replace(R.id.fragment_container_big, fragment)
			.addToBackStack(null).commit()
	}

	override fun onClick(obj: Any?) {
		if (obj is Cuenta) {
			Log.i("Tipo de objeto", "Es una Cuenta")
			//Creamos la instancia del fragment
			var frgMovements = AccountsMovementsFragment.newInstance(obj)

			var horizontal = resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

			if (horizontal) {

				Log.i("Landscape:", true.toString())
				supportFragmentManager
					.beginTransaction()
					.replace(
						mainBinding.fragmentContainerDetail!!.id,
						frgMovements)
					.commit()

			} else {
				supportFragmentManager
					.beginTransaction()
					.replace(
						mainBinding.fragmentContainerBig!!.id,
						frgMovements
					)
					.addToBackStack("movimientos")
					.commit()
				Log.i("Landscape:", false.toString())
			}
		} else if (obj is Movimiento) Log.i("Tipo de objeto", "Es un Movimiento") //Puc gestionar diferents objectes amb el mateix listener
	}


	override fun onBackPressed() {
		if (drawerLayout.isDrawerOpen(GravityCompat.START)) {

			drawerLayout.closeDrawer(GravityCompat.START)
		} else if (supportFragmentManager.backStackEntryCount > 0) {

			supportFragmentManager.popBackStack()

		} else {
			onBackPressedDispatcher.onBackPressed()
		}
	}
}