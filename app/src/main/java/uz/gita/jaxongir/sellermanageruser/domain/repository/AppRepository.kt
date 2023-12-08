package uz.gita.jaxongir.sellermanageruser.domain.repository

import kotlinx.coroutines.flow.Flow
import uz.gita.jaxongir.sellermanageruser.data.models.ProductData

interface AppRepository {


    fun login(name: String, password: String): Flow<Result<Unit>>

   fun getSoldProducts(name : String) : Flow<List<ProductData>>

   suspend fun sellProduct(productData: ProductData)

    fun getAllProducts(): Flow<List<ProductData>>


}