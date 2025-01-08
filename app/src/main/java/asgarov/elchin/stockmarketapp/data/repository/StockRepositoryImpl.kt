package asgarov.elchin.stockmarketapp.data.repository

import asgarov.elchin.stockmarketapp.data.csv.CSVParser
import asgarov.elchin.stockmarketapp.data.local.StockDatabase
import asgarov.elchin.stockmarketapp.data.mapper.toCompanyListing
import asgarov.elchin.stockmarketapp.data.mapper.toCompanyListingEntity
import asgarov.elchin.stockmarketapp.data.remote.StockApi
import asgarov.elchin.stockmarketapp.domain.model.CompanyListing
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
    private val companyListingsParser: CSVParser<CompanyListing>
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
            if(shouldJustLoadFromCache){
                emit(Resource.Loading(false))
                return@flow
            }
            val remoteListings = try {
                val response = api.getListings()
                companyListingsParser.parse(response.byteStream())

            }catch (e:IOException){
                emit(Resource.Error("${e.localizedMessage}"))
                null

            }catch (e:HttpException){
                emit(Resource.Error("${e.localizedMessage}"))
                null
            }

            remoteListings?.let { listings->
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
}