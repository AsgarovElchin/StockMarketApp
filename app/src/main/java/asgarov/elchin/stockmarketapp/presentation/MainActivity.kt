package asgarov.elchin.stockmarketapp.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import asgarov.elchin.stockmarketapp.presentation.company_listings.CompanyListingsScreen
import asgarov.elchin.stockmarketapp.presentation.ui.theme.StockMarketAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            StockMarketAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Navigation(
                        modifier = Modifier.padding(innerPadding)
                    )


                }
            }
        }
    }
}



@Composable
fun Navigation(modifier: Modifier = Modifier){
    val navController = rememberNavController()

    NavHost(navController, startDestination = "company_listings_screen", modifier = modifier){
        composable("company_listings_screen"){
            CompanyListingsScreen(navController)
        }

    }
}