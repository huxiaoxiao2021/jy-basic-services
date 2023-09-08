package com.jdl.basic.provider.core.provider.attBlackList;

import com.jdl.basic.api.domain.attBlackList.AttendanceBlackList;
import com.jdl.basic.api.domain.attBlackList.AttendanceBlackListCondition;
import com.jdl.basic.api.service.attBlackList.AttendanceBlackListJsfService;
import com.jdl.basic.common.utils.PageDto;
import com.jdl.basic.common.utils.Result;
import com.jdl.basic.provider.core.service.attBlackList.AttendanceBlackListService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@Slf4j
public class AttendanceBlackListJsfServiceImpl implements AttendanceBlackListJsfService {

    @Autowired
    private AttendanceBlackListService attendanceBlackListService;


    @Override
    public Result<Integer> add(AttendanceBlackList attendanceBlackList) {
        int added =attendanceBlackListService.add(attendanceBlackList);
        return Result.success(added);
    }

    @Override
    public Result<Integer> modify(AttendanceBlackList attendanceBlackList) {
        int modified = attendanceBlackListService.modify(attendanceBlackList);
        return Result.success(modified);
    }

    @Override
    public Result<Integer> remove(int id) {
        int removed =attendanceBlackListService.removeById(id);
        return Result.success(removed);
    }

    @Override
    public Result<PageDto<AttendanceBlackList>> queryByCondition(AttendanceBlackListCondition query) {
        List<AttendanceBlackList> list = attendanceBlackListService.queryByCondition(query);
        int total = attendanceBlackListService.countByCondition(query);
        PageDto<AttendanceBlackList> pageDto = new PageDto<>();
        pageDto.setTotalRow(total);
        pageDto.setResult(list);
        return Result.success(pageDto);
    }

    @Override
    public Result<AttendanceBlackList> queryByUerCode(String userCode) {
        AttendanceBlackList attendanceBlackList = attendanceBlackListService.queryByUserCode(userCode);
        return Result.success(attendanceBlackList);
    }

    @Override
    public Result<Integer> countByCondition(AttendanceBlackListCondition query) {
        return Result.success(attendanceBlackListService.countByCondition(query));
    }

    @Override
    public Result<Integer> checkCountCondition(AttendanceBlackListCondition query) {
        return Result.success(attendanceBlackListService.checkCountCondition(query));
    }
}
