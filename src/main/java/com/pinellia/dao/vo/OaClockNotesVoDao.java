package com.pinellia.dao.vo;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pinellia.entity.vo.OaClockNotesVo;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface OaClockNotesVoDao extends BaseMapper<OaClockNotesVo> {

    //通过id获取当前考勤的人的信息
    @Select("SELECT\n" +
            "\toa_department.department_name,\n" +
            "\toa_user.username,\n" +
            "\toa_user.`name`,\n" +
            "\toa_clock_notes.punch_time \n" +
            "FROM\n" +
            "\toa_clock\n" +
            "\tINNER JOIN oa_cc ON oa_clock.id = oa_cc.clock_id\n" +
            "\tINNER JOIN oa_clock_notes ON oa_cc.notes_id = oa_clock_notes.id\n" +
            "\tINNER JOIN oa_user ON oa_clock_notes.user_id = oa_user.id\n" +
            "\tINNER JOIN oa_ud ON oa_user.id = oa_ud.user_id\n" +
            "\tINNER JOIN oa_department ON oa_ud.department_id = oa_department.id \n" +
            "WHERE\n" +
            "\toa_clock.id = #{clockId}")
    List<OaClockNotesVo> getClock(Long clockId);
}
