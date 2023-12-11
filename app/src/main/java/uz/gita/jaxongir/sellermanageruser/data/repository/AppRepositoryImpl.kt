package uz.gita.jaxongir.sellermanageruser.data.repository

import android.util.Log
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
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
    val realDb = FirebaseDatabase.getInstance()
    override fun login(name: String, password: String): Flow<Result<Unit>> = callbackFlow {
        firestore.collection("Sellers")
            .whereEqualTo("name", name)
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

    override fun getSoldProducts(name: String): Flow<List<ProductData>>  = callbackFlow{
        realDb.getReference("Sellers").child(name)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    trySend(snapshot.children.map { it.toProductData() })
                }

                override fun onCancelled(error: DatabaseError) {}
            })

        awaitClose()
    }

    override suspend fun sellProduct(productData: ProductData) {
        val productRef = realDb.getReference("Sellers").child(productData.sellerName).child(productData.name)

        productRef.get().addOnSuccessListener { dataSnapshot ->
            val currentCount = dataSnapshot.child("sellCount").getValue(Int::class.java) ?: 0
            val newCount = currentCount + 1

            val updates = hashMapOf<String, Any?>(
                "id" to productData.id,
                "name" to productData.name,
                "sellCount" to newCount,
                "initialPrice" to productData.initialPrice,
                "soldPrice" to productData.soldPrice,
                "isValid" to true,
                "comment" to productData.comment,
                "sellerName" to productData.sellerName
            )

            productRef.updateChildren(updates).addOnSuccessListener {

            }


        }.addOnFailureListener {

        }


        val oldProductRef = realDb.getReference("products").child(productData.name)

        val oldUpdates = hashMapOf<String, Any?>(
            "id" to productData.id,
            "name" to productData.name,
            "count" to --productData.count,
            "initialPrice" to productData.initialPrice,
            "isValid" to true,
            "comment" to productData.comment,
            "sellerName" to productData.sellerName
        )

        oldProductRef.updateChildren(oldUpdates).addOnSuccessListener {

        }
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