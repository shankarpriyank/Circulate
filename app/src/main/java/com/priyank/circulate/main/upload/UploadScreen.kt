package com.priyank.circulate.main.upload

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.priyank.circulate.R
import com.priyank.circulate.UpdateNotificationService
import com.priyank.circulate.main.MainViewModel
import com.priyank.circulate.ui.theme.Lato
import com.priyank.circulate.ui.theme.PrimaryOrange
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@Composable
fun UploadScreen(uploadViewModel: MainViewModel = hiltViewModel()) {
    val context = LocalContext.current.applicationContext
    val service = UpdateNotificationService(context)
    var selectedImageUri by remember {
        mutableStateOf<Uri?>(null)
    }
    val singlePhotoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri ->
            selectedImageUri = uri
        }
    )

    LaunchedEffect(key1 = true) {
        uploadViewModel.eventFlow.collectLatest { event ->
            when (event) {
                is MainViewModel.UIEvent.ShowToast -> {
                    Toast.makeText(context, event.message, Toast.LENGTH_LONG).show()
                }
                is MainViewModel.UIEvent.ShowNotification -> {
                    service.showNotification(event.message)
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .heightIn(min = 384.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(painter = painterResource(id = R.drawable.ic_curious), contentDescription = "image")
        var text by remember { mutableStateOf(TextFieldValue("")) }
        var isFocused by remember { mutableStateOf(false) }
        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 164.dp)
                .padding(start = 16.dp, bottom = 16.dp, end = 16.dp, top = 16.dp)
                .border(
                    width = 1.dp,
                    color = Color.Black,
                    shape = RoundedCornerShape(8.dp)
                ),
            value = text,
            onValueChange = {
                text = it
            },
            label = {

                Text(text = "Add Description(Required)", color = Gray)
            },
            placeholder = {
                Text(text = "Something Interesting")
            },
            shape = RoundedCornerShape(8),
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = White,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            )
        )
        val textbelow = buildAnnotatedString {
            withStyle(
                style = SpanStyle(
                    color = Color.Black,
                    fontFamily = Lato,
                    fontWeight = FontWeight.Bold
                )
            ) {
                append("Add a photo ")
            }

            withStyle(style = SpanStyle(color = Color.Gray, fontFamily = Lato)) {
                append("(Optional)")
            }
        }

        Text(
            text = textbelow,
            fontSize = 18.sp,
            modifier = Modifier.padding(end = 180.dp, bottom = 16.dp)
        )

        AsyncImage(
            contentScale = ContentScale.Fit,
            model = selectedImageUri ?: R.drawable.camera,
            contentDescription = "image",
            modifier = Modifier
                .width(100.dp)
                .height(100.dp)
                .clickable {
                    singlePhotoPickerLauncher.launch(
                        PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                    )
                }
        )
        Button(
            onClick = { GlobalScope.launch { uploadViewModel.upload(selectedImageUri, text.text) } },
            colors = ButtonDefaults.buttonColors(backgroundColor = PrimaryOrange),
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(text = "Create Post", color = White, fontSize = 18.sp)
        }
    }
}
