package asgarov.elchin.stockmarketapp.presentation.company_listings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import asgarov.elchin.stockmarketapp.domain.model.CompanyListing


@Composable
fun CompanyItem(
    company:CompanyListing,
    modifier: Modifier = Modifier
){
        Column(modifier = modifier) {
            Row(modifier = modifier.fillMaxWidth()){
                Text(text = company.name, fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp, color = MaterialTheme.colorScheme.onBackground,
                    overflow = TextOverflow.Ellipsis, maxLines = 1, modifier =  Modifier.weight(1f))
                Spacer(modifier = Modifier.width(4.dp))
                Text(text = company.exchange, fontWeight = FontWeight.Light,
                    color = MaterialTheme.colorScheme.onBackground)
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "(${company.symbol})",
                fontStyle = androidx.compose.ui.text.font.FontStyle.Italic,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground)
    }
}