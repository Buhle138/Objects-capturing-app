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
import com.example.assignmentparttwo.cameraFeature.CameraViewModel
import com.example.assignmentparttwo.categoryScreen.CategoryPage
import com.example.assignmentparttwo.imageGenerator.DictionaryViewModel
import com.example.assignmentparttwo.itemCounter.CounterViewModel
import com.example.assignmentparttwo.itemsScreen.ItemScreen
import com.example.assignmentparttwo.itemsScreen.mapfeature.LocationUtils
import com.example.assignmentparttwo.itemsScreen.mapfeature.NewLocationViewModel
import com.example.assignmentparttwo.location.LocationViewModel
import com.example.assignmentparttwo.progressPage.MainScreen
import com.example.assignmentparttwo.ui.theme.AssignmentPartTwoTheme
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date


class MainActivity : ComponentActivity() {
    private val auth: FirebaseAuth by lazy { Firebase.auth }
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
            val myViewModelCamera by lazy{
                ViewModelProvider(this).get(CameraViewModel::class.java)
            }



            val myViewModelLocation by lazy {
                ViewModelProvider(this).get(NewLocationViewModel::class.java)
            }


            AssignmentPartTwoTheme {
                // A surface container using the 'background' color from the theme
                Surface(
//                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MyNavigation(
                        context,
                        viewModelLocation,
                        categoryScreenViewModel,
                        myViewModelDictionary,
                        myViewModelCounter,
                        myViewModelCamera,
                        myViewModelLocation
                    )

                }
            }
        }
    }
}



@Composable
fun MyNavigation(
    context: Context,
    viewModel: LocationViewModel,
    categoryScreenViewModel: CategoryScreenViewModel,
    myViewModelDictionary: DictionaryViewModel,
    myCounterViewModel: CounterViewModel,
    myViewModelCamera: CameraViewModel,
    myViewModelLocation: NewLocationViewModel
) {
    val localUtils = LocationUtils(context)
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "loginscreen") {
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
            CategoryPage(context,myViewModelDictionary, categoryScreenViewModel, navController) {
                navController.navigate("loginscreen")
            }
        }
        composable("itemsscreen"){
            ItemScreen(context, myViewModelDictionary = myViewModelDictionary, myViewModelCamera, categoryScreenViewModel ,navController,localUtils, myViewModelLocation){
                navController.navigate("categorypage")
            }
        }
        composable("progressbarwithstates"){
            MainScreen(navController, myViewModelDictionary)
        }
//        composable("login"){
//            Login(navController, myViewModelAuthenticate){
//                navController.navigate("loginscreen")
//            }
//        }




    }
}


fun Context.createImageFile(): File {

    val timeStamp = SimpleDateFormat("yyyy_MM_dd_HH:mm:ss").format(Date())
    val imageFileName = "JPEG_" + timeStamp + "_"
    val image = File.createTempFile(
        imageFileName,
        ".jpg",
        externalCacheDir
    )
    return image
}


/*IDEAS TO TAKE THE NAVIGATIOIN ISSUE*/
/*CREATE A BOOLEAN VALUE FOR EACH CATEGORY ITEM EACH TIME YOU'RE ON THAT CATEGORY IT'S BOOLEAN
 WILL BE TRUE AND THE REST OF THE CATEGORIES WILL BE FALSE.
* */
/**/