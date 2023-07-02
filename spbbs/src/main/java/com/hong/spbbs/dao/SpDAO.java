package com.hong.spbbs.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.sql.DataSource;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import com.hong.spbbs.dto.SpDTO;
import com.hong.spbbs.util.Static;

public class SpDAO {
	
	DataSource dataSource;
	JdbcTemplate template = null;
	
	//constructor DB접속
	public SpDAO()
	{
		template = Static.template;
	}
	
	//글쓰기
	public void write(String uname,String upass,String title,String content)
	{
		KeyHolder kh = new GeneratedKeyHolder();
		template.update(new PreparedStatementCreator() {
			
			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException 
			{
				String sql = "insert into spboard (uname,upass,title,content) values (?,?,?,?)";
				PreparedStatement ps = con.prepareStatement(sql,PreparedStatement.RETURN_GENERATED_KEYS);
				ps.setString(1, uname);
				ps.setString(2, upass);
				ps.setString(3, title);
				ps.setString(4, content);	
				ResultSet rs = ps.getGeneratedKeys();
				if(rs.next())
				{
					System.out.println("첫 번쨰"+rs.getInt(0));
					System.out.println("두 번쨰"+rs.getInt(1));
					groupUpdate(rs.getInt(1));
				}
				return ps;
			}
		},kh);
		int idx = kh.getKey().intValue();
		groupUpdate(idx);
		
	}
	
	private void groupUpdate(int num)
	{
		System.out.println("update complete");
		String sql = "update spboard set s_group=? where num=?";
		template.update(sql,new PreparedStatementSetter()
		{
			@Override
			public void setValues(PreparedStatement ps) throws SQLException
			{
				ps.setInt(1, num);
				ps.setInt(2, num);
			}
		});
	}
	
	//답글쓰기 페이지
	public SpDTO reply(String cNum)
	{
		int iNum = Integer.parseInt(cNum);
		String sql = "select * from spboard where num="+iNum;
		return template.queryForObject(sql, new BeanPropertyRowMapper<SpDTO>(SpDTO.class));
	}
	
	//답글쓰기 등록
	public void replyok(int s_group,int s_step,int s_indent,String uname,String upass,String title,String content)
	{
		String sql = "insert into spboard(s_group,s_step,s_indent,uname,upass,title,content) values(?,?,?,?,?,?,?)";
		template.update(sql,new PreparedStatementSetter()
		{
			@Override
			public void setValues(PreparedStatement ps) throws SQLException
			{
				ps.setInt(1, s_group);
				ps.setInt(2, s_step);
				ps.setInt(3, s_indent);
				ps.setString(4, uname);
				ps.setString(5, upass);
				ps.setString(6, title);
				ps.setString(7, content);
			}
		});

	}
	
	//수정페이지로 이동
		public SpDTO modify(String cNum)
		{
			int iNum = Integer.parseInt(cNum);
			String sql = "select * from spboard where num="+iNum;
			return template.queryForObject(sql, new BeanPropertyRowMapper<SpDTO>(SpDTO.class));
		}
		
		//수정하기 등록
		public void modifyok(String uname,String upass,String title,String content,int num)
		{
			String sql = "update spboard set uname=?, upass=?, title=?, content=? where num=?";
			template.update(sql,new PreparedStatementSetter()
			{
				@Override
				public void setValues(PreparedStatement ps) throws SQLException
				{
					ps.setString(1, uname);
					ps.setString(2, upass);
					ps.setString(3, title);
					ps.setString(4, content);
					ps.setInt(5, num);
				}
			});
		}
		
		//삭제하기
			public void delete(int num)
			{
				String sql = "delete from spboard where num=?";
				template.update(sql,new PreparedStatementSetter()
				{
					@Override
					public void setValues(PreparedStatement ps) throws SQLException
					{
						ps.setInt(1, num);
					}
				});
			}
	
	//본문 보기
	public SpDTO detail(String cNum)
	{
		int iNum = Integer.parseInt(cNum);
		//조회수 증가
		hitAdd(iNum);
		String sql = "select * from spboard where num="+iNum;
		return template.queryForObject(sql, new BeanPropertyRowMapper<SpDTO>(SpDTO.class));
	}
	
	//데이터를 받아서 SpDTO에 담음
	public ArrayList<SpDTO> list(String pg)
	{
		int listCount = 10;
		int page = Integer.parseInt(pg);
		int min = (page-1)*listCount;
		String limit = " limit " + min + " , " + listCount;
		
		String sql = "select * from spboard order by s_group desc, s_step asc" + limit;
		return (ArrayList<SpDTO>)template.query(sql,new BeanPropertyRowMapper<SpDTO>(SpDTO.class));
	}
	
	public int totalRecord()
	{
		String sql = "select count(*) from spboard";
		return template.queryForObject(sql,Integer.class);
	}
	
	public int totalRecord(String where)
	{
		String sql = "select count(*) from spboard where 1 and " + where;
		return template.queryForObject(sql,Integer.class);
	}
	
	//조회수 증가
	private void hitAdd(int num)
	{
		String sql = "update spboard set hit = hit+1 where num=?";
		template.update(sql,new PreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps) throws SQLException
			{
				ps.setInt(1, num);
			}
		});
	}
	
}





