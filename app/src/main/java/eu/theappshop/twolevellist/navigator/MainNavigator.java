package eu.theappshop.twolevellist.navigator;

import java.util.List;

import eu.theappshop.twolevellist.data.entity.LevelListEntity;

/**
 * Created by Fedchuk Maxim on 2018-04-23.
 */

public interface MainNavigator {
    void setList(List<LevelListEntity> levelListEntities);
}
