package com.hong.spbbs.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import com.hong.spbbs.dto.SpDTO;

public class SpDAO {
	
	DataSource dataSource;
	
	//constructor DB접속
	public SpDAO()
	{
		try
		{
			Context context = new InitialContext();
			dataSource = (DataSource) context.lookup("java:comp/env/jdbc/spbbs");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	//글쓰기
	public void write(String uname,String upass,String title,String content)
	{
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try
		{
			conn = dataSource.getConnection();
			String sql = "insert into spboard (uname,upass,title,content) values (?,?,?,?)";
			ps = conn.prepareStatement(sql,PreparedStatement.RETURN_GENERATED_KEYS);
			ps.setString(1, uname);
			ps.setString(2, upass);
			ps.setString(3, title);
			ps.setString(4, content);			
			ps.executeUpdate();
			
			rs=ps.getGeneratedKeys();  //쿼리 실행 후 생성된 키 값 반환
			ps.clearParameters();
		
			if(rs.next())
			{
				int num = rs.getInt(1);
				try
				{
					String query = "update spboard set s_group=? where num=?";
					ps = conn.prepareStatement(query);
					ps.setInt(1, num);
					ps.setInt(2, num);
					ps.executeUpdate();
				}
				catch(Exception e) {}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				if(rs!=null)rs.close();
				if(ps!=null)ps.close();
				if(conn!=null)conn.close();
			}
			catch(Exception e) {}
		}
	}
	
	//답글쓰기 페이지
	public SpDTO reply(String cNum)
	{
		int iNum = Integer.parseInt(cNum);
		SpDTO dto = null;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			conn = dataSource.getConnection();
			String sql = "select * from spboard where num=?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, iNum);
			rs = ps.executeQuery();
			
			//dto에 담기
			if(rs.next())
			{
				dto = new SpDTO();
				int num = rs.getInt("num");
				int s_group = rs.getInt("s_group");
				int s_step = rs.getInt("s_step");
				int s_indent = rs.getInt("s_indent");
				dto.setNum(num);
				dto.setS_group(s_group);
				dto.setS_step(s_step);
				dto.setS_indent(s_indent);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				if(rs!=null)rs.close();
				if(ps!=null)ps.close();
				if(conn!=null)conn.close();
			}
			catch(Exception e) {}
		}
		
		return dto;
	}
	
	//답글쓰기 등록
	public void replyok(int s_group,int s_step,int s_indent,String uname,String upass,String title,String content)
	{
		replyUpdate(s_group, s_step);
		String sql = "insert into spboard(s_group,s_step,s_indent,uname,upass,title,content) values(?,?,?,?,?,?,?)";
		Connection conn = null;
		PreparedStatement ps = null;
		try
		{
			conn=dataSource.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, s_group);
			ps.setInt(2, s_step);
			ps.setInt(3, s_indent);
			ps.setString(4, uname);
			ps.setString(5, upass);
			ps.setString(6, title);
			ps.setString(7, content);
			ps.executeUpdate();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				if(ps!=null)ps.close();
				if(conn!=null)conn.close();
			}
			catch(Exception e) {}
		}
	}
	
	private void replyUpdate(int s_group,int s_step)
	{
		String sql = "update spboard set s_step = s_step+1 where s_group=? and s_step>=?";
		Connection conn = null;
		PreparedStatement ps = null;
		try
		{
			conn = dataSource.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, s_group);
			ps.setInt(2,s_step);
			ps.executeUpdate();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				if(ps!=null)ps.close();
				if(conn!=null)conn.close();
			}
			catch(Exception e) {}
		}
	}
	
	//수정페이지로 이동
		public SpDTO modify(String cNum)
		{
			int iNum = Integer.parseInt(cNum);
			SpDTO dto = null;
			Connection conn = null;
			PreparedStatement ps = null;
			ResultSet rs = null;
			try
			{
				conn = dataSource.getConnection();
				String sql = "select * from spboard where num=?";
				ps = conn.prepareStatement(sql);
				ps.setInt(1, iNum);
				rs = ps.executeQuery();
				
				//dto에 담기
				if(rs.next())
				{
					dto = new SpDTO();
					int num = rs.getInt("num");
					String uname = rs.getString("uname");
					String upass = rs.getString("upass");
					String title = rs.getString("title");
					String content = rs.getString("content");
					dto.setNum(num);
					dto.setUname(uname);
					dto.setUpass(upass);
					dto.setTitle(title);
					dto.setContent(content);
				}
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			finally
			{
				try
				{
					if(rs!=null)rs.close();
					if(ps!=null)ps.close();
					if(conn!=null)conn.close();
				}
				catch(Exception e) {}
			}
			
			return dto;
		}
		
		//수정하기 등록
		public void modifyok(String uname,String upass,String title,String content,int num)
		{
			String sql = "update spboard set uname=?, upass=?, title=?, content=? where num=?";
			Connection conn = null;
			PreparedStatement ps = null;
			try
			{
				conn=dataSource.getConnection();
				ps = conn.prepareStatement(sql);
				ps.setString(1, uname);
				ps.setString(2, upass);
				ps.setString(3, title);
				ps.setString(4, content);
				ps.setInt(5,num);
				ps.executeUpdate();
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			finally
			{
				try
				{
					if(ps!=null)ps.close();
					if(conn!=null)conn.close();
				}
				catch(Exception e) {}
			}
		}
		
		//삭제하기
			public void delete(int num)
			{
				String sql = "delete from spboard where num=?";
				Connection conn = null;
				PreparedStatement ps = null;
				try
				{
					conn=dataSource.getConnection();
					ps = conn.prepareStatement(sql);
					ps.setInt(1,num);
					ps.executeUpdate();
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
				finally
				{
					try
					{
						if(ps!=null)ps.close();
						if(conn!=null)conn.close();
					}
					catch(Exception e) {}
				}
			}
	
	//본문 보기
	public SpDTO detail(String cNum)
	{
		int iNum = Integer.parseInt(cNum);
		//조회수 증가
		hitAdd(iNum);
		SpDTO dto = null;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try
		{
			conn = dataSource.getConnection();
			String sql = "select * from spboard where num=?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, iNum);
			rs = ps.executeQuery();
			
			//dto 에 담기
			if(rs.next())
			{
				int num = rs.getInt("num");
				int s_group = rs.getInt("s_group");
				int s_step = rs.getInt("s_step");
				int s_indent = rs.getInt("s_indent");
				String uname = rs.getString("uname");
				String upass = rs.getString("upass");
				String title = rs.getString("title");
				String content = rs.getString("content");
				int ct = rs.getInt("ct");
				int hit = rs.getInt("hit");
				Timestamp wdate = rs.getTimestamp("wdate");
				
				dto = new SpDTO(
					num,s_group,s_step,s_indent,uname,upass,title,content,ct,hit,wdate
				);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				if(rs!=null)rs.close();
				if(ps!=null)ps.close();
				if(conn!=null)conn.close();
			}
			catch(Exception e) {}
		}
		return dto;
	}
	
	//데이터를 받아서 SpDTO에 담음
	public ArrayList<SpDTO> list()
	{
		ArrayList<SpDTO> dtos  = new ArrayList<SpDTO>();
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			conn = dataSource.getConnection();
			String sql = "select * from spboard order by s_group desc, s_step asc";
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			
			while(rs.next())
			{
				int num = rs.getInt("num");
				int s_group = rs.getInt("s_group");
				int s_step = rs.getInt("s_step");
				int s_indent = rs.getInt("s_indent");
				String uname = rs.getString("uname");
				String upass = rs.getString("upass");
				String title = rs.getString("title");
				String content = rs.getString("content");
				int ct = rs.getInt("ct");
				int hit = rs.getInt("hit");
				Timestamp wdate = rs.getTimestamp("wdate");
				
				SpDTO dto = new SpDTO(
					num,s_group,s_step,s_indent,uname,upass,title,content,ct,hit,wdate
				);
				dtos.add(dto);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				if(rs!=null)rs.close();
				if(ps!=null)ps.close();
				if(conn!=null)conn.close();
			}
			catch(Exception e){}
		}
		return dtos;
	}  //list
	
	private void hitAdd(int num)
	{
		Connection conn = null;
		PreparedStatement ps = null;
		
		try
		{
			conn = dataSource.getConnection();
			String sql = "update spboard set hit = hit+1 where num=?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, num);
			int r = ps.executeUpdate();
			System.out.println("hit 업데이트"+r);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				if(ps!=null)ps.close();
				if(conn!=null)conn.close();
			}
			catch(Exception e) {}
		}
	}
	
}





