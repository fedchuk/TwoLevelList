package eu.theappshop.twolevellist.data.dao;


import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import eu.theappshop.twolevellist.data.entity.LevelListEntity;
import io.reactivex.Flowable;

/**
 * Created by Fedchuk Maxim on 2018-04-24.
 */

@Dao
public interface LevelListDao {
    @Insert
    void addListAll(List<LevelListEntity> levelListEntities);

    @Update
    int updateList(LevelListEntity levelListEntity);

    @Query("DELETE FROM levellist")
    void deleteListAll();

    @Query("SELECT * FROM levellist")
    Flowable<List<LevelListEntity>> getAllList();

    @Query("UPDATE levellist SET checks = 0 WHERE checks = 1")
    void clearChecks();
}
