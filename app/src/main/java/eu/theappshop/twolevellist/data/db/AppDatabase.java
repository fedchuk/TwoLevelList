package eu.theappshop.twolevellist.data.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import eu.theappshop.twolevellist.data.dao.LevelListDao;
import eu.theappshop.twolevellist.data.entity.LevelListEntity;
import eu.theappshop.twolevellist.utils.AppConstants;

/**
 * Created by Fedchuk Maxim on 2018-04-24.
 */

@Database(entities = {LevelListEntity.class},
        version = AppDatabase.VERSION, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    static final int VERSION = 1;
    private static final String DB_NAME = AppConstants.DB_NAME;
    private static volatile AppDatabase instance;

    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = create(context);
        }
        return instance;
    }

    private static AppDatabase create(final Context context) {
        return Room.databaseBuilder(
                context,
                AppDatabase.class,
                DB_NAME)
                .fallbackToDestructiveMigration() //temporary
                .allowMainThreadQueries() //temporary
                .build();
    }

    public abstract LevelListDao getLevelListDao();
}
