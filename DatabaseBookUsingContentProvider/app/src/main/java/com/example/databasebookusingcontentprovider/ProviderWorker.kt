package com.example.databasebookusingcontentprovider

interface ProviderWorker {
    fun deleteItem(id: Long)
    fun updateItem(item: Item)
}