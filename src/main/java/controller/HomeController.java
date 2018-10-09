package controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import dao.EmployeeDAO;
import model.Employee;

@Controller
public class HomeController {
	@Autowired
	EmployeeDAO dao;
	
	@RequestMapping(value="/")
	public ModelAndView Home(@RequestParam(value = "searchText", required=false) String searchText) {
		List<Employee> list = dao.getEmployees(searchText);
		for (Employee employee : list) {
			System.out.println(employee);
		}
		return new ModelAndView("index","list",list);
	}
	@RequestMapping("/insertForm")
	public ModelAndView insertForm(){
		return new ModelAndView("insertForm","command",new Employee());
	}
	@RequestMapping(value="/save", method = RequestMethod.POST)
	public ModelAndView save(@ModelAttribute("e") Employee e) {
		System.out.println(e);
		dao.save(e);
		return new ModelAndView("redirect:/");
	}
	
	@RequestMapping(value="/edit/{id}")
	public ModelAndView edit(@PathVariable int id) {
		Employee e = dao.getEmployeeById(id);
		return new ModelAndView("editForm","command",e);
		
	}
	
	@RequestMapping(value="/edit/update", method= RequestMethod.POST)
	public ModelAndView update(@ModelAttribute("e") Employee e) {
		dao.update(e);
		return new ModelAndView("redirect:/");
		
	}
	@RequestMapping(value="/delete/{id}")
	public ModelAndView delete(@PathVariable int id) {
		dao.delete(id);
		return new ModelAndView("redirect:/");
	}
}
