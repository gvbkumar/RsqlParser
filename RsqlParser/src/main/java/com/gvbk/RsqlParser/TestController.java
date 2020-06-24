package com.gvbk.RsqlParser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class TestController {

	@Autowired
	FilterationHelper filterationHelper;

	@PostMapping("/getSqlQuery")
	public String getSqlQuery(@RequestBody String rsqlQuery) {
		StringBuilder sqlQuery = filterationHelper.getSqlQuery(rsqlQuery);
		return "select * from emp where " + sqlQuery.toString();
	}

}
