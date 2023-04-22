package com.priyank.circulate.main.upload

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
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
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.graphics.Color.Companion.White
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
import com.priyank.circulate.R
import com.priyank.circulate.ui.theme.Lato
import com.priyank.circulate.ui.theme.PrimaryOrange
import kotlinx.coroutines.flow.collectLatest

@Composable
fun UploadScreen(uploadViewModel: UploadViewModel = hiltViewModel()) {
    val context = LocalContext.current.applicationContext

    LaunchedEffect(key1 = true) {
        uploadViewModel.eventFlow.collectLatest { event ->
            when (event) {
                is UploadViewModel.UIEvent.ShowToast -> {
                    Toast.makeText(context, event.message, Toast.LENGTH_LONG).show()
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

        Text(text = textbelow, fontSize = 18.sp, modifier = Modifier.padding(end = 180.dp, bottom = 16.dp))

        Image(
            painter = painterResource(id = R.drawable.camera),
            contentDescription = "image",
            modifier = Modifier
                .widthIn(min = 100.dp)
                .heightIn(min = 100.dp)
        )
        Button(onClick = { uploadViewModel.test() }, colors = ButtonDefaults.buttonColors(backgroundColor = PrimaryOrange), modifier = Modifier.fillMaxWidth().padding(16.dp)) {
        Text(text = "Create Post", color = White, fontSize = 18.sp)
    }
    }
}
