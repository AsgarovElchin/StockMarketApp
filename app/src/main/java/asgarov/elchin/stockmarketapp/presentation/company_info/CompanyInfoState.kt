package asgarov.elchin.stockmarketapp.presentation.company_info

import asgarov.elchin.stockmarketapp.domain.model.CompanyInfo
import asgarov.elchin.stockmarketapp.domain.model.IntradayInfo

data class CompanyInfoState(
    val stockInfos: List<IntradayInfo> = emptyList(),
    val company: CompanyInfo? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)
