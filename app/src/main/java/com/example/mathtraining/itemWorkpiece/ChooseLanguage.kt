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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mathtraining.R
import com.example.mathtraining.math.theme.LocaleApp
import com.example.mathtraining.math.theme.MathTheme

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ChooseLanguage(@StringRes label: Int
                   , @DrawableRes icon: Int
                   , image: Int
                   , backColor: Color
                   , tintColor: Color
                   , onChange: (locale: LocaleApp)-> Unit) {

    var expanded by remember { mutableStateOf(false) }
    


    Row(horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .clickable { expanded = true },
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
            modifier = Modifier.size(48.dp)
        ) {
            Image(painter = painterResource(id = image)
           , contentDescription = "")
            
            DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                DropdownMenuItem(onClick = {
                    onChange(LocaleApp.Russian)
                    expanded = false
                }) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Image(painter = painterResource(id = R.drawable.russia),
                            contentDescription = "",
                            modifier = Modifier
                                .size(48.dp)
                                .padding(end = 10.dp))
                        Text(text = "Русский")
                    }
                }

                DropdownMenuItem(onClick = {
                    onChange(LocaleApp.English)
                    expanded = false
                }) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Image(painter = painterResource(id = R.drawable.united_states),
                            contentDescription = "",
                            modifier = Modifier
                                .size(48.dp)
                                .padding(end = 10.dp))
                        Text(text = "English")
                    }

                }

            }
        }


    }
}