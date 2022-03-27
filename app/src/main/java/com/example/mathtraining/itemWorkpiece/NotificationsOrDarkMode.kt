package com.example.mathtraining.itemWorkpiece

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DarkMode(@StringRes label: Int, @DrawableRes icon: Int, backColor: Color, tintColor: Color, onChange: ()-> Unit) {
    val check = remember {
        mutableStateOf(false)
    }
    Row(horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth(),
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
                )
            }
        }

        Switch(checked = check.value, onCheckedChange = { check.value = it; onChange() })

    }

}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun Notifications(@StringRes label: Int, @DrawableRes icon: Int, backColor: Color, tintColor: Color, onChange: ()-> Unit) {
    val check = remember {
        mutableStateOf(false)
    }
    Row(horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth(),
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
                )
            }
        }

        Switch(checked = check.value, onCheckedChange = { check.value = it; onChange() })

    }

}