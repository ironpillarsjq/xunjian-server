package com.example.server.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.server.model.entity.SysUser;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SysUserMapper extends BaseMapper<SysUser> {

    @Select("SELECT id FROM admin WHERE email = #{email}")
    Long findIdByEmail(String email);

    void addSysUser(String loginID, String loginPwd, String userName, String userRoleName, String userDepartment, String userPhoneNum);


    int countAdmin();

    boolean existsByLoginID(String loginID);

    String getPasswordByLoginID(String loginID);

    SysUser getSysUserDOByLoginID(String loginID);


    SysUser getSysUserDOByID(Integer id);

    boolean checkPassword(Integer id, String oldPassword);

    void setPassword(Integer id, String newPassword);

    void editSystemUserByLoginID(String loginID, String userName, String userDepartment, String userPhoneNum);

    void setPasswordByLoginID(String loginID, String systemUserDefaultPassword);

    List<SysUser> getSysUserList();

    void deleteSystemUserByLoginID(String loginID);

    List<String> selectUserDepartments();
}
