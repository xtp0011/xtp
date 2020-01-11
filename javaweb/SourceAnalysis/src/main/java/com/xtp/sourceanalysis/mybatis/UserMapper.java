package com.xtp.sourceanalysis.mybatis;

import java.util.List;

public interface UserMapper {
    List<User> selectById(int id);
}
