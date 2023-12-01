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
import com.example.banco_papesa.R
import com.example.banco_papesa.adapter.OnClickListener
import com.example.banco_papesa.fragment.AccountsFragment
import com.example.banco_papesa.fragment.AccountsMovementsFragment
import com.example.banco_papesa.fragment.MainFragment
import com.example.bancoapiprofe.pojo.Cuenta
import com.example.bancoapiprofe.pojo.Movimiento
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() , NavigationView.OnNavigationItemSelectedListener, OnClickListener{

    private lateinit var mainBinding: ActivityMainBinding
    private lateinit var cliente: Cliente
    private lateinit var frgMain: MainFragment
    private lateinit var frgGlobal: AccountsFragment

    private lateinit var drawerLayout: DrawerLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)

        cliente = intent.getSerializableExtra("cliente") as Cliente


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

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container_big, frgMain).commit()
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
            R.id.nav_home ->
                supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container_big, frgMain)
                .addToBackStack(null).commit()
            R.id.nav_global_position -> {
                supportFragmentManager.beginTransaction().hide(frgMain).commit()
                supportFragmentManager.beginTransaction()
                    .replace(fragmentContainerAdecuado, frgGlobal)
                    .addToBackStack(null).commit()
            }
            R.id.nav_exit -> {
                Toast.makeText(this, "Logout!", Toast.LENGTH_SHORT).show()
                finish()
            }
        }

        drawerLayout.closeDrawer(GravityCompat.START)
        return true

    }

    override fun onClick(obj: Any?) {
        if (obj is Cuenta) {
            Log.i("Tipo de objeto", "Es una Cuenta")
            //Creamos la instancia del fragment
            var frgMovements = AccountsMovementsFragment.newInstance(obj as Cuenta)

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
                        mainBinding.fragmentContainerBig.id,
                        frgMovements
                    )
                    .addToBackStack(null)
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