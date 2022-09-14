package xyz.teamgravity.encrypteddatastore.injection.provide

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import xyz.teamgravity.encrypteddatastore.core.util.CryptoManager
import java.security.KeyStore
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApplicationModule {

    @Provides
    @Singleton
    fun provideKeyStore(): KeyStore = KeyStore.getInstance(CryptoManager.ANDROID_KEYSTORE).apply {
        load(null)
    }

    @Provides
    @Singleton
    fun provideCryptoManager(keyStore: KeyStore): CryptoManager = CryptoManager(keyStore)
}