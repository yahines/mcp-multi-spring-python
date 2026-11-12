package nesrine_dev.springmcpserver.tools;

import jdk.jfr.Description;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

public class StockTools {
    private List<Company> companies = List.of(
            new Company("Tunisie Telecom", "Telecom", 10000, 3.5, "Tunisie"),
            new Company("Eni", "Extraction Pétrolière", 20000, 5.5, "Tunisie")
    );

    @Tool(description = "get all companies")
    public List<Company> getAllCompanies() {
        return companies;
    }
    @Tool
    public Company getCompanyByName(String name) {
        return companies.stream()
                        .filter(company -> company.name().equals(name)).findFirst()
                        .orElseThrow(()-> new RuntimeException("Company not found"));
    }
    @Tool
    public Stock getStockByCompanyName(String name) {
        return new Stock(name,LocalDate.now(), 300+Math.round(Math.random()*300));
    }

}

record Company(String name,
               String activity,
               int employeesCount,
               @Description("the turnover in milliard EURO")
               double turnover,
               String country) { }

record Stock(String companyName, LocalDate date, double stock) { }