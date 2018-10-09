package dao;
import org.springframework.stereotype.Repository;

import model.Employee;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

public class EmployeeDAO {
	JdbcTemplate template;
	
	public void setTemplate(JdbcTemplate template) {
		this.template = template;
	} 
	public int save(Employee e) {
		String sql = "insert into employee(name,salary,address) values('"+e.getName()+"','"+e.getSalary()+"','"+e.getAddress()+"')";
		return template.update(sql);
	}
	public int update(Employee e) {
		String sql = "update employee set name='"+e.getName()+"',salary='"+e.getSalary()+"',address='"+e.getAddress()+"' where id="+e.getId();
		return template.update(sql);
	}
	public int delete(int id){  
	    String sql="delete from employee where id="+id+"";  
	    return template.update(sql);  
	}  
	public List<Employee> getEmployees(String searchText){
		String sql = "select * from employee where 1=1 ";
		if(searchText != null && !searchText.equals(""))
			sql += "and name like '%"+searchText+"%'";
		return template.query(sql, new RowMapper<Employee>(){

			public Employee mapRow(ResultSet rs, int rowNum) throws SQLException {
				Employee e = new Employee();
				e.setId(rs.getInt(1));
				e.setName(rs.getString(2));
				e.setSalary(rs.getDouble(3));
				e.setAddress(rs.getString(4));
				return e;
			}
			
		});
	}
	public Employee getEmployeeById(int id) {
		String sql = "Select * from employee where id=?";
		Employee e= (Employee) template.queryForObject(sql,new Object[]{id}, new BeanPropertyRowMapper<Employee>(Employee.class) );
		return e;
	}
}
