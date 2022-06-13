package com.homindolentrahar.moment.features.wishlist.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.homindolentrahar.moment.features.wishlist.data.mapper.toDocumentSnapshot
import com.homindolentrahar.moment.features.wishlist.data.mapper.toWishlist
import com.homindolentrahar.moment.features.wishlist.data.mapper.toWishlistSaving
import com.homindolentrahar.moment.features.wishlist.data.remote.dto.WishlistDto
import com.homindolentrahar.moment.features.wishlist.data.remote.dto.WishlistSavingDto
import com.homindolentrahar.moment.features.wishlist.domain.model.Wishlist
import com.homindolentrahar.moment.features.wishlist.domain.model.WishlistSaving
import com.homindolentrahar.moment.features.wishlist.domain.repository.WishlistRepository
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class WishlistRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val auth: FirebaseAuth
) : WishlistRepository {

    override suspend fun getAllWishlist(): List<Wishlist> {
        val currentUser = auth.currentUser!!
        val querySnapshot = firestore
            .document(currentUser.uid)
            .collection(WishlistDto.COLLECTION)
            .get()
            .await()

        return querySnapshot.toObjects(WishlistDto::class.java).map { it.toWishlist() }
    }

    override suspend fun getSingleWishlist(id: String): Wishlist? {
        val currentUser = auth.currentUser!!
        val documentSnapshot = firestore
            .document(currentUser.uid)
            .collection(WishlistDto.COLLECTION)
            .document(id)
            .get()
            .await()
        val wishlist = documentSnapshot.toObject(WishlistDto::class.java)

        return wishlist?.toWishlist()
    }

    override suspend fun getWishlistSavings(wishlistId: String): List<WishlistSaving> {
        val currentUser = auth.currentUser!!
        val querySnapshot = firestore
            .document(currentUser.uid)
            .collection(WishlistDto.COLLECTION)
            .document(wishlistId)
            .collection(WishlistSavingDto.COLLECTION)
            .get()
            .await()
        val savings = querySnapshot.toObjects(WishlistSavingDto::class.java)

        return savings.map { it.toWishlistSaving() }
    }

    override suspend fun getSingleSaving(
        id: String,
        wishlistId: String
    ): WishlistSaving? {
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

        return saving?.toWishlistSaving()
    }

    override suspend fun saveWishlist(wishlist: Wishlist) {
        val currentUser = auth.currentUser!!
        val wishlistDto = WishlistDto.fromWishlist(wishlist)

        firestore
            .document(currentUser.uid)
            .collection(WishlistDto.COLLECTION)
            .document(wishlistDto.id)
            .set(wishlistDto.toDocumentSnapshot())
            .await()
    }

    override suspend fun saveSaving(
        wishlistId: String,
        saving: WishlistSaving
    ) {
        val currentUser = auth.currentUser!!
        val savingDto = WishlistSavingDto.fromWishlistSaving(saving)

        firestore
            .document(currentUser.uid)
            .collection(WishlistDto.COLLECTION)
            .document(wishlistId)
            .collection(WishlistSavingDto.COLLECTION)
            .document(savingDto.id)
            .set(savingDto.toDocumentSnapshot())
            .await()
    }

    override suspend fun updateWishlist(id: String, wishlist: Wishlist) {
        val currentUser = auth.currentUser!!
        val wishlistDto = WishlistDto.fromWishlist(wishlist)

        firestore
            .document(currentUser.uid)
            .collection(WishlistDto.COLLECTION)
            .document(id)
            .update(wishlistDto.toDocumentSnapshot())
            .await()
    }

    override suspend fun updateSaving(
        wishlistId: String,
        savingId: String,
        saving: WishlistSaving
    ) {
        val currentUser = auth.currentUser!!
        val savingDto = WishlistSavingDto.fromWishlistSaving(saving)

        firestore
            .document(currentUser.uid)
            .collection(WishlistDto.COLLECTION)
            .document(wishlistId)
            .collection(WishlistSavingDto.COLLECTION)
            .document(savingId)
            .update(savingDto.toDocumentSnapshot())
            .await()
    }

    override suspend fun removeWishlist(wishlistId: String) {
        val currentUser = auth.currentUser!!

        firestore
            .document(currentUser.uid)
            .collection(WishlistDto.COLLECTION)
            .document(wishlistId)
            .delete()
            .await()
    }

    override suspend fun removeSaving(wishlistId: String, savingId: String) {
        val currentUser = auth.currentUser!!

        firestore
            .document(currentUser.uid)
            .collection(WishlistDto.COLLECTION)
            .document(wishlistId)
            .collection(WishlistSavingDto.COLLECTION)
            .document(savingId)
            .delete()
            .await()

    }
}