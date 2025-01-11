package asgarov.elchin.stockmarketapp.di

import asgarov.elchin.stockmarketapp.data.csv.CSVParser
import asgarov.elchin.stockmarketapp.data.csv.CompanyListingsParser
import asgarov.elchin.stockmarketapp.data.csv.IntradayInfoParser
import asgarov.elchin.stockmarketapp.data.repository.StockRepositoryImpl
import asgarov.elchin.stockmarketapp.domain.model.CompanyListing
import asgarov.elchin.stockmarketapp.domain.model.IntradayInfo
import asgarov.elchin.stockmarketapp.domain.repository.StockRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindCompanyListingsParser(
        companyListingsParser: CompanyListingsParser
    ):CSVParser<CompanyListing>


    @Binds
    @Singleton
    abstract fun bindStockRepository(
        stockRepositoryImpl: StockRepositoryImpl
    ):StockRepository

    @Binds
    @Singleton
    abstract fun bindIntrodayInfoParser(
        intradayInfoParser: IntradayInfoParser
    ):CSVParser<IntradayInfo>



}