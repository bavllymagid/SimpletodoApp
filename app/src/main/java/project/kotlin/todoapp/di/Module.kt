package project.kotlin.todoapp.di

import android.content.Context
import androidx.room.Room
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import project.kotlin.todoapp.data.api.TasksInterface
import project.kotlin.todoapp.data.dp.TaskDB
import project.kotlin.todoapp.data.dp.TaskDao
import project.kotlin.todoapp.data.mapper.TaskMapperToData
import project.kotlin.todoapp.data.mapper.TaskMapperToDomain
import project.kotlin.todoapp.data.repository.TasksLocalImpl
import project.kotlin.todoapp.data.repository.TasksRemoteImpl
import project.kotlin.todoapp.data.repository.TasksRepoImpl
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object Module {


    fun chuckProvider(@ApplicationContext context: Context) : OkHttpClient =
        OkHttpClient.Builder()
        .addInterceptor(
            ChuckerInterceptor.Builder(context)
                .collector(ChuckerCollector(context))
                .maxContentLength(250000L)
                .redactHeaders(emptySet())
                .alwaysReadResponseBody(false)
                .build()
        )
        .build()

    @Singleton
    @Provides
    fun getRetrofit(@ApplicationContext context: Context): TasksInterface = Retrofit.Builder()
        .client(chuckProvider(context))
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl("https://api-nodejs-todolist.herokuapp.com/")
        .build().create(TasksInterface::class.java)


    @Singleton
    @Provides
    fun getDB(@ApplicationContext context: Context): TaskDao = Room.databaseBuilder(
        context.applicationContext,
        TaskDB::class.java,
        "tasksDB"
    ).fallbackToDestructiveMigration()
        .build().tasksDao()

    @Singleton
    @Provides
    fun provideLocalData(taskDB: TaskDao) : TasksLocalImpl{
        return TasksLocalImpl(taskDB)
    }

    @Singleton
    @Provides
    fun provideRemoteData(tasksApi: TasksInterface) : TasksRemoteImpl{
        return TasksRemoteImpl(tasksApi)
    }


    @Singleton
    @Provides
    fun provideRepository(tasksLocalImpl: TasksLocalImpl, tasksRemoteImpl: TasksRemoteImpl,taskMapperToData:TaskMapperToData, taskMapperToDomain:TaskMapperToDomain) : TasksRepoImpl {
        return TasksRepoImpl(tasksLocalImpl, tasksRemoteImpl,taskMapperToData,taskMapperToDomain)
    }

    @Singleton
    @Provides
    fun provideDataToDomainMapper(): TaskMapperToDomain{
        return TaskMapperToDomain()
    }

    @Singleton
    @Provides
    fun provideDataToDataMapper(): TaskMapperToData {
        return TaskMapperToData()
    }

}