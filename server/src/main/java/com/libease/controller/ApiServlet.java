package com.libease.controller;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.libease.common.PathManager;
import com.libease.view.SampleView;

@WebServlet(urlPatterns = {"/api"})
public class ApiServlet extends HttpServlet {
   public void doGet(HttpServletRequest request, HttpServletResponse response)
         throws ServletException, IOException {
      try {
         response.setContentType("text/html");
         SampleView view = new SampleView();
         String render = view.render();

         PrintWriter out = response.getWriter();
         out.println(render);
      } catch (Exception e) {
         e.printStackTrace();
         request.getRequestDispatcher(PathManager.ERROR).forward(request, response);
      }
   }

}