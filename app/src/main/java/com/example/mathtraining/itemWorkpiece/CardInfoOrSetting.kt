package com.example.mathtraining.itemWorkpiece

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mathtraining.R
import com.example.mathtraining.math.theme.MathTheme

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CardInfoOrSetting(@StringRes label: Int, @DrawableRes icon: Int, backColor: Color, tintColor: Color, onClick: ()-> Unit) {
    Row(horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth()
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
        Surface(modifier = Modifier
            .size(50.dp)
            .alpha(0.5f),
            color = Color.LightGray,
            indication = rememberRipple(bounded = false),
            shape = RoundedCornerShape(5.dp),
            onClick = {
                onClick()
            }
        ) {
            Icon(painter = painterResource(id = R.drawable.ic_baseline_arrow_forward_ios_24),
                contentDescription ="",
                modifier = Modifier.requiredSize(20.dp)
            )
        }


    }
    Spacer(modifier = Modifier.size(20.dp))
}

