package asgarov.elchin.stockmarketapp.data.repository

import asgarov.elchin.stockmarketapp.data.csv.CSVParser
import asgarov.elchin.stockmarketapp.data.csv.IntradayInfoParser
import asgarov.elchin.stockmarketapp.data.local.StockDatabase
import asgarov.elchin.stockmarketapp.data.mapper.toCompanyInfo
import asgarov.elchin.stockmarketapp.data.mapper.toCompanyListing
import asgarov.elchin.stockmarketapp.data.mapper.toCompanyListingEntity
import asgarov.elchin.stockmarketapp.data.remote.StockApi
import asgarov.elchin.stockmarketapp.domain.model.CompanyInfo
import asgarov.elchin.stockmarketapp.domain.model.CompanyListing
import asgarov.elchin.stockmarketapp.domain.model.IntradayInfo
import asgarov.elchin.stockmarketapp.domain.repository.StockRepository
import asgarov.elchin.stockmarketapp.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okio.IOException
import retrofit2.HttpException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StockRepositoryImpl  @Inject constructor(
    private val api: StockApi,
    private val db: StockDatabase,
    private val companyListingsParser: CSVParser<CompanyListing>,
    private val intradayInfoParser: CSVParser<IntradayInfo>
) :StockRepository {

    private val dao = db.dao

    override suspend fun getCompanyListings(
        fetchFromRemote: Boolean,
        query: String
    ): Flow<Resource<List<CompanyListing>>> {
        return flow {
            emit(Resource.Loading(true))
            val localListings = dao.searchCompanyListing(query)
            emit(Resource.Success(
                data = localListings.map { it.toCompanyListing() }
            ))
            val isDbEmpty = localListings.isEmpty() && query.isBlank()
            val shouldJustLoadFromCache = !isDbEmpty && !fetchFromRemote
            if (shouldJustLoadFromCache) {
                emit(Resource.Loading(false))
                return@flow
            }
            val remoteListings = try {
                val response = api.getListings()
                companyListingsParser.parse(response.byteStream())

            } catch (e: IOException) {
                emit(Resource.Error("${e.localizedMessage}"))
                null

            } catch (e: HttpException) {
                emit(Resource.Error("${e.localizedMessage}"))
                null
            }

            remoteListings?.let { listings ->
                dao.clearCompanyListings()
                dao.insertCompanyListings(
                    listings.map { it.toCompanyListingEntity() }
                )
                emit(Resource.Success(
                    data = dao.searchCompanyListing("").map { it.toCompanyListing() }
                ))
                emit(Resource.Loading(false))
            }


        }
    }

    override suspend fun getIntradayInfo(symbol: String): Resource<List<IntradayInfo>> {
        return try {
            val response = api.getIntradayInfo(symbol)
            val results = intradayInfoParser.parse(response.byteStream())
            Resource.Success(results)
        } catch (e: IOException) {
            Resource.Error("${e.localizedMessage}")
        } catch (e: HttpException) {
            Resource.Error("${e.localizedMessage}")
        }

    }


    override suspend fun getCompanyInfo(symbol: String): Resource<CompanyInfo> {
        return try {
            val result = api.getCompanyInfo(symbol)
            Resource.Success(result.toCompanyInfo())
        } catch(e: IOException) {
            e.printStackTrace()
            Resource.Error("${e.localizedMessage}")
        } catch(e: HttpException) {
            e.printStackTrace()
            Resource.Error("${e.localizedMessage}")
        }
    }
}
