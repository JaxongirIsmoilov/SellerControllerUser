package uz.gita.jaxongir.sellermanageruser.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SellingPriceDialog(
    value:String,
    onClickCancel: () -> Unit
) {
    androidx.compose.material3.AlertDialog(
        onDismissRequest = {
            onClickCancel()
        },
        confirmButton = {
            Surface(
            modifier = Modifier
                .wrapContentWidth()
                .wrapContentHeight(),
            shape = MaterialTheme.shapes.large
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "enter price",
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 22.sp,

                    )
                Spacer(modifier = Modifier.size(30.dp))
                OutlinedTextField(
                    value = value,
                    onValueChange = {
//                        value = it
                    },
                    label = { Text(text = "enter price") },
                    modifier = Modifier.fillMaxWidth(),

                )
                Spacer(modifier = Modifier.size(5.dp))
                Button(
                    onClick = {
                        onClickCancel()
                    },
                    modifier = Modifier
                        .padding(top = 10.dp)
                        .height(56.dp)
                        .width(120.dp)
                        .align(Alignment.CenterHorizontally),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFA1466))
                ) {
                    Text(text = "Save", color = Color(0xFFFFFFFF))
                }
            }
        }
        }
    )
}

