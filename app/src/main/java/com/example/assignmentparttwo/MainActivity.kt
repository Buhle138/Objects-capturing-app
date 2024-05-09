package com.example.assignmentparttwo


import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.assignmentparttwo.categoryScreen.CategoryPage
import com.example.assignmentparttwo.imageGenerator.DictionaryViewModel
import com.example.assignmentparttwo.itemCounter.CounterViewModel
import com.example.assignmentparttwo.itemsScreen.ItemScreen
import com.example.assignmentparttwo.location.LocationViewModel
import com.example.assignmentparttwo.ui.theme.AssignmentPartTwoTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val context: Context = this
        setContent {
            val viewModelLocation: LocationViewModel = viewModel()
            val categoryScreenViewModel by lazy{
                ViewModelProvider(this).get(CategoryScreenViewModel::class.java)
            }
            val myViewModelDictionary by lazy{
                ViewModelProvider(this).get(DictionaryViewModel::class.java)
            }
            val myViewModelCounter by lazy{
                ViewModelProvider(this).get(CounterViewModel::class.java)
            }


            AssignmentPartTwoTheme {
                // A surface container using the 'background' color from the theme
                Surface(
//                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MyNavigation(context, viewModelLocation, categoryScreenViewModel, myViewModelDictionary, myViewModelCounter)

                }
            }
        }
    }
}

@Composable
fun MyNavigation(context: Context, viewModel: LocationViewModel, categoryScreenViewModel: CategoryScreenViewModel, myViewModelDictionary: DictionaryViewModel, myCounterViewModel: CounterViewModel) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "itemsscreen") {
        composable("loginscreen") {
            LoginScreen(
                context = context,
                viewModel = viewModel,
                categoryScreenViewModel = categoryScreenViewModel,
                myViewModelDictionary = myViewModelDictionary,
                myCounterViewModel = myCounterViewModel
            ) {
                navController.navigate("categorypage")
            }
        }
        composable("categorypage") {
            CategoryPage(myViewModelDictionary, categoryScreenViewModel, navController) {
                navController.navigate("loginscreen")
            }
        }
        composable("itemsscreen"){
            ItemScreen( myViewModelDictionary = myViewModelDictionary, navController){
                navController.navigate("categorypage")
            }
        }

    }
}
/*IDEAS TO TAKE THE NAVIGATIOIN ISSUE*/
/*CREATE A BOOLEAN VALUE FOR EACH CATEGORY ITEM EACH TIME YOU'RE ON THAT CATEGORY IT'S BOOLEAN
 WILL BE TRUE AND THE REST OF THE CATEGORIES WILL BE FALSE.
* */
/**/
