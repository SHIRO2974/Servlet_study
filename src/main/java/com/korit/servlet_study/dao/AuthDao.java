package com.korit.servlet_study.dao;

import com.korit.servlet_study.config.DBConnectionMgr;
import com.korit.servlet_study.entity.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class AuthDao {

    private DBConnectionMgr dbConnectionMgr;
    private static AuthDao instance;

    private AuthDao() {
        dbConnectionMgr = DBConnectionMgr.getInstance();
    }

    public static AuthDao getInstance() {
        if (instance == null) {
            instance = new AuthDao();
        }
        return instance;
    }


    public User findUserByUsername(String username) {   // username 으로 사용자를 찾는다
        User foundUser = null;
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = dbConnectionMgr.getConnection();
            // SQL 쿼리 작성: username 으로 사용자 정보 조회
            String sql = """
                    select
                        user_id,
                        username,
                        password,
                        name,
                        email
                    from
                        user_tb
                    where
                        username = ?
                    """;
            ps = con.prepareStatement(sql);
            ps.setString(1, username); // SQL 의 첫 번째 파라미터에 username 값을 설정
            rs = ps.executeQuery();

            if (rs.next()) {
                foundUser = User.builder()
                        .userId(rs.getInt("user_id"))
                        .username(rs.getString("username"))
                        .password(rs.getString("password"))
                        .name(rs.getString("name"))
                        .email(rs.getString("email"))
                        .build();
            }

        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            dbConnectionMgr.freeConnection(con, ps, rs);
        }

        return foundUser;   // 찾은 유저를 반환
    }

    public User signup(User user) {
        User insertedUser = null;
        Connection con = null;
        PreparedStatement ps = null;

        try {
            con = dbConnectionMgr.getConnection();
            // SQL 쿼리 작성: 사용자 정보를 user_tb 테이블에 삽입
            String sql = "insert into user_tb values(default, ?, ?, ?, ?)";
            ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getName());
            ps.setString(4, user.getEmail());
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();

            if (rs.next()) {    // user 를 테이블에 넣는다

                insertedUser = User.builder()
                        .userId(rs.getInt(1))
                        .username(user.getUsername())
                        .password(user.getPassword())
                        .name(user.getName())
                        .email(user.getEmail())
                        .build();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            dbConnectionMgr.freeConnection(con, ps);
        }


        return insertedUser;    // 데이터베이스에 삽입된 사용자 정보를 반환
    }
}