package domilopment.composetodo.data.room

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {
    @Singleton
    @Provides
    fun provideChannelDao(appDatabase: TodoRoomDatabase): TodoDao {
        return appDatabase.todoDao()
    }

    @Provides
    @Singleton
    fun getDatabase(@ApplicationContext context: Context): TodoRoomDatabase {
        return Room.databaseBuilder(
            context, TodoRoomDatabase::class.java, "todo_db"
        ).build()
    }
}
