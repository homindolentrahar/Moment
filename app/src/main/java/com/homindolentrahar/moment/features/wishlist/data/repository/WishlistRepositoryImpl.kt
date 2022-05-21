package com.homindolentrahar.moment.features.wishlist.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.homindolentrahar.moment.core.util.Resource
import com.homindolentrahar.moment.features.wishlist.data.mapper.toWishlist
import com.homindolentrahar.moment.features.wishlist.data.mapper.toWishlistSaving
import com.homindolentrahar.moment.features.wishlist.data.remote.dto.WishlistDto
import com.homindolentrahar.moment.features.wishlist.data.remote.dto.WishlistSavingDto
import com.homindolentrahar.moment.features.wishlist.domain.model.Wishlist
import com.homindolentrahar.moment.features.wishlist.domain.model.WishlistSaving
import com.homindolentrahar.moment.features.wishlist.domain.repository.WishlistRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class WishlistRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val auth: FirebaseAuth
) : WishlistRepository {

    override suspend fun getAllWishlist(): Flow<Resource<List<Wishlist>>> = flow {
        try {
            emit(Resource.Loading())

            val currentUser = auth.currentUser!!
            val querySnapshot = firestore
                .document(currentUser.uid)
                .collection(WishlistDto.COLLECTION)
                .get()
                .await()
            val wishlist = querySnapshot.toObjects(WishlistDto::class.java)

            emit(Resource.Success(wishlist.map { it.toWishlist() }))
        } catch (exception: Exception) {
            emit(Resource.Error(exception.localizedMessage ?: "Unexpected error"))
        }
    }

    override suspend fun getSingleWishlist(id: String): Flow<Resource<Wishlist>> = flow {
        try {
            emit(Resource.Loading())

            val currentUser = auth.currentUser!!
            val documentSnapshot = firestore
                .document(currentUser.uid)
                .collection(WishlistDto.COLLECTION)
                .document(id)
                .get()
                .await()
            val wishlist = documentSnapshot.toObject(WishlistDto::class.java)

            emit(Resource.Success(wishlist?.toWishlist()))
        } catch (exception: Exception) {
            emit(Resource.Error(exception.localizedMessage ?: "Unexpected error"))
        }
    }

    override suspend fun getWishlistSavings(wishlistId: String): Flow<Resource<List<WishlistSaving>>> =
        flow {
            try {
                emit(Resource.Loading())

                val currentUser = auth.currentUser!!
                val querySnapshot = firestore
                    .document(currentUser.uid)
                    .collection(WishlistDto.COLLECTION)
                    .document(wishlistId)
                    .collection(WishlistSavingDto.COLLECTION)
                    .get()
                    .await()
                val savings = querySnapshot.toObjects(WishlistSavingDto::class.java)

                emit(Resource.Success(savings.map { it.toWishlistSaving() }))
            } catch (exception: Exception) {
                emit(Resource.Error(exception.localizedMessage ?: "Unexpected error"))
            }
        }

    override suspend fun getSingleSaving(
        id: String,
        wishlistId: String
    ): Flow<Resource<WishlistSaving>> = flow {
        try {
            emit(Resource.Loading())

            val currentUser = auth.currentUser!!
            val documentSnapshot = firestore
                .document(currentUser.uid)
                .collection(WishlistDto.COLLECTION)
                .document(wishlistId)
                .collection(WishlistSavingDto.COLLECTION)
                .document(id)
                .get()
                .await()
            val saving = documentSnapshot.toObject(WishlistSavingDto::class.java)

            emit(Resource.Success(saving?.toWishlistSaving()))
        } catch (exception: Exception) {
            emit(Resource.Error(exception.localizedMessage ?: "Unexpected error"))
        }
    }

    override suspend fun saveWishlist(wishlist: Wishlist): Flow<Resource<Unit>> {
        TODO("Not yet implemented")
    }

    override suspend fun updateWishlist(id: String, wishlist: Wishlist): Flow<Resource<Unit>> {
        TODO("Not yet implemented")
    }

    override suspend fun removeWishlist(id: String): Flow<Resource<Unit>> {
        TODO("Not yet implemented")
    }
}