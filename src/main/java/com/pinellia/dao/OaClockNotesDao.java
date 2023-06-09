package com.pinellia.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pinellia.entity.OaClockNotes;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

/**
 * (OaClockNotes)表数据库访问层
 *
 * @author pinellia
 * @since 2023-04-08 15:22:47
 */
public interface OaClockNotesDao extends BaseMapper<OaClockNotes> {

    //用户签到，插入数据到考勤统计表
    @Insert("INSERT INTO oa_clock_notes(user_id, punch_time) value (#{id}, NOW())")
    public boolean setClock(Long id);

    //插入数据到oa_cc联表
    @Insert("INSERT INTO oa_cc(clock_id, notes_id) values(#{clockId}, #{notesId})")
    public boolean setCreateNotes(Long clockId, Long notesId);

    //通过签到查询用户是否已签到
    @Select("SELECT\n" +
            "\toa_clock_notes.* \n" +
            "FROM\n" +
            "\toa_clock\n" +
            "\tINNER JOIN oa_cc ON oa_clock.id = oa_cc.clock_id\n" +
            "\tINNER JOIN oa_clock_notes ON oa_cc.notes_id = oa_clock_notes.id \n" +
            "WHERE\n" +
            "\toa_clock.id = #{clockId} \n" +
            "\tAND oa_clock_notes.user_id = #{id}")
    public OaClockNotes getUserClockInfo(Long clockId, Long id);

    //查询用户未签到的信息
    @Select("SELECT\n" +
            "\t* \n" +
            "FROM\n" +
            "\toa_clock_notes \n" +
            "WHERE\n" +
            "\tuser_id = #{id} \n" +
            "\tAND oa_clock_notes.is_punch = 0")
    public OaClockNotes getUserById(Long id);

}

