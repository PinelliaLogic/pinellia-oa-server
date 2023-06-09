package com.pinellia.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pinellia.entity.OaClock;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * (OaClock)表数据库访问层
 *
 * @author pinellia
 * @since 2023-04-08 15:22:46
 */
public interface OaClockDao extends BaseMapper<OaClock> {

    //查询进行中的签到
    @Select("SELECT * FROM oa_clock WHERE status = 1")
    public OaClock getClock();

    //更新签到人数
    @Update("UPDATE oa_clock SET count = (SELECT count(*) FROM oa_cc WHERE oa_clock.id = oa_cc.clock_id)")
    public void updateCount();

    //删除签到，同时删除vo表数据
    @Delete("DELETE FROM oa_cc WHERE clock_id = #{clockId}")
    public void deleteClock(Long clockId);

    //删除签到，同时删除oa_clock_notes表数据
    @Delete("DELETE oa_clock_notes \n" +
            "FROM\n" +
            "\toa_clock_notes\n" +
            "\tINNER JOIN oa_cc ON oa_clock_notes.id = oa_cc.notes_id\n" +
            "\tINNER JOIN oa_clock ON oa_cc.clock_id = oa_clock.id \n" +
            "WHERE\n" +
            "\toa_clock.id = #{clcokId}")
    public void deleteClockNotes(Long clockId);
}

