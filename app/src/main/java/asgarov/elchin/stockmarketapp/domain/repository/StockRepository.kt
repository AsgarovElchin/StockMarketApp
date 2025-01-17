package asgarov.elchin.stockmarketapp.domain.repository

import asgarov.elchin.stockmarketapp.domain.model.CompanyInfo
import asgarov.elchin.stockmarketapp.domain.model.CompanyListing
import asgarov.elchin.stockmarketapp.domain.model.IntradayInfo
import asgarov.elchin.stockmarketapp.util.Resource
import kotlinx.coroutines.flow.Flow

interface StockRepository {
    suspend fun getCompanyListings(
        fetchFromRemote:Boolean,
        query:String
    ):Flow<Resource<List<CompanyListing>>>

    suspend fun getIntradayInfo(
        symbol:String
    ):Resource<List<IntradayInfo>>

    suspend fun getCompanyInfo(
        symbol:String
    ):Resource<CompanyInfo>
}