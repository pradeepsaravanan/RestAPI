package com.rest;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.fasterxml.jackson.databind.ObjectMapper;


public class productServlet extends HttpServlet{
     
    String url ="jdbc:mysql://localhost:3306/shop";
    String uname = "root";
    String pass = "Devel0per:)";
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException 
    {     
        try{
        String query = "SELECT * FROM product";
        
        Class.forName("com.mysql.jdbc.Driver");
        Connection con = DriverManager.getConnection(url,uname,pass);
        Statement st = con.createStatement();
        
        ResultSet rs = st.executeQuery(query);
        
        ArrayList<Product> p = new ArrayList<Product>();
        
        while(rs.next()){
            Product pdt  = new Product();
            pdt.setName(rs.getString("name"));
            pdt.setStock(rs.getInt("stock"));
            pdt.setPrice(rs.getInt("price"));
            p.add(pdt);
        }
        ObjectMapper mapper = new ObjectMapper();
        String jsonString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(p);           
        
        System.out.println(jsonString);
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().write(jsonString);
        response.getWriter().flush();
        
        st.close();
        con.close();
        }
        catch(Exception e){
            System.out.println("Database prob in read: "+e);
        }
    }
       
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException 
    {   
        ObjectMapper mapper = new ObjectMapper();
        Product product = mapper.readValue(request.getReader(), Product.class);
        
        try{       
        String query = "insert into product (name,stock,price)Values('"+product.getName()+"','"+product.getStock()+"',"+product.getPrice()+")";
 
        Class.forName("com.mysql.jdbc.Driver");
        Connection con = DriverManager.getConnection(url,uname,pass);
        Statement st = con.createStatement();
                   
        st.executeUpdate(query);
        response.setStatus(HttpServletResponse.SC_OK);
        
        st.close();
        con.close();
        }
        catch(Exception e){
            System.out.println("Database prob in create: "+e);
        }
    }
}
