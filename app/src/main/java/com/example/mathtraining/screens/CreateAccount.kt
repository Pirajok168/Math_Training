package com.example.mathtraining.screens

import android.util.Log
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.*
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mathtraining.R
import com.example.mathtraining.math.theme.MathTheme
import com.example.mathtraining.math.theme.baseLightPalette
import com.example.mathtraining.nav.AUTH_GRAPH_ROUTE
import com.example.mathtraining.nav.Screens
import com.example.mathtraining.viewmodel.CreateAccountViewModel
import com.google.accompanist.insets.LocalWindowInsets

@Composable
fun CreateAccount(
    onNavigation: (screen: Screens, popUpTo: String) -> Unit
) {
    Scaffold(
        backgroundColor = MathTheme.colors.backgroundCreateAccount,
    ) {
        BackgroundForInput(onNavigation)
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BackgroundForInput(
    onNavigation: (screen: Screens, popUpTo: String) -> Unit
) {


    val viewModelCreateAccount: CreateAccountViewModel = viewModel()

    val country = viewModelCreateAccount.selectedCountry
    val name = viewModelCreateAccount.nameUser
    val surname = viewModelCreateAccount.surnameUser
    val context = LocalContext.current
    val toastLabel = stringResource(id = MathTheme.localization.toastLabel)

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomEnd){
        Surface(
            color = MathTheme.colors.backgroundInputSurface,
            modifier = Modifier
                .size(600.dp),
            shape = RoundedCornerShape(topStart = 80.dp)
        ) {
            Column(
                modifier = Modifier.padding(horizontal = 70.dp, vertical = 60.dp)
            ){
                Text(
                    text = stringResource(id = MathTheme.localization.titleCreateAccount),
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 40.sp,
                    fontFamily = FontFamily.Monospace
                )


                Column(
                    modifier= Modifier
                        .fillMaxHeight()
                        .padding(top = 30.dp),
                    verticalArrangement = Arrangement.SpaceAround,

                ) {

                    Input(
                        name.value,
                        MathTheme.localization.inputName,
                        onChangeInput={
                            viewModelCreateAccount.nameUser.value = it
                        }
                    )

                    Input(
                        surname.value,
                        MathTheme.localization.inputSurname,
                        onChangeInput={
                            viewModelCreateAccount.surnameUser.value = it
                        }
                    )

                    Input(
                        country.value,
                        MathTheme.localization.inputCountry,
                        onChangeInput={
                            viewModelCreateAccount.selectedCountry.value = it
                        }
                    )

                    Row(
                        modifier=Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Text(
                            text = stringResource(id = MathTheme.localization.register),
                            fontSize = 30.sp,
                            fontFamily = FontFamily.Monospace,
                            fontWeight = FontWeight.Bold
                        )

                        Surface(
                            shape = CircleShape,
                            color = MathTheme.colors.buttonColorRegistr,
                            modifier = Modifier.size(60.dp),
                            onClick = {
                                if(name.value.isEmpty() || surname.value.isEmpty() || country.value.isEmpty()){
                                    Toast.makeText(
                                        context,
                                        toastLabel,
                                        Toast.LENGTH_SHORT).show()
                                }else{
                                    viewModelCreateAccount.createUser()
                                    onNavigation(Screens.MainScreen, AUTH_GRAPH_ROUTE)
                                }
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Filled.KeyboardArrowRight,
                                contentDescription = ""
                            )
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun Input(
    value: String,
    @StringRes label: Int,
    onChangeInput: (newValue: String) -> Unit
) {
    val colorLine = remember {
        mutableStateOf(Color.Black)
    }

    val isVisible = remember {
        mutableStateOf(false)
    }

    val color by animateColorAsState(
        targetValue = if(!isVisible.value) Color.Black else baseLightPalette.focusedInputColor,
        animationSpec = tween(150   , easing = LinearEasing),
    )

    val expanded = remember {
        mutableStateOf(false)
    }

    val rotate by animateFloatAsState(
        targetValue = if (!expanded.value) 0f else 180f,
        animationSpec = tween(500   , easing = LinearEasing),
    )



    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current
    val countryRu = stringResource(id = MathTheme.localization.countryRussia)
    val countryUSA = stringResource(id = MathTheme.localization.countryUsa)

    BasicTextField(
        value = value,
        onValueChange = { onChangeInput(it) },
        maxLines = 1,
        readOnly = label == MathTheme.localization.inputCountry,
        decorationBox = {
                innerTextField ->

            Row(modifier = Modifier
                .fillMaxWidth()
                .height(20.dp)
                .drawBehind {
                    val strokeWidth = 2 * density
                    val y = size.height - strokeWidth / 2 + 30
                    drawLine(
                        color = color,
                        Offset(0f, y),
                        Offset(size.width, y),
                        strokeWidth
                    )
                },

            ) {

                if (value.isEmpty()){
                    Text(text = stringResource(id = label), color = colorLine.value)
                }
                if(label == MathTheme.localization.inputCountry){
                    Box(modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.CenterEnd){
                        IconButton(onClick = { expanded.value = !expanded.value }) {
                            Icon(
                                imageVector = Icons.Filled.ArrowDropDown,
                                contentDescription = "",
                                modifier = Modifier.rotate(rotate)
                            )
                            DropdownMenu(
                                expanded = expanded.value,
                                onDismissRequest = { expanded.value=false }
                            ) {
                                DropdownMenuItem(onClick = {
                                    focusManager.clearFocus()
                                    onChangeInput(countryRu)
                                    expanded.value = false
                                }) {

                                    Text(text = countryRu)
                                }
                                DropdownMenuItem(onClick = {
                                    focusManager.clearFocus()
                                    onChangeInput(countryUSA)
                                    expanded.value = false
                                }) {
                                    Text(text = countryUSA)
                                }
                            }
                        }
                    }
                }
            }
            innerTextField()
        },
        modifier = Modifier
            .onFocusChanged {
                expanded.value = it.isFocused
                isVisible.value = it.isFocused
            },
        keyboardOptions= KeyboardOptions(imeAction = if(label==MathTheme.localization.inputCountry) ImeAction.Done else ImeAction.Next),
        keyboardActions = KeyboardActions(
            onDone = {
                keyboardController?.hide()
                focusManager.clearFocus()
            },

            onNext = {
                focusManager.moveFocus(FocusDirection.Down)
            }
        )

    )
}

