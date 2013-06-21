package com.gamemarket.web;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class WebImageCode extends HttpServlet {	
	private final int width = 35;
    private final int height = 16;

    public void doGet(HttpServletRequest request, HttpServletResponse response)	throws ServletException, IOException {
    	doPost(request, response);
    }    
    
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		final BufferedImage buffImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		final Graphics2D g = buffImg.createGraphics();
		
		final Random random = new Random();
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, width, height);
		
		final Font font = new Font("Times New Roman", Font.PLAIN, 18);
		g.setFont(font);
		g.setColor(Color.BLACK);
		g.drawRect(0, 0, width - 1, height - 1);
		
		
		//随机产生160条干扰线，使图象中的认证码不易被其它程序探测到。
		Color color = new Color(225, 245, 247);
		g.setColor(color);
		for (int i = 0;i < 100;i++) {
			int x = random.nextInt(width);
			int y = random.nextInt(height);
			int xl = random.nextInt(12);
			int yl = random.nextInt(12);
			g.drawLine(x, y, x + xl, y + yl);
		}		
		
		final StringBuilder randomCode = new StringBuilder();
		int red = 0, green = 0, blue = 0;

		for (int i = 0;i < 4;i++) {
			final String str = String.valueOf(random.nextInt(10));
			red = random.nextInt(110);
			green = random.nextInt(50);
			blue = random.nextInt(50);

			g.setColor(new Color(red, green, blue));
			g.drawString(str, 7 * i + 4, 14);
			
			randomCode.append(str);
		}
		
		final HttpSession session = request.getSession();
		session.setAttribute("admin_code", randomCode.toString());

		response.setHeader("Pragma", "no-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		response.setContentType("image/jpeg");

		final ServletOutputStream output = response.getOutputStream();
		ImageIO.write(buffImg, "jpeg", output);		
		output.close();
		g.dispose();
	}
}
