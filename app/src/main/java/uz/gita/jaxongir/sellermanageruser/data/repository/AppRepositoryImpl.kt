package uz.gita.jaxongir.sellermanageruser.data.repository

import android.util.Log
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import uz.gita.jaxongir.sellermanageruser.data.models.ProductData
import uz.gita.jaxongir.sellermanageruser.data.models.toProductData
import uz.gita.jaxongir.sellermanageruser.data.source.local.pref.MyPref
import uz.gita.jaxongir.sellermanageruser.domain.repository.AppRepository
import javax.inject.Inject

class AppRepositoryImpl @Inject constructor(
    private val myPref: MyPref
): AppRepository {

    val firestore = Firebase.firestore
    val realtimeDatabase = Firebase.database.getReference("Products")
    override fun login(name: String, password: String): Flow<Result<Unit>> = callbackFlow {
        firestore.collection("Sellers")
            .whereEqualTo("sellerName", name)
            .get()
            .addOnSuccessListener {
                if (it.documents.isEmpty()) {
                    trySend(Result.failure(Exception("There is not such user")))
                } else {
                    it.documents.forEach {
                        if (it.data?.getOrDefault("password", "").toString()
                            == password
                        ) {
                            myPref.saveId(it.id)
                            trySend(Result.success(Unit))
                        } else {
                            trySend(Result.failure(Exception("Password does not match")))
                        }
                    }
                }
            }
        awaitClose()
    }

    override fun editProducts(productsData: ProductData): Flow<String> = callbackFlow{
        val updates = hashMapOf<String, Any?>(
            "id" to productsData.id,
            "name" to productsData.name,
            "count" to productsData.count,
            "initialPrice" to productsData.initialPrice,
            "soldPrice" to productsData.soldPrice,
            "isValid" to true,
            "comment" to productsData.comment,
        )
        realtimeDatabase.child(productsData.id).updateChildren(updates)
            .addOnSuccessListener {
                trySend("You have updated successfully")
            }
            .addOnFailureListener {
                trySend(it.toString())
            }

        awaitClose()
    }

    override fun deleteProduct(productsData: ProductData): Flow<Result<String>> = callbackFlow{
        realtimeDatabase.child(productsData.id)
            .removeValue().addOnSuccessListener {
                trySend(Result.success("Deleted successfully"))
            }
            .addOnFailureListener {
                trySend(Result.failure(it))
            }
        awaitClose()
    }

    override fun getAllProducts(): Flow<List<ProductData>> = callbackFlow{
        realtimeDatabase.addValueEventListener(object  : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                trySend(snapshot.children.map {
                    it.toProductData()
                })
            }

            override fun onCancelled(error: DatabaseError) {}

        })
        awaitClose()
    }
}