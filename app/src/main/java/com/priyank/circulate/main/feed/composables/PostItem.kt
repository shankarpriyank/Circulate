package com.priyank.circulate.main.feed.composables

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.priyank.circulate.R
import com.priyank.circulate.main.model.Comment
import com.priyank.circulate.ui.theme.Lato

@Composable
fun PostItem(
    profileImageUrl: String,
    createdBy: String,
    description: String,
    imageUrl: String?,
    timeOfPost: Long,
    comments: List<Comment>?,
    upVotes: Long = 0
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clip(RoundedCornerShape(4)),
        elevation = 8.dp, shape = RoundedCornerShape(4), backgroundColor = Color.White
    ) {
        if (imageUrl != null) {
            Column(
                modifier = Modifier
            ) {
                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(top = 4.dp, start = 4.dp)
                ) {
                    AsyncImage(
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(28.dp)
                            .clip(CircleShape)
                            .border(1.dp, Color.White, CircleShape),
                        model = profileImageUrl,
                        contentDescription = "N/A"
                    )
                    Text(
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        text = createdBy,
                        fontFamily = Lato,
                        modifier = Modifier.padding(top = 2.dp, start = 8.dp),
                        fontSize = 16.sp
                    )
                }

                AsyncImage(
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(min = 200.dp)
                        .clip(RoundedCornerShape(0)),
                    model = imageUrl, contentDescription = "postImage",
                    error = painterResource(
                        id = R.drawable.error_place
                    )
                )

                Text(color = Color.Black, fontWeight = FontWeight.Bold, text = description, modifier = Modifier.padding(start = 4.dp), fontFamily = Lato)
            }
        } else {
            Column(modifier = Modifier, horizontalAlignment = Alignment.CenterHorizontally) {
                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(top = 4.dp, start = 4.dp)
                ) {
                    AsyncImage(
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(28.dp)
                            .clip(CircleShape),
                        model = profileImageUrl,
                        contentDescription = "N/A"
                    )
                    Text(
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        text = createdBy,
                        fontFamily = Lato,
                        modifier = Modifier.padding(top = 2.dp, start = 8.dp),
                        fontSize = 16.sp
                    )
                }

                Text(color = Color.Black, fontWeight = FontWeight.Bold, text = description, modifier = Modifier.padding(start = 4.dp, bottom = 16.dp, top = 8.dp).heightIn(100.dp), fontFamily = Lato, textAlign = TextAlign.Justify)
            }
        }
    }
}
