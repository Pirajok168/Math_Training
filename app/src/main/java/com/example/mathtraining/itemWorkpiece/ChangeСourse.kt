package com.example.mathtraining.itemWorkpiece

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mathtraining.R
import com.example.mathtraining.math.theme.MathTheme

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ChangeCourse(@StringRes label: Int,
                 @DrawableRes icon: Int,
                 backColor: Color,
                 tintColor: Color,
                 idCourse: Int,
                 onChange: (newValue: Int)-> Unit,
) {
    val courses: Array<String> = stringArrayResource(R.array.courser)
    var expanded by remember { mutableStateOf(false) }
    Row(horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth().clickable { expanded =   true },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Surface(
            color = Color.Transparent
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Surface(color = backColor, modifier = Modifier.size(50.dp), shape = CircleShape) {
                    Icon(painter = painterResource(id = icon)
                        , contentDescription = ""
                        , tint = tintColor
                        , modifier = Modifier.requiredSize(40.dp)
                    )
                }

                Text(text = stringResource(id = label)
                    , fontWeight = FontWeight.Bold
                    , fontSize = 18.sp
                    , modifier = Modifier.padding(start = 20.dp)
                    , color = MathTheme.colors.accentColor
                )
            }
        }

        Surface(shape = RoundedCornerShape(10.dp),
            color = Color.Transparent,
            contentColor = Color.Transparent,
            modifier = Modifier.width(95.dp)
        ) {
            Text(text = courses[idCourse], textAlign = TextAlign.End)
            

            DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {

                courses.forEachIndexed {
                       index, course ->
                    DropdownMenuItem(onClick = { expanded = false; onChange(index) }){
                        Text(text = course)
                    }
                }


            }
        }


    }
}