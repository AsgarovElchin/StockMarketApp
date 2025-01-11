package asgarov.elchin.stockmarketapp.data.mapper

import asgarov.elchin.stockmarketapp.data.local.CompanyListingEntity
import asgarov.elchin.stockmarketapp.data.remote.dto.CompanyInfoDto
import asgarov.elchin.stockmarketapp.domain.model.CompanyInfo
import asgarov.elchin.stockmarketapp.domain.model.CompanyListing

fun CompanyListingEntity.toCompanyListing():CompanyListing{
    return CompanyListing(
        name = name,
        symbol = symbol,
        exchange = exchange
    )
}

fun CompanyListing.toCompanyListingEntity():CompanyListingEntity{
    return CompanyListingEntity(
        name = name,
        symbol = symbol,
        exchange = exchange
    )
}

fun CompanyInfoDto.toCompanyInfo(): CompanyInfo {
    return CompanyInfo(
        symbol = symbol ?: "",
        description = description ?: "",
        name = name ?: "",
        country = country ?: "",
        industry = industry ?: ""

    )

}