package com.gxl.kratos.jdbc.test;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.gxl.kratos.jdbc.core.KratosJdbcTemplate;
import com.gxl.kratos.utils.sql.PropertyPlaceholderConfigurer;

/**
 * 用户信息Dao接口实现
 * 
 * @author gaoxianglong
 */
@Component("userDao")
public class UserDaoImpl implements UserDao {
	@Resource
	private KratosJdbcTemplate kJdbcTemplate;

	@Resource
	private UserMapper userMapper;

	@Resource
	private EmailIndexMapper emailIndexMapper;

	@Resource
	private PropertyPlaceholderConfigurer property;

	@Override
	public void addUser(long routeKey) {
		final String SQL = property.getSql("addUser", routeKey);
		kJdbcTemplate.update(SQL);
	}

	@Override
	public User getUserbyId(long routeKey) {
		final String SQL = "select * from userinfo where userinfo_id = " + routeKey + "";
		return kJdbcTemplate.queryForObject(SQL, userMapper);
	}

	@Override
	public void addEmailIndex(EmailIndex emailIndex) {
		final String SQL = "insert into email_index(email_hash,email,userinfo_id) values(" + emailIndex.getEmail_hash()
				+ ",?,?)";
		kJdbcTemplate.update(SQL, new Object[] { emailIndex.getEmail(), emailIndex.getUserinfo_id() });
	}

	@Override
	public EmailIndex getEmailIndex(EmailIndex emailIndex) {
		final String SQL = "select * from email_index where email_hash = " + emailIndex.getEmail_hash()
				+ " and email = ?";
		return kJdbcTemplate.queryForObject(SQL, new Object[] { emailIndex.getEmail() }, emailIndexMapper);
	}
}